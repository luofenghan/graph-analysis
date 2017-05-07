-- 用于UT的建表脚本, 和正常使用的MySQL脚本有以下不同
-- 1. 不需要Drop If Exist, 因为每次都是新建
-- 1. 不支持ON UPDATE CURRENT_TIMESTAMP的语法
-- 2. 不支持DOUBLE(D,M)的语法

 SET MODE=MYSQL ; -- set H2 to MySQL mode, this will fix most compatibility issue


CREATE TABLE `client` (
  `id` INTEGER AUTO_INCREMENT NOT NULL COMMENT '用户的id',
  `mobile` VARCHAR (128) NULL COMMENT '用户手机号, 用于登录(原则上使用密文)',
  `password` VARCHAR (128) NOT NULL COMMENT '登录密码, 数据库中为使用spring的BCryptPasswordEncoder加密后的值',
  `created_time` DATETIME DEFAULT CURRENT_TIMESTAMP (3) COMMENT '创建日期',
  `updated_time` DATETIME DEFAULT CURRENT_TIMESTAMP (3) COMMENT '更新日期',
  PRIMARY KEY (`id`),
  UNIQUE (`mobile`)
) ;


CREATE TABLE `datasource` (
  `id` INTEGER NOT NULL AUTO_INCREMENT COMMENT '数据源的id',
  `client_id` INTEGER NOT NULL COMMENT '使用该数据源信息的用户ID',
  `name` VARCHAR (50) NOT NULL COMMENT '数据源名称',
  `uri` VARCHAR (255) NOT NULL COMMENT ' 数据源的uri，例如：jdbc://root:123@127.0.0.1:3306/chinaregion?db=mysql&pooled=true&aggregatable=true',
  `created_time` DATETIME DEFAULT CURRENT_TIMESTAMP (3) COMMENT '创建日期',
  `updated_time` DATETIME DEFAULT CURRENT_TIMESTAMP (3) COMMENT '更新日期',
  PRIMARY KEY (`id`)
) ;

CREATE TABLE `dataset` (
	`id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '数据集的id',
	`client_id` INTEGER NOT NULL COMMENT '创建该数据集的用户',
	`datasource_id` INTEGER NOT NULL COMMENT '该数据集的数据源',
	`category` VARCHAR (50) DEFAULT NULL COMMENT '数据集所属的分类',
	`name` VARCHAR (50) DEFAULT NULL COMMENT '数据集的名称',
	`query` VARCHAR (255) DEFAULT NULL COMMENT '对数据源的查询，存储格式为Json,eg:[{"sql":"select * from dual"}]',
	`filter` VARCHAR (255) DEFAULT NULL COMMENT '数据集筛选器，用于添加到查询中,eg: [{"col":"created_time","values":["{now(''W'',-1,''yyyy-MM-dd'')}"],"type":">","alias":"创建时间不为1"},{"col":"created_time","type":">","values":["{now(''W'',-1,''yyyy-MM-dd'')}"],"alias":"创建时间不为1"}]',
	`expression` VARCHAR (255) DEFAULT NULL COMMENT '表达式存储格式为json 数组，eg:[{"alias":"CountCityOfProvince","type":"exp","exp":"count(city_name)"},{"type":"exp","exp":"max(min(city_id),max(province_id))","alias":"最大id"}]',
	`interval` BIGINT DEFAULT -1 COMMENT '表示数据集自动刷新间隔，-1表示不刷新',
	`created_time` DATETIME DEFAULT CURRENT_TIMESTAMP (3) COMMENT '创建日期',
	`updated_time` DATETIME DEFAULT CURRENT_TIMESTAMP (3) COMMENT '更新日期',
	PRIMARY KEY (`id`),
) ;


CREATE TABLE `graph` (
	`id` BIGINT AUTO_INCREMENT  NOT NULL ,
	`client_id` INTEGER NOT NULL,
	`category` VARCHAR (100) DEFAULT NULL,
	`name` VARCHAR (100) DEFAULT NULL,
	`datasource_id` INTEGER DEFAULT NULL COMMENT '图表的数据源，表示来自数据源，则必须提供查询的query',
	`query` VARCHAR (500) DEFAULT NULL COMMENT '如果数据来源于数据源，那么保存数据源的sql查询语句，否则为空',
	`dataset_id` BIGINT DEFAULT NULL COMMENT '图表的数据源，表示来自原数据集',
	`graph_type` VARCHAR (15) NOT NULL DEFAULT 'table' COMMENT '图表类型',
	`fill_field` VARCHAR (255) DEFAULT NULL COMMENT '表示可选择的字段，存储格式为: ["field1","field2","field3"]',
	`row` VARCHAR (255) DEFAULT NULL COMMENT '行显示的字段配置',
	`column` VARCHAR (255) DEFAULT NULL COMMENT '列显示的字段配置',
	`filter` VARCHAR (255) DEFAULT NULL COMMENT '过滤',
	`aggregation` VARCHAR (255) DEFAULT NULL COMMENT '聚合配置',
	`created_time` DATETIME DEFAULT CURRENT_TIMESTAMP (3) COMMENT '创建日期',
	`updated_time` DATETIME DEFAULT CURRENT_TIMESTAMP (3) COMMENT '更新日期',
  PRIMARY KEY(`id`)
);

-- 测试用数据库，global-superstore

CREATE TABLE `orders` (
	`id` BIGINT NOT NULL COMMENT 'ID',
	`order_id` VARCHAR (50) DEFAULT NULL COMMENT '订单ID',
	`order_time` datetime DEFAULT NULL COMMENT '订购日期',
	`shipping_time` datetime DEFAULT NULL COMMENT '装运日期',
	`shipment` VARCHAR (15) DEFAULT NULL COMMENT '装运方式',
	`customer_id` VARCHAR (20) DEFAULT NULL COMMENT '客户ID',
	`customer_name` VARCHAR (50) DEFAULT NULL COMMENT '客户姓名',
	`market_segment` VARCHAR (20) DEFAULT NULL COMMENT '细分市场',
	`postal_code` VARCHAR (10) DEFAULT NULL COMMENT '邮政编码',
	`city` VARCHAR (50) DEFAULT NULL COMMENT '城市',
	`province` VARCHAR (20) DEFAULT NULL COMMENT '省/市/自治区',
	`country` VARCHAR (15) DEFAULT NULL COMMENT '国家地区',
	`region` VARCHAR (15) DEFAULT NULL COMMENT '地区',
	`market` VARCHAR (15) DEFAULT NULL COMMENT '市场',
	`product_id` VARCHAR (50) DEFAULT NULL COMMENT '产品ID',
	`category` VARCHAR (10) DEFAULT NULL COMMENT '类别',
	`sub_category` VARCHAR (10) DEFAULT NULL COMMENT '子类别',
	`product_name` VARCHAR (255) DEFAULT NULL COMMENT '产品名称',
	`sales` DOUBLE DEFAULT NULL COMMENT '销售额',
	`amount` INT (11) DEFAULT NULL COMMENT '数量',
	`discount` VARCHAR (255) DEFAULT NULL COMMENT '折扣',
	`profit` DOUBLE DEFAULT NULL COMMENT '利润',
	`shipping_cost` DOUBLE DEFAULT NULL COMMENT '装运成本',
	`order_priority` VARCHAR (5) DEFAULT NULL COMMENT '订单优先级',
	PRIMARY KEY (`id`)
) ;

CREATE TABLE `persons` (
	`id` INTEGER AUTO_INCREMENT NOT NULL COMMENT 'id',
	`name` VARCHAR (75) DEFAULT NULL COMMENT '人员',
	`region` VARCHAR (30) DEFAULT NULL COMMENT '地区',
	PRIMARY KEY (`id`)
) ;


CREATE TABLE `returns` (
	`returned` VARCHAR (5) DEFAULT NULL COMMENT '是否已退回',
	`order_id` VARCHAR (100) NOT NULL COMMENT '订单ID',
	`region` VARCHAR (30) DEFAULT NULL COMMENT '地区',
	PRIMARY KEY (`order_id`)
) ;


