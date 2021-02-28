package com.github.jitwxs.sharding.horizontal.jdbcimpl.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * 数据库配置
 * @author jitwxs
 * @date 2020年02月15日 13:27
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "datasource")
public class DataSourceConfig {
    /**
     * 数据库连接池
     */
    private Class<? extends DataSource> type;
    /**
     * 分片数
     */
    private Integer moduloSize;

    private DbProperties db;

    @Primary
    @Bean("db_center")
    public DataSource getDbCenter() {
        return new HikariDataSource(db.getCenter().convertHikariConfig());
    }

    @Bean("db_ds1_master")
    public DataSource getDbDs1Master() {
        return new HikariDataSource(db.getDs1Master().convertHikariConfig());
    }

    @Bean("db_ds1_slave")
    public DataSource getDbDs1Slave() {
        return new HikariDataSource(db.getDs1Slave().convertHikariConfig());
    }

    @Bean("db_ds2_master")
    public DataSource getDbDs2Master() {
        return new HikariDataSource(db.getDs2Master().convertHikariConfig());
    }

    @Bean("db_ds2_slave")
    public DataSource getDbDs2Slave() {
        return new HikariDataSource(db.getDs2Slave().convertHikariConfig());
    }

    /**
     * 获取用户所属分片
     */
    public int getModulo(long userId) {
        return (int) (userId % getModuloSize());
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DbProperties {
        private DbConfig center;

        private DbConfig ds1Master;

        private DbConfig ds1Slave;

        private DbConfig ds2Master;

        private DbConfig ds2Slave;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DbConfig {
        /**
         * 数据库驱动名
         */
        private String driverClassName;
        /**
         * 数据库链接
         */
        private String jdbcUrl;

        private String username;

        private String password;
        /**
         * 数据库连接池名
         */
        private String poolName;

        private Integer maximumPoolSize;

        private Integer minimumIdle;

        public HikariConfig convertHikariConfig() {
            HikariConfig config = new HikariConfig();
            config.setDriverClassName(driverClassName);
            config.setJdbcUrl(jdbcUrl);
            config.setUsername(username);
            config.setPassword(password);
            config.setPoolName(poolName);
            config.setMaximumPoolSize(maximumPoolSize);
            config.setMinimumIdle(minimumIdle);

            return config;
        }
    }
}
