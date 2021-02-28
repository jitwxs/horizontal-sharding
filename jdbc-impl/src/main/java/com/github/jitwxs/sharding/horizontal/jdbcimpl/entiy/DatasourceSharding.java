package com.github.jitwxs.sharding.horizontal.jdbcimpl.entiy;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author jitwxs
 * @date 2020年02月15日 16:42
 */
@Data
public class DatasourceSharding {
    private Integer id;
    /**
     * 数据库类型
     */
    private Integer serverType;
    /**
     * 需和{@link com.github.jitwxs.sharding.horizontal.jdbcimpl.config.DataSourceConfig}中Bean名相同
     */
    private String serverName;
    /**
     * 模配置
     */
    private String modulo;
    /**
     * 是否启用，1：启用；0：禁用
     */
    private boolean enable;

    private LocalDateTime createdDate;
}
