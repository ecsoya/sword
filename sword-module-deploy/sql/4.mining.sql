# ************************************************************
# Sequel Pro SQL dump
# Version 4541
#
# http://www.sequelpro.com/
# https://github.com/sequelpro/sequelpro
#
# Host: bj-cdb-fmgl4u90.sql.tencentcdb.com (MySQL 5.7.18-txsql-log)
# Database: zbx-bga
# Generation Time: 2021-03-16 09:32:18 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table t_mining_level
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_mining_level`;

CREATE TABLE `t_mining_level` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(128) DEFAULT NULL COMMENT '名称',
  `rate` double(11,6) NOT NULL DEFAULT '0.000000' COMMENT '比例',
  `min_amount` double(20,6) NOT NULL DEFAULT '0.000000' COMMENT '最小值',
  `max_amount` double(20,6) NOT NULL DEFAULT '0.000000' COMMENT '最大值',
  `referral_amount` double(20,6) NOT NULL DEFAULT '0.000000' COMMENT '直推',
  `umbrella_amount` double(20,6) NOT NULL DEFAULT '0.000000' COMMENT '伞下',
  `child_level_count` int(4) NOT NULL DEFAULT '0' COMMENT '子级别人数',
  `child_level_id` bigint(20) DEFAULT NULL COMMENT '子级别ID',
  `type` int(1) NOT NULL DEFAULT '0' COMMENT '类型',
  `kind` int(1) NOT NULL DEFAULT '0' COMMENT '种类',
  `status` int(1) NOT NULL DEFAULT '0' COMMENT '状态',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` varchar(256) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Mining级别';



# Dump of table t_mining_symbol
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_mining_symbol`;

CREATE TABLE `t_mining_symbol` (
  `symbol` varchar(20) NOT NULL DEFAULT '' COMMENT '名称',
  `chain` varchar(128) NOT NULL DEFAULT '' COMMENT '链码',
  `withdrawal_fee` double(20,4) NOT NULL DEFAULT '0.0000' COMMENT '提币手续费',
  `withdrawal_enabled` int(1) NOT NULL DEFAULT '0' COMMENT '开关',
  `withdrawal_minimum` double(20,4) NOT NULL DEFAULT '0.0000' COMMENT '提币最小',
  `withdrawal_maximum` double(20,4) NOT NULL DEFAULT '0.0000' COMMENT '提币最多',
  `withdrawal_daily` double(20,4) NOT NULL DEFAULT '0.0000' COMMENT '提币日合计',
  `withdrawal_totally` double(20,4) NOT NULL DEFAULT '0.0000' COMMENT '提币总合计',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` varchar(256) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`symbol`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='币种';



# Dump of table t_user_binary_tree
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_user_binary_tree`;

CREATE TABLE `t_user_binary_tree` (
  `user_id` bigint(20) unsigned NOT NULL COMMENT '用户Id',
  `left_id` bigint(20) DEFAULT NULL COMMENT '左区ID',
  `left_time` timestamp NULL DEFAULT NULL COMMENT '左区时间',
  `right_id` bigint(20) DEFAULT NULL COMMENT '右区ID',
  `right_time` timestamp NULL DEFAULT NULL COMMENT '右区时间',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` varchar(256) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户双区树';



# Dump of table t_user_certificate
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_user_certificate`;

CREATE TABLE `t_user_certificate` (
  `user_id` bigint(20) unsigned NOT NULL COMMENT '用户ID',
  `type` int(1) NOT NULL DEFAULT '0' COMMENT '类型 0-身份证 1-护照',
  `country` varchar(64) DEFAULT NULL COMMENT '国家',
  `real_name` varchar(128) DEFAULT NULL COMMENT '实名',
  `identity_number` varchar(128) DEFAULT NULL COMMENT '号码',
  `front_image_url` varchar(1024) DEFAULT NULL COMMENT '正面照片',
  `back_image_url` varchar(1024) DEFAULT NULL COMMENT '背面照片',
  `holding_image_url` varchar(1024) DEFAULT NULL COMMENT '手持照片',
  `status` int(1) NOT NULL DEFAULT '0' COMMENT '状态',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` varchar(256) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户实名';



# Dump of table t_user_level
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_user_level`;

CREATE TABLE `t_user_level` (
  `user_id` bigint(20) unsigned NOT NULL COMMENT '用户Id',
  `level_id` bigint(20) DEFAULT NULL COMMENT '董事级别',
  `type` int(1) NOT NULL DEFAULT '0' COMMENT '类型 0-考核 1-后台设置',
  `status` int(1) NOT NULL DEFAULT '0' COMMENT '状态',
  `verify_time` timestamp NULL DEFAULT NULL COMMENT '考核时间',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` varchar(256) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户级别';



# Dump of table t_user_order_deposit
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_user_order_deposit`;

CREATE TABLE `t_user_order_deposit` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `order_no` varchar(32) NOT NULL DEFAULT '' COMMENT '订单ID',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `address` varchar(256) DEFAULT NULL COMMENT '充值地址',
  `amount` double(20,6) NOT NULL COMMENT '充值金额',
  `symbol` varchar(32) NOT NULL DEFAULT '' COMMENT '充值币种',
  `chain` varchar(32) DEFAULT NULL COMMENT '充值链',
  `status` int(1) NOT NULL DEFAULT '0' COMMENT '状态 0-开始 1-取消 2-成功 3-失败',
  `tx_id` varchar(256) NOT NULL DEFAULT '' COMMENT '交易ID',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` varchar(256) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `order_no_UNIQUE` (`order_no`),
  KEY `txid_UNIQUE` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='充值订单';



# Dump of table t_user_order_withdrawal
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_user_order_withdrawal`;

CREATE TABLE `t_user_order_withdrawal` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `order_no` varchar(32) NOT NULL DEFAULT '' COMMENT '订单ID',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `address` varchar(256) DEFAULT NULL COMMENT '提现地址',
  `amount` double(20,6) NOT NULL COMMENT '提现金额',
  `fee` double(20,6) NOT NULL COMMENT '手续费',
  `withdrawal` double(20,6) NOT NULL COMMENT '实际提现',
  `symbol` varchar(32) NOT NULL DEFAULT '' COMMENT '提现币种',
  `chain` varchar(32) DEFAULT NULL COMMENT '提现链',
  `status` int(1) NOT NULL DEFAULT '0' COMMENT '状态 0-开始 1-取消 2-成功 3-失败',
  `feedback` int(1) NOT NULL DEFAULT '0' COMMENT '回调',
  `callable` int(1) NOT NULL DEFAULT '0' COMMENT '调用接口',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` varchar(256) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  KEY `order_no_UNIQUE` (`order_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='提现订单';



# Dump of table t_user_record_deposit
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_user_record_deposit`;

CREATE TABLE `t_user_record_deposit` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `height` bigint(20) DEFAULT NULL COMMENT '区块高度',
  `tx_id` varchar(256) DEFAULT NULL COMMENT '交易Hash',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `order_no` varchar(32) DEFAULT NULL COMMENT '订单号',
  `type` int(1) NOT NULL DEFAULT '0' COMMENT '类型 0-后台设置 1-加 2-减',
  `symbol` varchar(32) NOT NULL COMMENT '币种',
  `amount` double(20,6) NOT NULL DEFAULT '0.000000' COMMENT '金额',
  `status` int(1) NOT NULL DEFAULT '0' COMMENT '状态 0-未同步 1-已同步',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` varchar(256) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  KEY `user_id_INDEX` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户充值';



# Dump of table t_user_record_withdrawal
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_user_record_withdrawal`;

CREATE TABLE `t_user_record_withdrawal` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `height` bigint(20) DEFAULT NULL COMMENT '区块高度',
  `tx_id` varchar(256) DEFAULT NULL COMMENT '交易Hash',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `order_no` varchar(32) DEFAULT NULL COMMENT '订单号',
  `type` int(1) NOT NULL DEFAULT '0' COMMENT '类型 0-后台设置 1-钱包回调',
  `symbol` varchar(32) NOT NULL COMMENT '币种',
  `amount` double(20,6) NOT NULL DEFAULT '0.000000' COMMENT '金额',
  `fee` double(20,6) NOT NULL DEFAULT '0.000000' COMMENT '手续费',
  `wallet_fee` double(20,6) NOT NULL DEFAULT '0.000000' COMMENT '交易手续费',
  `status` int(1) NOT NULL DEFAULT '0' COMMENT '状态 0-未同步 1-已同步',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` varchar(256) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  KEY `user_id_INDEX` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户提现';



# Dump of table t_user_referrer
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_user_referrer`;

CREATE TABLE `t_user_referrer` (
  `user_id` bigint(20) unsigned NOT NULL COMMENT '用户Id',
  `referral_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '推荐人ID',
  `referral_code` varchar(16) NOT NULL DEFAULT '0' COMMENT '推荐码',
  `left_code` varchar(16) NOT NULL DEFAULT '' COMMENT '左推荐码',
  `left_code_url` varchar(1024) DEFAULT NULL COMMENT '左推荐码URL',
  `left_code_enabled` int(1) NOT NULL DEFAULT '0' COMMENT '左推荐码可用',
  `right_code` varchar(16) DEFAULT NULL COMMENT '右推荐码',
  `right_code_url` varchar(1024) DEFAULT NULL COMMENT '右推荐码URL',
  `right_code_enabled` int(1) NOT NULL DEFAULT '1' COMMENT '右推荐码可用',
  `btree` int(1) NOT NULL DEFAULT '0' COMMENT 'B数',
  `base_url` varchar(1024) NOT NULL DEFAULT '' COMMENT 'URL',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` varchar(256) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `code_UNIQUE` (`left_code`),
  UNIQUE KEY `right_code_unique` (`right_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户直推';



# Dump of table t_user_wallet
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_user_wallet`;

CREATE TABLE `t_user_wallet` (
  `user_id` bigint(20) unsigned NOT NULL COMMENT 'ID',
  `name` varchar(128) DEFAULT NULL COMMENT '名称',
  `mnemonic` varchar(256) DEFAULT NULL COMMENT '助记ID',
  `password` varchar(256) DEFAULT NULL COMMENT '密码',
  `salt` varchar(32) DEFAULT NULL COMMENT '盐',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` varchar(256) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户钱包';



# Dump of table t_user_wallet_account
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_user_wallet_account`;

CREATE TABLE `t_user_wallet_account` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户Id',
  `symbol` varchar(16) NOT NULL DEFAULT '' COMMENT '币种',
  `address` varchar(256) DEFAULT NULL COMMENT '地址',
  `address_url` varchar(1024) DEFAULT NULL COMMENT '地址二维码URL',
  `amount` double(20,6) NOT NULL DEFAULT '0.000000' COMMENT '金额',
  `last_amount` double(20,6) NOT NULL DEFAULT '0.000000' COMMENT '昨日金额',
  `min_hold_amount` double(20,6) NOT NULL DEFAULT '0.000000' COMMENT '最低持币量',
  `frozen_amount` double(20,6) NOT NULL DEFAULT '0.000000' COMMENT '冻结金额',
  `locked_amount` double(20,6) NOT NULL DEFAULT '0.000000' COMMENT '锁定金额',
  `withdrawal_enabled` int(1) NOT NULL DEFAULT '0' COMMENT '提币开关',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` varchar(256) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_id_symbol` (`user_id`,`symbol`),
  UNIQUE KEY `address_UNIQUE` (`address`),
  KEY `address_INDEX` (`address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户钱包账号';



# Dump of table t_user_wallet_address
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_user_wallet_address`;

CREATE TABLE `t_user_wallet_address` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `name` varchar(128) DEFAULT NULL COMMENT '名称',
  `address` varchar(256) NOT NULL DEFAULT '' COMMENT '地址',
  `qrcode_url` varchar(1024) DEFAULT NULL,
  `symbol` varchar(16) NOT NULL DEFAULT '' COMMENT '币种',
  `type` int(1) NOT NULL DEFAULT '0' COMMENT '类型 (0 - 提现 1- 转账)',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` varchar(256) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户钱包地址';



# Dump of table t_user_wallet_record
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_user_wallet_record`;

CREATE TABLE `t_user_wallet_record` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `symbol` varchar(32) NOT NULL DEFAULT '' COMMENT '币种',
  `amount` double(20,6) NOT NULL DEFAULT '0.000000' COMMENT '金额',
  `type` int(1) NOT NULL DEFAULT '0' COMMENT '类型：0加1减',
  `kind` int(1) NOT NULL DEFAULT '0' COMMENT '种类：0余额1冻结',
  `status` int(1) NOT NULL DEFAULT '0' COMMENT '状态',
  `days` int(4) NOT NULL DEFAULT '-1' COMMENT '冻结天数',
  `order_no` varchar(128) DEFAULT NULL COMMENT '订单No',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` varchar(256) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户钱包记录';




/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
