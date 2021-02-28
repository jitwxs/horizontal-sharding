package com.github.jitwxs.sharding.horizontal.mybatisimpl;

import com.github.jitwxs.sharding.horizontal.mybatisimpl.database.ShardingContext;
import com.github.jitwxs.sharding.horizontal.mybatisimpl.entiy.Order;
import com.github.jitwxs.sharding.horizontal.mybatisimpl.entiy.User;
import com.github.jitwxs.sharding.horizontal.mybatisimpl.mapper.center.UserMapper;
import com.github.jitwxs.sharding.horizontal.mybatisimpl.mapper.shard.OrderMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 分库 CRUD 测试
 * @author jitwxs
 * @date 2021年02月28日 15:15
 */
@Slf4j
public class ShardingCrudTest extends BaseTest {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private UserMapper userMapper;

    @Test
    public void insertTest() {
        String username = RandomStringUtils.randomAscii(5);

        User user = User.builder().username(username).phone("110").build();
        userMapper.insert(user);

        final long userId = user.getId();
        final int modulo = ShardingContext.getModulo(userId);

        User user1 = userMapper.selectByUserId(userId);
        Assert.assertNotNull(user1);

        double amount = RandomUtils.nextDouble(3, 6);
        Order order = Order.builder().userId(userId).amount(amount).build();
        orderMapper.insert(order, modulo);

        final long orderId = order.getId();

        Order order1 = orderMapper.selectById(orderId, modulo);
        Assert.assertNotNull(order1);
        Assert.assertEquals(amount, order1.getAmount(), 0.000001);
    }

    @Test
    public void deleteTest() {
        String username = RandomStringUtils.randomAscii(5);

        User user = User.builder().username(username).phone("110").build();
        userMapper.insert(user);

        final long userId = user.getId();
        final int modulo = ShardingContext.getModulo(userId);

        User user1 = userMapper.selectByUserId(userId);
        Assert.assertNotNull(user1);

        double amount = RandomUtils.nextDouble(3, 6);
        Order order = Order.builder().userId(userId).amount(amount).build();
        orderMapper.insert(order, modulo);

        final long orderId = order.getId();

        Order order1 = orderMapper.selectById(orderId, modulo);
        Assert.assertNotNull(order1);
        Assert.assertEquals(amount, order1.getAmount(), 0.000001);

        orderMapper.removeAll(modulo);

        order1 = orderMapper.selectById(orderId, modulo);
        Assert.assertNull(order1);
    }
}
