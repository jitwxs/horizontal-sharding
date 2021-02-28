package com.github.jitwxs.sharding.horizontal.jdbcimpl.datasource.config;

import com.github.jitwxs.sharding.horizontal.common.BeanUtils;
import com.github.jitwxs.sharding.horizontal.common.PropertyUtil;
import com.github.jitwxs.sharding.horizontal.jdbcimpl.datasource.DBProperties;
import com.github.jitwxs.sharding.horizontal.jdbcimpl.datasource.Server;
import com.github.jitwxs.sharding.horizontal.jdbcimpl.datasource.ServerTypeEnum;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;

import java.util.HashMap;
import java.util.Map;

/**
 * 注册 {@link javax.sql.DataSource} 和 {@link Server}
 * @author jitwxs
 * @date 2021年02月28日 17:36
 */
public class DataSourceImportBeanDefinitionRegister implements ImportBeanDefinitionRegistrar, EnvironmentAware {
    DBProperties centerConfig;
    Map<String, DBProperties> shardConfigs = new HashMap<>();

    @Override
    public void registerBeanDefinitions(AnnotationMetadata metadata, BeanDefinitionRegistry registry) {
        // 注册中央库
        register(ServerTypeEnum.CENTER, "center", centerConfig, registry);

        // 注册分库
        for(Map.Entry<String, DBProperties> entry : MapUtils.emptyIfNull(shardConfigs).entrySet()) {
            register(ServerTypeEnum.SHARDING, entry.getKey(), entry.getValue(), registry);
        }
    }

    @Override
    public void setEnvironment(Environment environment) {
        centerConfig = PropertyUtil.reslove(environment, "datasource.center", DBProperties.class);

        final Map<String, Object> shardingPropertiesMap = PropertyUtil.reslove(environment, "datasource.ds", Map.class);

        MapUtils.emptyIfNull(shardingPropertiesMap).forEach((k, v) -> shardConfigs.put(k, PropertyUtil.reslove(environment, "datasource.ds." + k, DBProperties.class)));
    }

    private void register(ServerTypeEnum serverTypeEnum, String namePrefix, DBProperties dbProperties, BeanDefinitionRegistry registry) {
        final String dataSourceBeanName = dbSourceBeanName(namePrefix), serverBeanName = dbServerBeanName(namePrefix);

        ConstructorArgumentValues argument1 = new ConstructorArgumentValues();
        argument1.addGenericArgumentValue(dbProperties.convertHikariConfig());
        BeanUtils.register(registry, dataSourceBeanName, HikariDataSource.class, argument1);

        ConstructorArgumentValues argument2 = new ConstructorArgumentValues();
        argument2.addGenericArgumentValue(serverBeanName);
        argument2.addGenericArgumentValue(serverTypeEnum);
        argument2.addGenericArgumentValue(dbProperties.listModulo());
        argument2.addGenericArgumentValue(new RuntimeBeanReference(dataSourceBeanName));
        BeanUtils.register(registry, serverBeanName, Server.class, argument2);
    }

    private static String dbSourceBeanName(String suffix) {
        return "db_" + suffix;
    }

    private static String dbServerBeanName(String suffix) {
        return "server_" + suffix;
    }
}
