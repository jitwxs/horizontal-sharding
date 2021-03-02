package com.github.jitwxs.sharding.horizontal.mybatisimpl.database;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * 分库事务设置 datasource
 */
@Aspect
@Component
public class TransactionDataSourceAop implements Ordered {

    @Before(value = "@annotation(trans) && args(modulo,..)", argNames = "trans,modulo")
    public void before(final Transactional trans, final int modulo) {
        ShardingContext.setDataSource(modulo);
    }

    @AfterReturning(value = "@annotation(a)")
    public void afterReturning(final Transactional a) {
        ShardingContext.removeDataSource();
    }

    @Override
    public int getOrder() {
        return 1;
    }
}