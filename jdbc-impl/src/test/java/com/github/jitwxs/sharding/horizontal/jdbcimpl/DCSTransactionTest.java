package com.github.jitwxs.sharding.horizontal.jdbcimpl;

import com.github.jitwxs.sharding.horizontal.jdbcimpl.config.DataSourceConfig;
import com.github.jitwxs.sharding.horizontal.jdbcimpl.dao.OrderDao;
import com.github.jitwxs.sharding.horizontal.jdbcimpl.dao.UserDao;
import com.github.jitwxs.sharding.horizontal.jdbcimpl.datasource.Db;
import com.github.jitwxs.sharding.horizontal.jdbcimpl.datasource.ShardingContext;
import com.github.jitwxs.sharding.horizontal.jdbcimpl.entiy.Order;
import com.github.jitwxs.sharding.horizontal.jdbcimpl.entiy.User;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.SQLException;

/**
 * 跨库事务测试
 * @author jitwxs
 * @date 2020年02月16日 17:43
 */
public class DCSTransactionTest extends BaseTest {
    @Autowired
    private UserDao userDao;
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private DataSourceConfig dataSourceConfig;

    /**
     * 跨库事务，不支持
     * @author jitwxs
     * @date 2020/2/16 17:44
     */
    @Test
    public void testDCSTransaction() {
        User user = User.builder().username(RandomStringUtils.randomAscii(4)).phone(RandomStringUtils.randomNumeric(5)).build();
        long userId = userDao.insert(user);
        int modulo = dataSourceConfig.getModulo(userId);

        Throwable ex = null;

        ShardingContext context = ShardingContext.master(modulo);
        try {
            Db.beginTransaction(context);

            double amount = RandomUtils.nextDouble(3, 6);
            Order order = Order.builder().userId(userId).amount(amount).build();
            orderDao.insertTransaction(order);

            // will throw exception
            userDao.updatePhoneTransaction(userId, RandomStringUtils.randomNumeric(5));

            Db.commitTransaction(context);
        } catch (SQLException e) {
            ex = e;
            Db.rollbackTransaction(context);
        }

        Assert.assertNotNull(ex);
        Assert.assertTrue(ex.getMessage().contains("connection is null or closed"));
    }
}
