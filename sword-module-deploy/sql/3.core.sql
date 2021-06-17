# ************************************************************
# Sequel Pro SQL dump
# Version 4541
#
# http://www.sequelpro.com/
# https://github.com/sequelpro/sequelpro
#
# Host: bj-cdb-fmgl4u90.sql.tencentcdb.com (MySQL 5.7.18-txsql-log)
# Database: sword-survey
# Generation Time: 2021-06-17 01:40:30 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table t_sword_article
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_sword_article`;

CREATE TABLE `t_sword_article` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `title` varchar(256) NOT NULL DEFAULT '' COMMENT '标题',
  `category` varchar(256) DEFAULT NULL COMMENT '分类',
  `description` varchar(512) NOT NULL DEFAULT '' COMMENT '简介',
  `image_url` varchar(1024) NOT NULL DEFAULT '' COMMENT '图片',
  `content` longtext COMMENT '正文',
  `status` int(1) NOT NULL DEFAULT '0' COMMENT '状态（0正常 1关闭）',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文章';



# Dump of table t_sword_message
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_sword_message`;

CREATE TABLE `t_sword_message` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` bigint(20) DEFAULT NULL COMMENT '创建人',
  `type` int(1) DEFAULT '0' COMMENT '类型',
  `title` varchar(256) DEFAULT NULL COMMENT '标题',
  `content` varchar(512) DEFAULT '' COMMENT '正文',
  `status` int(1) NOT NULL DEFAULT '0' COMMENT '状态（0正常 1关闭）',
  `publish` int(10) NOT NULL DEFAULT '-1' COMMENT '发布人数',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='消息';



# Dump of table t_sword_task
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_sword_task`;

CREATE TABLE `t_sword_task` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(256) NOT NULL DEFAULT '' COMMENT '名称',
  `date` date NOT NULL COMMENT '时间',
  `status` int(1) NOT NULL DEFAULT '0' COMMENT '状态',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` varchar(256) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_date_UNIQUE` (`name`,`date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='定时任务执行结果';



# Dump of table t_sword_task_example
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_sword_task_example`;

CREATE TABLE `t_sword_task_example` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(256) NOT NULL DEFAULT '' COMMENT '名称',
  `date` date NOT NULL COMMENT '时间',
  `status` int(1) NOT NULL DEFAULT '0' COMMENT '状态',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` varchar(256) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_date_UNIQUE` (`name`,`date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='定时任务执行结果';



# Dump of table t_sword_version
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_sword_version`;

CREATE TABLE `t_sword_version` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `version` int(11) NOT NULL COMMENT '版本号',
  `type` varchar(64) NOT NULL DEFAULT '' COMMENT '类型',
  `url` varchar(1024) DEFAULT '' COMMENT '下载链接',
  `description` varchar(512) DEFAULT '' COMMENT '描述',
  `status` int(1) NOT NULL DEFAULT '0' COMMENT '状态',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` varchar(256) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='版本';



# Dump of table t_token_access
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_token_access`;

CREATE TABLE `t_token_access` (
  `oper_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '日志主键',
  `title` varchar(50) DEFAULT '' COMMENT '模块标题',
  `method` varchar(100) DEFAULT '' COMMENT '方法名称',
  `request_method` varchar(10) DEFAULT '' COMMENT '请求方式',
  `oper_url` varchar(255) DEFAULT '' COMMENT '请求URL',
  `oper_ip` varchar(50) DEFAULT '' COMMENT '主机地址',
  `oper_location` varchar(255) DEFAULT '' COMMENT '操作地点',
  `oper_param` varchar(2000) DEFAULT '' COMMENT '请求参数',
  `json_result` varchar(2000) DEFAULT '' COMMENT '返回参数',
  `status` int(1) DEFAULT '0' COMMENT '操作状态（0正常 1异常）',
  `error_msg` varchar(2000) DEFAULT '' COMMENT '错误消息',
  `oper_time` datetime DEFAULT NULL COMMENT '操作时间',
  PRIMARY KEY (`oper_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='操作日志记录';



# Dump of table t_token_secret
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_token_secret`;

CREATE TABLE `t_token_secret` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `access_key` varchar(255) NOT NULL DEFAULT '',
  `secret_key` varchar(255) NOT NULL DEFAULT '',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `remark` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `access_key_UNIQUE` (`access_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='开放接口API';

LOCK TABLES `t_token_secret` WRITE;
/*!40000 ALTER TABLE `t_token_secret` DISABLE KEYS */;

INSERT INTO `t_token_secret` (`id`, `access_key`, `secret_key`, `create_time`, `update_time`, `remark`)
VALUES
	(4,'7ccb9193-c551-4b67-b013-8064da683f9a','415787feb2cc02ab77b08d625e21877a','2020-10-13 09:39:06','2020-10-13 09:39:27','ZBX回调'),
	(5,'test','admin','2021-03-17 09:06:02','2021-03-17 09:06:26',NULL);

/*!40000 ALTER TABLE `t_token_secret` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table t_user_advise
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_user_advise`;

CREATE TABLE `t_user_advise` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `parent_Id` bigint(20) DEFAULT NULL COMMENT '父ID',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `title` varchar(256) DEFAULT NULL COMMENT '标题',
  `description` varchar(1024) DEFAULT NULL COMMENT '描述',
  `type` int(1) NOT NULL DEFAULT '0' COMMENT '类型 0-反馈 1-回复',
  `kind` int(1) NOT NULL DEFAULT '0' COMMENT '类别',
  `status` int(1) NOT NULL DEFAULT '0' COMMENT '状态 0-正常 1-关闭',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户反馈';



# Dump of table t_user_article
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_user_article`;

CREATE TABLE `t_user_article` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `article_Id` bigint(20) DEFAULT NULL COMMENT '文章ID',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `read` int(1) NOT NULL DEFAULT '0' COMMENT '已读',
  `like` int(1) NOT NULL DEFAULT '0' COMMENT '点赞',
  `comment` varchar(512) DEFAULT NULL COMMENT '回复',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户文章';



# Dump of table t_user_message
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_user_message`;

CREATE TABLE `t_user_message` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `message_id` bigint(20) DEFAULT NULL,
  `type` int(1) NOT NULL DEFAULT '0' COMMENT '类型 0-接收 1-发送',
  `status` int(1) NOT NULL DEFAULT '0' COMMENT '状态 0-未读 1-已读',
  `del_flag` int(1) NOT NULL DEFAULT '0',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户消息';




/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
