package com.github.jitwxs.sharding.horizontal.mybatisimpl;

import com.github.jitwxs.sharding.horizontal.common.DateUtils;
import com.github.jitwxs.sharding.horizontal.mybatisimpl.database.ShardingContext;
import com.github.jitwxs.sharding.horizontal.mybatisimpl.entiy.Order;
import com.github.jitwxs.sharding.horizontal.mybatisimpl.entiy.OrderDesc;
import com.github.jitwxs.sharding.horizontal.mybatisimpl.entiy.User;
import com.github.jitwxs.sharding.horizontal.mybatisimpl.mapper.center.UserMapper;
import com.github.jitwxs.sharding.horizontal.mybatisimpl.mapper.shard.OrderDescMapper;
import com.github.jitwxs.sharding.horizontal.mybatisimpl.mapper.shard.OrderMapper;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 分库事务测试
 * @author jitwxs
 * @date 2020年02月15日 19:26
 */
@Service
public class ShardingTransactionTest extends BaseTest {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderDescMapper orderDescMapper;

    @Autowired
    private ShardingTransactionLogic shardingTransactionLogic;

    public long userId;

    public int modulo;

    @Before
    public void createUser() {
        User user = User.builder().username(RandomStringUtils.randomAscii(4)).phone(RandomStringUtils.randomNumeric(5)).build();
        userMapper.insert(user);

        userId = user.getId();
        modulo = ShardingContext.getModulo(userId);

        orderMapper.removeAll(modulo);
        orderDescMapper.removeAll(modulo);
    }

    /**
     * 事务提交
     */
    @Test
    public void testCommit() {
        final double amount = RandomUtils.nextDouble(3, 6);
        final long orderId = DateUtils.now(), orderDescId =  DateUtils.now();

        Order order = Order.builder().id(orderId).userId(userId).amount(amount).build();

        OrderDesc orderDesc = OrderDesc.builder().id(orderDescId).orderId(orderId).userId(userId).description(RandomStringUtils.randomAscii(8)).build();

        shardingTransactionLogic.testCommitLogic(modulo, order, orderDesc);

        order = orderMapper.selectById(orderId, modulo);
        Assert.assertNotNull(order);
        Assert.assertEquals(userId, order.getUserId());

        orderDesc = orderDescMapper.selectById(orderDescId, modulo);
        Assert.assertNotNull(orderDesc);
        Assert.assertEquals(userId, orderDesc.getUserId());
    }

    /**
     * 事务回滚
     */
    @Test
    public void testRollback() {
        final int modulo = ShardingContext.getModulo(userId);

        orderMapper.removeAll(modulo);
        orderDescMapper.removeAll(modulo);

        final double amount = RandomUtils.nextDouble(3, 6);
        final long orderId = DateUtils.now(), orderDescId =  DateUtils.now();

        Order order = Order.builder().id(orderId).userId(userId).amount(amount).build();

        OrderDesc orderDesc = OrderDesc.builder().id(orderDescId).orderId(orderId).userId(userId).description(RandomStringUtils.randomAscii(8)).build();

        try {
            shardingTransactionLogic.testRollbackLogic(modulo, order, orderDesc);
        } catch (Exception e) {
        }

        order = orderMapper.selectById(orderId, modulo);
        Assert.assertNull(order);

        orderDesc = orderDescMapper.selectById(orderDescId, modulo);
        Assert.assertNull(orderDesc);
    }
}
