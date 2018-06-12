/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50640
Source Host           : localhost:3306
Source Database       : feing-account

Target Server Type    : MYSQL
Target Server Version : 50640
File Encoding         : 65001

Date: 2018-06-12 01:26:44
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sy_res
-- ----------------------------
DROP TABLE IF EXISTS `sy_res`;
CREATE TABLE `sy_res` (
  `id` varchar(255) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `sn` varchar(255) DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL,
  `icon` varchar(255) DEFAULT NULL,
  `location` int(11) DEFAULT NULL,
  `parent_id` varchar(255) DEFAULT NULL,
  `has_children` bit(1) DEFAULT b'0',
  `display` bit(1) DEFAULT b'1',
  `new_window` bit(1) DEFAULT b'0',
  `beta` bit(1) DEFAULT b'0',
  `enabled` bit(1) DEFAULT b'1',
  `descript` varchar(255) DEFAULT NULL,
  `created_by` varchar(50) NOT NULL,
  `created_date` datetime NOT NULL,
  `last_modified_by` varchar(50) DEFAULT NULL,
  `last_modified_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sy_res
-- ----------------------------
INSERT INTO `sy_res` VALUES ('0d7698d5-5289-4161-9a67-19bc82abf4be', '配置管理', '/monitor/service', 'MONITOR_SERVICE', '003002', 'fa fa-gg', '2', '8d92aa19-b7f1-470c-92a7-2aa58b9d17cc', '\0', '', '\0', '\0', '', null, 'admin', '2018-04-08 22:40:49', 'admin', '2018-06-11 22:27:20');
INSERT INTO `sy_res` VALUES ('27087df9-9eb3-408a-b19b-81a6588d502e', '分组管理', '/apis/group', 'API_GROUP', '002001', 'fa fa-book', '1', '677e5457-021b-4344-a34d-9c7273105821', '\0', '', '\0', '\0', '', null, 'admin', '2017-10-20 16:00:08', 'admin', '2018-05-17 08:16:18');
INSERT INTO `sy_res` VALUES ('2c9cd0815c59db1e015c59df8a350000', '数据字典', '/system/dict', 'SYS_DICT', '006004', 'ti-dropbox-alt', '4', '4028388148ac878b0148ac8820090000', '\0', '', '\0', '\0', '', '系统数据字典，公共数据配置/存储中心', 'admin', '2017-08-02 03:30:33', 'admin', '2018-06-11 22:27:20');
INSERT INTO `sy_res` VALUES ('4028388148ac878b0148ac8820090000', '系统管理', '/system', 'SYS', '006', 'glyphicon glyphicon-cog', '6', '-1', '', '', '\0', '\0', '', '管理系统中的用户、角色、菜单等', 'admin', '2017-08-02 03:30:33', 'admin', '2018-06-11 22:27:20');
INSERT INTO `sy_res` VALUES ('4028388148acb42f0148acccd58d0001', '角色管理', '/system/role', 'SYS_ROLE', '006002', 'fa fa-group', '2', '4028388148ac878b0148ac8820090000', '\0', '', '\0', '\0', '', null, 'admin', '2017-08-02 03:30:33', 'admin', '2018-06-11 22:27:20');
INSERT INTO `sy_res` VALUES ('4028388148b2fe810148b32ef7120030', '菜单管理', '/system/res', 'SYS_RES', '006003', 'ti-menu-alt', '3', '4028388148ac878b0148ac8820090000', '\0', '', '\0', '\0', '', null, 'admin', '2017-08-02 03:30:33', 'admin', '2018-06-11 22:27:20');
INSERT INTO `sy_res` VALUES ('40283881494264460149428484610000', '首页', '/welcome', 'HOME', '001', 'glyphicon glyphicon-home', '1', '-1', '\0', '', '\0', '\0', '', '集中显示各种数据，报表等。', 'admin', '2017-08-02 03:30:33', 'admin', '2017-10-20 16:47:09');
INSERT INTO `sy_res` VALUES ('4028388149428faf0149429c02770000', '系统日志', '/system/log', 'SYS_LOG', '006008', 'fa fa-history', '8', '4028388148ac878b0148ac8820090000', '\0', '', '\0', '\0', '', '查看系统日志信息，包含所有功能的操作日志。', 'admin', '2017-08-02 03:30:33', 'admin', '2018-06-11 22:27:20');
INSERT INTO `sy_res` VALUES ('402880e85d2e4679015d2e486d1f0003', '文件管理', '/system/senior/sysfile', 'SYS_FILE', '006005', 'fa fa-suitcase', '5', '4028388148ac878b0148ac8820090000', '\0', '', '\0', '\0', '', null, 'admin', '2017-08-02 03:30:33', 'admin', '2018-06-11 22:27:20');
INSERT INTO `sy_res` VALUES ('402881ee547ea15801547eb07a2f0002', '数据源管理', '/lf/datasource', 'DATASORUCE', '005', 'fa fa-database', '5', '-1', '\0', '', '\0', '\0', '', null, 'admin', '2017-08-02 03:30:33', 'admin', '2018-06-11 22:27:20');
INSERT INTO `sy_res` VALUES ('402881ee547ea15801547eb17d800004', '图表管理', '/lf/chart', 'CHART', '004', 'fa fa-pie-chart', '4', '-1', '\0', '', '\0', '\0', '', '基于 自定义数据源 模块生成图表', 'admin', '2017-08-02 03:30:33', 'admin', '2018-06-11 22:27:20');
INSERT INTO `sy_res` VALUES ('40289fe85cb671b8015cb6760f270001', '数据库操作', '/system/senior/database', 'SYS_DATABASE', '006006', 'fa fa-database', '6', '4028388148ac878b0148ac8820090000', '\0', '', '\0', '\0', '', null, 'admin', '2017-08-02 03:30:33', 'admin', '2018-06-11 22:27:20');
INSERT INTO `sy_res` VALUES ('40289fe85cb7239b015cb740d3480000', '系统初始化', '/system/senior/init', 'SYS_INIT', '006011', 'fa fa-play-circle', '11', '4028388148ac878b0148ac8820090000', '\0', '', '\0', '\0', '', null, 'admin', '2017-08-02 03:30:33', 'admin', '2018-06-11 22:27:20');
INSERT INTO `sy_res` VALUES ('40289fe85ccafd24015ccb02470c0000', '用户管理', '/system/user', 'SYS_USER', '006001', 'ti-user', '1', '4028388148ac878b0148ac8820090000', '\0', '', '\0', '\0', '', null, 'admin', '2017-08-02 03:30:33', 'admin', '2018-06-11 22:27:20');
INSERT INTO `sy_res` VALUES ('40289fe85d27bdb6015d27c1c17a0003', '系统配置', '/system/config', 'SYS_CONFIG', '006010', 'ti-settings', '10', '4028388148ac878b0148ac8820090000', '\0', '', '\0', '\0', '', null, 'admin', '2017-08-02 03:30:33', 'admin', '2018-06-11 22:27:20');
INSERT INTO `sy_res` VALUES ('40289fe85d27e86d015d27eaea120004', '插件管理', '/system/senior/plugin', 'SYS_PLUGIN', '006009', 'fa fa-plug', '9', '4028388148ac878b0148ac8820090000', '\0', '', '\0', '\0', '', null, 'admin', '2017-08-02 03:30:33', 'admin', '2018-06-11 22:27:20');
INSERT INTO `sy_res` VALUES ('48630f3b-31cc-4f67-95ec-a439e76b6154', '流量控制', '/apis/strategy/flow', 'API_STRATEGY_FLOW', '002003', 'fa fa-globe', '3', '677e5457-021b-4344-a34d-9c7273105821', '\0', '', '\0', '\0', '', null, 'admin', '2017-10-20 16:43:13', 'admin', '2018-06-11 22:01:10');
INSERT INTO `sy_res` VALUES ('677e5457-021b-4344-a34d-9c7273105821', '开放API', '/apis', 'API', '002', 'fa fa-buysellads', '2', '-1', '', '', '\0', '\0', '', null, 'admin', '2017-08-07 00:16:09', 'admin', '2018-04-13 00:30:12');
INSERT INTO `sy_res` VALUES ('7d14c59c-135f-494c-bc1e-0dcb2304661d', '签名密钥', '/apis/sign', 'API_SIGN', '002005', 'fa fa-key', '5', '677e5457-021b-4344-a34d-9c7273105821', '\0', '', '\0', '\0', '', null, 'admin', '2017-10-20 16:44:34', 'admin', '2018-06-11 22:27:20');
INSERT INTO `sy_res` VALUES ('8d92aa19-b7f1-470c-92a7-2aa58b9d17cc', '动态监控', '/monitor', 'MONITOR', '003', 'fa fa-cloud', '3', '-1', '', '', '\0', '\0', '', null, 'admin', '2018-04-09 23:42:23', 'admin', '2018-06-11 22:27:20');
INSERT INTO `sy_res` VALUES ('8ebbeeec-6fbb-439b-a43f-dca4f21b9ad7', '链路跟踪', '/monitor/client/zipkin', 'MONITOR_ZIPKIN', '003004', 'fa fa-users', '4', '8d92aa19-b7f1-470c-92a7-2aa58b9d17cc', '\0', '', '\0', '\0', '', null, 'admin', '2018-05-27 04:15:52', 'admin', '2018-06-11 22:27:20');
INSERT INTO `sy_res` VALUES ('99ef45c1-69e7-40ef-aaf9-0df3de672759', '容器管理', '/monitor/client/rancher', 'MONITOR_RANCHER', '003003', 'fa fa-cloud', '3', '8d92aa19-b7f1-470c-92a7-2aa58b9d17cc', '\0', '', '\0', '\0', '', null, 'admin', '2018-05-26 07:27:22', 'admin', '2018-06-11 22:27:20');
INSERT INTO `sy_res` VALUES ('b6dd9eea-b6b1-437d-a10a-ae2078521871', 'API列表', '/apis/list', 'API_LIST', '002002', 'glyphicon glyphicon-eye-open', '2', '677e5457-021b-4344-a34d-9c7273105821', '\0', '', '\0', '\0', '', null, 'admin', '2017-10-20 16:00:38', 'admin', '2017-10-29 09:03:24');
INSERT INTO `sy_res` VALUES ('cd4a95cd-9a45-4527-811e-71230796d8d0', '数据源监控', '/system/senior/druid', 'SYS_DRUID', '006007', 'ti-server', '7', '4028388148ac878b0148ac8820090000', '\0', '', '\0', '\0', '\0', null, 'admin', '2017-08-08 06:49:23', 'admin', '2018-06-11 22:27:20');
INSERT INTO `sy_res` VALUES ('d19392cb-58fe-4ff0-b487-fb5b895dcc8f', '监控方案', '/monitor/solution', 'MONITOR_SOLUTION', '003001', 'fa fa-book', '1', '8d92aa19-b7f1-470c-92a7-2aa58b9d17cc', '\0', '', '\0', '\0', '', null, 'admin', '2018-04-09 23:45:22', 'admin', '2018-06-11 22:27:20');
INSERT INTO `sy_res` VALUES ('d8740eb4-d7e4-48a1-bc91-70c581dfa609', '预警控制', '/apis/strategy/alarm', 'API_STRATEGY_ALARM', '002004', 'fa fa-bell', '4', '677e5457-021b-4344-a34d-9c7273105821', '\0', '', '\0', '\0', '', null, 'admin', '2018-06-11 22:27:02', 'admin', '2018-06-11 22:29:38');
INSERT INTO `sy_res` VALUES ('dcdaf2e2-bd74-4da8-b04e-2062b41da4f4', '令牌管理', '/apis/token', 'API_TOKEN', '002006', 'fa fa-btc', '6', '677e5457-021b-4344-a34d-9c7273105821', '\0', '', '\0', '\0', '\0', null, 'admin', '2017-10-20 16:45:20', 'admin', '2018-06-11 22:27:20');
INSERT INTO `sy_res` VALUES ('e969b46a-3f1b-4f18-97d5-34fad76ffb66', 'API日志', '/monitor/log', 'API_LOG', '002007', 'fa fa-eye', '7', '677e5457-021b-4344-a34d-9c7273105821', '\0', '', '\0', '\0', '', null, 'admin', '2018-05-08 05:30:28', 'admin', '2018-06-11 22:27:20');

-- ----------------------------
-- Table structure for sy_res_btn
-- ----------------------------
DROP TABLE IF EXISTS `sy_res_btn`;
CREATE TABLE `sy_res_btn` (
  `id` varchar(255) NOT NULL,
  `code` varchar(255) DEFAULT NULL,
  `location` int(11) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `res_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKryvr50nx72lliyqd8u84ny6ut` (`res_id`),
  CONSTRAINT `FKryvr50nx72lliyqd8u84ny6ut` FOREIGN KEY (`res_id`) REFERENCES `sy_res` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sy_res_btn
-- ----------------------------
INSERT INTO `sy_res_btn` VALUES ('159af381-1f3c-496c-8c42-75027613ab44', 'btnRemove', '2', '删除', '48630f3b-31cc-4f67-95ec-a439e76b6154');
INSERT INTO `sy_res_btn` VALUES ('1d7acde4-f0c3-4fec-b113-0f626c2befea', 'btnView', '4', '查看', '48630f3b-31cc-4f67-95ec-a439e76b6154');
INSERT INTO `sy_res_btn` VALUES ('3f85c254-ef7a-4712-8279-131a85a8d70c', 'btnRemove', '2', '删除', 'b6dd9eea-b6b1-437d-a10a-ae2078521871');
INSERT INTO `sy_res_btn` VALUES ('402880e85cdba52d015cdba5a306000c', 'btnAdd', null, '新增', '2c9cd0815c59db1e015c59df8a350000');
INSERT INTO `sy_res_btn` VALUES ('402880e85cdba52d015cdba5a306000d', 'btnRemove', null, '删除', '2c9cd0815c59db1e015c59df8a350000');
INSERT INTO `sy_res_btn` VALUES ('402880e85cdba52d015cdba5a306000e', 'btnEdit', null, '修改', '2c9cd0815c59db1e015c59df8a350000');
INSERT INTO `sy_res_btn` VALUES ('402880e85cdba52d015cdba5a306000f', 'btnView', null, '查看', '2c9cd0815c59db1e015c59df8a350000');
INSERT INTO `sy_res_btn` VALUES ('402880e85cdba52d015cdba5a3060010', 'btnEnable', null, '启用', '2c9cd0815c59db1e015c59df8a350000');
INSERT INTO `sy_res_btn` VALUES ('402880e85cdba52d015cdba5a3070011', 'btnDisable', null, '禁用', '2c9cd0815c59db1e015c59df8a350000');
INSERT INTO `sy_res_btn` VALUES ('402880e85cdba52d015cdba5a32e0018', 'btnAdd', null, '新增', '4028388148acb42f0148acccd58d0001');
INSERT INTO `sy_res_btn` VALUES ('402880e85cdba52d015cdba5a32f0019', 'btnRemove', null, '删除', '4028388148acb42f0148acccd58d0001');
INSERT INTO `sy_res_btn` VALUES ('402880e85cdba52d015cdba5a32f001a', 'btnEdit', null, '修改', '4028388148acb42f0148acccd58d0001');
INSERT INTO `sy_res_btn` VALUES ('402880e85cdba52d015cdba5a32f001b', 'btnView', null, '查看', '4028388148acb42f0148acccd58d0001');
INSERT INTO `sy_res_btn` VALUES ('402880e85cdba52d015cdba5a32f001c', 'btnEnable', null, '启用', '4028388148acb42f0148acccd58d0001');
INSERT INTO `sy_res_btn` VALUES ('402880e85cdba52d015cdba5a32f001d', 'btnDisable', null, '禁用', '4028388148acb42f0148acccd58d0001');
INSERT INTO `sy_res_btn` VALUES ('402880e85cdba52d015cdba5a3570024', 'btnAdd', null, '新增', '4028388148b2fe810148b32ef7120030');
INSERT INTO `sy_res_btn` VALUES ('402880e85cdba52d015cdba5a3570025', 'btnRemove', null, '删除', '4028388148b2fe810148b32ef7120030');
INSERT INTO `sy_res_btn` VALUES ('402880e85cdba52d015cdba5a3570026', 'btnEdit', null, '修改', '4028388148b2fe810148b32ef7120030');
INSERT INTO `sy_res_btn` VALUES ('402880e85cdba52d015cdba5a3570027', 'btnView', null, '查看', '4028388148b2fe810148b32ef7120030');
INSERT INTO `sy_res_btn` VALUES ('402880e85cdba52d015cdba5a3570028', 'btnEnable', null, '启用', '4028388148b2fe810148b32ef7120030');
INSERT INTO `sy_res_btn` VALUES ('402880e85cdba52d015cdba5a3570029', 'btnDisable', null, '禁用', '4028388148b2fe810148b32ef7120030');
INSERT INTO `sy_res_btn` VALUES ('402880e85cdba52d015cdba5a3ac003c', 'btnAdd', null, '新增', '402881ee547ea15801547eb07a2f0002');
INSERT INTO `sy_res_btn` VALUES ('402880e85cdba52d015cdba5a3ac003d', 'btnRemove', null, '删除', '402881ee547ea15801547eb07a2f0002');
INSERT INTO `sy_res_btn` VALUES ('402880e85cdba52d015cdba5a3ac003e', 'btnEdit', null, '修改', '402881ee547ea15801547eb07a2f0002');
INSERT INTO `sy_res_btn` VALUES ('402880e85cdba52d015cdba5a3ac003f', 'btnView', null, '查看', '402881ee547ea15801547eb07a2f0002');
INSERT INTO `sy_res_btn` VALUES ('402880e85cdba52d015cdba5a3ac0040', 'btnEnable', null, '启用', '402881ee547ea15801547eb07a2f0002');
INSERT INTO `sy_res_btn` VALUES ('402880e85cdba52d015cdba5a3ac0041', 'btnDisable', null, '禁用', '402881ee547ea15801547eb07a2f0002');
INSERT INTO `sy_res_btn` VALUES ('402880e85cdba52d015cdba5a3bd0042', 'btnAdd', null, '新增', '402881ee547ea15801547eb17d800004');
INSERT INTO `sy_res_btn` VALUES ('402880e85cdba52d015cdba5a3bd0043', 'btnRemove', null, '删除', '402881ee547ea15801547eb17d800004');
INSERT INTO `sy_res_btn` VALUES ('402880e85cdba52d015cdba5a3bd0044', 'btnEdit', null, '修改', '402881ee547ea15801547eb17d800004');
INSERT INTO `sy_res_btn` VALUES ('402880e85cdba52d015cdba5a3bd0045', 'btnView', null, '查看', '402881ee547ea15801547eb17d800004');
INSERT INTO `sy_res_btn` VALUES ('402880e85cdba52d015cdba5a3bd0046', 'btnEnable', null, '启用', '402881ee547ea15801547eb17d800004');
INSERT INTO `sy_res_btn` VALUES ('402880e85cdba52d015cdba5a3bd0047', 'btnDisable', null, '禁用', '402881ee547ea15801547eb17d800004');
INSERT INTO `sy_res_btn` VALUES ('402880e85cdba52d015cdba5a40c0060', 'btnAdd', null, '新增', '40289fe85cb671b8015cb6760f270001');
INSERT INTO `sy_res_btn` VALUES ('402880e85cdba52d015cdba5a40c0061', 'btnRemove', null, '删除', '40289fe85cb671b8015cb6760f270001');
INSERT INTO `sy_res_btn` VALUES ('402880e85cdba52d015cdba5a40c0062', 'btnEdit', null, '修改', '40289fe85cb671b8015cb6760f270001');
INSERT INTO `sy_res_btn` VALUES ('402880e85cdba52d015cdba5a40c0063', 'btnView', null, '查看', '40289fe85cb671b8015cb6760f270001');
INSERT INTO `sy_res_btn` VALUES ('402880e85cdba52d015cdba5a40c0064', 'btnEnable', null, '启用', '40289fe85cb671b8015cb6760f270001');
INSERT INTO `sy_res_btn` VALUES ('402880e85cdba52d015cdba5a40c0065', 'btnDisable', null, '禁用', '40289fe85cb671b8015cb6760f270001');
INSERT INTO `sy_res_btn` VALUES ('4529358d-cafc-4568-b9d2-52db9b67b01a', 'btnRemove', '2', '??', '40289fe85ccafd24015ccb02470c0000');
INSERT INTO `sy_res_btn` VALUES ('464d4ff2-92ea-4fdd-9aa5-c3e65c701c37', 'btnAdd', '1', '新增', '48630f3b-31cc-4f67-95ec-a439e76b6154');
INSERT INTO `sy_res_btn` VALUES ('4fd8369f-c85f-43d7-b10c-52101b8fa36a', 'btnView', '4', '??', '40289fe85ccafd24015ccb02470c0000');
INSERT INTO `sy_res_btn` VALUES ('50267ec1-3616-4368-93bf-90b5bb32c73e', 'btnAdd', '1', '新增', 'dcdaf2e2-bd74-4da8-b04e-2062b41da4f4');
INSERT INTO `sy_res_btn` VALUES ('521cc190-67a5-4e24-bb2d-160ecc52841a', 'btnEnable', '5', '启用', 'd19392cb-58fe-4ff0-b487-fb5b895dcc8f');
INSERT INTO `sy_res_btn` VALUES ('62463a7d-e332-4943-bc60-6b35af7ce802', 'btnView', '4', '查看', '7d14c59c-135f-494c-bc1e-0dcb2304661d');
INSERT INTO `sy_res_btn` VALUES ('62bee9de-a1a7-4495-979a-df0b03c8a680', 'btnView', '4', '查看', '27087df9-9eb3-408a-b19b-81a6588d502e');
INSERT INTO `sy_res_btn` VALUES ('6eef7e29-cfe1-4d0e-a8e2-28ad6f76cb40', 'btnEdit', '3', '修改', '27087df9-9eb3-408a-b19b-81a6588d502e');
INSERT INTO `sy_res_btn` VALUES ('711ce85a-5450-4c55-832c-67b9bdb8479d', 'btnView', '4', '查看', 'dcdaf2e2-bd74-4da8-b04e-2062b41da4f4');
INSERT INTO `sy_res_btn` VALUES ('71a5625a-c8b3-4787-ba3a-7f150b6d637d', 'btnDisable', '6', '??', '40289fe85ccafd24015ccb02470c0000');
INSERT INTO `sy_res_btn` VALUES ('747f384e-deaa-4016-b64b-1706e540550d', 'btnExport', '10', '导出', 'b6dd9eea-b6b1-437d-a10a-ae2078521871');
INSERT INTO `sy_res_btn` VALUES ('85490a1a-efb9-4982-a340-1117dcc8e334', 'btnEdit', '3', '修改', 'd19392cb-58fe-4ff0-b487-fb5b895dcc8f');
INSERT INTO `sy_res_btn` VALUES ('89136e58-c613-4846-b2b7-26088b8b67c3', 'btnCacheable', '7', '缓存开关', 'b6dd9eea-b6b1-437d-a10a-ae2078521871');
INSERT INTO `sy_res_btn` VALUES ('8bd95b2f-5764-425c-a180-e00c18cbfac1', 'btnEnable', '5', '发布/下线', 'b6dd9eea-b6b1-437d-a10a-ae2078521871');
INSERT INTO `sy_res_btn` VALUES ('9bb44bc9-a3de-4a7b-baab-3be96b535533', 'btnEnable', '5', '??', '40289fe85ccafd24015ccb02470c0000');
INSERT INTO `sy_res_btn` VALUES ('a1fe6fcd-1996-4f8e-8da0-b4002f460f15', 'btnRemove', '2', '删除', '7d14c59c-135f-494c-bc1e-0dcb2304661d');
INSERT INTO `sy_res_btn` VALUES ('a3a1b0fb-c194-4159-82e2-827442279f76', 'btnAuthorized', '6', '认证开关', 'b6dd9eea-b6b1-437d-a10a-ae2078521871');
INSERT INTO `sy_res_btn` VALUES ('ae6db18f-8735-4acf-8264-e75bc0f82963', 'btnEdit', '3', '修改', 'dcdaf2e2-bd74-4da8-b04e-2062b41da4f4');
INSERT INTO `sy_res_btn` VALUES ('af6b5c14-5518-4935-9cbf-cc2c6a84d7dc', 'btnView', '4', '查看', 'b6dd9eea-b6b1-437d-a10a-ae2078521871');
INSERT INTO `sy_res_btn` VALUES ('b19bbb27-8170-4795-819c-54645898ffad', 'btnBindApi', '5', '绑定API', '7d14c59c-135f-494c-bc1e-0dcb2304661d');
INSERT INTO `sy_res_btn` VALUES ('b7184071-8062-4b1f-8797-ddb499003b41', 'btnImport', '11', '导入', 'b6dd9eea-b6b1-437d-a10a-ae2078521871');
INSERT INTO `sy_res_btn` VALUES ('b80898db-6469-4722-b51c-080c4ee16591', 'btnAdd', '1', '新增', '27087df9-9eb3-408a-b19b-81a6588d502e');
INSERT INTO `sy_res_btn` VALUES ('b9872479-95ec-4306-bc43-6e48b8243266', 'btnAdd', '1', '新增', 'd19392cb-58fe-4ff0-b487-fb5b895dcc8f');
INSERT INTO `sy_res_btn` VALUES ('ba7e74a9-4f11-4f6d-a5e4-56b2eb08eb34', 'btnEdit', '3', '??', '40289fe85ccafd24015ccb02470c0000');
INSERT INTO `sy_res_btn` VALUES ('bd9433d5-954d-4b54-9dbd-069df1b443d1', 'btnAdd', '1', '新增', '7d14c59c-135f-494c-bc1e-0dcb2304661d');
INSERT INTO `sy_res_btn` VALUES ('c56b8a35-efeb-4a0f-8ac0-b60f3b14bd16', 'btnRemove', '2', '删除', 'dcdaf2e2-bd74-4da8-b04e-2062b41da4f4');
INSERT INTO `sy_res_btn` VALUES ('cafc6d4c-2e46-49da-9243-f242943623ec', 'btnAdd', '1', '??', '40289fe85ccafd24015ccb02470c0000');
INSERT INTO `sy_res_btn` VALUES ('ce2c5659-b514-4269-b29e-e4243d5e6014', 'btnMock', '8', 'Mock工具', 'b6dd9eea-b6b1-437d-a10a-ae2078521871');
INSERT INTO `sy_res_btn` VALUES ('d1892c88-1fec-4f35-b10b-50d88d9e39b4', 'btnDebug', '9', '调试工具', 'b6dd9eea-b6b1-437d-a10a-ae2078521871');
INSERT INTO `sy_res_btn` VALUES ('d7fa27e0-470b-40a9-acf9-16aa45e1166f', 'btnRemove', '2', '删除', 'd19392cb-58fe-4ff0-b487-fb5b895dcc8f');
INSERT INTO `sy_res_btn` VALUES ('e25b10b0-e986-4c9c-b610-38ab2d8e738b', 'btnEdit', '3', '修改', '7d14c59c-135f-494c-bc1e-0dcb2304661d');
INSERT INTO `sy_res_btn` VALUES ('e3a2f74c-b224-4245-8520-68510111b980', 'btnEdit', '3', '修改', '48630f3b-31cc-4f67-95ec-a439e76b6154');
INSERT INTO `sy_res_btn` VALUES ('e647b480-5800-4b37-afa7-e5ecea273c43', 'btnRemove', '2', '删除', '27087df9-9eb3-408a-b19b-81a6588d502e');
INSERT INTO `sy_res_btn` VALUES ('e9dd6215-fb21-4f97-a36a-43c0f3f7ef88', 'btnBindUser', '6', '绑定用户', '7d14c59c-135f-494c-bc1e-0dcb2304661d');
INSERT INTO `sy_res_btn` VALUES ('ea42ab22-420b-4dd1-adbd-daa2e3ef618a', 'btnEdit', '3', '修改', 'b6dd9eea-b6b1-437d-a10a-ae2078521871');
INSERT INTO `sy_res_btn` VALUES ('edf3d31e-26a7-428f-a75c-e3a89468b157', 'btnAdd', '1', '新增', 'b6dd9eea-b6b1-437d-a10a-ae2078521871');
INSERT INTO `sy_res_btn` VALUES ('f1701eb5-7763-4c99-a57b-450b20e81e20', 'btnDisable', '6', '禁用', 'd19392cb-58fe-4ff0-b487-fb5b895dcc8f');
INSERT INTO `sy_res_btn` VALUES ('fcbe62e4-a953-4306-8d08-54f272560947', 'btnView', '4', '查看', 'd19392cb-58fe-4ff0-b487-fb5b895dcc8f');

-- ----------------------------
-- Table structure for sy_res_column
-- ----------------------------
DROP TABLE IF EXISTS `sy_res_column`;
CREATE TABLE `sy_res_column` (
  `id` varchar(255) NOT NULL,
  `code` varchar(255) NOT NULL,
  `location` int(11) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `res_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKos8xi73uw09xa8y717yt7mrte` (`res_id`),
  CONSTRAINT `FKos8xi73uw09xa8y717yt7mrte` FOREIGN KEY (`res_id`) REFERENCES `sy_res` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sy_res_column
-- ----------------------------

-- ----------------------------
-- Table structure for sy_role
-- ----------------------------
DROP TABLE IF EXISTS `sy_role`;
CREATE TABLE `sy_role` (
  `id` varchar(255) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `descript` varchar(255) DEFAULT NULL,
  `enabled` bit(1) DEFAULT b'1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sy_role
-- ----------------------------
INSERT INTO `sy_role` VALUES ('7a974da4-b888-4ef5-9169-54e724fe9138', 'ADMIN', '超级管理员', '');
INSERT INTO `sy_role` VALUES ('d088859f-0190-42bc-a85a-7a9463d74a92', 'ACTUATOR', '监控', '');
INSERT INTO `sy_role` VALUES ('e12ac5b6-303d-4b50-8999-cb3ed3089579', 'USER', '登录用户', '');

-- ----------------------------
-- Table structure for sy_role_res
-- ----------------------------
DROP TABLE IF EXISTS `sy_role_res`;
CREATE TABLE `sy_role_res` (
  `role_id` varchar(255) NOT NULL,
  `res_id` varchar(255) NOT NULL,
  KEY `FKblg0ak50kinkqauyuqgcxv8fd` (`res_id`),
  KEY `FKm6n0hnuqrjv4wwa7mc2unyenk` (`role_id`),
  CONSTRAINT `FKblg0ak50kinkqauyuqgcxv8fd` FOREIGN KEY (`res_id`) REFERENCES `sy_res` (`id`),
  CONSTRAINT `FKm6n0hnuqrjv4wwa7mc2unyenk` FOREIGN KEY (`role_id`) REFERENCES `sy_role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sy_role_res
-- ----------------------------
INSERT INTO `sy_role_res` VALUES ('e12ac5b6-303d-4b50-8999-cb3ed3089579', '40283881494264460149428484610000');
INSERT INTO `sy_role_res` VALUES ('e12ac5b6-303d-4b50-8999-cb3ed3089579', '677e5457-021b-4344-a34d-9c7273105821');
INSERT INTO `sy_role_res` VALUES ('e12ac5b6-303d-4b50-8999-cb3ed3089579', '7d14c59c-135f-494c-bc1e-0dcb2304661d');
INSERT INTO `sy_role_res` VALUES ('e12ac5b6-303d-4b50-8999-cb3ed3089579', 'b6dd9eea-b6b1-437d-a10a-ae2078521871');

-- ----------------------------
-- Table structure for sy_role_res_btn
-- ----------------------------
DROP TABLE IF EXISTS `sy_role_res_btn`;
CREATE TABLE `sy_role_res_btn` (
  `id` varchar(255) NOT NULL,
  `res_id` varchar(255) DEFAULT NULL,
  `role_id` varchar(255) DEFAULT NULL,
  `btn_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKil57h9c7y6br0a2q8uwqrnp7u` (`btn_id`),
  CONSTRAINT `FKil57h9c7y6br0a2q8uwqrnp7u` FOREIGN KEY (`btn_id`) REFERENCES `sy_res_btn` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sy_role_res_btn
-- ----------------------------
INSERT INTO `sy_role_res_btn` VALUES ('078d10c1-73aa-4844-8234-becc345ec013', 'b6dd9eea-b6b1-437d-a10a-ae2078521871', 'e12ac5b6-303d-4b50-8999-cb3ed3089579', '3f85c254-ef7a-4712-8279-131a85a8d70c');
INSERT INTO `sy_role_res_btn` VALUES ('18714e19-cb94-4218-8a67-084b998e543a', 'b6dd9eea-b6b1-437d-a10a-ae2078521871', 'e12ac5b6-303d-4b50-8999-cb3ed3089579', 'af6b5c14-5518-4935-9cbf-cc2c6a84d7dc');
INSERT INTO `sy_role_res_btn` VALUES ('1e736eaf-e91e-44d3-bd01-000be67f42d2', '7d14c59c-135f-494c-bc1e-0dcb2304661d', 'e12ac5b6-303d-4b50-8999-cb3ed3089579', '62463a7d-e332-4943-bc60-6b35af7ce802');
INSERT INTO `sy_role_res_btn` VALUES ('3f2004d6-a381-4779-9648-e9c6a9a2acba', 'b6dd9eea-b6b1-437d-a10a-ae2078521871', 'e12ac5b6-303d-4b50-8999-cb3ed3089579', 'ea42ab22-420b-4dd1-adbd-daa2e3ef618a');
INSERT INTO `sy_role_res_btn` VALUES ('488ce06b-f497-41b1-9a6b-f85c5065c916', 'b6dd9eea-b6b1-437d-a10a-ae2078521871', 'e12ac5b6-303d-4b50-8999-cb3ed3089579', '8bd95b2f-5764-425c-a180-e00c18cbfac1');
INSERT INTO `sy_role_res_btn` VALUES ('4bccc135-4606-45a8-b1a4-75af3c867214', 'b6dd9eea-b6b1-437d-a10a-ae2078521871', 'e12ac5b6-303d-4b50-8999-cb3ed3089579', 'a3a1b0fb-c194-4159-82e2-827442279f76');
INSERT INTO `sy_role_res_btn` VALUES ('6b817b6c-5018-48f1-98da-064560d11f1d', '7d14c59c-135f-494c-bc1e-0dcb2304661d', 'e12ac5b6-303d-4b50-8999-cb3ed3089579', 'b19bbb27-8170-4795-819c-54645898ffad');
INSERT INTO `sy_role_res_btn` VALUES ('6be276a2-8254-428b-9a43-6141a612ccc9', '7d14c59c-135f-494c-bc1e-0dcb2304661d', 'e12ac5b6-303d-4b50-8999-cb3ed3089579', 'e25b10b0-e986-4c9c-b610-38ab2d8e738b');
INSERT INTO `sy_role_res_btn` VALUES ('82ded296-bf8f-448d-b9ab-bb7e4525c283', 'b6dd9eea-b6b1-437d-a10a-ae2078521871', 'e12ac5b6-303d-4b50-8999-cb3ed3089579', 'ce2c5659-b514-4269-b29e-e4243d5e6014');
INSERT INTO `sy_role_res_btn` VALUES ('89eaca56-3713-44f0-9f3d-68c6855ac9a2', 'b6dd9eea-b6b1-437d-a10a-ae2078521871', 'e12ac5b6-303d-4b50-8999-cb3ed3089579', 'edf3d31e-26a7-428f-a75c-e3a89468b157');
INSERT INTO `sy_role_res_btn` VALUES ('8fa17fcf-0bc5-43b5-b069-c675963b24b0', 'b6dd9eea-b6b1-437d-a10a-ae2078521871', 'e12ac5b6-303d-4b50-8999-cb3ed3089579', '89136e58-c613-4846-b2b7-26088b8b67c3');
INSERT INTO `sy_role_res_btn` VALUES ('91033290-ab55-4a56-940a-391bb9803500', 'b6dd9eea-b6b1-437d-a10a-ae2078521871', 'e12ac5b6-303d-4b50-8999-cb3ed3089579', '747f384e-deaa-4016-b64b-1706e540550d');
INSERT INTO `sy_role_res_btn` VALUES ('afb9ec85-0070-4e27-8ac7-965069b32e4f', 'b6dd9eea-b6b1-437d-a10a-ae2078521871', 'e12ac5b6-303d-4b50-8999-cb3ed3089579', 'b7184071-8062-4b1f-8797-ddb499003b41');
INSERT INTO `sy_role_res_btn` VALUES ('df1257a3-49a9-4df1-b168-1af49f8b540c', 'b6dd9eea-b6b1-437d-a10a-ae2078521871', 'e12ac5b6-303d-4b50-8999-cb3ed3089579', 'd1892c88-1fec-4f35-b10b-50d88d9e39b4');

-- ----------------------------
-- Table structure for sy_role_res_column
-- ----------------------------
DROP TABLE IF EXISTS `sy_role_res_column`;
CREATE TABLE `sy_role_res_column` (
  `id` varchar(255) NOT NULL,
  `res_id` varchar(255) DEFAULT NULL,
  `role_id` varchar(255) DEFAULT NULL,
  `column_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKq6fxl0a75tdqc378rvls59v32` (`column_id`),
  CONSTRAINT `FKq6fxl0a75tdqc378rvls59v32` FOREIGN KEY (`column_id`) REFERENCES `sy_res_column` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sy_role_res_column
-- ----------------------------

-- ----------------------------
-- Table structure for sy_user
-- ----------------------------
DROP TABLE IF EXISTS `sy_user`;
CREATE TABLE `sy_user` (
  `id` varchar(255) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(60) NOT NULL,
  `real_name` varchar(255) DEFAULT NULL,
  `layout` varchar(255) DEFAULT NULL,
  `page_style` varchar(255) DEFAULT NULL,
  `avatar` varchar(255) DEFAULT NULL,
  `enabled` bit(1) NOT NULL DEFAULT b'1',
  `created_by` varchar(50) NOT NULL,
  `created_date` datetime NOT NULL,
  `last_modified_by` varchar(50) DEFAULT NULL,
  `last_modified_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_romgwi829iiqo1u6vfjwrisna` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sy_user
-- ----------------------------
INSERT INTO `sy_user` VALUES ('34cbfb2d-5a66-4a66-846d-655d87cd5b96', 'user', 'e10adc3949ba59abbe56e057f20f883e', '普通用户', null, 'palette.3', null, '', 'admin', '2018-05-17 07:16:25', 'admin', '2018-05-17 07:16:25');
INSERT INTO `sy_user` VALUES ('7e15c12f-f4b3-4399-a14a-32514056d07b', 'admin', 'e10adc3949ba59abbe56e057f20f883e', '超级管理员', '', 'palette.3', null, '', 'admin', '2018-05-17 07:16:25', 'admin', '2018-06-11 01:48:43');
INSERT INTO `sy_user` VALUES ('90e37430-0284-4eed-8211-46c3c0d1ba5f', 'guest', 'e10adc3949ba59abbe56e057f20f883e', '访客', null, 'palette', null, '', 'admin', '2018-05-17 07:16:25', 'admin', '2018-05-17 07:16:25');

-- ----------------------------
-- Table structure for sy_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sy_user_role`;
CREATE TABLE `sy_user_role` (
  `id` varchar(255) NOT NULL,
  `role_id` varchar(255) NOT NULL,
  `user_id` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK1jn34lfyu8y7da7oxv2pchp6` (`role_id`),
  KEY `FKr6afdk6ou1r46vbw18ox8yaw3` (`user_id`),
  CONSTRAINT `FK1jn34lfyu8y7da7oxv2pchp6` FOREIGN KEY (`role_id`) REFERENCES `sy_role` (`id`),
  CONSTRAINT `FKr6afdk6ou1r46vbw18ox8yaw3` FOREIGN KEY (`user_id`) REFERENCES `sy_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sy_user_role
-- ----------------------------
INSERT INTO `sy_user_role` VALUES ('57397f00-8e0c-4dc3-bfc7-c91da708cf8c', 'e12ac5b6-303d-4b50-8999-cb3ed3089579', '34cbfb2d-5a66-4a66-846d-655d87cd5b96');
INSERT INTO `sy_user_role` VALUES ('621cc5ab-20dd-43e6-9361-339eca08a58b', 'd088859f-0190-42bc-a85a-7a9463d74a92', '7e15c12f-f4b3-4399-a14a-32514056d07b');
INSERT INTO `sy_user_role` VALUES ('930a6c04-8f12-4911-9088-4d0d0d98da70', '7a974da4-b888-4ef5-9169-54e724fe9138', '7e15c12f-f4b3-4399-a14a-32514056d07b');
INSERT INTO `sy_user_role` VALUES ('fcea5cea-e715-4f81-b9bb-f437fa54b861', 'e12ac5b6-303d-4b50-8999-cb3ed3089579', '90e37430-0284-4eed-8211-46c3c0d1ba5f');
