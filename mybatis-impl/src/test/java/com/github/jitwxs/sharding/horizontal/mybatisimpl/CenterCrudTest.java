package com.github.jitwxs.sharding.horizontal.mybatisimpl;

import com.github.jitwxs.sharding.horizontal.mybatisimpl.entiy.User;
import com.github.jitwxs.sharding.horizontal.mybatisimpl.mapper.center.UserMapper;
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
    private UserMapper userMapper;

    @Test
    public void insertTest() {
        String username = RandomStringUtils.randomAscii(5);

        User user = User.builder().username(username).phone("110").build();
        userMapper.insert(user);

        User user1 = userMapper.selectByUserId(user.getId());
        Assert.assertNotNull(user1);
        Assert.assertEquals(username, user1.getUsername());
    }

    @Test
    public void updateTest() {
        String username = RandomStringUtils.randomAscii(5);

        User user = User.builder().username(username).phone("110").build();
        userMapper.insert(user);

        long l = userMapper.updatePhone(user.getId(), "119");
        Assert.assertEquals(1, l);

        User user1 = userMapper.selectByUserId(user.getId());
        Assert.assertNotNull(user1);
        Assert.assertEquals("119", user1.getPhone());
    }

    @Test
    public void deleteTest() {
        String username = RandomStringUtils.randomAscii(5);

        User user = User.builder().username(username).phone("110").build();
        userMapper.insert(user);

        List<User> users = userMapper.listAll();
        Assert.assertNotEquals(0, users.size());

        userMapper.removeAll();
        users = userMapper.listAll();
        Assert.assertEquals(0, users.size());
    }
}
