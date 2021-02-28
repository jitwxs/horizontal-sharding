package com.github.jitwxs.sharding.horizontal.common;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;

/**
 * @author jitwxs
 * @date 2021年02月28日 16:50
 */
public class BeanUtils {
    /**
     * 注册 Bean
     */
    public static void register(final BeanDefinitionRegistry registry, final String beanName, final Class<?> clazz,
                                final ConstructorArgumentValues argumentValues) {
        register(registry, beanName, null, null, clazz, argumentValues);
    }

    /**
     * 注册 Bean
     */
    public static void register(final BeanDefinitionRegistry registry, final String beanName, final String initMethodName,
                                final String destroyMethodName, final Class<?> clazz, final ConstructorArgumentValues argumentValues) {
        final GenericBeanDefinition definition = new GenericBeanDefinition();
        definition.setBeanClass(clazz);
        definition.setSynthetic(true);

        if(argumentValues != null) {
            definition.getConstructorArgumentValues().addArgumentValues(argumentValues);
        }

        if(StringUtils.isNotBlank(initMethodName)) {
            definition.setInitMethodName(initMethodName);
        }

        if(StringUtils.isNotBlank(destroyMethodName)) {
            definition.setDestroyMethodName(destroyMethodName);
        }

        registry.registerBeanDefinition(beanName, definition);
    }
}
