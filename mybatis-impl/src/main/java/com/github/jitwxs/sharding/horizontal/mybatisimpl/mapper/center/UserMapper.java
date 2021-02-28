package com.github.jitwxs.sharding.horizontal.mybatisimpl.mapper.center;

import com.github.jitwxs.sharding.horizontal.mybatisimpl.entiy.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {
    /**
     * @return 主键ID
     */
    void insert(@Param("item") User user);

    void batchInsert(@Param("userList") List<User> userList);

    long updatePhone(@Param("userId") long userId, @Param("phone") String phone);

    List<User> listAll();

    User selectByUserId(@Param("userId") long userId);

    long removeAll();
}
