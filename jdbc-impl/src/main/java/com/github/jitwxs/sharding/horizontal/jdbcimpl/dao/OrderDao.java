package com.github.jitwxs.sharding.horizontal.jdbcimpl.dao;

import com.github.jitwxs.sharding.horizontal.jdbcimpl.entiy.Order;

import java.sql.SQLException;

public interface OrderDao {
    long insertTransaction(Order order) throws SQLException;

    long insert(Order order);

    Order selectById(long orderId, int modulo);

    long removeAll(int modulo);
}
