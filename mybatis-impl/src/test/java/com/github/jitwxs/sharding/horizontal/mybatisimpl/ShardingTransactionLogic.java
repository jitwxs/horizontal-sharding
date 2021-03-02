package com.github.jitwxs.sharding.horizontal.mybatisimpl;

import com.github.jitwxs.sharding.horizontal.mybatisimpl.entiy.Order;
import com.github.jitwxs.sharding.horizontal.mybatisimpl.entiy.OrderDesc;
import com.github.jitwxs.sharding.horizontal.mybatisimpl.mapper.shard.OrderDescMapper;
import com.github.jitwxs.sharding.horizontal.mybatisimpl.mapper.shard.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ShardingTransactionLogic {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderDescMapper orderDescMapper;

    @Transactional(transactionManager = "shardingTransactionManager", rollbackFor = Exception.class)
    public void testCommitLogic(final int modulo, final Order order, final OrderDesc orderDesc) {
        orderMapper.insert(order, modulo);
        orderDescMapper.insert(modulo, orderDesc);
    }

    @Transactional(transactionManager = "shardingTransactionManager", rollbackFor = Exception.class)
    public void testRollbackLogic(final int modulo, final Order order, final OrderDesc orderDesc) {
        orderMapper.insert(order, modulo);
        int i = 1 / 0;
        orderDescMapper.insert(modulo, orderDesc);
    }
}
