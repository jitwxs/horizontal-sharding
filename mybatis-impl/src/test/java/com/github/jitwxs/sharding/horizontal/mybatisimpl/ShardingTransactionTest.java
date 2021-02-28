package com.github.jitwxs.sharding.horizontal.mybatisimpl;

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
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * 分库事务测试
 * @author jitwxs
 * @date 2020年02月15日 19:26
 */
public class ShardingTransactionTest extends BaseTest {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderDescMapper orderDescMapper;

    /**
     * 事务提交
     * @author jitwxs
     * @date 2020/2/16 17:42
     */
    @Test
    @Transactional(rollbackFor = Exception.class)
    public void testCommit() {
        User user = User.builder().username(RandomStringUtils.randomAscii(4)).phone(RandomStringUtils.randomNumeric(5)).build();
        userMapper.insert(user);

        final long userId = user.getId();
        final int modulo = ShardingContext.getModulo(userId);

        orderMapper.removeAll(modulo);
        orderDescMapper.removeAll(modulo);

        double amount = RandomUtils.nextDouble(3, 6);
        Order order = Order.builder().userId(userId).amount(amount).build();
        orderMapper.insert(order, modulo);

        final long orderId = order.getId();

        OrderDesc orderDesc = OrderDesc.builder().orderId(orderId).userId(userId).description(RandomStringUtils.randomAscii(8)).build();
        orderDescMapper.insert(orderDesc);

        final long orderDescId = orderDesc.getId();

        order = orderMapper.selectById(orderId, modulo);
        Assert.assertNotNull(order);
        Assert.assertEquals(userId, order.getUserId());

        orderDesc = orderDescMapper.selectById(orderDescId, modulo);
        Assert.assertNotNull(orderDesc);
        Assert.assertEquals(userId, orderDesc.getUserId());
    }

    /**
     * 事务回滚
     * @author jitwxs
     * @date 2020/2/16 17:42
     */
    @Test
    @Transactional(rollbackFor = Exception.class)
    public void testRollback() {
        User user = User.builder().username(RandomStringUtils.randomAscii(4)).phone(RandomStringUtils.randomNumeric(5)).build();
        userMapper.insert(user);

        final long userId = user.getId();
        final int modulo = ShardingContext.getModulo(userId);

        orderMapper.removeAll(modulo);
        orderDescMapper.removeAll(modulo);

        double amount = RandomUtils.nextDouble(3, 6);
        Order order = Order.builder().userId(userId).amount(amount).build();
        orderMapper.insert(order, modulo);

        final long orderId = order.getId();

        OrderDesc orderDesc = OrderDesc.builder().orderId(orderId).userId(userId).description(RandomStringUtils.randomAscii(8)).build();
        orderDescMapper.insert(orderDesc);

        final long orderDescId = orderDesc.getId();

        order = orderMapper.selectById(orderId, modulo);
        Assert.assertNull(order);

        orderDesc = orderDescMapper.selectById(orderDescId, modulo);
        Assert.assertNull(orderDesc);
    }
}