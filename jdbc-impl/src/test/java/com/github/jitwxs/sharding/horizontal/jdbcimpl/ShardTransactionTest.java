package com.github.jitwxs.sharding.horizontal.jdbcimpl;

import com.github.jitwxs.sharding.horizontal.jdbcimpl.config.DataSourceConfig;
import com.github.jitwxs.sharding.horizontal.jdbcimpl.dao.OrderDao;
import com.github.jitwxs.sharding.horizontal.jdbcimpl.dao.OrderDescDao;
import com.github.jitwxs.sharding.horizontal.jdbcimpl.dao.UserDao;
import com.github.jitwxs.sharding.horizontal.jdbcimpl.datasource.Db;
import com.github.jitwxs.sharding.horizontal.jdbcimpl.datasource.ShardingContext;
import com.github.jitwxs.sharding.horizontal.jdbcimpl.entiy.Order;
import com.github.jitwxs.sharding.horizontal.jdbcimpl.entiy.OrderDesc;
import com.github.jitwxs.sharding.horizontal.jdbcimpl.entiy.User;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.SQLException;

/**
 * 分库事务测试
 * @author jitwxs
 * @date 2020年02月15日 19:26
 */
public class ShardTransactionTest extends com.github.jitwxs.sharding.horizontal.jdbcimpl.BaseTest {
    @Autowired
    private UserDao userDao;
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private OrderDescDao orderDescDao;
    @Autowired
    private DataSourceConfig dataSourceConfig;

    /**
     * 事务提交
     * @author jitwxs
     * @date 2020/2/16 17:42
     */
    @Test
    public void testCommit() {
        User user = User.builder().username(RandomStringUtils.randomAscii(4)).phone(RandomStringUtils.randomNumeric(5)).build();
        long userId = userDao.insert(user);
        int modulo = dataSourceConfig.getModulo(userId);
        orderDao.removeAll(modulo);
        orderDescDao.removeAll(modulo);

        long orderId = 0L, orderDescId = 0L;
        ShardingContext context = ShardingContext.master(modulo);
        try {
            Db.beginTransaction(context);

            double amount = RandomUtils.nextDouble(3, 6);
            Order order = Order.builder().userId(userId).amount(amount).build();
            orderId = orderDao.insertTransaction(order);

            OrderDesc orderDesc = OrderDesc.builder().orderId(orderId).userId(userId).description(RandomStringUtils.randomAscii(8)).build();
            orderDescId = orderDescDao.insertTransaction(orderDesc);

            Db.commitTransaction(context);
        } catch (SQLException e) {
            e.printStackTrace();
            Db.rollbackTransaction(context);
        }


        Order order = orderDao.selectById(orderId, modulo);
        Assert.assertNotNull(order);
        Assert.assertEquals(userId, order.getUserId());

        OrderDesc orderDesc = orderDescDao.selectById(orderDescId, modulo);
        Assert.assertNotNull(orderDesc);
        Assert.assertEquals(userId, orderDesc.getUserId());
    }

    /**
     * 事务回滚
     * @author jitwxs
     * @date 2020/2/16 17:42
     */
    @Test
    public void testRollback() {
        User user = User.builder().username(RandomStringUtils.randomAscii(4)).phone(RandomStringUtils.randomNumeric(5)).build();
        long userId = userDao.insert(user);
        int modulo = dataSourceConfig.getModulo(userId);
        orderDao.removeAll(modulo);
        orderDescDao.removeAll(modulo);

        long orderId = 0L, orderDescId = 0L;
        ShardingContext context = ShardingContext.master(modulo);
        try {
            Db.beginTransaction(context);

            double amount = RandomUtils.nextDouble(3, 6);
            Order order = Order.builder().userId(userId).amount(amount).build();
            orderId = orderDao.insertTransaction(order);

            OrderDesc orderDesc = OrderDesc.builder().orderId(orderId).userId(userId).description(RandomStringUtils.randomAscii(8)).build();
            orderDescId = orderDescDao.insertTransaction(orderDesc);

            Db.rollbackTransaction(context);
        } catch (SQLException e) {
            e.printStackTrace();
            Db.rollbackTransaction(context);
        }

        Order order = orderDao.selectById(orderId, modulo);
        Assert.assertNull(order);

        OrderDesc orderDesc = orderDescDao.selectById(orderDescId, modulo);
        Assert.assertNull(orderDesc);
    }
}
