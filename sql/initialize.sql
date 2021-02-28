SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

DROP DATABASE if EXISTS `hor-shard-center`;
CREATE DATABASE IF NOT EXISTS `hor-shard-center` default charset utf8 COLLATE utf8_general_ci;
USE `hor-shard-center`;

DROP TABLE IF EXISTS `datasource_sharding`;
CREATE TABLE `datasource_sharding`  (
                                        `id` int(11) NOT NULL AUTO_INCREMENT,
                                        `server_type` int(11) NULL DEFAULT NULL COMMENT '数据库类型',
                                        `server_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                        `modulo` varchar (64) NULL DEFAULT NULL COMMENT '分片配置',
                                        `enable` tinyint(2) NULL DEFAULT 1 COMMENT '是否启用，1：启用；0：禁用',
                                        `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                        PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

INSERT INTO `datasource_sharding`(`server_type`, `server_name`, `modulo`, `enable`) VALUES (1, 'db_center', NULL, 1);
INSERT INTO `datasource_sharding`(`server_type`, `server_name`, `modulo`, `enable`) VALUES (2, 'db_ds1_master', '0-3,5', 1);
INSERT INTO `datasource_sharding`(`server_type`, `server_name`, `modulo`, `enable`) VALUES (3, 'db_ds1_slave', '0-3,5', 1);
INSERT INTO `datasource_sharding`(`server_type`, `server_name`, `modulo`, `enable`) VALUES (2, 'db_ds2_master', '4,6-7', 1);
INSERT INTO `datasource_sharding`(`server_type`, `server_name`, `modulo`, `enable`) VALUES (3, 'db_ds1_slave', '4,6-7', 1);

CREATE TABLE `user` (
                        `id` bigint(64) NOT NULL AUTO_INCREMENT,
                        `username` varchar(255) DEFAULT NULL,
                        `phone` varchar(32) DEFAULT NULL,
                        `created_date` datetime DEFAULT NULL,
                        `modified_date` datetime DEFAULT NULL,
                        PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP DATABASE if EXISTS `hor-shard-ds1`;
CREATE DATABASE IF NOT EXISTS `hor-shard-ds1` default charset utf8 COLLATE utf8_general_ci;
USE `hor-shard-ds1`;

CREATE TABLE `order_0` (
                           `id` bigint(20) NOT NULL AUTO_INCREMENT,
                           `user_id` bigint(20) DEFAULT NULL,
                           `amount` decimal(16,8) DEFAULT NULL,
                           `created_date` datetime DEFAULT NULL,
                           `modified_date` datetime DEFAULT NULL,
                           PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `order_desc_0` (
                                `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                `order_id` bigint(20) DEFAULT NULL,
                                `user_id` bigint(20) DEFAULT NULL,
                                `description` varchar(255) DEFAULT NULL,
                                PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `order_1` (
                           `id` bigint(20) NOT NULL AUTO_INCREMENT,
                           `user_id` bigint(20) DEFAULT NULL,
                           `amount` decimal(16,8) DEFAULT NULL,
                           `created_date` datetime DEFAULT NULL,
                           `modified_date` datetime DEFAULT NULL,
                           PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `order_desc_1` (
                                `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                `order_id` bigint(20) DEFAULT NULL,
                                `user_id` bigint(20) DEFAULT NULL,
                                `description` varchar(255) DEFAULT NULL,
                                PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `order_2` (
                           `id` bigint(20) NOT NULL AUTO_INCREMENT,
                           `user_id` bigint(20) DEFAULT NULL,
                           `amount` decimal(16,8) DEFAULT NULL,
                           `created_date` datetime DEFAULT NULL,
                           `modified_date` datetime DEFAULT NULL,
                           PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `order_desc_2` (
                                `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                `order_id` bigint(20) DEFAULT NULL,
                                `user_id` bigint(20) DEFAULT NULL,
                                `description` varchar(255) DEFAULT NULL,
                                PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `order_3` (
                           `id` bigint(20) NOT NULL AUTO_INCREMENT,
                           `user_id` bigint(20) DEFAULT NULL,
                           `amount` decimal(16,8) DEFAULT NULL,
                           `created_date` datetime DEFAULT NULL,
                           `modified_date` datetime DEFAULT NULL,
                           PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `order_desc_3` (
                                `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                `order_id` bigint(20) DEFAULT NULL,
                                `user_id` bigint(20) DEFAULT NULL,
                                `description` varchar(255) DEFAULT NULL,
                                PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `order_5` (
                           `id` bigint(20) NOT NULL AUTO_INCREMENT,
                           `user_id` bigint(20) DEFAULT NULL,
                           `amount` decimal(16,8) DEFAULT NULL,
                           `created_date` datetime DEFAULT NULL,
                           `modified_date` datetime DEFAULT NULL,
                           PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `order_desc_5` (
                                `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                `order_id` bigint(20) DEFAULT NULL,
                                `user_id` bigint(20) DEFAULT NULL,
                                `description` varchar(255) DEFAULT NULL,
                                PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP DATABASE if EXISTS `hor-shard-ds2`;
CREATE DATABASE IF NOT EXISTS `hor-shard-ds2` default charset utf8 COLLATE utf8_general_ci;
USE `hor-shard-ds2`;

CREATE TABLE `order_4` (
                           `id` bigint(20) NOT NULL AUTO_INCREMENT,
                           `user_id` bigint(20) DEFAULT NULL,
                           `amount` decimal(16,8) DEFAULT NULL,
                           `created_date` datetime DEFAULT NULL,
                           `modified_date` datetime DEFAULT NULL,
                           PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `order_desc_4` (
                                `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                `order_id` bigint(20) DEFAULT NULL,
                                `user_id` bigint(20) DEFAULT NULL,
                                `description` varchar(255) DEFAULT NULL,
                                PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `order_6` (
                           `id` bigint(20) NOT NULL AUTO_INCREMENT,
                           `user_id` bigint(20) DEFAULT NULL,
                           `amount` decimal(16,8) DEFAULT NULL,
                           `created_date` datetime DEFAULT NULL,
                           `modified_date` datetime DEFAULT NULL,
                           PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `order_desc_6` (
                                `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                `order_id` bigint(20) DEFAULT NULL,
                                `user_id` bigint(20) DEFAULT NULL,
                                `description` varchar(255) DEFAULT NULL,
                                PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `order_7` (
                           `id` bigint(20) NOT NULL AUTO_INCREMENT,
                           `user_id` bigint(20) DEFAULT NULL,
                           `amount` decimal(16,8) DEFAULT NULL,
                           `created_date` datetime DEFAULT NULL,
                           `modified_date` datetime DEFAULT NULL,
                           PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `order_desc_7` (
                                `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                `order_id` bigint(20) DEFAULT NULL,
                                `user_id` bigint(20) DEFAULT NULL,
                                `description` varchar(255) DEFAULT NULL,
                                PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;