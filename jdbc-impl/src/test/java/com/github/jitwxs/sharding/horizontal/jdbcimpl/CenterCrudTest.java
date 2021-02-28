package com.github.jitwxs.sharding.horizontal.jdbcimpl;

import com.github.jitwxs.sharding.horizontal.jdbcimpl.dao.UserDao;
import com.github.jitwxs.sharding.horizontal.jdbcimpl.entiy.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 中央库 CRUD 测试
 * @author jitwxs
 * @date 2021年02月28日 15:15
 */
@Slf4j
public class CenterCrudTest extends BaseTest {
    @Autowired
    private UserDao userDao;

    @Test
    public void insertTest() {
        String username = RandomStringUtils.randomAscii(5);

        User user = User.builder().username(username).phone("110").build();
        long id = userDao.insert(user);

        User user1 = userDao.selectByUserId(id);
        Assert.assertNotNull(user1);
        Assert.assertEquals(username, user1.getUsername());
    }

    @Test
    public void updateTest() {
        String username = RandomStringUtils.randomAscii(5);

        User user = User.builder().username(username).phone("110").build();
        long userId = userDao.insert(user);

        long l = userDao.updatePhone(userId, "119");
        Assert.assertEquals(1, l);

        User user1 = userDao.selectByUserId(userId);
        Assert.assertNotNull(user1);
        Assert.assertEquals("119", user1.getPhone());
    }

    @Test
    public void deleteTest() {
        String username = RandomStringUtils.randomAscii(5);

        User user = User.builder().username(username).phone("110").build();
        long userId = userDao.insert(user);

        List<User> users = userDao.listAll();
        Assert.assertNotEquals(0, users.size());

        userDao.removeAll();
        users = userDao.listAll();
        Assert.assertEquals(0, users.size());
    }
}
