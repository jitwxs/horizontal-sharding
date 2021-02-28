package com.github.jitwxs.sharding.horizontal.jdbcimpl;

import com.github.jitwxs.sharding.horizontal.jdbcimpl.dao.UserDao;
import com.github.jitwxs.sharding.horizontal.jdbcimpl.datasource.Db;
import com.github.jitwxs.sharding.horizontal.jdbcimpl.datasource.ShardingContext;
import com.github.jitwxs.sharding.horizontal.jdbcimpl.entiy.User;
import com.github.jitwxs.sharding.horizontal.jdbcimpl.util.DateUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.SQLException;

/**
 * 中央库事务测试
 * @author jitwxs
 * @date 2020年02月15日 19:26
 */
public class CenterTransactionTest extends BaseTest {
    @Autowired
    private UserDao userDao;

    /**
     * 事务提交
     * @author jitwxs
     * @date 2020/2/16 17:42
     */
    @Test
    public void testCommit() {
        long id1 = DateUtils.now(), id2 = id1 + RandomUtils.nextInt();

        User user1 = User.builder().id(id1).username(RandomStringUtils.randomAscii(4)).phone(RandomStringUtils.randomNumeric(5)).build();
        User user2 = User.builder().id(id2).username(RandomStringUtils.randomAscii(4)).phone(RandomStringUtils.randomNumeric(5)).build();

        ShardingContext context = ShardingContext.CENTER;
        try {
            Db.beginTransaction(context);

            userDao.insertTransaction(user1);
            userDao.insertTransaction(user2);

            Db.commitTransaction(context);
        } catch (SQLException e) {
            e.printStackTrace();
            Db.rollbackTransaction(context);
        }

        user1 = userDao.selectByUserId(id1);
        user2 = userDao.selectByUserId(id2);

        Assert.assertNotNull(user1);
        Assert.assertNotNull(user2);
    }

    /**
     * 事务回滚
     * @author jitwxs
     * @date 2020/2/16 17:42
     */
    @Test
    public void testRollback() {
        long id1 = DateUtils.now(), id2 = id1 + RandomUtils.nextInt();

        User user1 = User.builder().id(id1).username(RandomStringUtils.randomAscii(4)).phone(RandomStringUtils.randomNumeric(5)).build();
        User user2 = User.builder().id(id2).username(RandomStringUtils.randomAscii(4)).phone(RandomStringUtils.randomNumeric(5)).build();

        ShardingContext context = ShardingContext.CENTER;
        try {
            Db.beginTransaction(context);

            userDao.insertTransaction(user1);

            int i = 1 / 0;

            userDao.insertTransaction(user2);

            Db.commitTransaction(context);
        } catch (Exception e) {
            Db.rollbackTransaction(context);
        }

        user1 = userDao.selectByUserId(id1);
        user2 = userDao.selectByUserId(id2);

        Assert.assertNull(user1);
        Assert.assertNull(user2);
    }
}
