package com.github.jitwxs.sharding.horizontal.mybatisimpl.database.config;

import com.github.jitwxs.sharding.horizontal.common.PropertyUtil;
import com.github.jitwxs.sharding.horizontal.mybatisimpl.database.DBProperties;
import com.github.jitwxs.sharding.horizontal.mybatisimpl.database.DataSourceInterceptor;
import com.github.jitwxs.sharding.horizontal.mybatisimpl.database.RouteDataSource;
import com.github.jitwxs.sharding.horizontal.mybatisimpl.database.ShardingContext;
import com.google.common.collect.Maps;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;
import java.util.Map;

/**
 * 分库数据库配置
 * @author jitwxs
 * @date 2021年02月28日 15:46
 */
@Slf4j
@Configuration
public class ShardingDataSourceConfig extends AbstractDataSourceConfig implements EnvironmentAware {
    private static final String[] MAPPER_PATHS = {"classpath*:mybatis/shard/*Mapper.xml"};

    private Map<Integer, DataSource> dataSourceMap;

    @Bean("shardingDataSource")
    public DataSource shardingDataSource() {
        return RouteDataSource.newInstance(dataSourceMap);
    }

    @Bean("shardingTransactionManager")
    public DataSourceTransactionManager shardingTransactionManager(@Qualifier("shardingDataSource") final DataSource dataSource) {
        return super.createTransactionManager(dataSource);
    }

    @Bean("shardingSqlSessionFactory")
    public SqlSessionFactory shardingSqlSessionFactory(@Qualifier("shardingDataSource") final DataSource dataSource) throws Exception {
        final SqlSessionFactory sqlSessionFactory = super.createSqlSessionFactory(dataSource, MAPPER_PATHS);
        sqlSessionFactory.getConfiguration().addInterceptor(new DataSourceInterceptor());
        return sqlSessionFactory;
    }

    @Bean("shardingSqlSessionTemplate")
    public SqlSessionTemplate shardingSqlSessionTemplate(@Qualifier("shardingSqlSessionFactory") final SqlSessionFactory sqlSessionFactory) {
        return super.createSqlSessionTemplate(sqlSessionFactory);
    }

    @Bean("shardingTransactionTemplate")
    public TransactionTemplate shardingTransactionTemplate(@Qualifier("shardingTransactionManager") final DataSourceTransactionManager transactionManager) {
        return super.createTransactionTemplate(transactionManager);
    }

    @Override
    @SuppressWarnings({"ConstantConditions", "unchecked"})
    public void setEnvironment(Environment environment) {
        final int moduloSize = environment.getProperty("datasource.modulo-size", Integer.class);

        final Map<String, Object> shardingPropertiesMap = PropertyUtil.reslove(environment, "datasource.ds", Map.class);

        final int shardingSize = shardingPropertiesMap.size();

        if(shardingSize <= 0) {
            return;
        }

        dataSourceMap = Maps.newHashMapWithExpectedSize(shardingSize);

        for(Map.Entry<String, Object> entry : shardingPropertiesMap.entrySet()) {
            final DBProperties properties = PropertyUtil.reslove(environment, "datasource.ds." + entry.getKey(), DBProperties.class);

            final HikariDataSource dataSource = new HikariDataSource(properties.convertHikariConfig());

            properties.listModulo().forEach(modulo -> dataSourceMap.put(modulo, dataSource));
        }

        ShardingContext.init(dataSourceMap, moduloSize, shardingSize);
    }
}
