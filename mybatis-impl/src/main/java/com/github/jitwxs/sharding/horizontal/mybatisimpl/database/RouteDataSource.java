package com.github.jitwxs.sharding.horizontal.mybatisimpl.database;

import com.google.common.collect.Maps;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.Map;

public class RouteDataSource extends AbstractRoutingDataSource {

    public static RouteDataSource newInstance(Map<Integer, DataSource> dataSourceMap) {
        Map<Object, Object> targetDataSources = Maps.newHashMapWithExpectedSize(dataSourceMap.size());
        dataSourceMap.forEach(targetDataSources::put);

        final RouteDataSource routeDataSource = new RouteDataSource();
        routeDataSource.setTargetDataSources(targetDataSources);

        return routeDataSource;
    }

    @Override
    protected DataSource determineTargetDataSource() {
        return ShardingContext.getDataSource();
    }

    @Override
    protected Object determineCurrentLookupKey() {
        return null;
    }
}