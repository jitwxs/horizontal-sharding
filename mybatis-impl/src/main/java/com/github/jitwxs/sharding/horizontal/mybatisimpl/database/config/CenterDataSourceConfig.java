package com.github.jitwxs.sharding.horizontal.mybatisimpl.database.config;

import com.github.jitwxs.sharding.horizontal.mybatisimpl.database.DBProperties;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;

/**
 * 中央库数据库配置
 * @author jitwxs
 * @date 2021年02月28日 15:46
 */
@Slf4j
@Configuration
public class CenterDataSourceConfig extends AbstractDataSourceConfig {
    private static final String[] MAPPER_PATHS = {"classpath*:mybatis/center/*Mapper.xml"};

    @Bean("centerDbProperties")
    @ConfigurationProperties("datasource.center")
    public DBProperties centerDbProperties() {
        return new DBProperties();
    }

    @Bean("centerDataSource")
    public DataSource centerDataSource(@Qualifier("centerDbProperties") final DBProperties dbProperties) {
        return new HikariDataSource(dbProperties.convertHikariConfig());
    }

    @Bean("centerTransactionManager")
    public DataSourceTransactionManager transactionManager(@Qualifier("centerDataSource") final DataSource dataSource) {
        return super.createTransactionManager(dataSource);
    }

    @Bean("centerSqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("centerDataSource") final DataSource dataSource) throws Exception {
        return super.createSqlSessionFactory(dataSource, MAPPER_PATHS);
    }

    @Bean("centerSqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("centerSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return super.createSqlSessionTemplate(sqlSessionFactory);
    }

    @Bean("centerTransactionTemplate")
    public TransactionTemplate transactionTemplate(@Qualifier("centerTransactionManager") DataSourceTransactionManager transactionManager) {
        return super.createTransactionTemplate(transactionManager);
    }
}
