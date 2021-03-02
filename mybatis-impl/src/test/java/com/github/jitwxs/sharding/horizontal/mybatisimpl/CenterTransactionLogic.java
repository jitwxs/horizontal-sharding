package com.github.jitwxs.sharding.horizontal.mybatisimpl;

import com.github.jitwxs.sharding.horizontal.mybatisimpl.entiy.User;
import com.github.jitwxs.sharding.horizontal.mybatisimpl.mapper.center.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CenterTransactionLogic {
    @Autowired
    private UserMapper userMapper;

    @Transactional(transactionManager = "centerTransactionManager", rollbackFor = Exception.class)
    public void testCommitLogic(final User user1, final User user2) {
        userMapper.insert(user1);
        userMapper.insert(user2);
    }

    @Transactional(transactionManager = "centerTransactionManager", rollbackFor = Exception.class)
    public void testRollbackLogic(final User user1, final User user2) {
        userMapper.insert(user1);
        int i = 1 / 0;
        userMapper.insert(user2);
    }
}
