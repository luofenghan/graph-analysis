CREATE DATABASE
IF NOT EXISTS `graph-analysis-db` DEFAULT CHARACTER
SET utf8 COLLATE utf8_general_ci;

DROP TABLE
IF EXISTS `graph-analysis-db`.`client`;

CREATE TABLE `graph-analysis-db`.`client` (
  `id` INTEGER AUTO_INCREMENT NOT NULL COMMENT '用户的id',
  `mobile` VARCHAR (128) NULL COMMENT '用户手机号, 用于登录(原则上使用密文)',
  `password` VARCHAR (128) NOT NULL COMMENT '登录密码, 数据库中为使用spring的BCryptPasswordEncoder加密后的值',
  `created_time` DATETIME (3) DEFAULT CURRENT_TIMESTAMP (3) COMMENT '创建日期',
  `updated_time` DATETIME (3) DEFAULT CURRENT_TIMESTAMP (3) COMMENT '更新日期',
  PRIMARY KEY (`id`),
  KEY (`mobile`),
  UNIQUE (mobile)
) ENGINE = INNODB DEFAULT CHARSET = utf8;

DROP TABLE
IF EXISTS `graph-analysis-db`.`data_source_info`;

CREATE TABLE `graph-analysis-db`.`data_source_info` (
  `id` INTEGER NOT NULL AUTO_INCREMENT COMMENT '数据源的id',
  `client_id` INTEGER NOT NULL COMMENT '使用该数据源信息的用户ID',
  `type` VARCHAR (20) NOT NULL COMMENT '数据源类型，现在支持的数据源有:jdbc',
  `name` VARCHAR (50) NOT NULL COMMENT '数据源名称',
  `uri` VARCHAR (255) NOT NULL COMMENT ' 数据源的配置',
  `created_time` DATETIME (3) DEFAULT CURRENT_TIMESTAMP (3) COMMENT '创建日期',
  `updated_time` DATETIME (3) DEFAULT CURRENT_TIMESTAMP (3) COMMENT '更新日期',
  PRIMARY KEY (`id`)
) ENGINE = INNODB DEFAULT CHARSET = utf8;

DROP TABLE
IF EXISTS `graph-analysis-db`.`data_set`;

CREATE TABLE `graph-analysis-db`.`data_set` (
  `id` BIGINT (20) NOT NULL AUTO_INCREMENT COMMENT '数据集的id',
  `client_id` INTEGER NOT NULL COMMENT '创建该数据集的用户',
  `data_source_id` INTEGER NOT NULL COMMENT '该数据集的数据源',
  `category_name` VARCHAR (100) DEFAULT NULL COMMENT '数据集所属的分类',
  `data_set_name` VARCHAR (100) DEFAULT NULL COMMENT '数据集的名称',
  `query` VARCHAR (255) DEFAULT NULL COMMENT '对数据源的查询，存储格式为Json,eg:[{"sql":"select * from dual"}]',
  `filter_group` VARCHAR (255) DEFAULT NULL COMMENT '过滤字段,eg: [{"filters":[{"col":"created_time","values":["{now(''W'',-1,''yyyy-MM-dd'')}"],"type":">"}],"group":"创建时间大于一周"},{"group":"province不为江苏","filters":[{"col":"province_id","type":"≠","values":["12000"]}]}]',
  `expressions` VARCHAR (255) DEFAULT NULL COMMENT '表达式存储格式为json 数组，eg:[{"alias":"CountCityOfProvince","type":"exp","exp":"count(city_name)"},{"type":"exp","exp":"max(min(city_id),max(province_id))","alias":"最大id"}]',
  `interval` BIGINT DEFAULT - 1 COMMENT '表示数据集自动刷新间隔，-1表示不刷新',
  `created_time` DATETIME (3) DEFAULT CURRENT_TIMESTAMP (3) COMMENT '创建日期',
  `updated_time` DATETIME (3) DEFAULT CURRENT_TIMESTAMP (3) COMMENT '更新日期',
  PRIMARY KEY (`id`),
  KEY (`client_id`),
  KEY (`data_source_id`)
) ENGINE = INNODB DEFAULT CHARSET = utf8;

DROP TABLE
IF EXISTS `graph-analysis-db`.`cron_job`;

CREATE TABLE `graph-analysis-db`.`cron_job` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '定时任务的id',
  `client_id` INTEGER NOT NULL COMMENT '创建定时任务的用户',
  `name` VARCHAR (50) NOT NULL COMMENT '定时任务名称',
  `expression` VARCHAR (100) DEFAULT NULL COMMENT '定时任务表达式，例如："0 0 12 * * ?"表示 每天中午十二点触发',
  `start_time` DATETIME NULL NOT NULL COMMENT '定时任务的开始日期',
  `last_exec_time` DATETIME NOT NULL COMMENT '上次定时任务的执行日期',
  `end_time` DATETIME NULL NOT NULL COMMENT '定时任务的终止日期',
  `type` VARCHAR(10) NOT NULL COMMENT '定时任务类型，类型包括：email',
  `config` VARCHAR(255) NOT NULL COMMENT '详细任务配置',
  `exec_log` TEXT COMMENT '定时任务的运行日志',
  `created_time` DATETIME (3) DEFAULT CURRENT_TIMESTAMP (3) COMMENT '创建日期',
  `updated_time` DATETIME (3) DEFAULT CURRENT_TIMESTAMP (3) COMMENT '更新日期',
  PRIMARY KEY (`id`),
  KEY (`client_id`)
) ENGINE = INNODB DEFAULT CHARSET = utf8;