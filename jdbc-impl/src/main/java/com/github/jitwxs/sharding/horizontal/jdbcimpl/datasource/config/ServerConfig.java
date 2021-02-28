package com.github.jitwxs.sharding.horizontal.jdbcimpl.datasource.config;

import com.github.jitwxs.sharding.horizontal.jdbcimpl.datasource.Db;
import com.github.jitwxs.sharding.horizontal.jdbcimpl.datasource.Server;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.util.List;


/**
 * @author jitwxs
 * @date 2020年02月15日 16:11
 */
@Slf4j
@Getter
@Configuration
@Import({DataSourceImportBeanDefinitionRegister.class})
public class ServerConfig implements InitializingBean {

    @Value("${datasource.modulo-size}")
    private int moduloSize;

    @Autowired
    private List<Server> serverList;

    /**
     * 获取用户所属分片
     */
    public int getModulo(long userId) {
        return (int) (userId % getModuloSize());
    }

    @Override
    public void afterPropertiesSet() {
        Db.initServer(serverList);
    }
}
