package com.github.jitwxs.sharding.horizontal.jdbcimpl.datasource;

import lombok.Data;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.*;

/**
 * @author jitwxs
 * @date 2020年02月15日 13:34
 */
@Data
public class Server {
    private String name;

    private ServerTypeEnum type;

    private List<Integer> moduloList;

    private DataSource dataSource;

    private final ThreadLocal<Connection> connThreadLocal = new ThreadLocal<>();

    public Server(String name, ServerTypeEnum type, List<Integer> moduloList, DataSource dataSource) {
        this.name = name;
        this.type = type;
        this.moduloList = moduloList;
        this.dataSource = dataSource;
    }
}
