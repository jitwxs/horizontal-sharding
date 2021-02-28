package com.github.jitwxs.sharding.horizontal.mybatisimpl.mapper.shard;

import com.github.jitwxs.sharding.horizontal.mybatisimpl.entiy.OrderDesc;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OrderDescMapper {
    void insert(@Param("item") OrderDesc orderDesc);

    long removeAll(@Param("modulo") int modulo);

    OrderDesc selectById(@Param("id")long id, @Param("modulo") int modulo);
}
