/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50640
Source Host           : localhost:3306
Source Database       : feing-uaa

Target Server Type    : MYSQL
Target Server Version : 50640
File Encoding         : 65001

Date: 2018-06-09 17:31:49
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for oauth_access_token
-- ----------------------------
DROP TABLE IF EXISTS `oauth_access_token`;
CREATE TABLE `oauth_access_token` (
  `id` varchar(255) NOT NULL,
  `serialized_authentication` longblob NOT NULL,
  `authentication_id` varchar(32) NOT NULL,
  `client_id` varchar(200) DEFAULT NULL,
  `serialized_token` varchar(255) NOT NULL,
  `token_id` varchar(255) NOT NULL,
  `user_name` varchar(50) DEFAULT NULL,
  `refresh_token_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_s2kltbu5u5i3wo8kb5uonm8ho` (`token_id`),
  KEY `FKmw0i62mn8ga1idknbptvpg71e` (`refresh_token_id`),
  CONSTRAINT `FKmw0i62mn8ga1idknbptvpg71e` FOREIGN KEY (`refresh_token_id`) REFERENCES `oauth_refresh_token` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of oauth_access_token
-- ----------------------------

-- ----------------------------
-- Table structure for oauth_authority
-- ----------------------------
DROP TABLE IF EXISTS `oauth_authority`;
CREATE TABLE `oauth_authority` (
  `id` varchar(255) NOT NULL,
  `enabled` bit(1) NOT NULL DEFAULT b'1',
  `value` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_20hq3j5vh9kyqcxkdh1al6smg` (`value`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of oauth_authority
-- ----------------------------
INSERT INTO `oauth_authority` VALUES ('1', '', 'CLIENT');
INSERT INTO `oauth_authority` VALUES ('2', '', 'API');

-- ----------------------------
-- Table structure for oauth_client_detail
-- ----------------------------
DROP TABLE IF EXISTS `oauth_client_detail`;
CREATE TABLE `oauth_client_detail` (
  `id` varchar(255) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `client_id` varchar(255) NOT NULL,
  `client_secret` varchar(255) NOT NULL,
  `username` varchar(255) DEFAULT NULL,
  `access_token_validity_seconds` int(11) DEFAULT NULL,
  `refresh_token_validity_seconds` int(11) DEFAULT NULL,
  `created_by` varchar(50) NOT NULL,
  `created_date` datetime NOT NULL,
  `last_modified_by` varchar(50) DEFAULT NULL,
  `last_modified_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_drvkw14dr24anwe8f1bugyal3` (`client_id`),
  UNIQUE KEY `UK_lenhlmgfqp27tytfvkrtlt9eh` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of oauth_client_detail
-- ----------------------------
INSERT INTO `oauth_client_detail` VALUES ('b6b796d8-3b0e-4fb6-b492-2669ad7d87f7', 'gateway', 'gateway', '4f0ceaa505f350a92c651243af467d9f', null, '86400', '2592000', 'admin', '2017-08-14 15:32:35', 'admin', '2018-06-05 05:45:44');
INSERT INTO `oauth_client_detail` VALUES ('f079f8d2-e45f-4a49-b6cb-9d77d083eb4c', 'producer密钥', 'producer', '8b04045d868e5a10043c228299d1e5e0', 'user', '3600', '86400', 'admin', '2017-08-14 23:07:10', 'admin', '2017-10-25 02:45:46');

-- ----------------------------
-- Table structure for oauth_client_detail_api
-- ----------------------------
DROP TABLE IF EXISTS `oauth_client_detail_api`;
CREATE TABLE `oauth_client_detail_api` (
  `id` varchar(255) NOT NULL,
  `api_id` varchar(255) NOT NULL,
  `stage` varchar(16) NOT NULL,
  `client_detail_id` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK8ybc10owcv2a0axttpcgys54o` (`client_detail_id`),
  CONSTRAINT `FK8ybc10owcv2a0axttpcgys54o` FOREIGN KEY (`client_detail_id`) REFERENCES `oauth_client_detail` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of oauth_client_detail_api
-- ----------------------------

-- ----------------------------
-- Table structure for oauth_client_detail_authority
-- ----------------------------
DROP TABLE IF EXISTS `oauth_client_detail_authority`;
CREATE TABLE `oauth_client_detail_authority` (
  `id` varchar(255) NOT NULL,
  `authority_id` varchar(255) NOT NULL,
  `client_detail_id` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK9il6w8e5akd89kbydjf4fpjsm` (`authority_id`),
  KEY `FKg6is0e4vy843e7sxqbprttba2` (`client_detail_id`),
  CONSTRAINT `FK9il6w8e5akd89kbydjf4fpjsm` FOREIGN KEY (`authority_id`) REFERENCES `oauth_authority` (`id`),
  CONSTRAINT `FKg6is0e4vy843e7sxqbprttba2` FOREIGN KEY (`client_detail_id`) REFERENCES `oauth_client_detail` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of oauth_client_detail_authority
-- ----------------------------
INSERT INTO `oauth_client_detail_authority` VALUES ('12bf020c-889c-414f-b8f4-095feee4d409', '2', 'f079f8d2-e45f-4a49-b6cb-9d77d083eb4c');
INSERT INTO `oauth_client_detail_authority` VALUES ('c2de9b6b-35d0-4d7d-a73e-70e075e63cf9', '1', 'f079f8d2-e45f-4a49-b6cb-9d77d083eb4c');
INSERT INTO `oauth_client_detail_authority` VALUES ('c74ea064-0f8b-4e5e-a8f8-cb2c2b9d5b48', '1', 'b6b796d8-3b0e-4fb6-b492-2669ad7d87f7');
INSERT INTO `oauth_client_detail_authority` VALUES ('f7e28fca-4cda-4280-8c6a-cce9fc7fe4b1', '2', 'b6b796d8-3b0e-4fb6-b492-2669ad7d87f7');

-- ----------------------------
-- Table structure for oauth_client_detail_grant_type
-- ----------------------------
DROP TABLE IF EXISTS `oauth_client_detail_grant_type`;
CREATE TABLE `oauth_client_detail_grant_type` (
  `id` varchar(255) NOT NULL,
  `client_detail_id` varchar(255) NOT NULL,
  `grant_type_id` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK2l3x8f09uyufypldfx8cibgi5` (`client_detail_id`),
  KEY `FKm88mu81st4hcile0t0m8pnuns` (`grant_type_id`),
  CONSTRAINT `FK2l3x8f09uyufypldfx8cibgi5` FOREIGN KEY (`client_detail_id`) REFERENCES `oauth_client_detail` (`id`),
  CONSTRAINT `FKm88mu81st4hcile0t0m8pnuns` FOREIGN KEY (`grant_type_id`) REFERENCES `oauth_grant_type` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of oauth_client_detail_grant_type
-- ----------------------------
INSERT INTO `oauth_client_detail_grant_type` VALUES ('0232b10b-8952-40e8-8fc6-8b07103693d2', 'b6b796d8-3b0e-4fb6-b492-2669ad7d87f7', 'b588d4a5-4aa2-414d-bf3e-b8465df50cb7');
INSERT INTO `oauth_client_detail_grant_type` VALUES ('0449ef98-16fc-4d7a-85b1-3c9a006e6653', 'b6b796d8-3b0e-4fb6-b492-2669ad7d87f7', 'ef9008eb-5ce2-473c-9869-7e43dcb65df2');
INSERT INTO `oauth_client_detail_grant_type` VALUES ('1a2fce7f-3644-4a49-813a-c5ea025f3418', 'f079f8d2-e45f-4a49-b6cb-9d77d083eb4c', 'ef9008eb-5ce2-473c-9869-7e43dcb65df2');
INSERT INTO `oauth_client_detail_grant_type` VALUES ('3c71f7a7-9085-4622-85a2-e8686df276b1', 'b6b796d8-3b0e-4fb6-b492-2669ad7d87f7', '6c9c9302-fe97-4ea0-be47-6b71aa0f9972');
INSERT INTO `oauth_client_detail_grant_type` VALUES ('671cec71-a59e-4726-95da-bd34c021b6f6', 'b6b796d8-3b0e-4fb6-b492-2669ad7d87f7', 'f29de777-603b-4078-ae91-7ac1cb3a58fb');
INSERT INTO `oauth_client_detail_grant_type` VALUES ('9df1b7a8-5bfb-4ccf-8216-cc8ff5fdf728', 'f079f8d2-e45f-4a49-b6cb-9d77d083eb4c', 'f29de777-603b-4078-ae91-7ac1cb3a58fb');
INSERT INTO `oauth_client_detail_grant_type` VALUES ('e41c5b0f-7b4f-4c09-9078-fd385d7fe205', 'f079f8d2-e45f-4a49-b6cb-9d77d083eb4c', 'b588d4a5-4aa2-414d-bf3e-b8465df50cb7');
INSERT INTO `oauth_client_detail_grant_type` VALUES ('f3fd2370-b829-4011-8869-0656c00958a3', 'f079f8d2-e45f-4a49-b6cb-9d77d083eb4c', '6c9c9302-fe97-4ea0-be47-6b71aa0f9972');

-- ----------------------------
-- Table structure for oauth_client_detail_limit
-- ----------------------------
DROP TABLE IF EXISTS `oauth_client_detail_limit`;
CREATE TABLE `oauth_client_detail_limit` (
  `id` varchar(255) NOT NULL,
  `frequency` bigint(20) DEFAULT NULL,
  `interval_unit` varchar(16) DEFAULT 'MINUTES',
  `limits` bigint(20) DEFAULT NULL,
  `client_detail_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKnpqi37nyh32llrrf90mpy9eml` (`client_detail_id`),
  CONSTRAINT `FKnpqi37nyh32llrrf90mpy9eml` FOREIGN KEY (`client_detail_id`) REFERENCES `oauth_client_detail` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of oauth_client_detail_limit
-- ----------------------------
INSERT INTO `oauth_client_detail_limit` VALUES ('1daeee1f-e32c-4a1b-ad33-3ea28c5d4934', '1', 'DAYS', '10000', 'f079f8d2-e45f-4a49-b6cb-9d77d083eb4c');
INSERT INTO `oauth_client_detail_limit` VALUES ('563e44ec-88ec-41b0-8b01-6f01c3e545af', '1', 'MINUTES', '100', 'b6b796d8-3b0e-4fb6-b492-2669ad7d87f7');

-- ----------------------------
-- Table structure for oauth_client_detail_resource
-- ----------------------------
DROP TABLE IF EXISTS `oauth_client_detail_resource`;
CREATE TABLE `oauth_client_detail_resource` (
  `id` varchar(255) NOT NULL,
  `client_detail_id` varchar(255) NOT NULL,
  `resource_id` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK1rw7p2m4okhgncuc8go8xbpgo` (`client_detail_id`),
  KEY `FKclrw48qja5okhupdqb8p1b065` (`resource_id`),
  CONSTRAINT `FK1rw7p2m4okhgncuc8go8xbpgo` FOREIGN KEY (`client_detail_id`) REFERENCES `oauth_client_detail` (`id`),
  CONSTRAINT `FKclrw48qja5okhupdqb8p1b065` FOREIGN KEY (`resource_id`) REFERENCES `oauth_resource` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of oauth_client_detail_resource
-- ----------------------------

-- ----------------------------
-- Table structure for oauth_client_detail_scope
-- ----------------------------
DROP TABLE IF EXISTS `oauth_client_detail_scope`;
CREATE TABLE `oauth_client_detail_scope` (
  `id` varchar(255) NOT NULL,
  `auto_approve` bit(1) NOT NULL,
  `client_detail_id` varchar(255) NOT NULL,
  `scope_id` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKflw1gyq5p9dl0pfjyv1n8ajff` (`client_detail_id`),
  KEY `FK5tlnm1xr3xfcdl6wxuqodw40y` (`scope_id`),
  CONSTRAINT `FK5tlnm1xr3xfcdl6wxuqodw40y` FOREIGN KEY (`scope_id`) REFERENCES `oauth_scope` (`id`),
  CONSTRAINT `FKflw1gyq5p9dl0pfjyv1n8ajff` FOREIGN KEY (`client_detail_id`) REFERENCES `oauth_client_detail` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of oauth_client_detail_scope
-- ----------------------------
INSERT INTO `oauth_client_detail_scope` VALUES ('04c08504-b688-4f15-a3cd-98fe006331de', '', 'f079f8d2-e45f-4a49-b6cb-9d77d083eb4c', '697c97ca-d6b8-4672-b0a0-c131b7eb901c');
INSERT INTO `oauth_client_detail_scope` VALUES ('4886c4a1-221d-4128-9fdd-98c3ab3bfb67', '', 'f079f8d2-e45f-4a49-b6cb-9d77d083eb4c', '3e383b6d-fe3c-46f6-ad74-b13a0b441e84');
INSERT INTO `oauth_client_detail_scope` VALUES ('5af9a43e-f433-426a-a302-bd822601c92e', '', 'f079f8d2-e45f-4a49-b6cb-9d77d083eb4c', '60df1fd8-32df-4670-89fe-74a8f9de387c');
INSERT INTO `oauth_client_detail_scope` VALUES ('6704aab0-2f18-4007-b51f-4b1456f35eb4', '', 'b6b796d8-3b0e-4fb6-b492-2669ad7d87f7', '697c97ca-d6b8-4672-b0a0-c131b7eb901c');
INSERT INTO `oauth_client_detail_scope` VALUES ('ba83cf66-3b27-43e0-847c-4a2c97d4a4cf', '', 'b6b796d8-3b0e-4fb6-b492-2669ad7d87f7', '60df1fd8-32df-4670-89fe-74a8f9de387c');
INSERT INTO `oauth_client_detail_scope` VALUES ('cbe1b789-55fa-4dcd-9fd0-c7d6ac7b7de8', '', 'b6b796d8-3b0e-4fb6-b492-2669ad7d87f7', '3e383b6d-fe3c-46f6-ad74-b13a0b441e84');

-- ----------------------------
-- Table structure for oauth_grant_type
-- ----------------------------
DROP TABLE IF EXISTS `oauth_grant_type`;
CREATE TABLE `oauth_grant_type` (
  `id` varchar(255) NOT NULL,
  `value` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of oauth_grant_type
-- ----------------------------
INSERT INTO `oauth_grant_type` VALUES ('6c9c9302-fe97-4ea0-be47-6b71aa0f9972', 'refresh_token');
INSERT INTO `oauth_grant_type` VALUES ('b588d4a5-4aa2-414d-bf3e-b8465df50cb7', 'password');
INSERT INTO `oauth_grant_type` VALUES ('ef9008eb-5ce2-473c-9869-7e43dcb65df2', 'authorization_code');
INSERT INTO `oauth_grant_type` VALUES ('f29de777-603b-4078-ae91-7ac1cb3a58fb', 'client_credentials');

-- ----------------------------
-- Table structure for oauth_redirect_uri
-- ----------------------------
DROP TABLE IF EXISTS `oauth_redirect_uri`;
CREATE TABLE `oauth_redirect_uri` (
  `id` varchar(255) NOT NULL,
  `value` varchar(255) NOT NULL,
  `client_detail_id` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK1e2sxay4sqnnjpl13m114cwkj` (`client_detail_id`),
  CONSTRAINT `FK1e2sxay4sqnnjpl13m114cwkj` FOREIGN KEY (`client_detail_id`) REFERENCES `oauth_client_detail` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of oauth_redirect_uri
-- ----------------------------

-- ----------------------------
-- Table structure for oauth_refresh_token
-- ----------------------------
DROP TABLE IF EXISTS `oauth_refresh_token`;
CREATE TABLE `oauth_refresh_token` (
  `id` varchar(255) NOT NULL,
  `serialized_authentication` longblob NOT NULL,
  `serialized_token` varchar(255) NOT NULL,
  `token_id` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_dyt1a9sjjp24o6ds2t7b5p3ae` (`token_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of oauth_refresh_token
-- ----------------------------

-- ----------------------------
-- Table structure for oauth_resource
-- ----------------------------
DROP TABLE IF EXISTS `oauth_resource`;
CREATE TABLE `oauth_resource` (
  `id` varchar(255) NOT NULL,
  `value` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of oauth_resource
-- ----------------------------

-- ----------------------------
-- Table structure for oauth_scope
-- ----------------------------
DROP TABLE IF EXISTS `oauth_scope`;
CREATE TABLE `oauth_scope` (
  `id` varchar(255) NOT NULL,
  `value` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of oauth_scope
-- ----------------------------
INSERT INTO `oauth_scope` VALUES ('3e383b6d-fe3c-46f6-ad74-b13a0b441e84', 'read');
INSERT INTO `oauth_scope` VALUES ('60df1fd8-32df-4670-89fe-74a8f9de387c', 'trust');
INSERT INTO `oauth_scope` VALUES ('697c97ca-d6b8-4672-b0a0-c131b7eb901c', 'write');
