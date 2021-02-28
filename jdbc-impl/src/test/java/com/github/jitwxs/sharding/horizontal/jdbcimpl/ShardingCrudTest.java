package com.github.jitwxs.sharding.horizontal.jdbcimpl;

import com.github.jitwxs.sharding.horizontal.jdbcimpl.datasource.config.ServerConfig;
import com.github.jitwxs.sharding.horizontal.jdbcimpl.dao.OrderDao;
import com.github.jitwxs.sharding.horizontal.jdbcimpl.dao.UserDao;
import com.github.jitwxs.sharding.horizontal.jdbcimpl.entiy.Order;
import com.github.jitwxs.sharding.horizontal.jdbcimpl.entiy.User;
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
    private OrderDao orderDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private ServerConfig serverConfig;

    @Test
    public void insertTest() {
        String username = RandomStringUtils.randomAscii(5);

        User user = User.builder().username(username).phone("110").build();
        long userId = userDao.insert(user);

        User user1 = userDao.selectByUserId(userId);
        Assert.assertNotNull(user1);

        double amount = RandomUtils.nextDouble(3, 6);
        Order order = Order.builder().userId(userId).amount(amount).build();
        long orderId = orderDao.insert(order);

        int modulo = serverConfig.getModulo(userId);
        Order order1 = orderDao.selectById(orderId, modulo);
        Assert.assertNotNull(order1);
        Assert.assertEquals(amount, order1.getAmount(), 0.000001);
    }


    @Test
    public void deleteTest() {
        String username = RandomStringUtils.randomAscii(5);

        User user = User.builder().username(username).phone("110").build();
        long userId = userDao.insert(user);

        User user1 = userDao.selectByUserId(userId);
        Assert.assertNotNull(user1);

        double amount = RandomUtils.nextDouble(3, 6);
        Order order = Order.builder().userId(userId).amount(amount).build();
        long orderId = orderDao.insert(order);

        int modulo = serverConfig.getModulo(userId);
        Order order1 = orderDao.selectById(orderId, modulo);
        Assert.assertNotNull(order1);
        Assert.assertEquals(amount, order1.getAmount(), 0.000001);

        orderDao.removeAll(modulo);

        order1 = orderDao.selectById(orderId, modulo);
        Assert.assertNull(order1);
    }
}
