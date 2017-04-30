-- 用于UT的建表脚本, 和正常使用的MySQL脚本有以下不同
-- 1. 不需要Drop If Exist, 因为每次都是新建
-- 1. 不支持ON UPDATE CURRENT_TIMESTAMP的语法
-- 2. 不支持DOUBLE(D,M)的语法

SET MODE=MySQL; -- set H2 to MySQL mode, this will fix most compatibility issue


CREATE TABLE `client` (
  `id` INTEGER AUTO_INCREMENT NOT NULL COMMENT '用户的id',
  `mobile` VARCHAR (128) NULL COMMENT '用户手机号, 用于登录(原则上使用密文)',
  `password` VARCHAR (128) NOT NULL COMMENT '登录密码, 数据库中为使用spring的BCryptPasswordEncoder加密后的值',
  `created_time` DATETIME (3) DEFAULT CURRENT_TIMESTAMP (3) COMMENT '创建日期',
  `updated_time` DATETIME (3) DEFAULT CURRENT_TIMESTAMP (3) COMMENT '更新日期',
  PRIMARY KEY (`id`),
  KEY (`mobile`),
  UNIQUE (mobile)
) ENGINE = INNODB DEFAULT CHARSET = utf8;


CREATE TABLE `data_source_info` (
  `id` INTEGER NOT NULL AUTO_INCREMENT COMMENT '数据源的id',
  `client_id` INTEGER NOT NULL COMMENT '使用该数据源信息的用户ID',
  `name` VARCHAR (50) NOT NULL COMMENT '数据源名称',
  `uri` VARCHAR (255) NOT NULL COMMENT ' 数据源的uri，例如：jdbc://root:123@127.0.0.1:3306/chinaregion?db=mysql&pooled=true&aggregatable=true',
  `created_time` DATETIME (3) DEFAULT CURRENT_TIMESTAMP (3) COMMENT '创建日期',
  `updated_time` DATETIME (3) DEFAULT CURRENT_TIMESTAMP (3) COMMENT '更新日期',
  PRIMARY KEY (`id`)
) ENGINE = INNODB DEFAULT CHARSET = utf8;

-- 测试用数据库
CREATE TABLE `city` (
	`id` BIGINT (20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
	`city_id` VARCHAR (8) NOT NULL COMMENT '地级行政区ID',
	`city_name` VARCHAR (16) NOT NULL COMMENT '地级行政区名称',
	`province_id` VARCHAR (8) NOT NULL COMMENT '该行政区所在省级行政区ID',
	`created_time` datetime (3) DEFAULT CURRENT_TIMESTAMP (3) COMMENT '创建日期',
	`updated_time` datetime (3) DEFAULT CURRENT_TIMESTAMP (3)  COMMENT '更新日期',
	PRIMARY KEY (`id`),
	KEY `province_id` (`province_id`)
) ENGINE = INNODB  DEFAULT CHARSET = utf8;

CREATE TABLE `county` (
	`id` BIGINT (20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
	`county_id` VARCHAR (8) NOT NULL COMMENT '区县行政区ID',
	`county_name` VARCHAR (16) NOT NULL COMMENT '区县行政区名称',
	`city_id` VARCHAR (8) NOT NULL COMMENT '该行政区所在地级行政区ID',
	`created_time` datetime (3) DEFAULT CURRENT_TIMESTAMP (3) COMMENT '创建日期',
	`updated_time` datetime (3) DEFAULT CURRENT_TIMESTAMP (3)  COMMENT '更新日期',
	PRIMARY KEY (`id`),
	KEY `city_id` (`city_id`)
) ENGINE = INNODB  DEFAULT CHARSET = utf8;

CREATE TABLE `province` (
	`id` BIGINT (20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
	`province_id` VARCHAR (8) NOT NULL COMMENT '省级行政区ID',
	`province_name` VARCHAR (16) NOT NULL COMMENT '省级行政区名称',
	`created_time` datetime (3) DEFAULT CURRENT_TIMESTAMP (3) COMMENT '创建日期',
	`updated_time` datetime (3) DEFAULT CURRENT_TIMESTAMP (3) COMMENT '更新日期',
	PRIMARY KEY (`id`)
) ENGINE = INNODB  DEFAULT CHARSET = utf8;



SET MODE=REGULAR;   -- restore SQL mode, MySQL mode didn't check NOT NULL column
