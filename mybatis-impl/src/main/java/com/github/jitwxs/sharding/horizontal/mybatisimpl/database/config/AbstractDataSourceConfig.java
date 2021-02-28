package com.github.jitwxs.sharding.horizontal.mybatisimpl.database.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author jitwxs
 * @date 2021年02月28日 15:47
 */
public abstract class AbstractDataSourceConfig {
    private static final String CONFIG_PATH = "classpath:mybatis/mybatis-config.xml";

    protected DataSourceTransactionManager createTransactionManager(final DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    protected SqlSessionFactory createSqlSessionFactory(final DataSource dataSource, final String[] mapperPaths) throws Exception {
        return this.createSqlSessionFactoryBean(dataSource, mapperPaths).getObject();
    }

    protected SqlSessionFactoryBean createSqlSessionFactoryBean(final DataSource dataSource, final String[] mapperPaths) {
        final SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource);
        sessionFactoryBean.setConfigLocation(new PathMatchingResourcePatternResolver().getResource(CONFIG_PATH));
        sessionFactoryBean.setMapperLocations(this.resolveMapperPaths(mapperPaths));
        return sessionFactoryBean;
    }

    protected SqlSessionTemplate createSqlSessionTemplate(final SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    protected TransactionTemplate createTransactionTemplate(final DataSourceTransactionManager transactionManager) {
        return new TransactionTemplate(transactionManager);
    }

    private Resource[] resolveMapperPaths(final String[] mapperPaths) {
        final PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        List<Resource> resources = new ArrayList<>(mapperPaths.length);

        Arrays.stream(mapperPaths).forEach(e -> {
            try {
                resources.addAll(Arrays.asList(resolver.getResources(e)));
            } catch (IOException ignored) {}
        });

        return resources.toArray(new Resource[0]);
    }
}
