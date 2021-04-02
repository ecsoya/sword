# ************************************************************
# Sequel Pro SQL dump
# Version 4541
#
# http://www.sequelpro.com/
# https://github.com/sequelpro/sequelpro
#
# Host: bj-cdb-fmgl4u90.sql.tencentcdb.com (MySQL 5.7.18-txsql-log)
# Database: zbx-bga
# Generation Time: 2021-03-25 01:22:53 +0000
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
	(1,'主框架页-默认皮肤样式名称','sys.index.skinName','skin-blue','Y','admin','2021-01-05 10:29:53','',NULL,'蓝色 skin-blue、绿色 skin-green、紫色 skin-purple、红色 skin-red、黄色 skin-yellow'),
	(2,'用户管理-账号初始密码','sys.user.initPassword','123456','Y','admin','2021-01-05 10:29:53','',NULL,'初始化密码 123456'),
	(3,'主框架页-侧边栏主题','sys.index.sideTheme','theme-dark','Y','admin','2021-01-05 10:29:53','',NULL,'深黑主题theme-dark，浅色主题theme-light，深蓝主题theme-blue'),
	(4,'账号自助-是否开启用户注册功能','sys.account.registerUser','false','Y','admin','2021-01-05 10:29:53','',NULL,'是否开启注册用户功能（true开启，false关闭）'),
	(5,'用户管理-密码字符范围','sys.account.chrtype','0','Y','admin','2021-01-05 10:29:53','',NULL,'默认任意字符范围，0任意（密码可以输入任意字符），1数字（密码只能为0-9数字），2英文字母（密码只能为a-z和A-Z字母），3字母和数字（密码必须包含字母，数字）,4字母数字和特殊字符（目前支持的特殊字符包括：~!@#$%^&*()-=_+）'),
	(6,'用户管理-初始密码修改策略','sys.account.initPasswordModify','0','Y','admin','2021-01-05 10:29:53','',NULL,'0：初始密码修改策略关闭，没有任何提示，1：提醒用户，如果未修改初始密码，则在登录时就会提醒修改密码对话框'),
	(7,'用户管理-账号密码更新周期','sys.account.passwordValidateDays','0','Y','admin','2021-01-05 10:29:53','',NULL,'密码更新周期（填写数字，数据初始化值为0不限制，若修改必须为大于0小于365的正整数），如果超过这个周期登录系统时，则在登录时就会提醒修改密码对话框'),
	(8,'主框架页-菜单导航显示风格','sys.index.menuStyle','default','Y','admin','2021-01-05 10:29:53','',NULL,'菜单导航显示风格（default为左侧导航菜单，topnav为顶部导航菜单）'),
	(9,'主框架页-是否开启页脚','sys.index.ignoreFooter','true','Y','admin','2021-01-05 10:29:53','',NULL,'是否开启底部页脚显示（true显示，false隐藏）'),
	(100,'注册需要推荐码','sword.registerNeedReferrerCode','true','Y','',NULL,'','2020-10-12 15:49:17',NULL),
	(101,'顶层用户ID','sword.rootUserId','3','N','',NULL,'',NULL,NULL),
	(102,'钱包默认支付密码','wallet.defaultPassword','111111','N','',NULL,'','2020-09-28 09:44:12',NULL),
	(103,'分享二维码前缀','sword.referralLinkUrl','http://62.234.130.187/bga/register/index.html?referrerCode=','N','',NULL,'develop','2021-03-19 12:03:30',''),
	(104,'注册手机号唯一','sword.registerMobileUnique','true','N','',NULL,'',NULL,NULL),
	(105,'注册邮箱唯一','sword.registerEmailUnique','true','N','',NULL,'',NULL,NULL),
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
	(100,0,'0','soyatec',0,'若依','15888888888','ry@qq.com','0','0','admin','2021-01-05 15:07:03','',NULL),
	(101,0,'0','sword',1,'若依','15888888888','ry@qq.com','0','0','admin','2021-01-05 15:07:03','',NULL);

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
	(1126, '密钥管理', 3, 3, '/admin/token', 'menuItem', 'C', '0', '1', 'admin:token:view', 'fa fa-bomb', 'develop', '2021-03-18 08:13:11', 'develop', '2021-03-18 08:13:59', ''),
	(1127, '添加', 1126, 1, '#', 'menuItem', 'F', '0', '1', 'admin:token:add', '#', 'develop', '2021-03-18 08:13:11', '', NULL, ''),
	(1128, '删除', 1126, 2, '#', 'menuItem', 'F', '0', '1', 'admin:token:remove', '#', 'develop', '2021-03-18 08:13:11', '', NULL, ''),
	(1129, '修改', 1126, 3, '#', 'menuItem', 'F', '0', '1', 'admin:token:edit', '#', 'develop', '2021-03-18 08:13:11', '', NULL, ''),
	(1130, '查询', 1126, 4, '#', 'menuItem', 'F', '0', '1', 'admin:token:list', '#', 'develop', '2021-03-18 08:13:11', '', NULL, ''),
	(1131, '验证码', 3, 5, '/admin/code', 'menuItem', 'C', '0', '1', 'admin:code:view', 'fa fa-envelope', 'develop', '2021-03-18 08:41:12', '', NULL, ''),
	(1132, '查询', 1131, 4, '#', 'menuItem', 'F', '0', '1', 'admin:code:list', '#', 'develop', '2021-03-18 08:41:12', '', NULL, ''),
	(1133, '二维码', 3, 3, '/tool/resource', 'menuItem', 'C', '0', '1', 'tool:resource:view', 'fa fa-file-image-o', 'develop', '2021-03-26 17:16:46', '', NULL, '');



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

LOCK TABLES `sys_notice` WRITE;
/*!40000 ALTER TABLE `sys_notice` DISABLE KEYS */;

INSERT INTO `sys_notice` (`notice_id`, `notice_title`, `notice_type`, `notice_content`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES
	(1,'亲爱的朋友们，新版BGA将于3月底上线，尽情期待','2',NULL,'0','admin','2021-03-16 17:11:02','',NULL,NULL),
	(2,'BGA轻量级APP上线了','1','<p>BGA独立APP将于3月底上线，欢迎大家体验</p>','0','admin','2021-03-16 17:11:48','',NULL,NULL);

/*!40000 ALTER TABLE `sys_notice` ENABLE KEYS */;
UNLOCK TABLES;


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
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色信息表';

LOCK TABLES `sys_role` WRITE;
/*!40000 ALTER TABLE `sys_role` DISABLE KEYS */;

INSERT INTO `sys_role` (`role_id`, `role_name`, `role_key`, `role_sort`, `data_scope`, `status`, `del_flag`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES
	(1,'超级管理员','admin',1,'1','0','0','admin','2021-01-05 10:29:45','',NULL,'超级管理员'),
	(2,'BGA','bga',2,'2','0','0','admin','2021-01-05 10:29:45','develop','2021-03-22 12:07:30','BeePlus'),
	(3,'查询','4ISGZD',1,'5','0','0','admin','2021-03-16 17:23:18','',NULL,NULL);

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
	(2,1086),
	(2,1087),
	(2,1088),
	(2,1089),
	(2,1090),
	(2,1091),
	(2,1092),
	(2,1093),
	(2,1094),
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
	(2,1121),
	(2,1122),
	(2,1123),
	(2,1124),
	(2,1125),
	(2,1133),
	(2,1134),
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
	(3,1062),
	(3,1063),
	(3,1064),
	(3,1065),
	(3,1066),
	(3,1067),
	(3,1071),
	(3,1073),
	(3,1077),
	(3,1079),
	(3,1083),
	(3,1084),
	(3,1085),
	(3,1086),
	(3,1087),
	(3,1089),
	(3,1093),
	(3,1095),
	(3,1096),
	(3,1097),
	(3,1098),
	(3,1099),
	(3,1103),
	(3,1104),
	(3,1108),
	(3,1109),
	(3,1113),
	(3,1115),
	(3,1119);

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
  `avatar` varchar(512) DEFAULT '' COMMENT '头像路径',
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
	(1,100,'develop','Developer','10','develop@soyatec.sword','13140000001','1','https://bga-1301683227.file.myqcloud.com/2021/03/19/blob.image/jpeg','af1bd4a1901a6748861befe40ffbcb2b','111111','0','0','127.0.0.1','2021-03-25 09:06:16','2021-01-05 10:29:45','admin','2021-01-05 10:29:45','','2021-03-25 09:06:18','开发'),
	(2,101,'admin','Admin','00','admin@soyatec.com','13140000002','0','https://bga-1301683227.cos.ap-shanghai.myqcloud.com/2021/03/18/blob.image/jpeg','e9c2459220e8af8a4debb5a65b97d635','222222','0','0','127.0.0.1','2021-03-25 08:55:33','2021-01-05 10:29:45','admin','2021-01-05 10:29:45','develop','2021-03-25 08:55:34','管理员'),
	(3,101,'sword','Sword','01','root-user@soyatec.sword','13140000003','0','','','','0','0','',NULL,NULL,'',NULL,'',NULL,NULL);

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
	(337585,3),
	(337586,3);

/*!40000 ALTER TABLE `sys_user_role` ENABLE KEYS */;
UNLOCK TABLES;



/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
