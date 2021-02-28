package com.github.jitwxs.sharding.horizontal.jdbcimpl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @author jitwxs
 * @date 2021年02月28日 12:08
 */
@SpringBootApplication
@EnableConfigurationProperties
public class JdbcImplApplication {
    public static void main(String[] args) {
        SpringApplication.run(JdbcImplApplication.class);
    }
}
