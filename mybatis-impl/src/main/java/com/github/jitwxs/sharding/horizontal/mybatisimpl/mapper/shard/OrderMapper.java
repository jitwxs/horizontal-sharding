package com.github.jitwxs.sharding.horizontal.mybatisimpl.mapper.shard;

import com.github.jitwxs.sharding.horizontal.mybatisimpl.entiy.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OrderMapper {
    void insert(@Param("item") Order order, @Param("modulo") int modulo);

    Order selectById(@Param("orderId") long orderId, @Param("modulo") int modulo);

    long removeAll(@Param("modulo") int modulo);
}
