CREATE DATABASE
IF NOT EXISTS `global-superstore` DEFAULT CHARACTER
SET utf8 COLLATE utf8_general_ci;

DROP TABLE
IF EXISTS `global-superstore`.`order`;

CREATE TABLE `global-superstore`.`order` (
  `id` BIGINT (20) NOT NULL COMMENT 'ID',
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
) ENGINE = INNODB DEFAULT CHARSET = utf8;

DROP TABLE
IF EXISTS `global-superstore`.`person`;

CREATE TABLE `global-superstore`.`person` (
  `id` INTEGER AUTO_INCREMENT NOT NULL COMMENT 'id',
  `name` VARCHAR (75) DEFAULT NULL COMMENT '人员',
  `region` VARCHAR (30) DEFAULT NULL COMMENT '地区',
  PRIMARY KEY (`id`)
) ENGINE = INNODB DEFAULT CHARSET = utf8;

DROP TABLE
IF EXISTS `global-superstore`.`returns`;

CREATE TABLE `global-superstore`.`returns` (
  `returned` VARCHAR (5) DEFAULT NULL COMMENT '是否已退回',
  `order_id` VARCHAR (100) NOT NULL COMMENT '订单ID',
  `region` VARCHAR (30) DEFAULT NULL COMMENT '地区',
  PRIMARY KEY (`order_id`)
) ENGINE = INNODB DEFAULT CHARSET = utf8;