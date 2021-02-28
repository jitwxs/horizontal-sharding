package com.github.jitwxs.sharding.horizontal.jdbcimpl.dao.impl;

import com.github.jitwxs.sharding.horizontal.jdbcimpl.datasource.config.ServerConfig;
import com.github.jitwxs.sharding.horizontal.jdbcimpl.dao.OrderDao;
import com.github.jitwxs.sharding.horizontal.jdbcimpl.datasource.Db;
import com.github.jitwxs.sharding.horizontal.jdbcimpl.datasource.DbAssist;
import com.github.jitwxs.sharding.horizontal.jdbcimpl.datasource.ShardingContext;
import com.github.jitwxs.sharding.horizontal.jdbcimpl.entiy.Order;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @author jitwxs
 * @date 2020年02月15日 19:11
 */
@Repository
public class OrderDaoImpl implements OrderDao {
    @Autowired
    private ServerConfig serverConfig;

    private String buildTable(final int modulo) {
        return "order_" + modulo;
    }

    @Override
    public long insertTransaction(Order order) throws SQLException {
        int modulo = serverConfig.getModulo(order.getUserId());

        String sql = "INSERT INTO " + buildTable(modulo) + " (user_id, amount, created_date) VALUES (?,?,now())";
        Object[] objects = {order.getUserId(), order.getAmount()};
        return Db.updateTransaction(sql, objects, ShardingContext.sharding(modulo));
    }

    @Override
    public long insert(Order order) {
        int modulo = serverConfig.getModulo(order.getUserId());

        String sql = "INSERT INTO " + buildTable(modulo) + " (user_id, amount, created_date) VALUES (?,?,now())";
        Object[] objects = {order.getUserId(), order.getAmount()};
        return Db.update(sql, objects, ShardingContext.sharding(modulo));
    }

    @Override
    public Order selectById(long orderId, int modulo) {
        String sql = "SELECT * FROM " + buildTable(modulo) + " WHERE id = ?";
        List<Map<String, Object>> mapList = Db.query(sql, new Object[]{orderId}, ShardingContext.sharding(modulo));
        return CollectionUtils.isEmpty(mapList) ? null : DbAssist.convert(mapList.get(0), Order.class);
    }

    @Override
    public long removeAll(int modulo) {
        String sql = "DELETE FROM " + buildTable(modulo) + " WHERE 1 = 1";
        return Db.update(sql, null, ShardingContext.sharding(modulo));
    }
}
