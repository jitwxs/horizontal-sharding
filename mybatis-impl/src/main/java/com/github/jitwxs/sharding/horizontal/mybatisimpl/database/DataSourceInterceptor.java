package com.github.jitwxs.sharding.horizontal.mybatisimpl.database;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

@Intercepts({
        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class}),
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class}),
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
        @Signature(type = Executor.class, method = "queryCursor", args = {MappedStatement.class, Object.class, RowBounds.class}),
})
@Slf4j
public class DataSourceInterceptor implements Interceptor {

    private static final String USER_ID = "userId", MODULO = "modulo";

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public Object intercept(final Invocation invocation) throws Throwable {

        final Object object = invocation.getArgs()[1];
        if (!(object instanceof MapperMethod.ParamMap)) {
            return invocation.proceed();
        }

        final MapperMethod.ParamMap paramMap = (MapperMethod.ParamMap) object;

        if(!paramMap.containsKey(USER_ID) && !paramMap.containsKey(MODULO)) {
            return invocation.proceed();
        }

        int modulo;
        if(!paramMap.containsKey(MODULO)) {
            modulo =  ShardingContext.getModulo((long) paramMap.get(USER_ID));
            // 增加标识
            paramMap.put("modulo", modulo);
        } else {
            modulo = (int) paramMap.get(MODULO);
        }

        boolean set = false;
        try {
            set = ShardingContext.setDataSource(modulo);
            return invocation.proceed();
        } finally {
            if (set) {
                ShardingContext.removeDataSource();
            }

        }
    }
}