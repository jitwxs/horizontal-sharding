package com.github.jitwxs.sharding.horizontal.mybatisimpl;

import com.github.jitwxs.sharding.horizontal.common.DateUtils;
import com.github.jitwxs.sharding.horizontal.mybatisimpl.entiy.User;
import com.github.jitwxs.sharding.horizontal.mybatisimpl.mapper.center.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

/**
 * 中央库事务测试
 * @author jitwxs
 * @date 2020年02月15日 19:26
 */
@Slf4j
public class CenterTransactionTest extends BaseTest {
    @Autowired
    private UserMapper userMapper;

    /**
     * 事务提交
     * @author jitwxs
     * @date 2020/2/16 17:42
     */
    @Test
    @Transactional(transactionManager = "centerTransactionManager", rollbackFor = Exception.class)
    @Commit // junit 下必须要 commit，实际使用不需要
    public void testCommit() {
        long id1 = DateUtils.now(), id2 = id1 + RandomUtils.nextInt();

        User user1 = User.builder().id(id1).username(RandomStringUtils.randomAscii(4)).phone(RandomStringUtils.randomNumeric(5)).build();
        User user2 = User.builder().id(id2).username(RandomStringUtils.randomAscii(4)).phone(RandomStringUtils.randomNumeric(5)).build();

        userMapper.insert(user1);
        userMapper.insert(user2);

        log.error("手动查询数据库，是否存在记录：{},{}，期望存在", user1.getId(), user2.getId());
    }
}
