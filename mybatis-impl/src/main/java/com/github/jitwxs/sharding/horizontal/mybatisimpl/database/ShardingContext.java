package com.github.jitwxs.sharding.horizontal.mybatisimpl.database;

import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.util.Map;

@Slf4j
public class ShardingContext {
    private static final ThreadLocal<DataSource> THREAD_LOCAL = new ThreadLocal<>();

    /**
     * <modulo, data_source>
     */
    private static Map<Integer, DataSource> dataSourceMap;

    /**
     * 累计分片数
     */
    private static int moduloSize;

    /**
     * 累计分库数
     */
    private static int shardingSize;

    public static void init(final Map<Integer, DataSource> dataSourceMap, final int moduloSize, final int shardingSize) {
        ShardingContext.dataSourceMap = dataSourceMap;
        ShardingContext.moduloSize = moduloSize;
        ShardingContext.shardingSize = shardingSize;
    }

    public static DataSource getDataSource() {
        return THREAD_LOCAL.get();
    }

    public static boolean setDataSource(final int modulo) {
        if(getDataSource() != null) {
            return false;
        }

        THREAD_LOCAL.set(dataSourceMap.get(modulo));

        return true;
    }

    public static void removeDataSource() {
        THREAD_LOCAL.remove();
    }

    public static int getModulo(final long userId) {
        return (int) userId % moduloSize;
    }
}