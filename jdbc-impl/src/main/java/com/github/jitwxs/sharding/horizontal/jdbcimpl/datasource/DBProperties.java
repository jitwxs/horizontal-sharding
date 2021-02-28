package com.github.jitwxs.sharding.horizontal.jdbcimpl.datasource;

import com.github.jitwxs.sharding.horizontal.common.SymbolConstant;
import com.zaxxer.hikari.HikariConfig;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DBProperties {
    /**
     * 分片配置
     */
    private String modulo;
    /**
     * 数据库驱动名
     */
    private String driverClassName;
    /**
     * 数据库链接
     */
    private String jdbcUrl;

    private String username;

    private String password;
    /**
     * 数据库连接池名
     */
    private String poolName;

    private Integer maximumPoolSize;

    private Integer minimumIdle;

    public HikariConfig convertHikariConfig() {
        HikariConfig config = new HikariConfig();
        config.setDriverClassName(driverClassName);
        config.setJdbcUrl(jdbcUrl);
        config.setUsername(username);
        config.setPassword(password);
        config.setPoolName(poolName);
        config.setMaximumPoolSize(maximumPoolSize);
        config.setMinimumIdle(minimumIdle);

        return config;
    }

    public List<Integer> listModulo() {
        if(StringUtils.isBlank(modulo)) {
            return Collections.singletonList(null);
        }

        String[] moduloArray = modulo.trim().split(SymbolConstant.COMMA);
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

        return moduloList;
    }
}