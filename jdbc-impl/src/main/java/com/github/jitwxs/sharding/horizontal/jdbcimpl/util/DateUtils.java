package com.github.jitwxs.sharding.horizontal.jdbcimpl.util;

/**
 * 日期工具类
 * @author jitwxs
 * @date 2020年02月15日 14:00
 */
public class DateUtils {
    public static long now() {
        return System.currentTimeMillis();
    }

    public static long diff(long startMs) {
        return now() - startMs;
    }
}
