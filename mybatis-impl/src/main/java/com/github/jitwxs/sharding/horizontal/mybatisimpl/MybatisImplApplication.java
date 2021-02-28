package com.github.jitwxs.sharding.horizontal.mybatisimpl;

import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScans;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @author jitwxs
 * @date 2021年02月28日 15:40
 */
@MapperScans({
        @MapperScan(basePackages = "com.github.jitwxs.sharding.horizontal.mybatisimpl.mapper.center", sqlSessionTemplateRef = "centerSqlSessionTemplate"),
        @MapperScan(basePackages = "com.github.jitwxs.sharding.horizontal.mybatisimpl.mapper.shard", sqlSessionTemplateRef = "shardingSqlSessionTemplate"),
})
@SpringBootApplication
@EnableConfigurationProperties
public class MybatisImplApplication {
    public static void main(String[] args) {
        SpringApplication.run(MybatisImplApplication.class);
    }
}
