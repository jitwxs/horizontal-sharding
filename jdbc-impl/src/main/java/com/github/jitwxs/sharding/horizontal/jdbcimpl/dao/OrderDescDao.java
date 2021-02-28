package com.github.jitwxs.sharding.horizontal.jdbcimpl.dao;

import com.github.jitwxs.sharding.horizontal.jdbcimpl.entiy.OrderDesc;

import java.sql.SQLException;

public interface OrderDescDao {
    long insertTransaction(OrderDesc orderDesc) throws SQLException;

    long removeAll(int modulo);

    OrderDesc selectById(long id, int modulo);
}
