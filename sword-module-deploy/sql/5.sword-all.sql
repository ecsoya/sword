# ************************************************************
# Sequel Pro SQL dump
# Version 4541
#
# http://www.sequelpro.com/
# https://github.com/sequelpro/sequelpro
#
# Host: bj-cdb-fmgl4u90.sql.tencentcdb.com (MySQL 5.7.18-txsql-log)
# Database: sword-timiner
# Generation Time: 2021-08-30 00:43:55 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table gen_table
# ------------------------------------------------------------

DROP TABLE IF EXISTS `gen_table`;

CREATE TABLE `gen_table` (
  `table_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `table_name` varchar(200) DEFAULT '' COMMENT '表名称',
  `table_comment` varchar(500) DEFAULT '' COMMENT '表描述',
  `sub_table_name` varchar(64) DEFAULT NULL COMMENT '关联子表的表名',
  `sub_table_fk_name` varchar(64) DEFAULT NULL COMMENT '子表关联的外键名',
  `class_name` varchar(100) DEFAULT '' COMMENT '实体类名称',
  `tpl_category` varchar(200) DEFAULT 'crud' COMMENT '使用的模板（crud单表操作 tree树表操作 sub主子表操作）',
  `package_name` varchar(100) DEFAULT NULL COMMENT '生成包路径',
  `module_name` varchar(30) DEFAULT NULL COMMENT '生成模块名',
  `business_name` varchar(30) DEFAULT NULL COMMENT '生成业务名',
  `function_name` varchar(50) DEFAULT NULL COMMENT '生成功能名',
  `function_author` varchar(50) DEFAULT NULL COMMENT '生成功能作者',
  `gen_type` char(1) DEFAULT '0' COMMENT '生成代码方式（0zip压缩包 1自定义路径）',
  `gen_path` varchar(200) DEFAULT '/' COMMENT '生成路径（不填默认项目路径）',
  `options` varchar(1000) DEFAULT NULL COMMENT '其它生成选项',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`table_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='代码生成业务表';



# Dump of table gen_table_column
# ------------------------------------------------------------

DROP TABLE IF EXISTS `gen_table_column`;

CREATE TABLE `gen_table_column` (
  `column_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `table_id` varchar(64) DEFAULT NULL COMMENT '归属表编号',
  `column_name` varchar(200) DEFAULT NULL COMMENT '列名称',
  `column_comment` varchar(500) DEFAULT NULL COMMENT '列描述',
  `column_type` varchar(100) DEFAULT NULL COMMENT '列类型',
  `java_type` varchar(500) DEFAULT NULL COMMENT 'JAVA类型',
  `java_field` varchar(200) DEFAULT NULL COMMENT 'JAVA字段名',
  `is_pk` char(1) DEFAULT NULL COMMENT '是否主键（1是）',
  `is_increment` char(1) DEFAULT NULL COMMENT '是否自增（1是）',
  `is_required` char(1) DEFAULT NULL COMMENT '是否必填（1是）',
  `is_insert` char(1) DEFAULT NULL COMMENT '是否为插入字段（1是）',
  `is_edit` char(1) DEFAULT NULL COMMENT '是否编辑字段（1是）',
  `is_list` char(1) DEFAULT NULL COMMENT '是否列表字段（1是）',
  `is_query` char(1) DEFAULT NULL COMMENT '是否查询字段（1是）',
  `query_type` varchar(200) DEFAULT 'EQ' COMMENT '查询方式（等于、不等于、大于、小于、范围）',
  `html_type` varchar(200) DEFAULT NULL COMMENT '显示类型（文本框、文本域、下拉框、复选框、单选框、日期控件）',
  `dict_type` varchar(200) DEFAULT '' COMMENT '字典类型',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`column_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='代码生成业务表字段';



# Dump of table sys_config
# ------------------------------------------------------------

DROP TABLE IF EXISTS `sys_config`;

CREATE TABLE `sys_config` (
  `config_id` int(5) NOT NULL AUTO_INCREMENT COMMENT '参数主键',
  `config_name` varchar(100) DEFAULT '' COMMENT '参数名称',
  `config_key` varchar(100) DEFAULT '' COMMENT '参数键名',
  `config_value` varchar(500) DEFAULT '' COMMENT '参数键值',
  `config_type` char(1) DEFAULT 'N' COMMENT '系统内置（Y是 N否）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`config_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='参数配置表';

LOCK TABLES `sys_config` WRITE;
/*!40000 ALTER TABLE `sys_config` DISABLE KEYS */;

INSERT INTO `sys_config` (`config_id`, `config_name`, `config_key`, `config_value`, `config_type`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES
	(1,'主框架页-默认皮肤样式名称','sys.index.skinName','skin-primary','Y','admin','2021-01-05 10:29:53','','2021-04-08 14:25:04','蓝色 skin-blue、绿色 skin-green、紫色 skin-purple、红色 skin-red、黄色 skin-yellow'),
	(2,'用户管理-账号初始密码','sys.user.initPassword','123456','Y','admin','2021-01-05 10:29:53','',NULL,'初始化密码 123456'),
	(3,'主框架页-侧边栏主题','sys.index.sideTheme','theme-dark','Y','admin','2021-01-05 10:29:53','',NULL,'深黑主题theme-dark，浅色主题theme-light，深蓝主题theme-blue'),
	(4,'账号自助-是否开启用户注册功能','sys.account.registerUser','false','Y','admin','2021-01-05 10:29:53','',NULL,'是否开启注册用户功能（true开启，false关闭）'),
	(5,'用户管理-密码字符范围','sys.account.chrtype','0','Y','admin','2021-01-05 10:29:53','',NULL,'默认任意字符范围，0任意（密码可以输入任意字符），1数字（密码只能为0-9数字），2英文字母（密码只能为a-z和A-Z字母），3字母和数字（密码必须包含字母，数字）,4字母数字和特殊字符（目前支持的特殊字符包括：~!@#$%^&*()-=_+）'),
	(6,'用户管理-初始密码修改策略','sys.account.initPasswordModify','0','Y','admin','2021-01-05 10:29:53','',NULL,'0：初始密码修改策略关闭，没有任何提示，1：提醒用户，如果未修改初始密码，则在登录时就会提醒修改密码对话框'),
	(7,'用户管理-账号密码更新周期','sys.account.passwordValidateDays','0','Y','admin','2021-01-05 10:29:53','',NULL,'密码更新周期（填写数字，数据初始化值为0不限制，若修改必须为大于0小于365的正整数），如果超过这个周期登录系统时，则在登录时就会提醒修改密码对话框'),
	(8,'主框架页-菜单导航显示风格','sys.index.menuStyle','default','Y','admin','2021-01-05 10:29:53','',NULL,'菜单导航显示风格（default为左侧导航菜单，topnav为顶部导航菜单）'),
	(9,'主框架页-是否开启页脚','sys.index.ignoreFooter','true','Y','admin','2021-01-05 10:29:53','','2021-04-08 14:27:19','是否开启底部页脚显示（true显示，false隐藏）'),
	(100,'注册需要推荐码','sword.registerNeedReferrerCode','true','Y','',NULL,'','2020-10-12 15:49:17',NULL),
	(101,'顶层用户ID','sword.rootUserId','3','N','',NULL,'',NULL,NULL),
	(102,'钱包默认支付密码','wallet.defaultPassword','111111','N','',NULL,'','2020-09-28 09:44:12',NULL),
	(103,'分享二维码前缀','sword.referralLinkUrl','https://develop.tqmi.xyz/filminer/register/index.html?referrerCode=','N','',NULL,'develop','2021-04-14 16:09:38',''),
	(104,'注册手机号唯一','sword.registerMobileUnique','true','N','',NULL,'',NULL,NULL),
	(105,'注册邮箱唯一','sword.registerEmailUnique','false','N','',NULL,'develop','2021-08-25 11:50:58',''),
	(106,'实名认证功能','sword.enableUserCertificate','0','N','',NULL,'',NULL,'0-关闭，1-钱包，2-提币，3=钱包和提币，4=购买，5=钱包和购买，6=提币和购买，7=钱包、提币和购买'),
	(107,'收益全局开关','sword.userProfitEnabled','true','N','',NULL,'',NULL,NULL),
	(108,'双区树','sword.userBinaryTreeEnabled','false','N','',NULL,'',NULL,NULL);

/*!40000 ALTER TABLE `sys_config` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table sys_dept
# ------------------------------------------------------------

DROP TABLE IF EXISTS `sys_dept`;

CREATE TABLE `sys_dept` (
  `dept_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '部门id',
  `parent_id` bigint(20) DEFAULT '0' COMMENT '父部门id',
  `ancestors` varchar(50) DEFAULT '' COMMENT '祖级列表',
  `dept_name` varchar(30) DEFAULT '' COMMENT '部门名称',
  `order_num` int(4) DEFAULT '0' COMMENT '显示顺序',
  `leader` varchar(20) DEFAULT NULL COMMENT '负责人',
  `phone` varchar(11) DEFAULT NULL COMMENT '联系电话',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `status` char(1) DEFAULT '0' COMMENT '部门状态（0正常 1停用）',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`dept_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='部门表';

LOCK TABLES `sys_dept` WRITE;
/*!40000 ALTER TABLE `sys_dept` DISABLE KEYS */;

INSERT INTO `sys_dept` (`dept_id`, `parent_id`, `ancestors`, `dept_name`, `order_num`, `leader`, `phone`, `email`, `status`, `del_flag`, `create_by`, `create_time`, `update_by`, `update_time`)
VALUES
	(100,0,'0','松亚科技',0,'若依','15888888888','ry@qq.com','0','0','admin','2021-01-05 15:07:03','',NULL),
	(101,0,'0','时空存储',1,'若依','15888888888','ry@qq.com','0','0','admin','2021-01-05 15:07:03','',NULL);

/*!40000 ALTER TABLE `sys_dept` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table sys_dict_data
# ------------------------------------------------------------

DROP TABLE IF EXISTS `sys_dict_data`;

CREATE TABLE `sys_dict_data` (
  `dict_code` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '字典编码',
  `dict_sort` int(4) DEFAULT '0' COMMENT '字典排序',
  `dict_label` varchar(100) DEFAULT '' COMMENT '字典标签',
  `dict_value` varchar(100) DEFAULT '' COMMENT '字典键值',
  `dict_type` varchar(100) DEFAULT '' COMMENT '字典类型',
  `css_class` varchar(100) DEFAULT NULL COMMENT '样式属性（其他样式扩展）',
  `list_class` varchar(100) DEFAULT NULL COMMENT '表格回显样式',
  `is_default` char(1) DEFAULT 'N' COMMENT '是否默认（Y是 N否）',
  `status` char(1) DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`dict_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='字典数据表';

LOCK TABLES `sys_dict_data` WRITE;
/*!40000 ALTER TABLE `sys_dict_data` DISABLE KEYS */;

INSERT INTO `sys_dict_data` (`dict_code`, `dict_sort`, `dict_label`, `dict_value`, `dict_type`, `css_class`, `list_class`, `is_default`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES
	(1,1,'男','0','sys_user_sex','','','Y','0','admin','2021-01-05 10:29:52','',NULL,'性别男'),
	(2,2,'女','1','sys_user_sex','','','N','0','admin','2021-01-05 10:29:52','',NULL,'性别女'),
	(3,3,'未知','2','sys_user_sex','','','N','0','admin','2021-01-05 10:29:52','',NULL,'性别未知'),
	(4,1,'显示','0','sys_show_hide','','primary','Y','0','admin','2021-01-05 10:29:52','',NULL,'显示菜单'),
	(5,2,'隐藏','1','sys_show_hide','','danger','N','0','admin','2021-01-05 10:29:52','',NULL,'隐藏菜单'),
	(6,1,'正常','0','sys_normal_disable','','primary','Y','0','admin','2021-01-05 10:29:52','',NULL,'正常状态'),
	(7,2,'停用','1','sys_normal_disable','','danger','N','0','admin','2021-01-05 10:29:52','',NULL,'停用状态'),
	(8,1,'正常','0','sys_job_status','','primary','Y','0','admin','2021-01-05 10:29:52','',NULL,'正常状态'),
	(9,2,'暂停','1','sys_job_status','','danger','N','0','admin','2021-01-05 10:29:52','',NULL,'停用状态'),
	(10,1,'默认','DEFAULT','sys_job_group','','','Y','0','admin','2021-01-05 10:29:52','',NULL,'默认分组'),
	(11,2,'系统','SYSTEM','sys_job_group','','','N','0','admin','2021-01-05 10:29:52','',NULL,'系统分组'),
	(12,1,'是','Y','sys_yes_no','','primary','Y','0','admin','2021-01-05 10:29:52','',NULL,'系统默认是'),
	(13,2,'否','N','sys_yes_no','','danger','N','0','admin','2021-01-05 10:29:52','',NULL,'系统默认否'),
	(14,1,'通知','1','sys_notice_type','','warning','Y','0','admin','2021-01-05 10:29:52','',NULL,'通知'),
	(15,2,'公告','2','sys_notice_type','','success','N','0','admin','2021-01-05 10:29:52','',NULL,'公告'),
	(16,1,'正常','0','sys_notice_status','','primary','Y','0','admin','2021-01-05 10:29:52','',NULL,'正常状态'),
	(17,2,'关闭','1','sys_notice_status','','danger','N','0','admin','2021-01-05 10:29:52','',NULL,'关闭状态'),
	(18,99,'其他','0','sys_oper_type','','info','N','0','admin','2021-01-05 10:29:52','',NULL,'其他操作'),
	(19,1,'新增','1','sys_oper_type','','info','N','0','admin','2021-01-05 10:29:52','',NULL,'新增操作'),
	(20,2,'修改','2','sys_oper_type','','info','N','0','admin','2021-01-05 10:29:52','',NULL,'修改操作'),
	(21,3,'删除','3','sys_oper_type','','danger','N','0','admin','2021-01-05 10:29:52','',NULL,'删除操作'),
	(22,4,'授权','4','sys_oper_type','','primary','N','0','admin','2021-01-05 10:29:52','',NULL,'授权操作'),
	(23,5,'导出','5','sys_oper_type','','warning','N','0','admin','2021-01-05 10:29:53','',NULL,'导出操作'),
	(24,6,'导入','6','sys_oper_type','','warning','N','0','admin','2021-01-05 10:29:53','',NULL,'导入操作'),
	(25,7,'强退','7','sys_oper_type','','danger','N','0','admin','2021-01-05 10:29:53','',NULL,'强退操作'),
	(26,8,'生成代码','8','sys_oper_type','','warning','N','0','admin','2021-01-05 10:29:53','',NULL,'生成操作'),
	(27,9,'清空数据','9','sys_oper_type','','danger','N','0','admin','2021-01-05 10:29:53','',NULL,'清空操作'),
	(28,1,'成功','0','sys_common_status','','primary','N','0','admin','2021-01-05 10:29:53','',NULL,'正常状态'),
	(29,2,'失败','1','sys_common_status','','danger','N','0','admin','2021-01-05 10:29:53','',NULL,'停用状态'),
	(100,0,'草稿','0','filecoin_type',NULL,'warning','N','0','',NULL,'',NULL,NULL),
	(101,1,'已发布','1','filecoin_type',NULL,'primary','Y','0','',NULL,'',NULL,NULL),
	(102,2,'已停用','2','filecoin_type',NULL,'danger','N','0','',NULL,'',NULL,NULL);

/*!40000 ALTER TABLE `sys_dict_data` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table sys_dict_type
# ------------------------------------------------------------

DROP TABLE IF EXISTS `sys_dict_type`;

CREATE TABLE `sys_dict_type` (
  `dict_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '字典主键',
  `dict_name` varchar(100) DEFAULT '' COMMENT '字典名称',
  `dict_type` varchar(100) DEFAULT '' COMMENT '字典类型',
  `status` char(1) DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`dict_id`),
  UNIQUE KEY `dict_type` (`dict_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='字典类型表';

LOCK TABLES `sys_dict_type` WRITE;
/*!40000 ALTER TABLE `sys_dict_type` DISABLE KEYS */;

INSERT INTO `sys_dict_type` (`dict_id`, `dict_name`, `dict_type`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES
	(1,'用户性别','sys_user_sex','0','admin','2021-01-05 10:29:51','',NULL,'用户性别列表'),
	(2,'菜单状态','sys_show_hide','0','admin','2021-01-05 10:29:51','',NULL,'菜单状态列表'),
	(3,'系统开关','sys_normal_disable','0','admin','2021-01-05 10:29:52','',NULL,'系统开关列表'),
	(4,'任务状态','sys_job_status','0','admin','2021-01-05 10:29:52','',NULL,'任务状态列表'),
	(5,'任务分组','sys_job_group','0','admin','2021-01-05 10:29:52','',NULL,'任务分组列表'),
	(6,'系统是否','sys_yes_no','0','admin','2021-01-05 10:29:52','',NULL,'系统是否列表'),
	(7,'通知类型','sys_notice_type','0','admin','2021-01-05 10:29:52','',NULL,'通知类型列表'),
	(8,'通知状态','sys_notice_status','0','admin','2021-01-05 10:29:52','',NULL,'通知状态列表'),
	(9,'操作类型','sys_oper_type','0','admin','2021-01-05 10:29:52','',NULL,'操作类型列表'),
	(10,'系统状态','sys_common_status','0','admin','2021-01-05 10:29:52','',NULL,'登录状态列表'),
	(11,'矿机状态','filecoin_type','0','',NULL,'',NULL,NULL);

/*!40000 ALTER TABLE `sys_dict_type` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table sys_job
# ------------------------------------------------------------

DROP TABLE IF EXISTS `sys_job`;

CREATE TABLE `sys_job` (
  `job_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '任务ID',
  `job_name` varchar(64) NOT NULL DEFAULT '' COMMENT '任务名称',
  `job_group` varchar(64) NOT NULL DEFAULT 'DEFAULT' COMMENT '任务组名',
  `invoke_target` varchar(500) NOT NULL COMMENT '调用目标字符串',
  `cron_expression` varchar(255) DEFAULT '' COMMENT 'cron执行表达式',
  `misfire_policy` varchar(20) DEFAULT '3' COMMENT '计划执行错误策略（1立即执行 2执行一次 3放弃执行）',
  `concurrent` char(1) DEFAULT '1' COMMENT '是否并发执行（0允许 1禁止）',
  `status` char(1) DEFAULT '0' COMMENT '状态（0正常 1暂停）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT '' COMMENT '备注信息',
  PRIMARY KEY (`job_id`,`job_name`,`job_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='定时任务调度表';



# Dump of table sys_job_log
# ------------------------------------------------------------

DROP TABLE IF EXISTS `sys_job_log`;

CREATE TABLE `sys_job_log` (
  `job_log_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '任务日志ID',
  `job_name` varchar(64) NOT NULL COMMENT '任务名称',
  `job_group` varchar(64) NOT NULL COMMENT '任务组名',
  `invoke_target` varchar(500) NOT NULL COMMENT '调用目标字符串',
  `job_message` varchar(500) DEFAULT NULL COMMENT '日志信息',
  `status` char(1) DEFAULT '0' COMMENT '执行状态（0正常 1失败）',
  `exception_info` varchar(2000) DEFAULT '' COMMENT '异常信息',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`job_log_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='定时任务调度日志表';



# Dump of table sys_logininfor
# ------------------------------------------------------------

DROP TABLE IF EXISTS `sys_logininfor`;

CREATE TABLE `sys_logininfor` (
  `info_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '访问ID',
  `login_name` varchar(50) DEFAULT '' COMMENT '登录账号',
  `ipaddr` varchar(50) DEFAULT '' COMMENT '登录IP地址',
  `login_location` varchar(255) DEFAULT '' COMMENT '登录地点',
  `browser` varchar(50) DEFAULT '' COMMENT '浏览器类型',
  `os` varchar(50) DEFAULT '' COMMENT '操作系统',
  `status` char(1) DEFAULT '0' COMMENT '登录状态（0成功 1失败）',
  `msg` varchar(255) DEFAULT '' COMMENT '提示消息',
  `login_time` datetime DEFAULT NULL COMMENT '访问时间',
  PRIMARY KEY (`info_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统访问记录';



# Dump of table sys_menu
# ------------------------------------------------------------

DROP TABLE IF EXISTS `sys_menu`;

CREATE TABLE `sys_menu` (
  `menu_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
  `menu_name` varchar(50) NOT NULL COMMENT '菜单名称',
  `parent_id` bigint(20) DEFAULT '0' COMMENT '父菜单ID',
  `order_num` int(4) DEFAULT '0' COMMENT '显示顺序',
  `url` varchar(200) DEFAULT '#' COMMENT '请求地址',
  `target` varchar(20) DEFAULT '' COMMENT '打开方式（menuItem页签 menuBlank新窗口）',
  `menu_type` char(1) DEFAULT '' COMMENT '菜单类型（M目录 C菜单 F按钮）',
  `visible` char(1) DEFAULT '0' COMMENT '菜单状态（0显示 1隐藏）',
  `is_refresh` char(1) DEFAULT '1' COMMENT '是否刷新（0刷新 1不刷新）',
  `perms` varchar(100) DEFAULT NULL COMMENT '权限标识',
  `icon` varchar(100) DEFAULT '#' COMMENT '菜单图标',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='菜单权限表';

LOCK TABLES `sys_menu` WRITE;
/*!40000 ALTER TABLE `sys_menu` DISABLE KEYS */;

INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `parent_id`, `order_num`, `url`, `target`, `menu_type`, `visible`, `is_refresh`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES
	(1,'系统管理（开发）',0,1,'#','','M','0','1','','fa fa-gear','admin','2021-01-05 15:07:04','',NULL,'系统管理目录'),
	(2,'系统监控（开发）',0,2,'#','','M','0','1','','fa fa-video-camera','admin','2021-01-05 15:07:04','',NULL,'系统监控目录'),
	(3,'系统工具（开发）',0,3,'#','','M','0','1','','fa fa-bars','admin','2021-01-05 15:07:04','',NULL,'系统工具目录'),
	(100,'用户管理',1,1,'/system/user','menuItem','C','1','1','system:user:view','fa fa-user-o','admin','2021-01-05 15:07:04','develop','2021-03-19 12:11:59','用户管理菜单'),
	(101,'角色管理',1,2,'/system/role','','C','0','1','system:role:view','fa fa-user-secret','admin','2021-01-05 15:07:04','',NULL,'角色管理菜单'),
	(102,'菜单管理',1,3,'/system/menu','','C','0','1','system:menu:view','fa fa-th-list','admin','2021-01-05 15:07:04','',NULL,'菜单管理菜单'),
	(103,'部门管理',1,4,'/system/dept','menuItem','C','1','1','system:dept:view','fa fa-outdent','admin','2021-01-05 15:07:04','develop','2021-03-19 12:12:14','部门管理菜单'),
	(104,'岗位管理',1,5,'/system/post','menuItem','C','1','1','system:post:view','fa fa-address-card-o','admin','2021-01-05 15:07:04','develop','2021-03-19 12:12:20','岗位管理菜单'),
	(105,'字典管理',1,6,'/system/dict','','C','0','1','system:dict:view','fa fa-bookmark-o','admin','2021-01-05 15:07:04','',NULL,'字典管理菜单'),
	(106,'参数设置',1,7,'/system/config','','C','0','1','system:config:view','fa fa-sun-o','admin','2021-01-05 15:07:04','',NULL,'参数设置菜单'),
	(107,'通知公告',1,8,'/system/notice','menuItem','C','1','1','system:notice:view','fa fa-bullhorn','admin','2021-01-05 15:07:04','develop','2021-03-19 12:12:29','通知公告菜单'),
	(108,'日志管理',1,9,'#','','M','0','1','','fa fa-pencil-square-o','admin','2021-01-05 15:07:04','',NULL,'日志管理菜单'),
	(109,'在线用户',2,1,'/monitor/online','menuItem','C','1','1','monitor:online:view','fa fa-user-circle','admin','2021-01-05 15:07:04','develop','2021-03-19 12:12:42','在线用户菜单'),
	(110,'定时任务',2,2,'/monitor/job','','C','0','1','monitor:job:view','fa fa-tasks','admin','2021-01-05 15:07:04','',NULL,'定时任务菜单'),
	(111,'数据监控',2,3,'/monitor/data','','C','0','1','monitor:data:view','fa fa-bug','admin','2021-01-05 15:07:04','',NULL,'数据监控菜单'),
	(112,'服务监控',2,4,'/monitor/server','','C','0','1','monitor:server:view','fa fa-server','admin','2021-01-05 15:07:04','',NULL,'服务监控菜单'),
	(113,'缓存监控',2,5,'/monitor/cache','','C','0','1','monitor:cache:view','fa fa-cube','admin','2021-01-05 15:07:04','',NULL,'缓存监控菜单'),
	(115,'版本管理',3,1,'/tool/version','menuItem','C','0','1','tool:version:view','fa fa-code','admin','2021-01-05 15:07:04','develop','2021-03-18 08:13:34','版本管理'),
	(500,'操作日志',108,1,'/monitor/operlog','','C','0','1','monitor:operlog:view','fa fa-address-book','admin','2021-01-05 15:07:04','',NULL,'操作日志菜单'),
	(501,'登录日志',108,2,'/monitor/logininfor','','C','0','1','monitor:logininfor:view','fa fa-file-image-o','admin','2021-01-05 15:07:04','',NULL,'登录日志菜单'),
	(1000,'用户查询',100,1,'#','','F','0','1','system:user:list','#','admin','2021-01-05 15:07:04','',NULL,''),
	(1001,'用户新增',100,2,'#','','F','0','1','system:user:add','#','admin','2021-01-05 15:07:04','',NULL,''),
	(1002,'用户修改',100,3,'#','','F','0','1','system:user:edit','#','admin','2021-01-05 15:07:04','',NULL,''),
	(1003,'用户删除',100,4,'#','','F','0','1','system:user:remove','#','admin','2021-01-05 15:07:04','',NULL,''),
	(1004,'用户导出',100,5,'#','','F','0','1','system:user:export','#','admin','2021-01-05 15:07:04','',NULL,''),
	(1005,'用户导入',100,6,'#','','F','0','1','system:user:import','#','admin','2021-01-05 15:07:04','',NULL,''),
	(1006,'重置密码',100,7,'#','','F','0','1','system:user:resetPwd','#','admin','2021-01-05 15:07:05','',NULL,''),
	(1007,'角色查询',101,1,'#','','F','0','1','system:role:list','#','admin','2021-01-05 15:07:05','',NULL,''),
	(1008,'角色新增',101,2,'#','','F','0','1','system:role:add','#','admin','2021-01-05 15:07:05','',NULL,''),
	(1009,'角色修改',101,3,'#','','F','0','1','system:role:edit','#','admin','2021-01-05 15:07:05','',NULL,''),
	(1010,'角色删除',101,4,'#','','F','0','1','system:role:remove','#','admin','2021-01-05 15:07:05','',NULL,''),
	(1011,'角色导出',101,5,'#','','F','0','1','system:role:export','#','admin','2021-01-05 15:07:05','',NULL,''),
	(1012,'菜单查询',102,1,'#','','F','0','1','system:menu:list','#','admin','2021-01-05 15:07:05','',NULL,''),
	(1013,'菜单新增',102,2,'#','','F','0','1','system:menu:add','#','admin','2021-01-05 15:07:05','',NULL,''),
	(1014,'菜单修改',102,3,'#','','F','0','1','system:menu:edit','#','admin','2021-01-05 15:07:05','',NULL,''),
	(1015,'菜单删除',102,4,'#','','F','0','1','system:menu:remove','#','admin','2021-01-05 15:07:05','',NULL,''),
	(1016,'部门查询',103,1,'#','','F','0','1','system:dept:list','#','admin','2021-01-05 15:07:05','',NULL,''),
	(1017,'部门新增',103,2,'#','','F','0','1','system:dept:add','#','admin','2021-01-05 15:07:05','',NULL,''),
	(1018,'部门修改',103,3,'#','','F','0','1','system:dept:edit','#','admin','2021-01-05 15:07:05','',NULL,''),
	(1019,'部门删除',103,4,'#','','F','0','1','system:dept:remove','#','admin','2021-01-05 15:07:05','',NULL,''),
	(1020,'岗位查询',104,1,'#','','F','0','1','system:post:list','#','admin','2021-01-05 15:07:05','',NULL,''),
	(1021,'岗位新增',104,2,'#','','F','0','1','system:post:add','#','admin','2021-01-05 15:07:05','',NULL,''),
	(1022,'岗位修改',104,3,'#','','F','0','1','system:post:edit','#','admin','2021-01-05 15:07:05','',NULL,''),
	(1023,'岗位删除',104,4,'#','','F','0','1','system:post:remove','#','admin','2021-01-05 15:07:05','',NULL,''),
	(1024,'岗位导出',104,5,'#','','F','0','1','system:post:export','#','admin','2021-01-05 15:07:05','',NULL,''),
	(1025,'字典查询',105,1,'#','','F','0','1','system:dict:list','#','admin','2021-01-05 15:07:05','',NULL,''),
	(1026,'字典新增',105,2,'#','','F','0','1','system:dict:add','#','admin','2021-01-05 15:07:05','',NULL,''),
	(1027,'字典修改',105,3,'#','','F','0','1','system:dict:edit','#','admin','2021-01-05 15:07:05','',NULL,''),
	(1028,'字典删除',105,4,'#','','F','0','1','system:dict:remove','#','admin','2021-01-05 15:07:05','',NULL,''),
	(1029,'字典导出',105,5,'#','','F','0','1','system:dict:export','#','admin','2021-01-05 15:07:05','',NULL,''),
	(1030,'参数查询',106,1,'#','','F','0','1','system:config:list','#','admin','2021-01-05 15:07:05','',NULL,''),
	(1031,'参数新增',106,2,'#','','F','0','1','system:config:add','#','admin','2021-01-05 15:07:05','',NULL,''),
	(1032,'参数修改',106,3,'#','','F','0','1','system:config:edit','#','admin','2021-01-05 15:07:05','',NULL,''),
	(1033,'参数删除',106,4,'#','','F','0','1','system:config:remove','#','admin','2021-01-05 15:07:05','',NULL,''),
	(1034,'参数导出',106,5,'#','','F','0','1','system:config:export','#','admin','2021-01-05 15:07:05','',NULL,''),
	(1035,'公告查询',107,1,'#','','F','0','1','system:notice:list','#','admin','2021-01-05 15:07:05','',NULL,''),
	(1036,'公告新增',107,2,'#','','F','0','1','system:notice:add','#','admin','2021-01-05 15:07:06','',NULL,''),
	(1037,'公告修改',107,3,'#','','F','0','1','system:notice:edit','#','admin','2021-01-05 15:07:06','',NULL,''),
	(1038,'公告删除',107,4,'#','','F','0','1','system:notice:remove','#','admin','2021-01-05 15:07:06','',NULL,''),
	(1039,'操作查询',500,1,'#','','F','0','1','monitor:operlog:list','#','admin','2021-01-05 15:07:06','',NULL,''),
	(1040,'操作删除',500,2,'#','','F','0','1','monitor:operlog:remove','#','admin','2021-01-05 15:07:06','',NULL,''),
	(1041,'详细信息',500,3,'#','','F','0','1','monitor:operlog:detail','#','admin','2021-01-05 15:07:06','',NULL,''),
	(1042,'日志导出',500,4,'#','','F','0','1','monitor:operlog:export','#','admin','2021-01-05 15:07:06','',NULL,''),
	(1043,'登录查询',501,1,'#','','F','0','1','monitor:logininfor:list','#','admin','2021-01-05 15:07:06','',NULL,''),
	(1044,'登录删除',501,2,'#','','F','0','1','monitor:logininfor:remove','#','admin','2021-01-05 15:07:06','',NULL,''),
	(1045,'日志导出',501,3,'#','','F','0','1','monitor:logininfor:export','#','admin','2021-01-05 15:07:06','',NULL,''),
	(1046,'账户解锁',501,4,'#','','F','0','1','monitor:logininfor:unlock','#','admin','2021-01-05 15:07:06','',NULL,''),
	(1047,'在线查询',109,1,'#','','F','0','1','monitor:online:list','#','admin','2021-01-05 15:07:06','',NULL,''),
	(1048,'批量强退',109,2,'#','','F','0','1','monitor:online:batchForceLogout','#','admin','2021-01-05 15:07:06','',NULL,''),
	(1049,'单条强退',109,3,'#','','F','0','1','monitor:online:forceLogout','#','admin','2021-01-05 15:07:06','',NULL,''),
	(1050,'任务查询',110,1,'#','','F','0','1','monitor:job:list','#','admin','2021-01-05 15:07:06','',NULL,''),
	(1051,'任务新增',110,2,'#','','F','0','1','monitor:job:add','#','admin','2021-01-05 15:07:06','',NULL,''),
	(1052,'任务修改',110,3,'#','','F','0','1','monitor:job:edit','#','admin','2021-01-05 15:07:06','',NULL,''),
	(1053,'任务删除',110,4,'#','','F','0','1','monitor:job:remove','#','admin','2021-01-05 15:07:06','',NULL,''),
	(1054,'状态修改',110,5,'#','','F','0','1','monitor:job:changeStatus','#','admin','2021-01-05 15:07:06','',NULL,''),
	(1055,'任务详细',110,6,'#','','F','0','1','monitor:job:detail','#','admin','2021-01-05 15:07:06','',NULL,''),
	(1056,'任务导出',110,7,'#','','F','0','1','monitor:job:export','#','admin','2021-01-05 15:07:06','',NULL,''),
	(1057,'版本查询',115,1,'#','','F','0','1','tool:version:list','#','admin','2021-01-05 15:07:06','',NULL,''),
	(1058,'版本修改',115,2,'#','','F','0','1','tool:version:edit','#','admin','2021-01-05 15:07:06','',NULL,''),
	(1059,'版本删除',115,3,'#','','F','0','1','tool:version:remove','#','admin','2021-01-05 15:07:06','',NULL,''),
	(1062,'用户管理',0,4,'#','menuItem','M','0','1','','fa fa-group','develop','2021-03-15 09:13:02','develop','2021-03-15 09:13:09',''),
	(1063,'矿机管理',0,5,'#','menuItem','M','0','1','','fa fa-calendar-plus-o','develop','2021-03-15 09:13:33','develop','2021-04-06 15:04:56',''),
	(1064,'收益报表',0,16,'#','menuItem','M','0','1','','fa fa-diamond','develop','2021-03-15 09:14:32','develop','2021-04-19 09:06:54',''),
	(1065,'钱包管理',0,17,'#','menuItem','M','0','1','','fa fa-globe','develop','2021-03-15 09:14:57','develop','2021-04-07 12:15:55',''),
	(1066,'系统设置',0,30,'#','menuItem','M','0','1','','fa fa-gears','develop','2021-03-15 09:15:40','develop','2021-04-07 12:16:17',''),
	(1067,'矿机设置',1063,1,'/filminer/filecoin','menuItem','C','0','1','filminer:filecoin:view','fa fa-calendar-o','develop','2021-03-15 09:16:25','develop','2021-04-06 15:05:38',''),
	(1068,'添加',1067,1,'#','menuItem','F','0','1','filminer:filecoin:add','#','develop','2021-03-15 09:16:25','develop','2021-04-06 15:15:04',''),
	(1069,'删除',1067,2,'#','menuItem','F','0','1','filminer:filecoin:remove','#','develop','2021-03-15 09:16:25','develop','2021-04-06 15:15:13',''),
	(1070,'修改',1067,3,'#','menuItem','F','0','1','filminer:filecoin:edit','#','develop','2021-03-15 09:16:25','develop','2021-04-06 15:15:22',''),
	(1071,'查询',1067,4,'#','menuItem','F','0','1','filminer:filecoin:list','#','develop','2021-03-15 09:16:25','develop','2021-04-06 15:15:31',''),
	(1072,'导出',1067,6,'#','menuItem','F','0','1','filminer:filecoin:export','#','develop','2021-03-15 09:16:25','develop','2021-04-06 15:15:39',''),
	(1073,'矿机订单',1063,2,'/filecoin/record','menuItem','C','0','1','filecoin:record:view','fa fa-calendar-check-o','develop','2021-03-15 09:17:10','develop','2021-04-06 15:15:53',''),
	(1074,'添加',1073,1,'#','menuItem','F','0','1','filecoin:record:add','#','develop','2021-03-15 09:17:10','',NULL,''),
	(1075,'删除',1073,2,'#','menuItem','F','0','1','filecoin:record:remove','#','develop','2021-03-15 09:17:10','',NULL,''),
	(1076,'修改',1073,3,'#','menuItem','F','0','1','filecoin:record:edit','#','develop','2021-03-15 09:17:10','',NULL,''),
	(1077,'查询',1073,4,'#','menuItem','F','0','1','filecoin:record:list','#','develop','2021-03-15 09:17:10','',NULL,''),
	(1078,'导出',1073,6,'#','menuItem','F','0','1','filecoin:record:export','#','develop','2021-03-15 09:17:10','',NULL,''),
	(1079,'用户设置',1062,1,'/mining/user','menuItem','C','0','1','mining:user:view','fa fa-user-circle-o','develop','2021-03-15 11:14:38','',NULL,''),
	(1080,'添加',1079,1,'#','menuItem','F','0','1','mining:user:add','#','develop','2021-03-15 11:14:38','',NULL,''),
	(1081,'删除',1079,2,'#','menuItem','F','0','1','mining:user:remove','#','develop','2021-03-15 11:14:38','',NULL,''),
	(1082,'修改',1079,3,'#','menuItem','F','0','1','mining:user:edit','#','develop','2021-03-15 11:14:38','',NULL,''),
	(1083,'查询',1079,4,'#','menuItem','F','0','1','mining:user:list','#','develop','2021-03-15 11:14:38','',NULL,''),
	(1084,'静态收益',1064,1,'/profit/static','menuItem','C','0','1','profit:static:view','fa fa-barcode','develop','2021-03-16 09:39:50','develop','2021-04-07 13:41:48',''),
	(1085,'查询',1084,4,'#','menuItem','F','0','1','profit:static:list','#','develop','2021-03-16 09:39:51','develop','2021-04-07 13:41:33',''),
	(1088,'收益验算',1064,13,'/profit/test','menuItem','C','0','1','profit:test:view','fa fa-bug','develop','2021-03-16 10:44:15','develop','2021-04-07 13:46:55',''),
	(1095,'提币订单',1065,11,'/mining/wallet/withdrawal','menuItem','C','0','1','mining:wallet:withdrawal:view','fa fa-quote-left','develop','2021-03-16 16:48:28','develop','2021-03-19 09:20:31',''),
	(1096,'查询',1095,4,'#','menuItem','F','0','1','mining:wallet:withdrawal:list','#','develop','2021-03-16 16:48:28','',NULL,''),
	(1097,'充值订单',1065,12,'/mining/wallet/deposit','menuItem','C','0','1','mining:wallet:deposit:view','fa fa-quote-right','develop','2021-03-16 16:49:09','develop','2021-03-19 09:20:38',''),
	(1098,'查询',1097,4,'#','menuItem','F','0','1','mining:wallet:deposit:list','#','develop','2021-03-16 16:49:09','',NULL,''),
	(1099,'公告管理',1066,1,'/admin/notice','menuItem','C','0','1','admin:notice:view','fa fa-bullhorn','develop','2021-03-16 17:08:59','develop','2021-03-16 17:10:06',''),
	(1100,'添加',1099,1,'#','menuItem','F','0','1','admin:notice:add','#','develop','2021-03-16 17:08:59','',NULL,''),
	(1101,'删除',1099,2,'#','menuItem','F','0','1','admin:notice:remove','#','develop','2021-03-16 17:08:59','',NULL,''),
	(1102,'修改',1099,3,'#','menuItem','F','0','1','admin:notice:edit','#','develop','2021-03-16 17:09:00','',NULL,''),
	(1103,'查询',1099,4,'#','menuItem','F','0','1','admin:notice:list','#','develop','2021-03-16 17:09:00','',NULL,''),
	(1104,'消息管理',1066,2,'/admin/inform','menuItem','C','0','1','admin:inform:view','fa fa-envelope','develop','2021-03-16 17:09:54','',NULL,''),
	(1105,'添加',1104,1,'#','menuItem','F','0','1','admin:inform:add','#','develop','2021-03-16 17:09:54','',NULL,''),
	(1106,'删除',1104,2,'#','menuItem','F','0','1','admin:inform:remove','#','develop','2021-03-16 17:09:54','',NULL,''),
	(1107,'修改',1104,3,'#','menuItem','F','0','1','admin:inform:edit','#','develop','2021-03-16 17:09:55','',NULL,''),
	(1108,'查询',1104,4,'#','menuItem','F','0','1','admin:inform:list','#','develop','2021-03-16 17:09:55','',NULL,''),
	(1109,'权限管理',1066,4,'/admin/manager','menuItem','C','0','1','admin:manager:view','fa fa-paw','develop','2021-03-16 17:16:15','develop','2021-03-16 17:19:06',''),
	(1110,'添加',1109,1,'#','menuItem','F','0','1','admin:manager:add','#','develop','2021-03-16 17:16:15','',NULL,''),
	(1111,'删除',1109,2,'#','menuItem','F','0','1','admin:manager:remove','#','develop','2021-03-16 17:16:15','',NULL,''),
	(1112,'修改',1109,3,'#','menuItem','F','0','1','admin:manager:edit','#','develop','2021-03-16 17:16:15','',NULL,''),
	(1113,'查询',1109,4,'#','menuItem','F','0','1','admin:manager:list','#','develop','2021-03-16 17:16:15','',NULL,''),
	(1114,'导出',1109,6,'#','menuItem','F','0','1','admin:manager:export','#','develop','2021-03-16 17:16:15','',NULL,''),
	(1115,'角色管理',1066,3,'/admin/role','menuItem','C','0','1','admin:role:view','fa fa-exclamation-circle','develop','2021-03-16 17:19:43','',NULL,''),
	(1116,'添加',1115,1,'#','menuItem','F','0','1','admin:role:add','#','develop','2021-03-16 17:19:43','',NULL,''),
	(1117,'删除',1115,2,'#','menuItem','F','0','1','admin:role:remove','#','develop','2021-03-16 17:19:43','',NULL,''),
	(1118,'修改',1115,3,'#','menuItem','F','0','1','admin:role:edit','#','develop','2021-03-16 17:19:43','',NULL,''),
	(1119,'查询',1115,4,'#','menuItem','F','0','1','admin:role:list','#','develop','2021-03-16 17:19:43','',NULL,''),
	(1120,'导出',1115,6,'#','menuItem','F','0','1','admin:role:export','#','develop','2021-03-16 17:19:43','',NULL,''),
	(1126,'密钥管理',3,3,'/admin/token','menuItem','C','0','1','admin:token:view','fa fa-bomb','develop','2021-03-18 08:13:11','develop','2021-03-18 08:13:59',''),
	(1127,'添加',1126,1,'#','menuItem','F','0','1','admin:token:add','#','develop','2021-03-18 08:13:11','',NULL,''),
	(1128,'删除',1126,2,'#','menuItem','F','0','1','admin:token:remove','#','develop','2021-03-18 08:13:11','',NULL,''),
	(1129,'修改',1126,3,'#','menuItem','F','0','1','admin:token:edit','#','develop','2021-03-18 08:13:11','',NULL,''),
	(1130,'查询',1126,4,'#','menuItem','F','0','1','admin:token:list','#','develop','2021-03-18 08:13:11','',NULL,''),
	(1131,'验证码',3,5,'/admin/code','menuItem','C','0','1','admin:code:view','fa fa-envelope','develop','2021-03-18 08:41:12','',NULL,''),
	(1132,'查询',1131,4,'#','menuItem','F','0','1','admin:code:list','#','develop','2021-03-18 08:41:12','',NULL,''),
	(1133,'用户推荐',1062,3,'/mining/user/referrer','menuItem','C','0','1','mining:user:referrer:view','fa fa-users','develop','2021-03-18 17:18:55','',NULL,''),
	(1134,'导入',1133,5,'#','menuItem','F','0','1','mining:user:referrer:import','#','develop','2021-03-18 17:18:55','',NULL,''),
	(1135,'查询',1133,5,'#','menuItem','F','0','1','mining:user:referrer:list','#','develop','2021-03-18 17:18:55','',NULL,''),
	(1136,'提币设置',1065,1,'/filminer/symbol','menuItem','C','0','1','filminer:symbol:view','fa fa-diamond','develop','2021-03-19 09:20:23','develop','2021-08-30 08:25:37',''),
	(1137,'修改',1136,3,'#','menuItem','F','0','1','filminer:symbol:edit','#','develop','2021-03-19 09:20:23','develop','2021-08-30 08:25:51',''),
	(1138,'查询',1136,3,'#','menuItem','F','0','1','filminer:symbol:list','#','develop','2021-03-19 09:20:23','develop','2021-08-30 08:26:02',''),
	(1139,'用户反馈',1066,5,'/admin/advise','menuItem','C','0','1','admin:advise:view','fa fa-question-circle','develop','2021-03-19 13:58:05','',NULL,''),
	(1140,'添加',1139,1,'#','menuItem','F','0','1','admin:advise:add','#','develop','2021-03-19 13:58:05','',NULL,''),
	(1141,'删除',1139,2,'#','menuItem','F','0','1','admin:advise:remove','#','develop','2021-03-19 13:58:05','',NULL,''),
	(1142,'修改',1139,3,'#','menuItem','F','0','1','admin:advise:edit','#','develop','2021-03-19 13:58:05','',NULL,''),
	(1143,'查询',1139,4,'#','menuItem','F','0','1','admin:advise:list','#','develop','2021-03-19 13:58:05','',NULL,''),
	(1144,'日志管理',0,20,'#','menuItem','M','0','1',NULL,'fa fa-dot-circle-o','develop','2021-03-22 12:04:34','',NULL,''),
	(1145,'操作日志',1144,1,'/monitor/operlog','','C','0','1','monitor:operlog:view','fa fa-address-book','admin','2021-01-05 15:07:04','',NULL,'操作日志菜单'),
	(1146,'登录日志',1144,2,'/monitor/logininfor','','C','0','1','monitor:logininfor:view','fa fa-file-image-o','admin','2021-01-05 15:07:04','',NULL,'登录日志菜单'),
	(1147,'操作查询',1145,1,'#','','F','0','1','monitor:operlog:list','#','admin','2021-01-05 15:07:06','',NULL,''),
	(1148,'操作删除',1145,2,'#','','F','0','1','monitor:operlog:remove','#','admin','2021-01-05 15:07:06','',NULL,''),
	(1149,'详细信息',1145,3,'#','','F','0','1','monitor:operlog:detail','#','admin','2021-01-05 15:07:06','',NULL,''),
	(1150,'日志导出',1145,4,'#','','F','0','1','monitor:operlog:export','#','admin','2021-01-05 15:07:06','',NULL,''),
	(1151,'登录查询',1146,1,'#','','F','0','1','monitor:logininfor:list','#','admin','2021-01-05 15:07:06','',NULL,''),
	(1152,'登录删除',1146,2,'#','','F','0','1','monitor:logininfor:remove','#','admin','2021-01-05 15:07:06','',NULL,''),
	(1153,'日志导出',1146,3,'#','','F','0','1','monitor:logininfor:export','#','admin','2021-01-05 15:07:06','',NULL,''),
	(1154,'账户解锁',1146,4,'#','','F','0','1','monitor:logininfor:unlock','#','admin','2021-01-05 15:07:06','',NULL,''),
	(1155,'导出',1133,3,'#','menuItem','F','0','1','mining:user:referrer:export','#','develop','2021-03-26 07:59:54','',NULL,''),
	(1157,'导出',1084,2,'#','menuItem','F','0','1','profit:static:export','#','develop','2021-03-26 08:02:12','develop','2021-04-07 13:41:18',''),
	(1160,'导出',1095,2,'#','menuItem','F','0','1','mining:wallet:withdrawal:export','#','develop','2021-03-26 08:03:18','',NULL,''),
	(1161,'导出',1097,2,'#','menuItem','F','0','1','mining:wallet:deposit:export','#','develop','2021-03-26 08:03:43','',NULL,''),
	(1162,'二维码',3,3,'/tool/resource','menuItem','C','0','1','tool:resource:view','fa fa-file-image-o','develop','2021-03-26 17:16:46','',NULL,''),
	(1164,'规则设置',0,10,'#','menuItem','M','0','1',NULL,'fa fa-battery','develop','2021-04-07 12:17:39','',NULL,''),
	(1165,'推荐奖励',1164,1,'/rule/referral','menuItem','C','0','1','rule:referral:view','fa fa-check-circle','develop','2021-04-07 12:20:20','',NULL,''),
	(1166,'添加',1165,1,'#','menuItem','F','0','1','rule:referral:add','#','develop','2021-04-07 12:20:20','',NULL,''),
	(1167,'删除',1165,2,'#','menuItem','F','0','1','rule:referral:remove','#','develop','2021-04-07 12:20:20','',NULL,''),
	(1168,'修改',1165,3,'#','menuItem','F','0','1','rule:referral:edit','#','develop','2021-04-07 12:20:20','',NULL,''),
	(1169,'查询',1165,4,'#','menuItem','F','0','1','rule:referral:list','#','develop','2021-04-07 12:20:20','',NULL,''),
	(1170,'静态收益服务费',1164,2,'/rule/umbrella','menuItem','C','0','1','rule:umbrella:view','fa fa-crosshairs','develop','2021-04-07 12:21:07','develop','2021-08-24 16:05:55',''),
	(1171,'添加',1170,1,'#','menuItem','F','0','1','rule:umbrella:add','#','develop','2021-04-07 12:21:07','',NULL,''),
	(1172,'删除',1170,2,'#','menuItem','F','0','1','rule:umbrella:remove','#','develop','2021-04-07 12:21:07','',NULL,''),
	(1173,'修改',1170,3,'#','menuItem','F','0','1','rule:umbrella:edit','#','develop','2021-04-07 12:21:07','',NULL,''),
	(1174,'查询',1170,4,'#','menuItem','F','0','1','rule:umbrella:list','#','develop','2021-04-07 12:21:07','',NULL,''),
	(1175,'导出',1170,6,'#','menuItem','F','0','1','rule:umbrella:export','#','develop','2021-04-07 12:21:07','',NULL,''),
	(1176,'积分规则',1164,3,'/rule/level','menuItem','C','0','1','rule:level:view','fa fa-fighter-jet','develop','2021-04-07 12:22:09','develop','2021-08-24 16:06:14',''),
	(1177,'添加',1176,1,'#','menuItem','F','0','1','rule:level:add','#','develop','2021-04-07 12:22:09','',NULL,''),
	(1178,'删除',1176,2,'#','menuItem','F','0','1','rule:level:remove','#','develop','2021-04-07 12:22:09','',NULL,''),
	(1179,'修改',1176,3,'#','menuItem','F','0','1','rule:level:edit','#','develop','2021-04-07 12:22:09','',NULL,''),
	(1180,'查询',1176,4,'#','menuItem','F','0','1','rule:level:list','#','develop','2021-04-07 12:22:09','',NULL,''),
	(1181,'导出',1176,6,'#','menuItem','F','0','1','rule:level:export','#','develop','2021-04-07 12:22:09','',NULL,''),
	(1182,'管理奖励',1164,4,'/rule/manager','menuItem','C','1','1','rule:manager:view','fa fa-male','develop','2021-04-07 12:22:42','develop','2021-08-24 16:06:24',''),
	(1185,'修改',1182,3,'#','menuItem','F','0','1','rule:manager:edit','#','develop','2021-04-07 12:22:42','',NULL,''),
	(1186,'查询',1182,4,'#','menuItem','F','0','1','rule:manager:list','#','develop','2021-04-07 12:22:42','',NULL,''),
	(1188,'推荐奖励',1064,2,'/profit/referral','menuItem','C','0','1','profit:referral:view','fa fa-bar-chart','develop','2021-04-07 13:42:33','develop','2021-04-07 13:42:59',''),
	(1189,'查询',1188,4,'#','menuItem','F','0','1','profit:referral:list','#','develop','2021-04-07 13:42:33','',NULL,''),
	(1190,'导出',1188,6,'#','menuItem','F','0','1','profit:referral:export','#','develop','2021-04-07 13:42:33','',NULL,''),
	(1191,'市场奖励',1064,3,'/profit/umbrella','menuItem','C','1','1','profit:umbrella:view','fa fa-bar-chart-o','develop','2021-04-07 13:45:22','develop','2021-08-26 07:53:15',''),
	(1192,'查询',1191,4,'#','menuItem','F','0','1','profit:umbrella:list','#','develop','2021-04-07 13:45:22','',NULL,''),
	(1193,'导出',1191,6,'#','menuItem','F','0','1','profit:umbrella:export','#','develop','2021-04-07 13:45:22','',NULL,''),
	(1194,'管理奖励',1064,4,'/profit/manager','menuItem','C','1','1','profit:manager:view','fa fa-bar-chart','develop','2021-04-07 13:45:57','develop','2021-08-26 07:53:01',''),
	(1195,'查询',1194,4,'#','menuItem','F','0','1','profit:manager:list','#','develop','2021-04-07 13:45:57','',NULL,''),
	(1196,'导出',1194,6,'#','menuItem','F','0','1','profit:manager:export','#','develop','2021-04-07 13:45:57','',NULL,''),
	(1197,'节点分红',1064,5,'/profit/feedback','menuItem','C','0','1','profit:feedback:view','fa fa-bar-chart-o','develop','2021-04-07 13:46:36','',NULL,''),
	(1198,'查询',1197,4,'#','menuItem','F','0','1','profit:feedback:list','#','develop','2021-04-07 13:46:36','',NULL,''),
	(1199,'导出',1197,6,'#','menuItem','F','0','1','profit:feedback:export','#','develop','2021-04-07 13:46:36','',NULL,''),
	(1200,'质押赎回',1063,4,'/filecoin/release','menuItem','C','0','1','filecoin:release:view','fa fa-dot-circle-o','develop','2021-04-07 17:24:04','',NULL,''),
	(1201,'修改',1200,3,'#','menuItem','F','0','1','filecoin:release:edit','#','develop','2021-04-07 17:24:04','',NULL,''),
	(1202,'查询',1200,4,'#','menuItem','F','0','1','filecoin:release:list','#','develop','2021-04-07 17:24:04','',NULL,''),
	(1203,'导出',1200,6,'#','menuItem','F','0','1','filecoin:release:export','#','develop','2021-04-07 17:24:05','',NULL,''),
	(1204,'数据查询',3,6,'/database/query','menuItem','C','0','1','database:query:view','fa fa-paw','develop','2021-04-08 14:18:55','',NULL,''),
	(1205,'查询',1204,4,'#','menuItem','F','0','1','database:query:list','#','develop','2021-04-08 14:18:55','',NULL,''),
	(1206,'钱包记录',3,7,'/mining/wallet/record','menuItem','C','0','1','mining:wallet:record:view','fa fa-graduation-cap','develop','2021-04-15 18:23:42','develop','2021-04-15 18:53:06',''),
	(1207,'查询',1206,4,'#','menuItem','F','0','1','mining:wallet:record:list','#','develop','2021-04-15 18:23:43','',NULL,''),
	(1208,'导出',1206,2,'#','menuItem','F','0','1','mining:wallet:record:export','#','develop','2021-04-15 18:52:34','',NULL,''),
	(1209,'收益汇总',1064,6,'/profit/user','menuItem','C','0','1','profit:user:view','fa fa-bars','develop','2021-04-16 10:09:38','',NULL,''),
	(1210,'查询',1209,4,'#','menuItem','F','0','1','profit:user:list','#','develop','2021-04-16 10:09:39','',NULL,''),
	(1211,'导出',1209,6,'#','menuItem','F','0','1','profit:user:export','#','develop','2021-04-16 10:09:39','',NULL,''),
	(1212,'KPI报表',1064,0,'/profit/kpi','menuItem','C','0','1','profit:kpi:view','fa fa-bar-chart','develop','2021-04-19 09:07:35','',NULL,''),
	(1213,'删除',1212,2,'#','menuItem','F','0','1','profit:kpi:remove','#','develop','2021-04-19 09:07:35','',NULL,''),
	(1214,'查询',1212,4,'#','menuItem','F','0','1','profit:kpi:list','#','develop','2021-04-19 09:07:35','',NULL,''),
	(1215,'导出',1212,6,'#','menuItem','F','0','1','profit:kpi:export','#','develop','2021-04-19 09:07:35','',NULL,''),
	(1216,'审核',1095,3,'#','menuItem','F','0','1','mining:wallet:withdrawal:edit','#','develop','2021-04-22 11:00:42','',NULL,''),
	(1217,'积分兑换',0,18,'#','menuItem','M','0','1',NULL,'fa fa-diamond','develop','2021-08-26 12:56:42','',NULL,''),
	(1218,'积分商城',1217,1,'/goods/goods','menuItem','C','0','1','goods:goods:view','fa fa-coffee','develop','2021-08-26 12:57:21','',NULL,''),
	(1219,'添加',1218,1,'#','menuItem','F','0','1','goods:goods:add','#','develop','2021-08-26 12:57:21','',NULL,''),
	(1220,'删除',1218,2,'#','menuItem','F','0','1','goods:goods:remove','#','develop','2021-08-26 12:57:21','',NULL,''),
	(1221,'修改',1218,3,'#','menuItem','F','0','1','goods:goods:edit','#','develop','2021-08-26 12:57:21','',NULL,''),
	(1222,'查询',1218,4,'#','menuItem','F','0','1','goods:goods:list','#','develop','2021-08-26 12:57:21','',NULL,''),
	(1223,'导出',1218,6,'#','menuItem','F','0','1','goods:goods:export','#','develop','2021-08-26 12:57:21','',NULL,''),
	(1224,'积分兑换',1217,2,'/goods/exchange','menuItem','C','0','1','goods:exchange:view','fa fa-exchange','develop','2021-08-26 12:58:09','',NULL,''),
	(1225,'添加',1224,1,'#','menuItem','F','0','1','goods:exchange:add','#','develop','2021-08-26 12:58:09','',NULL,''),
	(1226,'删除',1224,2,'#','menuItem','F','0','1','goods:exchange:remove','#','develop','2021-08-26 12:58:09','',NULL,''),
	(1227,'修改',1224,3,'#','menuItem','F','0','1','goods:exchange:edit','#','develop','2021-08-26 12:58:09','',NULL,''),
	(1228,'查询',1224,4,'#','menuItem','F','0','1','goods:exchange:list','#','develop','2021-08-26 12:58:09','',NULL,''),
	(1229,'导出',1224,6,'#','menuItem','F','0','1','goods:exchange:export','#','develop','2021-08-26 12:58:09','',NULL,'');

/*!40000 ALTER TABLE `sys_menu` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table sys_notice
# ------------------------------------------------------------

DROP TABLE IF EXISTS `sys_notice`;

CREATE TABLE `sys_notice` (
  `notice_id` int(4) NOT NULL AUTO_INCREMENT COMMENT '公告ID',
  `notice_title` varchar(1024) NOT NULL DEFAULT '' COMMENT '公告标题',
  `notice_type` char(1) NOT NULL COMMENT '公告类型（1通知 2公告）',
  `notice_content` longtext COMMENT '公告内容',
  `status` char(1) DEFAULT '0' COMMENT '公告状态（0正常 1关闭）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`notice_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='通知公告表';



# Dump of table sys_oper_log
# ------------------------------------------------------------

DROP TABLE IF EXISTS `sys_oper_log`;

CREATE TABLE `sys_oper_log` (
  `oper_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '日志主键',
  `title` varchar(50) DEFAULT '' COMMENT '模块标题',
  `business_type` int(2) DEFAULT '0' COMMENT '业务类型（0其它 1新增 2修改 3删除）',
  `method` varchar(100) DEFAULT '' COMMENT '方法名称',
  `request_method` varchar(10) DEFAULT '' COMMENT '请求方式',
  `operator_type` int(1) DEFAULT '0' COMMENT '操作类别（0其它 1后台用户 2手机端用户）',
  `oper_name` varchar(50) DEFAULT '' COMMENT '操作人员',
  `dept_name` varchar(50) DEFAULT '' COMMENT '部门名称',
  `oper_url` varchar(255) DEFAULT '' COMMENT '请求URL',
  `oper_ip` varchar(50) DEFAULT '' COMMENT '主机地址',
  `oper_location` varchar(255) DEFAULT '' COMMENT '操作地点',
  `oper_param` varchar(2000) DEFAULT '' COMMENT '请求参数',
  `json_result` text COMMENT '返回参数',
  `status` int(1) DEFAULT '0' COMMENT '操作状态（0正常 1异常）',
  `error_msg` varchar(2000) DEFAULT '' COMMENT '错误消息',
  `oper_time` datetime DEFAULT NULL COMMENT '操作时间',
  PRIMARY KEY (`oper_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='操作日志记录';



# Dump of table sys_oper_notify
# ------------------------------------------------------------

DROP TABLE IF EXISTS `sys_oper_notify`;

CREATE TABLE `sys_oper_notify` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `type` int(1) NOT NULL DEFAULT '0' COMMENT '类型：0-邮件 1-短信',
  `operate_url` varchar(256) DEFAULT NULL COMMENT 'URL',
  `subject` varchar(256) DEFAULT NULL COMMENT '主题',
  `template` text COMMENT '模板',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` varchar(256) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='敏感操作通知';



# Dump of table sys_post
# ------------------------------------------------------------

DROP TABLE IF EXISTS `sys_post`;

CREATE TABLE `sys_post` (
  `post_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '岗位ID',
  `post_code` varchar(64) NOT NULL COMMENT '岗位编码',
  `post_name` varchar(50) NOT NULL COMMENT '岗位名称',
  `post_sort` int(4) NOT NULL COMMENT '显示顺序',
  `status` char(1) NOT NULL COMMENT '状态（0正常 1停用）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`post_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='岗位信息表';



# Dump of table sys_role
# ------------------------------------------------------------

DROP TABLE IF EXISTS `sys_role`;

CREATE TABLE `sys_role` (
  `role_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `role_name` varchar(30) NOT NULL COMMENT '角色名称',
  `role_key` varchar(100) NOT NULL COMMENT '角色权限字符串',
  `role_sort` int(4) NOT NULL COMMENT '显示顺序',
  `data_scope` char(1) DEFAULT '1' COMMENT '数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）',
  `status` char(1) NOT NULL COMMENT '角色状态（0正常 1停用）',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `index_page` varchar(256) DEFAULT NULL,
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色信息表';

LOCK TABLES `sys_role` WRITE;
/*!40000 ALTER TABLE `sys_role` DISABLE KEYS */;

INSERT INTO `sys_role` (`role_id`, `role_name`, `role_key`, `role_sort`, `data_scope`, `status`, `del_flag`, `index_page`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES
	(1,'超级管理员','admin',1,'1','0','0','main_filminer','develop','2021-01-05 10:29:45','',NULL,'超级管理员'),
	(2,'后台','root',2,'2','0','0','main_filminer','develop','2021-01-05 10:29:45','develop','2021-08-26 12:58:17','BeePlus'),
	(3,'查询','4ISGZD',1,'5','0','0','main_filminer','develop','2021-03-16 17:23:18','develop','2021-04-07 13:43:54',''),
	(4,'开发','soyatec',1,'1','0','0','main_filminer','develop','2021-01-05 10:29:45','develop','2021-04-08 14:04:30','开发');

/*!40000 ALTER TABLE `sys_role` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table sys_role_dept
# ------------------------------------------------------------

DROP TABLE IF EXISTS `sys_role_dept`;

CREATE TABLE `sys_role_dept` (
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  `dept_id` bigint(20) NOT NULL COMMENT '部门ID',
  PRIMARY KEY (`role_id`,`dept_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色和部门关联表';

LOCK TABLES `sys_role_dept` WRITE;
/*!40000 ALTER TABLE `sys_role_dept` DISABLE KEYS */;

INSERT INTO `sys_role_dept` (`role_id`, `dept_id`)
VALUES
	(3,101);

/*!40000 ALTER TABLE `sys_role_dept` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table sys_role_menu
# ------------------------------------------------------------

DROP TABLE IF EXISTS `sys_role_menu`;

CREATE TABLE `sys_role_menu` (
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  `menu_id` bigint(20) NOT NULL COMMENT '菜单ID',
  PRIMARY KEY (`role_id`,`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色和菜单关联表';

LOCK TABLES `sys_role_menu` WRITE;
/*!40000 ALTER TABLE `sys_role_menu` DISABLE KEYS */;

INSERT INTO `sys_role_menu` (`role_id`, `menu_id`)
VALUES
	(2,1062),
	(2,1063),
	(2,1064),
	(2,1065),
	(2,1066),
	(2,1067),
	(2,1068),
	(2,1069),
	(2,1070),
	(2,1071),
	(2,1072),
	(2,1073),
	(2,1074),
	(2,1075),
	(2,1076),
	(2,1077),
	(2,1078),
	(2,1079),
	(2,1080),
	(2,1081),
	(2,1082),
	(2,1083),
	(2,1084),
	(2,1085),
	(2,1088),
	(2,1095),
	(2,1096),
	(2,1097),
	(2,1098),
	(2,1099),
	(2,1100),
	(2,1101),
	(2,1102),
	(2,1103),
	(2,1104),
	(2,1105),
	(2,1106),
	(2,1107),
	(2,1108),
	(2,1109),
	(2,1110),
	(2,1111),
	(2,1112),
	(2,1113),
	(2,1114),
	(2,1115),
	(2,1116),
	(2,1117),
	(2,1118),
	(2,1119),
	(2,1120),
	(2,1133),
	(2,1135),
	(2,1136),
	(2,1137),
	(2,1138),
	(2,1139),
	(2,1140),
	(2,1141),
	(2,1142),
	(2,1143),
	(2,1144),
	(2,1145),
	(2,1146),
	(2,1147),
	(2,1148),
	(2,1149),
	(2,1150),
	(2,1151),
	(2,1152),
	(2,1153),
	(2,1154),
	(2,1155),
	(2,1157),
	(2,1160),
	(2,1161),
	(2,1164),
	(2,1165),
	(2,1168),
	(2,1169),
	(2,1170),
	(2,1173),
	(2,1174),
	(2,1176),
	(2,1179),
	(2,1180),
	(2,1182),
	(2,1185),
	(2,1186),
	(2,1188),
	(2,1189),
	(2,1190),
	(2,1191),
	(2,1192),
	(2,1193),
	(2,1194),
	(2,1195),
	(2,1196),
	(2,1197),
	(2,1198),
	(2,1199),
	(2,1200),
	(2,1201),
	(2,1202),
	(2,1203),
	(2,1209),
	(2,1210),
	(2,1211),
	(2,1212),
	(2,1213),
	(2,1214),
	(2,1215),
	(2,1216),
	(2,1217),
	(2,1218),
	(2,1219),
	(2,1220),
	(2,1221),
	(2,1222),
	(2,1223),
	(2,1224),
	(2,1225),
	(2,1226),
	(2,1227),
	(2,1228),
	(2,1229),
	(4,1),
	(4,2),
	(4,3),
	(4,100),
	(4,101),
	(4,102),
	(4,103),
	(4,104),
	(4,105),
	(4,106),
	(4,107),
	(4,108),
	(4,109),
	(4,110),
	(4,111),
	(4,112),
	(4,113),
	(4,115),
	(4,500),
	(4,501),
	(4,1000),
	(4,1001),
	(4,1002),
	(4,1003),
	(4,1004),
	(4,1005),
	(4,1006),
	(4,1007),
	(4,1008),
	(4,1009),
	(4,1010),
	(4,1011),
	(4,1012),
	(4,1013),
	(4,1014),
	(4,1015),
	(4,1016),
	(4,1017),
	(4,1018),
	(4,1019),
	(4,1020),
	(4,1021),
	(4,1022),
	(4,1023),
	(4,1024),
	(4,1025),
	(4,1026),
	(4,1027),
	(4,1028),
	(4,1029),
	(4,1030),
	(4,1031),
	(4,1032),
	(4,1033),
	(4,1034),
	(4,1035),
	(4,1036),
	(4,1037),
	(4,1038),
	(4,1039),
	(4,1040),
	(4,1041),
	(4,1042),
	(4,1043),
	(4,1044),
	(4,1045),
	(4,1046),
	(4,1047),
	(4,1048),
	(4,1049),
	(4,1050),
	(4,1051),
	(4,1052),
	(4,1053),
	(4,1054),
	(4,1055),
	(4,1056),
	(4,1057),
	(4,1058),
	(4,1059),
	(4,1126),
	(4,1127),
	(4,1128),
	(4,1129),
	(4,1130),
	(4,1131),
	(4,1132),
	(4,1162);

/*!40000 ALTER TABLE `sys_role_menu` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table sys_user
# ------------------------------------------------------------

DROP TABLE IF EXISTS `sys_user`;

CREATE TABLE `sys_user` (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `dept_id` bigint(20) DEFAULT NULL COMMENT '部门ID',
  `login_name` varchar(30) NOT NULL COMMENT '登录账号',
  `user_name` varchar(30) DEFAULT '' COMMENT '用户昵称',
  `user_type` varchar(2) DEFAULT '00' COMMENT '用户类型（00系统用户 01注册用户）',
  `email` varchar(50) DEFAULT '' COMMENT '用户邮箱',
  `phonenumber` varchar(20) DEFAULT '' COMMENT '手机号码',
  `sex` char(1) DEFAULT '0' COMMENT '用户性别（0男 1女 2未知）',
  `avatar` varchar(1024) DEFAULT '' COMMENT '头像路径',
  `password` varchar(50) DEFAULT '' COMMENT '密码',
  `salt` varchar(20) DEFAULT '' COMMENT '盐加密',
  `status` char(1) DEFAULT '0' COMMENT '帐号状态（0正常 1停用）',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `login_ip` varchar(50) DEFAULT '' COMMENT '最后登录IP',
  `login_date` datetime DEFAULT NULL COMMENT '最后登录时间',
  `pwd_update_date` datetime DEFAULT NULL COMMENT '密码最后更新时间',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`user_id`),
  KEY `user_type_INDEX` (`user_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户信息表';

LOCK TABLES `sys_user` WRITE;
/*!40000 ALTER TABLE `sys_user` DISABLE KEYS */;

INSERT INTO `sys_user` (`user_id`, `dept_id`, `login_name`, `user_name`, `user_type`, `email`, `phonenumber`, `sex`, `avatar`, `password`, `salt`, `status`, `del_flag`, `login_ip`, `login_date`, `pwd_update_date`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES
	(1,100,'develop','Developer','10','angryred@qq.com','13140000001','1','https://filminer-1301683227.cos.accelerate.myqcloud.com/2021/04/12/blob.image/jpeg','af1bd4a1901a6748861befe40ffbcb2b','111111','0','0','127.0.0.1','2021-08-30 08:39:33','2021-01-05 10:29:45','admin','2021-01-05 10:29:45','','2021-08-30 08:39:33','开发'),
	(2,101,'admin','Admin','00','angryred@qq.com','13140000002','0','https://filminer-1301683227.cos.accelerate.myqcloud.com/2021/04/12/blob.image/jpeg','e9c2459220e8af8a4debb5a65b97d635','222222','0','0','127.0.0.1','2021-08-30 08:26:22','2021-01-05 10:29:45','admin','2021-01-05 10:29:45','develop','2021-08-30 08:26:21','管理员'),
	(3,101,'root','Sword','01','root-user@soyatec.sword','13140000003','0','','','','0','0','',NULL,NULL,'',NULL,'',NULL,NULL),
	(4,100,'soyatec','Soyatec','10','angryred@qq.com','13140000001','1','https://filminer-1301683227.cos.accelerate.myqcloud.com/2021/04/12/blob.image/jpeg','af1bd4a1901a6748861befe40ffbcb2b','111111','0','0','127.0.0.1','2021-04-08 14:02:46','2021-01-05 10:29:45','admin','2021-01-05 10:29:45','','2021-04-08 14:02:46','开发'),
	(200000,NULL,'AA','','01','aa@filminer.cloud','15393170882','0','','7ebcb33a9930db652747dc3526b58538','XyZX9w','0','0','127.0.0.1','2021-08-26 10:22:00',NULL,'','2021-08-26 08:15:39','','2021-08-26 10:21:59',NULL),
	(200001,NULL,'BB','','01','bb@filminer.cloud','132000000002','0','','ecbbaf750b7d263fcac1c19e4d6e7be8','Z0OtST','0','0','',NULL,NULL,'','2021-08-26 08:15:42','',NULL,NULL),
	(200002,NULL,'CC','','01','cc@filminer.cloud','132000000003','0','','4b4e8fcce5ce7fb6eade12ee87e2d221','DtwkKO','0','0','',NULL,NULL,'','2021-08-26 08:15:47','',NULL,NULL),
	(200003,NULL,'DD','','01','dd@filminer.cloud','132000000004','0','','891b867a2150d0f8ad8e8a68caff9683','OU1jcM','0','0','',NULL,NULL,'','2021-08-26 08:15:49','',NULL,NULL),
	(200004,NULL,'EE','','01','ee@filminer.cloud','132000000005','0','','bd7df07c54d16198039a47c667449722','ayFiJx','0','0','',NULL,NULL,'','2021-08-26 08:15:52','',NULL,NULL),
	(200005,NULL,'FF','','01','ff@filminer.cloud','132000000006','0','','e7e9584a6fdbdfc6f1966002522ebbf8','TtymYU','0','0','',NULL,NULL,'','2021-08-26 08:15:55','',NULL,NULL),
	(200006,NULL,'GG','','01','gg@filminer.cloud','132000000007','0','','25e02ec64df24fa063542b6425754c8f','4lORXR','0','0','',NULL,NULL,'','2021-08-26 08:15:58','',NULL,NULL),
	(200007,NULL,'HH','','01','hh@filminer.cloud','132000000008','0','','4e9f841d0d5477b9e53c0e3e7b977ed8','8drT67','0','0','',NULL,NULL,'','2021-08-26 08:16:01','',NULL,NULL),
	(200008,NULL,'II','','01','ii@filminer.cloud','132000000009','0','','2a3f6e139077d3aade82c02bac9d0395','gacaQ1','0','0','',NULL,NULL,'','2021-08-26 08:16:04','',NULL,NULL),
	(200009,NULL,'JJ','','01','jj@filminer.cloud','132000000010','0','','48d6d486bf99c85b5aa48cc1a8d73d3f','GzWR5x','0','0','',NULL,NULL,'','2021-08-26 08:16:07','',NULL,NULL),
	(200010,NULL,'KK','','01','kk@filminer.cloud','132000000011','0','','c58c06213d96d273515415de5f5469a5','jAMAWb','0','0','',NULL,NULL,'','2021-08-26 08:16:10','',NULL,NULL),
	(200011,NULL,'LL','','01','ll@filminer.cloud','132000000012','0','','d36a8230720278c886b57d5fe761bece','CfhtVL','0','0','',NULL,NULL,'','2021-08-26 08:16:13','',NULL,NULL),
	(200012,NULL,'MM','','01','mm@filminer.cloud','132000000013','0','','2b7b7b8be40866300c83f31b77218024','PZw8Vx','0','0','',NULL,NULL,'','2021-08-26 08:16:16','',NULL,NULL),
	(200013,NULL,'NN','','01','nn@filminer.cloud','132000000014','0','','eab6a05889895a54ca4b3132c79e0644','tDt3q9','0','0','',NULL,NULL,'','2021-08-26 08:16:19','',NULL,NULL),
	(200014,NULL,'OO','','01','oo@filminer.cloud','132000000015','0','','8d8a34580b986b5fc153528c86d52513','D0bmcw','0','0','',NULL,NULL,'','2021-08-26 08:16:21','',NULL,NULL),
	(200015,NULL,'PP','','01','pp@filminer.cloud','132000000016','0','','7338439b38bb028c08f99baadcc28f4f','a0eoAF','0','0','',NULL,NULL,'','2021-08-26 08:16:24','',NULL,NULL);

/*!40000 ALTER TABLE `sys_user` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table sys_user_online
# ------------------------------------------------------------

DROP TABLE IF EXISTS `sys_user_online`;

CREATE TABLE `sys_user_online` (
  `sessionId` varchar(50) NOT NULL DEFAULT '' COMMENT '用户会话id',
  `login_name` varchar(50) DEFAULT '' COMMENT '登录账号',
  `dept_name` varchar(50) DEFAULT '' COMMENT '部门名称',
  `ipaddr` varchar(50) DEFAULT '' COMMENT '登录IP地址',
  `login_location` varchar(255) DEFAULT '' COMMENT '登录地点',
  `browser` varchar(50) DEFAULT '' COMMENT '浏览器类型',
  `os` varchar(50) DEFAULT '' COMMENT '操作系统',
  `status` varchar(10) DEFAULT '' COMMENT '在线状态on_line在线off_line离线',
  `start_timestamp` datetime DEFAULT NULL COMMENT 'session创建时间',
  `last_access_time` datetime DEFAULT NULL COMMENT 'session最后访问时间',
  `expire_time` int(5) DEFAULT '0' COMMENT '超时时间，单位为分钟',
  PRIMARY KEY (`sessionId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='在线用户记录';



# Dump of table sys_user_post
# ------------------------------------------------------------

DROP TABLE IF EXISTS `sys_user_post`;

CREATE TABLE `sys_user_post` (
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `post_id` bigint(20) NOT NULL COMMENT '岗位ID',
  PRIMARY KEY (`user_id`,`post_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户与岗位关联表';



# Dump of table sys_user_role
# ------------------------------------------------------------

DROP TABLE IF EXISTS `sys_user_role`;

CREATE TABLE `sys_user_role` (
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户和角色关联表';

LOCK TABLES `sys_user_role` WRITE;
/*!40000 ALTER TABLE `sys_user_role` DISABLE KEYS */;

INSERT INTO `sys_user_role` (`user_id`, `role_id`)
VALUES
	(1,1),
	(2,2),
	(4,4),
	(337585,3),
	(337586,3);

/*!40000 ALTER TABLE `sys_user_role` ENABLE KEYS */;
UNLOCK TABLES;


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
  `child_level_count` int(4) DEFAULT NULL,
  `child_level_id` bigint(20) DEFAULT NULL,
  `type` int(1) NOT NULL DEFAULT '0',
  `kind` int(1) NOT NULL DEFAULT '0',
  `status` int(1) NOT NULL DEFAULT '0' COMMENT '状态',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` varchar(256) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Mining级别';

LOCK TABLES `t_mining_level` WRITE;
/*!40000 ALTER TABLE `t_mining_level` DISABLE KEYS */;

INSERT INTO `t_mining_level` (`id`, `name`, `rate`, `min_amount`, `max_amount`, `referral_amount`, `umbrella_amount`, `child_level_count`, `child_level_id`, `type`, `kind`, `status`, `create_time`, `update_time`, `remark`)
VALUES
	(1,'青铜',0.040000,100.000000,500.000000,5.000000,50000.000000,112,NULL,0,0,0,'2021-04-07 13:26:20','2021-08-24 17:19:30',''),
	(2,'白银',0.050000,500.000000,2000.000000,25.000000,200000.000000,140,NULL,0,0,0,'2021-04-07 13:27:25','2021-08-24 17:19:32',''),
	(3,'黄金',0.060000,2000.000000,5000.000000,100.000000,1000000.000000,168,NULL,0,0,0,'2021-04-07 13:36:54','2021-08-24 17:19:36',''),
	(4,'王者',0.070000,5000.000000,-1.000000,250.000000,5000000.000000,196,NULL,0,0,0,'2021-04-07 13:37:23','2021-08-24 17:19:39','');

/*!40000 ALTER TABLE `t_mining_level` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table t_mining_symbol
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_mining_symbol`;

CREATE TABLE `t_mining_symbol` (
  `symbol` varchar(20) NOT NULL DEFAULT '' COMMENT '名称',
  `chain` varchar(128) NOT NULL DEFAULT '' COMMENT '链码',
  `type` int(11) NOT NULL DEFAULT '0' COMMENT '类型',
  `withdrawal_fee` double(20,4) NOT NULL DEFAULT '0.0000' COMMENT '提币手续费',
  `withdrawal_fee_symbol` varchar(20) DEFAULT NULL COMMENT '提币手续费单位',
  `withdrawal_enabled` int(1) NOT NULL DEFAULT '0' COMMENT '开关',
  `withdrawal_minimum` double(20,4) NOT NULL DEFAULT '0.0000' COMMENT '提币最小',
  `withdrawal_maximum` double(20,4) NOT NULL DEFAULT '0.0000' COMMENT '提币最多',
  `withdrawal_daily` double(20,4) NOT NULL DEFAULT '0.0000' COMMENT '提币日合计',
  `withdrawal_totally` double(20,4) NOT NULL DEFAULT '0.0000' COMMENT '提币总合计',
  `withdrawal_manual_audit` int(1) NOT NULL DEFAULT '0' COMMENT '提币审核开关',
  `withdrawal_audit_limation` double(20,6) NOT NULL DEFAULT '0.000000' COMMENT '提币免审额度',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` varchar(256) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`symbol`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='币种';

LOCK TABLES `t_mining_symbol` WRITE;
/*!40000 ALTER TABLE `t_mining_symbol` DISABLE KEYS */;

INSERT INTO `t_mining_symbol` (`symbol`, `chain`, `type`, `withdrawal_fee`, `withdrawal_fee_symbol`, `withdrawal_enabled`, `withdrawal_minimum`, `withdrawal_maximum`, `withdrawal_daily`, `withdrawal_totally`, `withdrawal_manual_audit`, `withdrawal_audit_limation`, `create_time`, `update_time`, `remark`)
VALUES
	('cny','',1,0.0000,NULL,0,0.0000,0.0000,0.0000,0.0000,0,0.000000,'2021-08-25 11:41:01','2021-08-25 11:41:04',NULL),
	('fil','',0,0.0100,'fil',0,1.0000,1000.0000,50000.0000,0.0000,0,0.000000,'2021-03-12 10:18:07','2021-08-30 08:21:04',''),
	('usdt','eth',0,1.0000,'usdt',0,1.0000,10000.0000,50000.0000,0.0000,0,20.000000,'2021-03-12 10:18:07','2021-04-23 07:43:25','');

/*!40000 ALTER TABLE `t_mining_symbol` ENABLE KEYS */;
UNLOCK TABLES;


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
	(5,'test','admin','2021-03-17 09:13:25',NULL,NULL),
	(6,'c3b06b4c-dc18-46af-8d79-0d2c082ca031','e533b8ac5f191c01c9e0736cc457980c','2021-03-18 08:24:00',NULL,NULL);

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
  KEY `address_INDEX` (`address`),
  KEY `user_id_symbol_amount_INDEX` (`user_id`,`symbol`,`amount`)
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



# Dump of table t_user_wallet_record_0
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_user_wallet_record_0`;

CREATE TABLE `t_user_wallet_record_0` (
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



# Dump of table t_user_wallet_record_1
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_user_wallet_record_1`;

CREATE TABLE `t_user_wallet_record_1` (
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



# Dump of table t_user_wallet_record_2
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_user_wallet_record_2`;

CREATE TABLE `t_user_wallet_record_2` (
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



# Dump of table t_user_wallet_union_record
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_user_wallet_union_record`;

CREATE TABLE `t_user_wallet_union_record` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `order_no` varchar(32) DEFAULT NULL COMMENT '订单号',
  `type` int(1) NOT NULL DEFAULT '0' COMMENT '类型 0-后台设置 1-加 2-减',
  `symbol` varchar(32) NOT NULL COMMENT '币种',
  `amount` double(20,6) NOT NULL DEFAULT '0.000000' COMMENT '金额',
  `status` int(1) NOT NULL DEFAULT '0' COMMENT '状态 0-未同步 1-已同步',
  `kind` int(1) NOT NULL DEFAULT '0',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `user_id_INDEX` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='钱包记录，联合查询';




/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
