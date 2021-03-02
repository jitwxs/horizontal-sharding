package com.github.jitwxs.sharding.horizontal.mybatisimpl;

import com.github.jitwxs.sharding.horizontal.common.DateUtils;
import com.github.jitwxs.sharding.horizontal.mybatisimpl.entiy.User;
import com.github.jitwxs.sharding.horizontal.mybatisimpl.mapper.center.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 中央库事务测试
 * @author jitwxs
 * @date 2020年02月15日 19:26
 */
@Slf4j
public class CenterTransactionTest extends BaseTest {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private CenterTransactionLogic centerTransactionLogic;

    @Before
    public void cleanUser() {
        userMapper.removeAll();
    }

    /**
     * 事务提交
     */
    @Test
    public void testCommit() {
        long id1 = DateUtils.now(), id2 = id1 + RandomUtils.nextInt();

        User user1 = User.builder().id(id1).username(RandomStringUtils.randomAscii(4)).phone(RandomStringUtils.randomNumeric(5)).build();
        User user2 = User.builder().id(id2).username(RandomStringUtils.randomAscii(4)).phone(RandomStringUtils.randomNumeric(5)).build();

        centerTransactionLogic.testCommitLogic(user1, user2);

        user1 = userMapper.selectByUserId(id1);
        Assert.assertNotNull(user1);

        user2 = userMapper.selectByUserId(id2);
        Assert.assertNotNull(user2);
    }

    /**
     * 事务回滚
     */
    @Test
    public void testRollback() {
        long id1 = DateUtils.now(), id2 = id1 + RandomUtils.nextInt();

        User user1 = User.builder().id(id1).username(RandomStringUtils.randomAscii(4)).phone(RandomStringUtils.randomNumeric(5)).build();
        User user2 = User.builder().id(id2).username(RandomStringUtils.randomAscii(4)).phone(RandomStringUtils.randomNumeric(5)).build();

        try {
            centerTransactionLogic.testRollbackLogic(user1, user2);
        } catch (Exception e) {
        }

        user1 = userMapper.selectByUserId(id1);
        Assert.assertNull(user1);

        user2 = userMapper.selectByUserId(id2);
        Assert.assertNull(user2);
    }
}
