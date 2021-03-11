# ************************************************************
# Sequel Pro SQL dump
# Version 4541
#
# http://www.sequelpro.com/
# https://github.com/sequelpro/sequelpro
#
# Host: bj-cdb-fmgl4u90.sql.tencentcdb.com (MySQL 5.7.18-txsql-log)
# Database: beeplus
# Generation Time: 2021-03-11 04:49:25 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


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

LOCK TABLES `t_user_level` WRITE;
/*!40000 ALTER TABLE `t_user_level` DISABLE KEYS */;

INSERT INTO `t_user_level` (`user_id`, `level_id`, `type`, `status`, `verify_time`, `create_time`, `update_time`, `remark`)
VALUES
	(1163,1,1,0,NULL,'2021-01-21 20:42:22',NULL,NULL),
	(1164,1,1,0,NULL,'2021-01-21 22:02:29',NULL,NULL),
	(1165,2,1,0,NULL,'2021-01-21 22:02:34',NULL,NULL);

/*!40000 ALTER TABLE `t_user_level` ENABLE KEYS */;
UNLOCK TABLES;


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

LOCK TABLES `t_user_order_deposit` WRITE;
/*!40000 ALTER TABLE `t_user_order_deposit` DISABLE KEYS */;

INSERT INTO `t_user_order_deposit` (`id`, `order_no`, `user_id`, `address`, `amount`, `symbol`, `chain`, `status`, `tx_id`, `create_time`, `update_time`, `remark`)
VALUES
	(1,'55f4d0807c3955e0c41718acd496a51d',1226,'TTPxH35LMWjJSuy5B5JtCqtb5JGNk1ebzp',820.000000,'usdt',NULL,1,'c347e83f5ceb6dfa0163d26ebcc8c7c5c5870da7a3b680876a42f78f1fd22c35','2021-01-23 09:24:48',NULL,NULL),
	(2,'2e10a26ec2b3469918d258549db4e9e2',1227,'TUdbp2VB5TQoaUjpZoR39WANmX6USRYppj',500.000000,'usdt',NULL,1,'6668515d2c8df4249ec109c2051ff12240751259b19e447787465844b65d064d','2021-01-23 09:46:09',NULL,NULL),
	(3,'849d143e0129164cc4cf68e3f48d6df7',1230,'TUHoudcQq7mjWzMMLGa6wYP9ow2cak4W5b',500.000000,'usdt',NULL,1,'3b2116f6d3f398666eb7b0edef00210d8b006b07c39c8f3afbdec125d34c953b','2021-01-23 09:54:06',NULL,NULL);

/*!40000 ALTER TABLE `t_user_order_deposit` ENABLE KEYS */;
UNLOCK TABLES;


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
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` varchar(256) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  KEY `order_no_UNIQUE` (`order_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='提现订单';

LOCK TABLES `t_user_order_withdrawal` WRITE;
/*!40000 ALTER TABLE `t_user_order_withdrawal` DISABLE KEYS */;

INSERT INTO `t_user_order_withdrawal` (`id`, `order_no`, `user_id`, `address`, `amount`, `fee`, `withdrawal`, `symbol`, `chain`, `status`, `create_time`, `update_time`, `remark`)
VALUES
	(1,'240bd830a14a3742b6c66a0c600e1960',1163,'aaa',2.100000,0.000000,2.100000,'fil',NULL,0,'2021-03-04 09:23:43',NULL,'提币申请'),
	(2,'fc24c7df14fd07c1c38482d4c8560def',1163,'aaa',2.100000,0.069914,2.030086,'fil',NULL,0,'2021-03-04 09:26:03',NULL,'提币申请'),
	(3,'854fbd2dd21cfbf1b2fdd834a450a5ef',1165,'aaaaaaa',1.000000,0.071073,0.928927,'fil',NULL,1,'2021-03-04 10:56:26','2021-03-04 10:58:12','sss'),
	(4,'c963dcbe8aa26f964bffee7fe17d29d2',1163,'123',3.643347,0.071463,3.571884,'fil',NULL,0,'2021-03-10 16:20:55',NULL,'提币申请');

/*!40000 ALTER TABLE `t_user_order_withdrawal` ENABLE KEYS */;
UNLOCK TABLES;


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

LOCK TABLES `t_user_referrer` WRITE;
/*!40000 ALTER TABLE `t_user_referrer` DISABLE KEYS */;

INSERT INTO `t_user_referrer` (`user_id`, `referral_id`, `referral_code`, `left_code`, `left_code_url`, `left_code_enabled`, `right_code`, `right_code_url`, `right_code_enabled`, `btree`, `base_url`, `create_time`, `update_time`, `remark`)
VALUES
	(3,0,'0','Luk28B4u','https://beecluster-1301683227.cos.ap-chongqing.myqcloud.com/2021/01/19/qrcode/1351387377095000066.png',0,'RbseCyLK','https://beecluster-1301683227.cos.ap-chongqing.myqcloud.com/2021/01/19/qrcode/1351387408556474369.png',0,1,'https://62.234.130.187/app/register.html?referrerCode=','2021-01-06 09:22:19','2021-01-19 12:34:01',NULL),
	(1163,3,'Luk28B4u','LUn3myG6','https://beecluster-1301683227.cos.ap-chongqing.myqcloud.com/2021/01/21/qrcode/1352239018552778753.png',0,'RXc8xmoL','https://beecluster-1301683227.cos.ap-chongqing.myqcloud.com/2021/01/21/qrcode/1352239020087894018.png',0,1,'https://62.234.130.187/app/register.html?referrerCode=','2021-01-21 20:38:04','2021-01-21 20:58:01',NULL),
	(1164,3,'RbseCyLK','L9Ctk87M','https://beecluster-1301683227.cos.ap-chongqing.myqcloud.com/2021/01/21/qrcode/1352239020524101634.png',0,'RKvwhUtz','https://beecluster-1301683227.cos.ap-chongqing.myqcloud.com/2021/01/21/qrcode/1352239021094526977.png',0,1,'https://62.234.130.187/app/register.html?referrerCode=','2021-01-21 20:38:14','2021-01-21 20:58:01',NULL),
	(1165,3,'Luk28B4u','LRrHwcrk','https://beecluster-1301683227.cos.ap-chongqing.myqcloud.com/2021/01/21/qrcode/1352239021585260546.png',0,'RAK6c8h5','https://beecluster-1301683227.cos.ap-chongqing.myqcloud.com/2021/01/21/qrcode/1352239022092771329.png',0,1,'https://62.234.130.187/app/register.html?referrerCode=','2021-01-21 20:38:29','2021-01-21 20:58:01',NULL),
	(1166,3,'Luk28B4u','Lc5mvlD6','https://beecluster-1301683227.cos.ap-chongqing.myqcloud.com/2021/01/21/qrcode/1352239022541561858.png',0,'R3paZChx','https://beecluster-1301683227.cos.ap-chongqing.myqcloud.com/2021/01/21/qrcode/1352239023103598594.png',0,1,'https://62.234.130.187/app/register.html?referrerCode=','2021-01-21 20:38:42','2021-01-21 20:58:01',NULL),
	(1167,3,'RbseCyLK','LkEQVzxQ','https://beecluster-1301683227.cos.ap-chongqing.myqcloud.com/2021/01/21/qrcode/1352239023804047362.png',0,'RlZZvf7J','https://beecluster-1301683227.cos.ap-chongqing.myqcloud.com/2021/01/21/qrcode/1352239024269615105.png',0,1,'https://62.234.130.187/app/register.html?referrerCode=','2021-01-21 20:38:54','2021-01-21 20:58:02',NULL),
	(1168,1163,'LUn3myG6','LZ2iqcV0','https://beecluster-1301683227.cos.ap-chongqing.myqcloud.com/2021/01/21/qrcode/1352239024697434114.png',0,'R8T5YIeF','https://beecluster-1301683227.cos.ap-chongqing.myqcloud.com/2021/01/21/qrcode/1352239025079115778.png',0,1,'https://62.234.130.187/app/register.html?referrerCode=','2021-01-21 20:39:05','2021-01-21 20:58:02',NULL),
	(1169,1163,'LUn3myG6','L79mVUJi','https://beecluster-1301683227.cos.ap-chongqing.myqcloud.com/2021/01/21/qrcode/1352239025511129090.png',0,'RcTrNYmA','https://beecluster-1301683227.cos.ap-chongqing.myqcloud.com/2021/01/21/qrcode/1352239026052194305.png',0,1,'https://62.234.130.187/app/register.html?referrerCode=','2021-01-21 20:39:18','2021-01-21 20:58:02',NULL),
	(1170,1163,'RXc8xmoL','L0sCFe03','https://beecluster-1301683227.cos.ap-chongqing.myqcloud.com/2021/01/21/qrcode/1352239026517762049.png',0,'RaZ8BJHy','https://beecluster-1301683227.cos.ap-chongqing.myqcloud.com/2021/01/21/qrcode/1352239026949775362.png',0,1,'https://62.234.130.187/app/register.html?referrerCode=','2021-01-21 20:39:29','2021-01-21 20:58:02',NULL),
	(1171,1164,'L9Ctk87M','LRNQVAmW','https://beecluster-1301683227.cos.ap-chongqing.myqcloud.com/2021/01/21/qrcode/1352239027411148802.png',0,'RJ0BYFMX','https://beecluster-1301683227.cos.ap-chongqing.myqcloud.com/2021/01/21/qrcode/1352239027851550722.png',0,1,'https://62.234.130.187/app/register.html?referrerCode=','2021-01-21 20:39:40','2021-01-21 20:58:02',NULL),
	(1172,1164,'RKvwhUtz','LqW3cfd2','https://beecluster-1301683227.cos.ap-chongqing.myqcloud.com/2021/01/21/qrcode/1352239028384227329.png',0,'R8jmq2do','https://beecluster-1301683227.cos.ap-chongqing.myqcloud.com/2021/01/21/qrcode/1352239028853989377.png',0,1,'https://62.234.130.187/app/register.html?referrerCode=','2021-01-21 20:39:50','2021-01-21 20:58:03',NULL),
	(1173,1164,'RKvwhUtz','L5VHVIA8','https://beecluster-1301683227.cos.ap-chongqing.myqcloud.com/2021/01/21/qrcode/1352239029311168514.png',0,'Rbc1Nvak','https://beecluster-1301683227.cos.ap-chongqing.myqcloud.com/2021/01/21/qrcode/1352239029898371074.png',0,1,'https://62.234.130.187/app/register.html?referrerCode=','2021-01-21 20:40:00','2021-01-21 20:58:03',NULL),
	(1174,1164,'L9Ctk87M','L5MVD6uA','https://beecluster-1301683227.cos.ap-chongqing.myqcloud.com/2021/01/21/qrcode/1352239030502350850.png',0,'RMBpOvXC','https://beecluster-1301683227.cos.ap-chongqing.myqcloud.com/2021/01/21/qrcode/1352239030917586946.png',0,1,'https://62.234.130.187/app/register.html?referrerCode=','2021-01-21 20:40:15','2021-01-21 20:58:03',NULL),
	(1175,1167,'RlZZvf7J','LPpvYBIF','https://beecluster-1301683227.cos.ap-chongqing.myqcloud.com/2021/01/21/qrcode/1352254118088200193.png',0,'RF1Af7Jk','https://beecluster-1301683227.cos.ap-chongqing.myqcloud.com/2021/01/21/qrcode/1352254118868340738.png',0,1,'https://62.234.130.187/app/register.html?referrerCode=','2021-01-21 21:54:18','2021-01-21 21:58:00',NULL),
	(1263,1170,'L0sCFe03','Ljek36Ih','https://beecluster-1301683227.file.myqcloud.com/2021/03/08/qrcode/1368855183666429954.png',0,'RcMCRM9R','https://beecluster-1301683227.file.myqcloud.com/2021/03/08/qrcode/1368855185914576897.png',1,1,'https://62.234.130.187/app/register.html?referrerCode=','2021-03-08 17:24:42','2021-03-11 12:29:00',NULL),
	(1264,1170,'RaZ8BJHy','Lp9DGYTb','https://beecluster-1301683227.file.myqcloud.com/2021/03/08/qrcode/1368856224017399809.png',1,'RWOeGiJA','https://beecluster-1301683227.file.myqcloud.com/2021/03/08/qrcode/1368856224470384642.png',1,1,'https://62.234.130.187/app/register.html?referrerCode=','2021-03-08 17:28:50','2021-03-11 12:29:00',NULL);

/*!40000 ALTER TABLE `t_user_referrer` ENABLE KEYS */;
UNLOCK TABLES;


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

LOCK TABLES `t_user_wallet` WRITE;
/*!40000 ALTER TABLE `t_user_wallet` DISABLE KEYS */;

INSERT INTO `t_user_wallet` (`user_id`, `name`, `mnemonic`, `password`, `salt`, `create_time`, `update_time`, `remark`)
VALUES
	(1163,NULL,NULL,'cfc9058f6b0254eb1ed567e182c7d7cf','338475','2021-01-21 20:38:04','2021-03-03 08:55:05',NULL),
	(1164,NULL,NULL,'1043aaf0ce4e2fdb51682b32c0a05ef5','358794','2021-01-21 20:38:14','2021-01-21 20:38:14',NULL),
	(1165,NULL,NULL,'79d879b6bcaf5c518c93e2ac63eed373','575865','2021-01-21 20:38:29','2021-01-21 20:38:29',NULL),
	(1166,NULL,NULL,'a7ded05b1a16738c1b37619fd894e453','738514','2021-01-21 20:38:42','2021-01-21 20:38:42',NULL),
	(1167,NULL,NULL,'3804a6c66afa62c936c6c878ec569831','379684','2021-01-21 20:38:54','2021-01-21 20:38:54',NULL),
	(1168,NULL,NULL,'df08c7fa70a27c7c5a44c9698c45c865','131865','2021-01-21 20:39:05','2021-01-21 20:39:05',NULL),
	(1169,NULL,NULL,'590579f8da1351dae2828b88143e3fcb','449628','2021-01-21 20:39:19','2021-01-21 20:39:19',NULL),
	(1170,NULL,NULL,'c10144f2efc3430470ea6dc1edf35d17','763169','2021-01-21 20:39:29','2021-01-21 20:39:29',NULL),
	(1171,NULL,NULL,'13aaa652bf7ee20f21a89d4e7ff5e200','519239','2021-01-21 20:39:40','2021-01-21 20:39:40',NULL),
	(1172,NULL,NULL,'7271f769765c09fa3d9b71226fcb59f8','281371','2021-01-21 20:39:51','2021-01-21 20:39:51',NULL),
	(1173,NULL,NULL,'8b432f55eb8df6d4cf1d606bafc6bde0','279177','2021-01-21 20:40:00','2021-01-21 20:40:00',NULL),
	(1174,NULL,NULL,'e2a712a35802c8d0b5e50346c6149bc0','951591','2021-01-21 20:40:15','2021-01-21 20:40:15',NULL),
	(1175,NULL,NULL,'7a15b1e00328722490001663b2096204','838997','2021-01-21 21:54:18','2021-01-21 21:54:18',NULL),
	(1263,NULL,NULL,'170dc13bf533bbe571fbd2e3f14667d1','664758','2021-03-08 17:24:43','2021-03-08 17:24:43',NULL),
	(1264,NULL,NULL,'69f2c339aeb354d9ae6a76fa13b46d20','428643','2021-03-08 17:28:51','2021-03-08 17:28:51',NULL);

/*!40000 ALTER TABLE `t_user_wallet` ENABLE KEYS */;
UNLOCK TABLES;


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

LOCK TABLES `t_user_wallet_account` WRITE;
/*!40000 ALTER TABLE `t_user_wallet_account` DISABLE KEYS */;

INSERT INTO `t_user_wallet_account` (`id`, `user_id`, `symbol`, `address`, `address_url`, `amount`, `last_amount`, `min_hold_amount`, `frozen_amount`, `locked_amount`, `withdrawal_enabled`, `create_time`, `update_time`, `remark`)
VALUES
	(1,1163,'fil','f1zxnn74jwnarmliamjxn2lz32lderqxq4pgqgmjq','https://beecluster-1301683227.cos.ap-chongqing.myqcloud.com/2021/01/21/qrcode/1352234005260591106.png',87.000000,0.000000,0.000000,24.000000,0.252740,0,'2021-01-21 20:38:05','2021-03-11 11:34:50',NULL),
	(2,1163,'usdt','TYW9DSgxkCVEyp6fBiHnZ3cYLYhy1upNfK','https://beecluster-1301683227.cos.ap-chongqing.myqcloud.com/2021/01/21/qrcode/1352234008762834945.png',998953.000000,0.000000,0.000000,0.000000,0.000000,0,'2021-01-21 20:38:06','2021-03-11 11:34:50',NULL),
	(3,1164,'fil','f1yvz2hz2aihcr77qlgci63cufwgdku7jlpe3knny','https://beecluster-1301683227.cos.ap-chongqing.myqcloud.com/2021/01/21/qrcode/1352234047836971009.png',0.000000,0.000000,0.000000,0.000000,0.000000,0,'2021-01-21 20:38:15','2021-03-10 11:39:17',NULL),
	(4,1164,'usdt','TYaBAQyi3DTq3EUJoxK8iAXPQd5BPAk3SW','https://beecluster-1301683227.cos.ap-chongqing.myqcloud.com/2021/01/21/qrcode/1352234050802343937.png',14.000000,0.000000,0.000000,0.000000,0.000000,0,'2021-01-21 20:38:16','2021-03-10 22:33:48',NULL),
	(5,1165,'fil','f1y6r7nnedsrrrmgyynaxcgqc2eimjp4jh2wblfey','https://beecluster-1301683227.cos.ap-chongqing.myqcloud.com/2021/01/21/qrcode/1352234108780208130.png',0.000000,0.000000,0.000000,0.000000,0.000000,0,'2021-01-21 20:38:30','2021-03-10 22:11:04',NULL),
	(6,1165,'usdt','TXGHKmhwbhS7cHfzZ7HQCPojx8NJGUNSih','https://beecluster-1301683227.cos.ap-chongqing.myqcloud.com/2021/01/21/qrcode/1352234111405842433.png',16.800000,0.000000,0.000000,0.000000,0.000000,0,'2021-01-21 20:38:30','2021-03-10 22:33:48',NULL),
	(7,1166,'fil','f1y44yjmpx3cs2bjxf5fj6mbsludvqzei7bs2kddi','https://beecluster-1301683227.cos.ap-chongqing.myqcloud.com/2021/01/21/qrcode/1352234164862246913.png',0.000000,0.000000,0.000000,0.000000,0.000000,0,'2021-01-21 20:38:43','2021-03-10 11:39:17',NULL),
	(8,1166,'usdt','TX2UYZsPDBbpaZpfBZsb4xtKL9viwNV7bJ','https://beecluster-1301683227.cos.ap-chongqing.myqcloud.com/2021/01/21/qrcode/1352234167773093889.png',0.000000,0.000000,0.000000,0.000000,0.000000,0,'2021-01-21 20:38:44','2021-01-21 21:27:42',NULL),
	(9,1167,'fil','f1xupjdinwm3yzolgwtx3257nzoow7wwn5rrzgtwq','https://beecluster-1301683227.cos.ap-chongqing.myqcloud.com/2021/01/21/qrcode/1352234215227449345.png',0.000000,0.000000,0.000000,0.000000,0.000000,0,'2021-01-21 20:38:55','2021-03-10 11:39:17',NULL),
	(10,1167,'usdt','TWNX7sZUEbztWgpYjGUVTN8oDsrJgVqqrX','https://beecluster-1301683227.cos.ap-chongqing.myqcloud.com/2021/01/21/qrcode/1352234217995689985.png',0.000000,0.000000,0.000000,0.000000,0.000000,0,'2021-01-21 20:38:56','2021-01-21 21:59:26',NULL),
	(11,1168,'fil','f1vzxq42qmbxmyrgdwonw754t6jy3ndintj6bn4gy','https://beecluster-1301683227.cos.ap-chongqing.myqcloud.com/2021/01/21/qrcode/1352234260450435074.png',0.000000,0.000000,0.000000,0.000000,0.000000,0,'2021-01-21 20:39:06','2021-03-10 11:39:17',NULL),
	(12,1168,'usdt','TW4PjYchKAEZPqaGwj6oGNTB2XWJEw8nJ6','https://beecluster-1301683227.cos.ap-chongqing.myqcloud.com/2021/01/21/qrcode/1352234263361282049.png',0.000000,0.000000,0.000000,0.000000,0.000000,0,'2021-01-21 20:39:06','2021-01-21 21:27:42',NULL),
	(13,1169,'fil','f1uejhxxj7upccdm6ysnz6azfkrmu3x4owkq5pg4y','https://beecluster-1301683227.cos.ap-chongqing.myqcloud.com/2021/01/21/qrcode/1352234318109532161.png',0.000000,0.000000,0.000000,0.000000,0.000000,0,'2021-01-21 20:39:20','2021-03-10 11:39:17',NULL),
	(14,1169,'usdt','TW25TASMqyX7GngGfGcXEmU4bHG4BwAcnN','https://beecluster-1301683227.cos.ap-chongqing.myqcloud.com/2021/01/21/qrcode/1352234321150402562.png',0.000000,0.000000,0.000000,0.000000,0.000000,0,'2021-01-21 20:39:20','2021-01-21 21:27:42',NULL),
	(15,1170,'fil','f1u2xzhpl6yyrczrndm6v4vsh4yxmm4elvc5q6fpa','https://beecluster-1301683227.cos.ap-chongqing.myqcloud.com/2021/01/21/qrcode/1352234360748826625.png',0.000000,0.000000,0.000000,0.000000,0.000000,0,'2021-01-21 20:39:30','2021-03-10 11:39:17',NULL),
	(16,1170,'usdt','TVXcMUeL2wdTbeJPfVXSyZ5jrySdzHHsrf','https://beecluster-1301683227.cos.ap-chongqing.myqcloud.com/2021/01/21/qrcode/1352234363525455874.png',0.000000,0.000000,0.000000,0.000000,0.000000,0,'2021-01-21 20:39:30','2021-03-10 11:39:17',NULL),
	(17,1171,'fil','f1twu3wbhxcrfahgdthy4muiuqcnqevi2i256iqia','https://beecluster-1301683227.cos.ap-chongqing.myqcloud.com/2021/01/21/qrcode/1352234406630318082.png',0.000000,0.000000,0.000000,0.000000,0.000000,0,'2021-01-21 20:39:41','2021-03-10 11:39:17',NULL),
	(18,1171,'usdt','TViVwguZjWVtGBSmfipJHC4uwtyS6EPe8r','https://beecluster-1301683227.cos.ap-chongqing.myqcloud.com/2021/01/21/qrcode/1352234409293701121.png',0.000000,0.000000,0.000000,0.000000,0.000000,0,'2021-01-21 20:39:41','2021-01-21 21:27:42',NULL),
	(19,1172,'fil','f1tqyqxwxzsdrcwhvoraxztgqx5z62es5o5sslgwi','https://beecluster-1301683227.cos.ap-chongqing.myqcloud.com/2021/01/21/qrcode/1352234450955722753.png',0.000000,0.000000,0.000000,0.000000,0.000000,0,'2021-01-21 20:39:51','2021-01-21 21:59:26',NULL),
	(20,1172,'usdt','TVB8pn7hto8zLx7QaCEnCtsqdEdJQQyrBM','https://beecluster-1301683227.cos.ap-chongqing.myqcloud.com/2021/01/21/qrcode/1352234453749129217.png',0.000000,0.000000,0.000000,0.000000,0.000000,0,'2021-01-21 20:39:52','2021-01-21 21:27:42',NULL),
	(21,1173,'fil','f1td4pgacogpk3ium4bv5x6kfvwxo7qeecjyh3tuq','https://beecluster-1301683227.cos.ap-chongqing.myqcloud.com/2021/01/21/qrcode/1352234495381790722.png',0.000000,0.000000,0.000000,0.000000,0.000000,0,'2021-01-21 20:40:02','2021-03-10 11:39:17',NULL),
	(22,1173,'usdt','TV9GuJ6Zy8sK2FdjQ5SHRTvsgRQXbvLYTf','https://beecluster-1301683227.cos.ap-chongqing.myqcloud.com/2021/01/21/qrcode/1352234498196168705.png',0.000000,0.000000,0.000000,0.000000,0.000000,0,'2021-01-21 20:40:02','2021-01-21 21:27:42',NULL),
	(23,1174,'fil','f1t6b4iexzu7fmcn4muc4e7rk2nlpt3qouqtg2tay','https://beecluster-1301683227.cos.ap-chongqing.myqcloud.com/2021/01/21/qrcode/1352234553535815681.png',0.000000,0.000000,0.000000,0.000000,0.000000,0,'2021-01-21 20:40:16','2021-03-10 11:39:17',NULL),
	(24,1174,'usdt','TV2Q25zco9wwm5Sh7htwQysr69ztTHoMHh','https://beecluster-1301683227.cos.ap-chongqing.myqcloud.com/2021/01/21/qrcode/1352234556132089858.png',0.000000,0.000000,0.000000,0.000000,0.000000,0,'2021-01-21 20:40:16','2021-01-21 21:27:42',NULL),
	(25,3,'usdt','TUEsL7g7yY8YPzytQ9oXH9wemaMigEUFSE','https://beecluster-1301683227.cos.ap-chongqing.myqcloud.com/2021/01/21/qrcode/1352235714301059074.png',0.000000,0.000000,0.000000,0.000000,0.000000,0,'2021-01-21 20:44:52','2021-01-21 21:53:53',NULL),
	(28,1175,'fil','f1hld7mjlbb3fkxwzjoeuuexgdz3sovzy3pcf7qsa','https://beecluster-1301683227.cos.ap-chongqing.myqcloud.com/2021/01/20/qrcode/1351895080507084801.png',0.000000,0.000000,0.000000,0.000000,0.000000,0,'2021-01-20 22:11:19','2021-01-21 21:59:26',NULL),
	(29,1175,'usdt','TWdiFqJzWV2PxYDCNvDPmjFXgVwWVVAqTZ','https://beecluster-1301683227.cos.ap-chongqing.myqcloud.com/2021/01/20/qrcode/1351895083581509634.png',0.000000,0.000000,0.000000,0.000000,0.000000,0,'2021-01-20 22:11:20','2021-01-20 22:14:10',NULL),
	(36,1263,'fil','f1hld7mjlbsb3fkxwzjoeuuexgdz3sovzy3pcf7qsa','https://beecluster-1301683227.cos.ap-chongqing.myqcloud.com/2021/01/20/qrcode/1351895080507084801.png',0.000000,0.000000,0.000000,0.000000,0.000000,0,'2021-01-20 22:11:19','2021-03-10 15:33:57',NULL),
	(37,1263,'usdt','TWdiFqJzWV2PsxYDCNvDPmjFXgVwWVVAqTZ','https://beecluster-1301683227.cos.ap-chongqing.myqcloud.com/2021/01/20/qrcode/1351895083581509634.png',0.000000,0.000000,0.000000,0.000000,0.000000,0,'2021-01-20 22:11:20','2021-03-10 15:33:57',NULL);

/*!40000 ALTER TABLE `t_user_wallet_account` ENABLE KEYS */;
UNLOCK TABLES;


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

LOCK TABLES `t_user_wallet_record` WRITE;
/*!40000 ALTER TABLE `t_user_wallet_record` DISABLE KEYS */;

INSERT INTO `t_user_wallet_record` (`id`, `user_id`, `symbol`, `amount`, `type`, `kind`, `status`, `days`, `order_no`, `create_time`, `update_time`, `remark`)
VALUES
	(1,1163,'usdt',999999.000000,2,0,1,-1,NULL,'2021-03-10 22:23:08',NULL,'后台设置'),
	(2,1163,'usdt',550.000000,1,0,0,-1,'8acdb757b9223fc41e677a864525f149','2021-03-10 22:23:10',NULL,NULL),
	(3,1163,'fil',8.000000,0,1,0,-1,'8acdb757b9223fc41e677a864525f149','2021-03-10 22:23:10',NULL,'矿机质押'),
	(4,1163,'usdt',10.000000,1,0,0,-1,'2420b4594227d85236cc356f2b59f307','2021-03-10 22:23:58',NULL,NULL),
	(5,1163,'fil',8.000000,0,1,0,-1,'2420b4594227d85236cc356f2b59f307','2021-03-10 22:23:58',NULL,'矿机质押'),
	(6,1163,'fil',0.015319,0,0,0,-1,'2420b4594227d85236cc356f2b59f307_20210309235959','2021-03-10 22:33:28',NULL,'静态收益'),
	(7,1163,'fil',0.045956,0,2,1,0,'2420b4594227d85236cc356f2b59f307_20210309235959','2021-03-10 22:33:28','2021-03-10 22:37:03','静态收益'),
	(8,1163,'fil',0.357500,1,0,0,-1,'20210309235959_1163_2','2021-03-10 22:33:28',NULL,'归还贷款利息第10-1期'),
	(9,1163,'fil',0.015319,0,0,0,-1,'2420b4594227d85236cc356f2b59f307_20210310235959','2021-03-10 22:33:48',NULL,'静态收益'),
	(10,1163,'fil',0.045956,0,2,1,0,'2420b4594227d85236cc356f2b59f307_20210310235959','2021-03-10 22:33:48','2021-03-10 22:37:04','静态收益'),
	(11,1163,'usdt',14.000000,0,0,0,-1,'1','2021-03-10 22:33:48',NULL,'节点分红'),
	(12,1164,'usdt',14.000000,0,0,0,-1,'2','2021-03-10 22:33:48',NULL,'节点分红'),
	(13,1165,'usdt',16.800000,0,0,0,-1,'3','2021-03-10 22:33:48',NULL,'节点分红'),
	(14,1163,'fil',0.004596,0,0,0,-1,'2420b4594227d85236cc356f2b59f307_20210309235959_10','2021-03-10 22:33:48',NULL,'释放锁定余额'),
	(15,1163,'fil',0.357500,1,0,0,-1,'20210310235959_1163_2','2021-03-10 22:33:48',NULL,'归还贷款利息第10-2期'),
	(16,1163,'fil',0.015319,0,0,0,-1,'2420b4594227d85236cc356f2b59f307_20210311235959','2021-03-10 22:36:58',NULL,'静态收益'),
	(17,1163,'fil',0.045956,0,2,0,1,'2420b4594227d85236cc356f2b59f307_20210311235959','2021-03-10 22:36:58','2021-03-10 22:37:04','静态收益'),
	(18,1163,'fil',0.004596,0,0,0,-1,'2420b4594227d85236cc356f2b59f307_20210309235959_9','2021-03-10 22:36:58',NULL,'释放锁定余额'),
	(19,1163,'fil',0.004596,0,0,0,-1,'2420b4594227d85236cc356f2b59f307_20210310235959_10','2021-03-10 22:36:58',NULL,'释放锁定余额'),
	(20,1163,'fil',0.357500,1,0,0,-1,'20210311235959_1163_2','2021-03-10 22:36:58',NULL,'归还贷款利息第10-3期'),
	(21,1163,'fil',0.015319,0,0,0,-1,'2420b4594227d85236cc356f2b59f307_20210312235959','2021-03-10 22:36:58',NULL,'静态收益'),
	(22,1163,'fil',0.045956,0,2,0,2,'2420b4594227d85236cc356f2b59f307_20210312235959','2021-03-10 22:36:58','2021-03-10 22:37:04','静态收益'),
	(23,1163,'fil',0.004596,0,0,0,-1,'2420b4594227d85236cc356f2b59f307_20210309235959_8','2021-03-10 22:36:59',NULL,'释放锁定余额'),
	(24,1163,'fil',0.004596,0,0,0,-1,'2420b4594227d85236cc356f2b59f307_20210310235959_9','2021-03-10 22:36:59',NULL,'释放锁定余额'),
	(25,1163,'fil',0.004596,0,0,0,-1,'2420b4594227d85236cc356f2b59f307_20210311235959_10','2021-03-10 22:36:59',NULL,'释放锁定余额'),
	(26,1163,'fil',0.357500,1,0,0,-1,'20210312235959_1163_2','2021-03-10 22:36:59',NULL,'归还贷款利息第10-4期'),
	(27,1163,'fil',0.015319,0,0,0,-1,'2420b4594227d85236cc356f2b59f307_20210313235959','2021-03-10 22:36:59',NULL,'静态收益'),
	(28,1163,'fil',0.045956,0,2,0,3,'2420b4594227d85236cc356f2b59f307_20210313235959','2021-03-10 22:36:59','2021-03-10 22:37:04','静态收益'),
	(29,1163,'fil',0.004596,0,0,0,-1,'2420b4594227d85236cc356f2b59f307_20210309235959_7','2021-03-10 22:36:59',NULL,'释放锁定余额'),
	(30,1163,'fil',0.004596,0,0,0,-1,'2420b4594227d85236cc356f2b59f307_20210310235959_8','2021-03-10 22:36:59',NULL,'释放锁定余额'),
	(31,1163,'fil',0.004596,0,0,0,-1,'2420b4594227d85236cc356f2b59f307_20210311235959_9','2021-03-10 22:36:59',NULL,'释放锁定余额'),
	(32,1163,'fil',0.004596,0,0,0,-1,'2420b4594227d85236cc356f2b59f307_20210312235959_10','2021-03-10 22:36:59',NULL,'释放锁定余额'),
	(33,1163,'fil',0.357500,1,0,0,-1,'20210313235959_1163_2','2021-03-10 22:36:59',NULL,'归还贷款利息第10-5期'),
	(34,1163,'fil',0.015319,0,0,0,-1,'2420b4594227d85236cc356f2b59f307_20210314235959','2021-03-10 22:36:59',NULL,'静态收益'),
	(35,1163,'fil',0.045956,0,2,0,4,'2420b4594227d85236cc356f2b59f307_20210314235959','2021-03-10 22:36:59','2021-03-10 22:37:04','静态收益'),
	(36,1163,'fil',0.004596,0,0,0,-1,'2420b4594227d85236cc356f2b59f307_20210309235959_6','2021-03-10 22:37:00',NULL,'释放锁定余额'),
	(37,1163,'fil',0.004596,0,0,0,-1,'2420b4594227d85236cc356f2b59f307_20210310235959_7','2021-03-10 22:37:00',NULL,'释放锁定余额'),
	(38,1163,'fil',0.004596,0,0,0,-1,'2420b4594227d85236cc356f2b59f307_20210311235959_8','2021-03-10 22:37:00',NULL,'释放锁定余额'),
	(39,1163,'fil',0.004596,0,0,0,-1,'2420b4594227d85236cc356f2b59f307_20210312235959_9','2021-03-10 22:37:00',NULL,'释放锁定余额'),
	(40,1163,'fil',0.004596,0,0,0,-1,'2420b4594227d85236cc356f2b59f307_20210313235959_10','2021-03-10 22:37:00',NULL,'释放锁定余额'),
	(41,1163,'fil',0.357500,1,0,0,-1,'20210314235959_1163_2','2021-03-10 22:37:00',NULL,'归还贷款利息第10-6期'),
	(42,1163,'fil',0.015319,0,0,0,-1,'2420b4594227d85236cc356f2b59f307_20210315235959','2021-03-10 22:37:00',NULL,'静态收益'),
	(43,1163,'fil',0.045956,0,2,0,5,'2420b4594227d85236cc356f2b59f307_20210315235959','2021-03-10 22:37:00','2021-03-10 22:37:04','静态收益'),
	(44,1163,'fil',0.004596,0,0,0,-1,'2420b4594227d85236cc356f2b59f307_20210309235959_5','2021-03-10 22:37:00',NULL,'释放锁定余额'),
	(45,1163,'fil',0.004596,0,0,0,-1,'2420b4594227d85236cc356f2b59f307_20210310235959_6','2021-03-10 22:37:00',NULL,'释放锁定余额'),
	(46,1163,'fil',0.004596,0,0,0,-1,'2420b4594227d85236cc356f2b59f307_20210311235959_7','2021-03-10 22:37:00',NULL,'释放锁定余额'),
	(47,1163,'fil',0.004596,0,0,0,-1,'2420b4594227d85236cc356f2b59f307_20210312235959_8','2021-03-10 22:37:00',NULL,'释放锁定余额'),
	(48,1163,'fil',0.004596,0,0,0,-1,'2420b4594227d85236cc356f2b59f307_20210313235959_9','2021-03-10 22:37:01',NULL,'释放锁定余额'),
	(49,1163,'fil',0.004596,0,0,0,-1,'2420b4594227d85236cc356f2b59f307_20210314235959_10','2021-03-10 22:37:01',NULL,'释放锁定余额'),
	(50,1163,'fil',0.357500,1,0,0,-1,'20210315235959_1163_2','2021-03-10 22:37:01',NULL,'归还贷款利息第10-7期'),
	(51,1163,'fil',0.015319,0,0,0,-1,'2420b4594227d85236cc356f2b59f307_20210316235959','2021-03-10 22:37:01',NULL,'静态收益'),
	(52,1163,'fil',0.045956,0,2,0,6,'2420b4594227d85236cc356f2b59f307_20210316235959','2021-03-10 22:37:01','2021-03-10 22:37:04','静态收益'),
	(53,1163,'fil',0.004596,0,0,0,-1,'2420b4594227d85236cc356f2b59f307_20210309235959_4','2021-03-10 22:37:01',NULL,'释放锁定余额'),
	(54,1163,'fil',0.004596,0,0,0,-1,'2420b4594227d85236cc356f2b59f307_20210310235959_5','2021-03-10 22:37:01',NULL,'释放锁定余额'),
	(55,1163,'fil',0.004596,0,0,0,-1,'2420b4594227d85236cc356f2b59f307_20210311235959_6','2021-03-10 22:37:01',NULL,'释放锁定余额'),
	(56,1163,'fil',0.004596,0,0,0,-1,'2420b4594227d85236cc356f2b59f307_20210312235959_7','2021-03-10 22:37:01',NULL,'释放锁定余额'),
	(57,1163,'fil',0.004596,0,0,0,-1,'2420b4594227d85236cc356f2b59f307_20210313235959_8','2021-03-10 22:37:01',NULL,'释放锁定余额'),
	(58,1163,'fil',0.004596,0,0,0,-1,'2420b4594227d85236cc356f2b59f307_20210314235959_9','2021-03-10 22:37:01',NULL,'释放锁定余额'),
	(59,1163,'fil',0.004596,0,0,0,-1,'2420b4594227d85236cc356f2b59f307_20210315235959_10','2021-03-10 22:37:01',NULL,'释放锁定余额'),
	(60,1163,'fil',0.357500,1,0,0,-1,'20210316235959_1163_2','2021-03-10 22:37:01',NULL,'归还贷款利息第10-8期'),
	(61,1163,'fil',0.015319,0,0,0,-1,'2420b4594227d85236cc356f2b59f307_20210317235959','2021-03-10 22:37:02',NULL,'静态收益'),
	(62,1163,'fil',0.045956,0,2,0,7,'2420b4594227d85236cc356f2b59f307_20210317235959','2021-03-10 22:37:02','2021-03-10 22:37:04','静态收益'),
	(63,1163,'fil',0.004596,0,0,0,-1,'2420b4594227d85236cc356f2b59f307_20210309235959_3','2021-03-10 22:37:02',NULL,'释放锁定余额'),
	(64,1163,'fil',0.004596,0,0,0,-1,'2420b4594227d85236cc356f2b59f307_20210310235959_4','2021-03-10 22:37:02',NULL,'释放锁定余额'),
	(65,1163,'fil',0.004596,0,0,0,-1,'2420b4594227d85236cc356f2b59f307_20210311235959_5','2021-03-10 22:37:02',NULL,'释放锁定余额'),
	(66,1163,'fil',0.004596,0,0,0,-1,'2420b4594227d85236cc356f2b59f307_20210312235959_6','2021-03-10 22:37:02',NULL,'释放锁定余额'),
	(67,1163,'fil',0.004596,0,0,0,-1,'2420b4594227d85236cc356f2b59f307_20210313235959_7','2021-03-10 22:37:02',NULL,'释放锁定余额'),
	(68,1163,'fil',0.004596,0,0,0,-1,'2420b4594227d85236cc356f2b59f307_20210314235959_8','2021-03-10 22:37:02',NULL,'释放锁定余额'),
	(69,1163,'fil',0.004596,0,0,0,-1,'2420b4594227d85236cc356f2b59f307_20210315235959_9','2021-03-10 22:37:02',NULL,'释放锁定余额'),
	(70,1163,'fil',0.004596,0,0,0,-1,'2420b4594227d85236cc356f2b59f307_20210316235959_10','2021-03-10 22:37:02',NULL,'释放锁定余额'),
	(71,1163,'fil',0.357500,1,0,0,-1,'20210317235959_1163_2','2021-03-10 22:37:02',NULL,'归还贷款利息第10-9期'),
	(72,1163,'fil',0.015319,0,0,0,-1,'2420b4594227d85236cc356f2b59f307_20210318235959','2021-03-10 22:37:02',NULL,'静态收益'),
	(73,1163,'fil',0.045956,0,2,0,8,'2420b4594227d85236cc356f2b59f307_20210318235959','2021-03-10 22:37:02','2021-03-10 22:37:04','静态收益'),
	(74,1163,'fil',0.004596,0,0,0,-1,'2420b4594227d85236cc356f2b59f307_20210309235959_2','2021-03-10 22:37:02',NULL,'释放锁定余额'),
	(75,1163,'fil',0.004596,0,0,0,-1,'2420b4594227d85236cc356f2b59f307_20210310235959_3','2021-03-10 22:37:02',NULL,'释放锁定余额'),
	(76,1163,'fil',0.004596,0,0,0,-1,'2420b4594227d85236cc356f2b59f307_20210311235959_4','2021-03-10 22:37:03',NULL,'释放锁定余额'),
	(77,1163,'fil',0.004596,0,0,0,-1,'2420b4594227d85236cc356f2b59f307_20210312235959_5','2021-03-10 22:37:03',NULL,'释放锁定余额'),
	(78,1163,'fil',0.004596,0,0,0,-1,'2420b4594227d85236cc356f2b59f307_20210313235959_6','2021-03-10 22:37:03',NULL,'释放锁定余额'),
	(79,1163,'fil',0.004596,0,0,0,-1,'2420b4594227d85236cc356f2b59f307_20210314235959_7','2021-03-10 22:37:03',NULL,'释放锁定余额'),
	(80,1163,'fil',0.004596,0,0,0,-1,'2420b4594227d85236cc356f2b59f307_20210315235959_8','2021-03-10 22:37:03',NULL,'释放锁定余额'),
	(81,1163,'fil',0.004596,0,0,0,-1,'2420b4594227d85236cc356f2b59f307_20210316235959_9','2021-03-10 22:37:03',NULL,'释放锁定余额'),
	(82,1163,'fil',0.004596,0,0,0,-1,'2420b4594227d85236cc356f2b59f307_20210317235959_10','2021-03-10 22:37:03',NULL,'释放锁定余额'),
	(83,1163,'fil',0.357500,1,0,0,-1,'20210318235959_1163_2','2021-03-10 22:37:03',NULL,'归还贷款利息第10-10期'),
	(84,1163,'fil',0.015319,0,0,0,-1,'2420b4594227d85236cc356f2b59f307_20210319235959','2021-03-10 22:37:03',NULL,'静态收益'),
	(85,1163,'fil',0.045956,0,2,0,9,'2420b4594227d85236cc356f2b59f307_20210319235959','2021-03-10 22:37:03','2021-03-10 22:37:04','静态收益'),
	(86,1163,'fil',0.004592,0,0,0,-1,'2420b4594227d85236cc356f2b59f307_20210309235959_1','2021-03-10 22:37:03',NULL,'释放锁定余额'),
	(87,1163,'fil',0.004596,0,0,0,-1,'2420b4594227d85236cc356f2b59f307_20210310235959_2','2021-03-10 22:37:03',NULL,'释放锁定余额'),
	(88,1163,'fil',0.004596,0,0,0,-1,'2420b4594227d85236cc356f2b59f307_20210311235959_3','2021-03-10 22:37:03',NULL,'释放锁定余额'),
	(89,1163,'fil',0.004596,0,0,0,-1,'2420b4594227d85236cc356f2b59f307_20210312235959_4','2021-03-10 22:37:03',NULL,'释放锁定余额'),
	(90,1163,'fil',0.004596,0,0,0,-1,'2420b4594227d85236cc356f2b59f307_20210313235959_5','2021-03-10 22:37:03',NULL,'释放锁定余额'),
	(91,1163,'fil',0.004596,0,0,0,-1,'2420b4594227d85236cc356f2b59f307_20210314235959_6','2021-03-10 22:37:03',NULL,'释放锁定余额'),
	(92,1163,'fil',0.004596,0,0,0,-1,'2420b4594227d85236cc356f2b59f307_20210315235959_7','2021-03-10 22:37:03',NULL,'释放锁定余额'),
	(93,1163,'fil',0.004596,0,0,0,-1,'2420b4594227d85236cc356f2b59f307_20210316235959_8','2021-03-10 22:37:03',NULL,'释放锁定余额'),
	(94,1163,'fil',0.004596,0,0,0,-1,'2420b4594227d85236cc356f2b59f307_20210317235959_9','2021-03-10 22:37:04',NULL,'释放锁定余额'),
	(95,1163,'fil',0.004596,0,0,0,-1,'2420b4594227d85236cc356f2b59f307_20210318235959_10','2021-03-10 22:37:04',NULL,'释放锁定余额'),
	(96,1163,'fil',13.000000,1,0,0,-1,'20210310223703_1163_2','2021-03-10 22:37:04',NULL,'归还贷款本金'),
	(97,1163,'fil',0.015319,0,0,0,-1,'2420b4594227d85236cc356f2b59f307_20210320235959','2021-03-10 22:37:04',NULL,'静态收益'),
	(98,1163,'fil',0.045956,0,2,0,10,'2420b4594227d85236cc356f2b59f307_20210320235959','2021-03-10 22:37:04',NULL,'静态收益'),
	(99,1163,'fil',0.004592,0,0,0,-1,'2420b4594227d85236cc356f2b59f307_20210310235959_1','2021-03-10 22:37:04',NULL,'释放锁定余额'),
	(100,1163,'fil',0.004596,0,0,0,-1,'2420b4594227d85236cc356f2b59f307_20210311235959_2','2021-03-10 22:37:04',NULL,'释放锁定余额'),
	(101,1163,'fil',0.004596,0,0,0,-1,'2420b4594227d85236cc356f2b59f307_20210312235959_3','2021-03-10 22:37:04',NULL,'释放锁定余额'),
	(102,1163,'fil',0.004596,0,0,0,-1,'2420b4594227d85236cc356f2b59f307_20210313235959_4','2021-03-10 22:37:04',NULL,'释放锁定余额'),
	(103,1163,'fil',0.004596,0,0,0,-1,'2420b4594227d85236cc356f2b59f307_20210314235959_5','2021-03-10 22:37:04',NULL,'释放锁定余额'),
	(104,1163,'fil',0.004596,0,0,0,-1,'2420b4594227d85236cc356f2b59f307_20210315235959_6','2021-03-10 22:37:04',NULL,'释放锁定余额'),
	(105,1163,'fil',0.004596,0,0,0,-1,'2420b4594227d85236cc356f2b59f307_20210316235959_7','2021-03-10 22:37:04',NULL,'释放锁定余额'),
	(106,1163,'fil',0.004596,0,0,0,-1,'2420b4594227d85236cc356f2b59f307_20210317235959_8','2021-03-10 22:37:04',NULL,'释放锁定余额'),
	(107,1163,'fil',0.004596,0,0,0,-1,'2420b4594227d85236cc356f2b59f307_20210318235959_9','2021-03-10 22:37:04',NULL,'释放锁定余额'),
	(108,1163,'fil',0.004596,0,0,0,-1,'2420b4594227d85236cc356f2b59f307_20210319235959_10','2021-03-10 22:37:04',NULL,'释放锁定余额'),
	(109,1163,'fil',100.000000,2,0,1,-1,NULL,'2021-03-10 22:44:17',NULL,'后台设置'),
	(110,1163,'fil',13.000000,1,0,0,-1,'20210310224421_1163_1','2021-03-10 22:44:22',NULL,'提前还款'),
	(111,1163,'usdt',500.000000,1,0,0,-1,'5a67da5146e02c269e9f293c2606fd35','2021-03-11 11:34:50',NULL,NULL),
	(112,1163,'fil',8.000000,0,1,0,-1,'5a67da5146e02c269e9f293c2606fd35','2021-03-11 11:34:50',NULL,'矿机质押');

/*!40000 ALTER TABLE `t_user_wallet_record` ENABLE KEYS */;
UNLOCK TABLES;



/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
