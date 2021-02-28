package com.github.jitwxs.sharding.horizontal.jdbcimpl.datasource;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author jitwxs
 * @date 2020年02月15日 13:52
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShardingContext {
    private ServerTypeEnum type;
    private Integer modulo;

    public static ShardingContext sharding(int modulo) {
        return new ShardingContext(ServerTypeEnum.SHARDING, modulo);
    }

    public static final ShardingContext CENTER = new ShardingContext(ServerTypeEnum.CENTER, null);
}
