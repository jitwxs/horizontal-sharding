package com.github.jitwxs.sharding.horizontal.jdbcimpl.dao.impl;

import com.github.jitwxs.sharding.horizontal.jdbcimpl.datasource.config.ServerConfig;
import com.github.jitwxs.sharding.horizontal.jdbcimpl.dao.OrderDescDao;
import com.github.jitwxs.sharding.horizontal.jdbcimpl.datasource.Db;
import com.github.jitwxs.sharding.horizontal.jdbcimpl.datasource.DbAssist;
import com.github.jitwxs.sharding.horizontal.jdbcimpl.datasource.ShardingContext;
import com.github.jitwxs.sharding.horizontal.jdbcimpl.entiy.OrderDesc;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @author jitwxs
 * @date 2020年02月15日 19:04
 */
@Repository
public class OrderDescDaoImpl implements OrderDescDao {
    @Autowired
    private ServerConfig serverConfig;

    private static String buildTable(int modulo) {
        return "order_desc_" + modulo;
    }

    @Override
    public long insertTransaction(OrderDesc orderDesc) throws SQLException {
        int modulo = serverConfig.getModulo(orderDesc.getUserId());

        String sql = "INSERT INTO " + buildTable(modulo) + " (user_id, order_id, description) VALUES (?,?,?)";
        Object[] objects = {orderDesc.getUserId(), orderDesc.getOrderId(), orderDesc.getDescription()};
        return Db.updateTransaction(sql, objects, ShardingContext.sharding(modulo));
    }

    @Override
    public long removeAll(int modulo) {
        String sql = "DELETE FROM " + buildTable(modulo) + " WHERE 1 = 1";
        return Db.update(sql, null, ShardingContext.sharding(modulo));
    }

    @Override
    public OrderDesc selectById(long id, int modulo) {
        String sql = "SELECT * FROM " + buildTable(modulo) + " WHERE id = ?";
        List<Map<String, Object>> mapList = Db.query(sql, new Object[]{id}, ShardingContext.sharding(modulo));
        return CollectionUtils.isEmpty(mapList) ? null : DbAssist.convert(mapList.get(0), OrderDesc.class);
    }
}
