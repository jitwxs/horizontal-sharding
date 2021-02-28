package com.github.jitwxs.sharding.horizontal.jdbcimpl.datasource;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author jitwxs
 * @date 2021年02月28日 19:23
 */
@Getter
@AllArgsConstructor
public enum ServerTypeEnum {
    /**
     * 中央库
     */
    CENTER(1),
    /**
     * 分库
     */
    SHARDING(2);

    private final int code;
}
