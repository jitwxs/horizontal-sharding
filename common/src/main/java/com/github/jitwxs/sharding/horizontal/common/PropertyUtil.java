package com.github.jitwxs.sharding.horizontal.common;

import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertyResolver;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Map;

public class PropertyUtil {
    public static int springBootVersion = 1;

    static {
        try {
            Class.forName("org.springframework.boot.bind.RelaxedPropertyResolver");
        } catch (final ClassNotFoundException e) {
            PropertyUtil.springBootVersion = 2;
        }
    }

    /**
     * Spring Boot 1.x is compatible with Spring Boot 2.x by Using Java Reflect.
     *
     * @param environment : the environment context
     * @param prefix      : the prefix part of property key
     * @param targetClass : the target class orderType of result
     * @param <T>         : refer to @param targetClass
     * @return T
     */
    public static <T> T reslove(final Environment environment, final String prefix, final Class<T> targetClass) {
        switch (PropertyUtil.springBootVersion) {
            case 1:
                return (T) PropertyUtil.v1(environment, prefix);
            default:
                return PropertyUtil.v2(environment, prefix, targetClass);
        }
    }

    private static Object v1(final Environment environment, final String prefix) {
        try {
            final Class<?> resolverClass = Class.forName("org.springframework.boot.bind.RelaxedPropertyResolver");
            final Constructor<?> resolverConstructor = resolverClass.getDeclaredConstructor(PropertyResolver.class);
            final Method getSubPropertiesMethod = resolverClass.getDeclaredMethod("getSubProperties", String.class);
            final Object resolverObject = resolverConstructor.newInstance(environment);
            final String prefixParam = prefix.endsWith(".") ? prefix : prefix + ".";
            return getSubPropertiesMethod.invoke(resolverObject, prefixParam);
        } catch (final ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException
                | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            throw new RuntimeException(ex.getMessage(), ex);
        }
    }

    private static <T> T v2(final Environment environment, final String prefix, final Class<T> targetClass) {
        try {
            final String prefixParam = prefix.endsWith(".") ? prefix.substring(0, prefix.length() - 1) : prefix;
            final Binder binder = Binder.get(environment);
            final BindResult<T> result = binder.bind(prefixParam, targetClass);

            return result.orElseGet(() -> targetClass.isAssignableFrom(Map.class) ? (T) Collections.emptyMap() : null);
        } catch (final SecurityException | IllegalArgumentException ex) {
            throw new RuntimeException(ex.getMessage(), ex);
        }
    }
}