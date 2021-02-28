SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

DROP DATABASE if EXISTS `hor-shard-center`;
CREATE DATABASE IF NOT EXISTS `hor-shard-center` default charset utf8 COLLATE utf8_general_ci;
USE `hor-shard-center`;

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