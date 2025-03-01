CREATE DATABASE
IF NOT EXISTS `graph-analysis-db` DEFAULT CHARACTER
SET utf8 COLLATE utf8_general_ci;

-- 用户表
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

-- 数据连接源记录表
DROP TABLE
IF EXISTS `graph-analysis-db`.`datasource`;

CREATE TABLE `graph-analysis-db`.`datasource` (
  `id` INTEGER NOT NULL AUTO_INCREMENT COMMENT '数据源的id',
  `client_id` INTEGER NOT NULL COMMENT '使用该数据源信息的用户ID',
  `name` VARCHAR (50) NOT NULL COMMENT '数据源名称',
  `uri` VARCHAR (255) NOT NULL COMMENT ' 数据源的uri，例如：jdbc://root:123@127.0.0.1:3306/chinaregion?db=mysql&pooled=true&aggregatable=true',
  `created_time` DATETIME (3) DEFAULT CURRENT_TIMESTAMP (3) COMMENT '创建日期',
  `updated_time` DATETIME (3) DEFAULT CURRENT_TIMESTAMP (3) COMMENT '更新日期',
  PRIMARY KEY (`id`)
) ENGINE = INNODB DEFAULT CHARSET = utf8;

-- 用户数据集
DROP TABLE
IF EXISTS `graph-analysis-db`.`dataset`;

CREATE TABLE `graph-analysis-db`.`dataset` (
  `id` BIGINT (20) NOT NULL AUTO_INCREMENT COMMENT '数据集的id',
  `client_id` INTEGER NOT NULL COMMENT '创建该数据集的用户',
  `datasource_id` INTEGER NOT NULL COMMENT '该数据集的数据源',
  `category` VARCHAR (50) DEFAULT NULL COMMENT '数据集所属的分类',
  `name` VARCHAR (50) DEFAULT NULL COMMENT '数据集的名称',
  `query` VARCHAR (255) DEFAULT NULL COMMENT '对数据源的查询，存储格式为Json,eg:[{"sql":"select * from dual"}]',
  `filter` VARCHAR (255) DEFAULT NULL COMMENT '数据集筛选器，用于添加到查询中,eg: [{"col":"created_time","values":["{now(''W'',-1,''yyyy-MM-dd'')}"],"type":">","alias":"创建时间不为1"},{"col":"created_time","type":">","values":["{now(''W'',-1,''yyyy-MM-dd'')}"],"alias":"创建时间不为1"}]',
  `metric` VARCHAR (255) DEFAULT NULL COMMENT '度量存储格式为json 数组，eg:[{"alias":"CountCityOfProvince","column":"exp","function":"count"}]',
  `interval` BIGINT DEFAULT - 1 COMMENT '表示数据集自动刷新间隔，-1表示不刷新',
  `created_time` DATETIME (3) DEFAULT CURRENT_TIMESTAMP (3) COMMENT '创建日期',
  `updated_time` DATETIME (3) DEFAULT CURRENT_TIMESTAMP (3) COMMENT '更新日期',
  PRIMARY KEY (`id`),
  KEY (`client_id`),
  KEY (`datasource_id`)
) ENGINE = INNODB DEFAULT CHARSET = utf8;

-- 组件表
DROP TABLE
IF EXISTS `graph-analysis-db`.`widget`;

CREATE TABLE `graph-analysis-db`.`widget` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `client_id` INTEGER NOT NULL,
  `dataset_id` BIGINT DEFAULT NULL COMMENT '数据集',
  `category` VARCHAR (100) DEFAULT NULL,
  `name` VARCHAR (100) DEFAULT NULL,
  `graph_type` VARCHAR (15) NOT NULL DEFAULT 'table' COMMENT '图表类型',
  `optional_field` VARCHAR (1000) DEFAULT NULL COMMENT '表示可选择的字段，存储格式为: ["field1","field2","field3"]',
  `row_field` VARCHAR (255) DEFAULT NULL COMMENT '行显示的字段配置',
  `column_field` VARCHAR (255) DEFAULT NULL COMMENT '列显示的字段配置',
  `filter_field` VARCHAR (255) DEFAULT NULL COMMENT '过滤',
  `metric_field` VARCHAR (255) DEFAULT NULL COMMENT '聚合配置',
  `created_time` DATETIME (3) DEFAULT CURRENT_TIMESTAMP (3) COMMENT '创建日期',
  `updated_time` DATETIME (3) DEFAULT CURRENT_TIMESTAMP (3) COMMENT '更新日期',
  KEY (`client_id`),
  KEY (`dataset_id`),
  PRIMARY KEY (`id`)
) ENGINE = INNODB DEFAULT CHARSET = utf8;

-- 仪表板
DROP TABLE
IF EXISTS `graph-analysis-db`.`dashboard`;

CREATE TABLE `graph-analysis-db`.`dashboard` (
  `id` BIGINT (20) NOT NULL AUTO_INCREMENT,
  `client_id` INTEGER NOT NULL,
  `category` VARCHAR (100) DEFAULT NULL COMMENT '仪表板的分类',
  `name` VARCHAR (100) NOT NULL COMMENT '仪表板的命名',
  `layout_config` TEXT DEFAULT NULL COMMENT '仪表板的布局配置',
  `created_time` DATETIME (3) DEFAULT CURRENT_TIMESTAMP (3) COMMENT '创建日期',
  `updated_time` DATETIME (3) DEFAULT CURRENT_TIMESTAMP (3) COMMENT '更新日期',
  PRIMARY KEY (`id`)
) ENGINE = INNODB DEFAULT CHARSET = utf8;

-- 定时任务
DROP TABLE
IF EXISTS `graph-analysis-db`.`cronjob`;

CREATE TABLE `graph-analysis-db`.`cronjob` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '定时任务的id',
  `client_id` INTEGER NOT NULL COMMENT '创建定时任务的用户',
  `name` VARCHAR (50) NOT NULL COMMENT '定时任务名称',
  `expression` VARCHAR (100) DEFAULT NULL COMMENT '定时任务表达式，例如："0 0 12 * * ?"表示 每天中午十二点触发',
  `start_time` DATETIME NULL NOT NULL COMMENT '定时任务的开始日期',
  `last_exec_time` DATETIME NOT NULL COMMENT '上次定时任务的执行日期',
  `end_time` DATETIME NULL NOT NULL COMMENT '定时任务的终止日期',
  `type` VARCHAR (10) NOT NULL COMMENT '定时任务类型，类型包括：email',
  `config` VARCHAR (255) NOT NULL COMMENT '详细任务配置',
  `exec_log` TEXT COMMENT '定时任务的运行日志',
  `created_time` DATETIME (3) DEFAULT CURRENT_TIMESTAMP (3) COMMENT '创建日期',
  `updated_time` DATETIME (3) DEFAULT CURRENT_TIMESTAMP (3) COMMENT '更新日期',
  PRIMARY KEY (`id`),
  KEY (`client_id`)
) ENGINE = INNODB DEFAULT CHARSET = utf8;


-- 角色，考虑到这表很小，就直接用role_id做primary key
DROP TABLE
IF EXISTS `graph-analysis-db`.`role`;

CREATE TABLE `graph-analysis-db`.`role` (
  `id` VARCHAR (64) NOT NULL COMMENT '角色ID，使用英文大写，单词间以下划线分隔如 ADMIN,CLIENT',
  `name` VARCHAR (64) NOT NULL COMMENT '角色中文名称，如：管理员',
  `resource_list` TEXT DEFAULT NULL COMMENT '角色拥有的资源列表，资源列表由一系列api组成，例如["/api/dashboard",'']',
  `created_time` DATETIME (3) DEFAULT CURRENT_TIMESTAMP (3) COMMENT '创建日期',
  `updated_time` DATETIME (3) DEFAULT CURRENT_TIMESTAMP (3) COMMENT '更新日期',
  PRIMARY KEY (`id`)
) ENGINE = INNODB DEFAULT CHARSET = utf8;

-- 管理用户和角色的对应关系，一个用户可以属于多个角色
DROP TABLE
IF EXISTS `graph-analysis-db`.`client_role`;

CREATE TABLE `graph-analysis-db`.`client_role` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
  `client_id` INTEGER UNSIGNED NOT NULL COMMENT '用户ID',
  `role_id` VARCHAR (64) NOT NULL COMMENT '用户角色ID',
  `created_time` DATETIME (3) DEFAULT CURRENT_TIMESTAMP (3) COMMENT '创建日期',
  `updated_time` DATETIME (3) DEFAULT CURRENT_TIMESTAMP (3) COMMENT '更新日期',
  PRIMARY KEY (`id`),
  UNIQUE KEY (`client_id`, `role_id`)
) ENGINE = INNODB DEFAULT CHARSET = utf8;

