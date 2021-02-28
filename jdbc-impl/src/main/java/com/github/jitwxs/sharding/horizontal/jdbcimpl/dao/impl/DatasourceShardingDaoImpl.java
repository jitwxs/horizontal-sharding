package com.github.jitwxs.sharding.horizontal.jdbcimpl.dao.impl;

import com.github.jitwxs.sharding.horizontal.jdbcimpl.dao.DatasourceShardingDao;
import com.github.jitwxs.sharding.horizontal.jdbcimpl.datasource.Db;
import com.github.jitwxs.sharding.horizontal.jdbcimpl.datasource.DbAssist;
import com.github.jitwxs.sharding.horizontal.jdbcimpl.datasource.ShardingContext;
import com.github.jitwxs.sharding.horizontal.jdbcimpl.entiy.DatasourceSharding;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class DatasourceShardingDaoImpl implements DatasourceShardingDao {
    private static String buildTable() {
        return "datasource_sharding";
    }

    @Override
    public List<DatasourceSharding> listAllEnabled() {
        String sql = "SELECT * FROM " + buildTable() + " WHERE enable = 1";
        List<Map<String, Object>> list = Db.query(sql, null, ShardingContext.CENTER);

        return DbAssist.convert(list, DatasourceSharding.class);
    }
}
