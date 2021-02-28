package com.github.jitwxs.sharding.horizontal.jdbcimpl.datasource;

import com.github.jitwxs.sharding.horizontal.jdbcimpl.constant.SymbolConstant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.*;
import java.util.stream.IntStream;

/**
 * @author jitwxs
 * @date 2020年02月15日 13:34
 */
@Slf4j
@Getter
@Setter
public class Server {
    private Type type;
    private Integer modulo;
    private String name;
    private DataSource dataSource;

    private final ThreadLocal<Connection> connThreadLocal = new ThreadLocal<>();
    private static final Map<String, Server> INSTANCE_MAP = new HashMap<>();

    private Server(Type type, Integer modulo, String name, DataSource dataSource) {
        this.type = type;
        this.name = name;
        this.dataSource = dataSource;
        if(isShardingAware(type)) {
            this.modulo = modulo;
        }
    }

    /**
     *
     * @param moduloConfigs 形如 1,2,3，或者 1-3
     */
    public static void addInstance(Type type, String moduloConfigs, String name, DataSource dataSource) {
        if(type.needModulo) {
            String[] moduloArray = moduloConfigs.trim().split(SymbolConstant.COMMA);
            if(moduloArray.length == 0) {
                throw new IllegalArgumentException("Server modulo must config one");
            }

            final List<Integer> moduloList = new ArrayList<>();

            for (String moduloStr : moduloArray) {
                if(moduloStr.contains(SymbolConstant.LINE)) {
                    String[] splitArray = moduloStr.trim().split(SymbolConstant.LINE);
                    IntStream.range(Integer.parseInt(splitArray[0]), Integer.parseInt(splitArray[1]) + 1).boxed().forEach(moduloList::add);
                } else {
                    moduloList.add(Integer.parseInt(moduloStr));
                }
            }

            moduloList.forEach(modulo -> {
                String key = buildInstanceMapKey(type, modulo);
                INSTANCE_MAP.put(key, new Server(type, modulo, name, dataSource));
            });
        } else {
            String key = buildInstanceMapKey(type, null);
            INSTANCE_MAP.put(key, new Server(type, null, name, dataSource));
        }

        log.info("Db Sharding: {} --> {}", moduloConfigs, ((com.zaxxer.hikari.HikariDataSource)dataSource).getJdbcUrl());
    }

    public static Server getInstance(Type type, Integer modulo) {
        return INSTANCE_MAP.get(buildInstanceMapKey(type, modulo));
    }

    public static String getServerName(Type type, Integer modulo) {
        Server server = INSTANCE_MAP.get(buildInstanceMapKey(type, modulo));
        return Objects.nonNull(server) ? server.getName() : StringUtils.EMPTY;
    }

    private static String buildInstanceMapKey(Type type, Integer modulo) {
        String key = type.name();
        if(isShardingAware(type)) {
            key = key + SymbolConstant.UNDERLINE + modulo;
        }
        return key;
    }

    /**
     * 是否是需要分库的类型
     */
    private static boolean isShardingAware(Type type) {
        return type == Type.MASTER || type == Type.SLAVE;
    }

    @Getter
    @AllArgsConstructor
    public enum Type {
        /**
         * 中央库
         */
        CENTER(1, false),
        /**
         * 主库
         */
        MASTER(2, true),
        /**
         * 从库
         */
        SLAVE(3, true);

        private final int code;

        /**
         * 是否需要分片
         */
        private final boolean needModulo;

        public static Type fetch(int code) {
            return Arrays.stream(Type.values()).filter(e -> e.getCode() == code).findFirst().orElse(null);
        }
    }
}
