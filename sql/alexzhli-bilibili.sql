/*
 Navicat Premium Data Transfer

 Source Server         : mac_loacl
 Source Server Type    : MySQL
 Source Server Version : 80021
 Source Host           : localhost:3306
 Source Schema         : alexzhli-bilibili

 Target Server Type    : MySQL
 Target Server Version : 80021
 File Encoding         : 65001

 Date: 13/11/2022 04:14:04
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_auth_element_operation
-- ----------------------------
DROP TABLE IF EXISTS `t_auth_element_operation`;
CREATE TABLE `t_auth_element_operation` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `elementName` varchar(255) DEFAULT NULL,
  `elementCode` varchar(50) DEFAULT NULL,
  `operationType` varchar(5) DEFAULT NULL,
  `createTime` datetime DEFAULT NULL,
  `updateTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of t_auth_element_operation
-- ----------------------------
BEGIN;
INSERT INTO `t_auth_element_operation` VALUES (1, '视频投稿按钮', 'postVideoButton', '0', NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for t_auth_menu
-- ----------------------------
DROP TABLE IF EXISTS `t_auth_menu`;
CREATE TABLE `t_auth_menu` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `code` varchar(50) DEFAULT NULL,
  `createTime` datetime DEFAULT NULL,
  `updateTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of t_auth_menu
-- ----------------------------
BEGIN;
INSERT INTO `t_auth_menu` VALUES (1, '购买邀请码', 'buyInviteCode', NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for t_auth_role
-- ----------------------------
DROP TABLE IF EXISTS `t_auth_role`;
CREATE TABLE `t_auth_role` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `createTIme` datetime DEFAULT NULL,
  `updateTime` datetime DEFAULT NULL,
  `code` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of t_auth_role
-- ----------------------------
BEGIN;
INSERT INTO `t_auth_role` VALUES (1, '等级0', NULL, NULL, 'Lv0');
INSERT INTO `t_auth_role` VALUES (2, '等级1', NULL, NULL, 'Lv1');
INSERT INTO `t_auth_role` VALUES (3, '等级2', NULL, NULL, 'Lv2');
COMMIT;

-- ----------------------------
-- Table structure for t_auth_role_element_operation
-- ----------------------------
DROP TABLE IF EXISTS `t_auth_role_element_operation`;
CREATE TABLE `t_auth_role_element_operation` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `roleId` bigint DEFAULT NULL,
  `elementOperationId` bigint DEFAULT NULL,
  `createTIme` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of t_auth_role_element_operation
-- ----------------------------
BEGIN;
INSERT INTO `t_auth_role_element_operation` VALUES (1, 2, 1, NULL);
COMMIT;

-- ----------------------------
-- Table structure for t_auth_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `t_auth_role_menu`;
CREATE TABLE `t_auth_role_menu` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `roleId` bigint DEFAULT NULL,
  `menuId` bigint DEFAULT NULL,
  `createTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of t_auth_role_menu
-- ----------------------------
BEGIN;
INSERT INTO `t_auth_role_menu` VALUES (1, 2, 1, NULL);
COMMIT;

-- ----------------------------
-- Table structure for t_collection_group
-- ----------------------------
DROP TABLE IF EXISTS `t_collection_group`;
CREATE TABLE `t_collection_group` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `userId` bigint NOT NULL,
  `name` varchar(255) NOT NULL COMMENT '分组名称',
  `type` varchar(5) NOT NULL COMMENT '分组类型0为系统默认，1位用户自定义',
  `createTime` datetime DEFAULT NULL,
  `updateTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of t_collection_group
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for t_danmu
-- ----------------------------
DROP TABLE IF EXISTS `t_danmu`;
CREATE TABLE `t_danmu` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `userId` bigint NOT NULL,
  `videoId` bigint NOT NULL,
  `content` text,
  `danmuTime` varchar(100) DEFAULT NULL COMMENT '弹幕弹出时间',
  `createTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of t_danmu
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for t_file
-- ----------------------------
DROP TABLE IF EXISTS `t_file`;
CREATE TABLE `t_file` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `url` varchar(500) DEFAULT NULL COMMENT '文件存储路径',
  `type` varchar(45) DEFAULT NULL COMMENT '文件类型',
  `md5` varchar(200) DEFAULT NULL COMMENT '文件的MD5',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of t_file
-- ----------------------------
BEGIN;
INSERT INTO `t_file` VALUES (1, 'M00/00/03/J2s2tGNhOymEGyazAAAAALxysJ8157.png', 'png', 'fd8522e97e960b2df78a094f823ce98e', '2022-11-01 23:28:42');
COMMIT;

-- ----------------------------
-- Table structure for t_following_group
-- ----------------------------
DROP TABLE IF EXISTS `t_following_group`;
CREATE TABLE `t_following_group` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `userId` bigint DEFAULT NULL COMMENT '用户id',
  `name` varchar(100) DEFAULT NULL COMMENT '关注分组名称',
  `type` varchar(5) DEFAULT NULL COMMENT '关注分组类型：0.特别关注 1.悄悄关注 2.默认分组 3.自定义分组',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `updateTime` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of t_following_group
-- ----------------------------
BEGIN;
INSERT INTO `t_following_group` VALUES (1, NULL, '特别关注', '0', '2022-06-08 15:08:55', '2022-06-08 15:08:58');
INSERT INTO `t_following_group` VALUES (2, NULL, '悄悄关注', '1', '2022-06-08 15:09:12', '2022-06-08 15:09:14');
INSERT INTO `t_following_group` VALUES (3, NULL, '默认分组', '2', '2022-06-08 15:13:04', '2022-06-08 15:13:06');
COMMIT;

-- ----------------------------
-- Table structure for t_refresh_token
-- ----------------------------
DROP TABLE IF EXISTS `t_refresh_token`;
CREATE TABLE `t_refresh_token` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `userId` bigint DEFAULT NULL,
  `refreshToken` varchar(500) DEFAULT NULL,
  `createTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of t_refresh_token
-- ----------------------------
BEGIN;
INSERT INTO `t_refresh_token` VALUES (2, 2, 'eyJraWQiOiIyIiwidHlwIjoiSldUIiwiYWxnIjoiUlMyNTYifQ.eyJpc3MiOiJhbGV4emhsaSIsImV4cCI6MTY1Njc2Nzc4N30.ZWB0U_i83btZM0lbWF98sAPBHwYOBm1lyCSbZCZhnG_BJaJXRcj7cz686ou4ESi6pA2FeJlEYKEjk9vyf-ifSPgkcNyoGmsrzk0GMbrTtQBe5GwQbxcv0x2wunTxOKrqX1snf93WwUsowdC5y5UlZutrvfwevGlwU4MEQRylTkQ', '2022-06-25 21:16:28');
COMMIT;

-- ----------------------------
-- Table structure for t_tag
-- ----------------------------
DROP TABLE IF EXISTS `t_tag`;
CREATE TABLE `t_tag` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `createTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of t_tag
-- ----------------------------
BEGIN;
INSERT INTO `t_tag` VALUES (1, '鬼畜', '2022-11-02 01:37:52');
INSERT INTO `t_tag` VALUES (2, '动漫', '2022-11-02 01:38:02');
COMMIT;

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `phone` varchar(100) DEFAULT NULL COMMENT '手机号',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `password` varchar(255) DEFAULT NULL COMMENT '密码',
  `salt` varchar(50) DEFAULT NULL COMMENT '盐值',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `updateTime` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of t_user
-- ----------------------------
BEGIN;
INSERT INTO `t_user` VALUES (1, '15927093114', NULL, 'e0af1bac37b10ca5b4c49004f68d7021', '1652637412806', '2022-05-16 01:56:53', NULL);
INSERT INTO `t_user` VALUES (2, '222', NULL, 'e1ccb53d36a11e1a0a815f561a70e35a', '1652637760044', '2022-05-16 02:02:40', NULL);
INSERT INTO `t_user` VALUES (3, '333', NULL, 'a4175308e105d110caced3c31a484270', '1652637871505', '2022-05-16 02:04:32', NULL);
INSERT INTO `t_user` VALUES (4, '444', NULL, '1e62b4d0ffbd03ce6f053073c826cfec', '1652638052935', '2022-05-16 02:07:33', NULL);
INSERT INTO `t_user` VALUES (5, '555', NULL, '628d54cb814fb894e221d1ff19560f3e', '1652638307431', '2022-05-16 02:11:47', NULL);
INSERT INTO `t_user` VALUES (6, '666', NULL, 'aaa461ec028f46b48c41272b9df22859', '1654943479455', '2022-06-11 18:31:19', NULL);
COMMIT;

-- ----------------------------
-- Table structure for t_user_coin
-- ----------------------------
DROP TABLE IF EXISTS `t_user_coin`;
CREATE TABLE `t_user_coin` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `userId` bigint NOT NULL,
  `amount` int DEFAULT NULL COMMENT '用户拥有硬币总数',
  `createTime` datetime DEFAULT NULL,
  `updateTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of t_user_coin
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for t_user_following
-- ----------------------------
DROP TABLE IF EXISTS `t_user_following`;
CREATE TABLE `t_user_following` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `userId` bigint DEFAULT NULL COMMENT '用户id',
  `followingId` bigint DEFAULT NULL COMMENT '关注用户id',
  `groupId` bigint DEFAULT NULL COMMENT '关注分组id',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of t_user_following
-- ----------------------------
BEGIN;
INSERT INTO `t_user_following` VALUES (5, 1, 5, 3, '2022-06-11 18:40:59');
INSERT INTO `t_user_following` VALUES (7, 1, 2, 3, '2022-06-11 18:41:09');
INSERT INTO `t_user_following` VALUES (8, 1, 3, 3, '2022-06-11 18:41:11');
INSERT INTO `t_user_following` VALUES (10, 1, 4, 3, '2022-06-11 18:41:47');
INSERT INTO `t_user_following` VALUES (11, 2, 1, 3, '2022-06-11 19:33:11');
INSERT INTO `t_user_following` VALUES (12, 2, 3, 3, '2022-06-11 19:33:25');
INSERT INTO `t_user_following` VALUES (13, 2, 4, 3, '2022-06-11 19:33:28');
INSERT INTO `t_user_following` VALUES (14, 2, 5, 3, '2022-06-11 19:33:35');
INSERT INTO `t_user_following` VALUES (15, 5, 1, 3, '2022-06-11 19:34:18');
INSERT INTO `t_user_following` VALUES (16, 5, 2, 3, '2022-06-11 19:34:23');
COMMIT;

-- ----------------------------
-- Table structure for t_user_info
-- ----------------------------
DROP TABLE IF EXISTS `t_user_info`;
CREATE TABLE `t_user_info` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `userId` bigint DEFAULT NULL COMMENT '用户id',
  `nick` varchar(100) DEFAULT NULL COMMENT '昵称',
  `avatar` varchar(255) DEFAULT NULL COMMENT '头像',
  `sign` text COMMENT '签名',
  `gender` varchar(2) DEFAULT NULL COMMENT '性别：0男 1女 2未知',
  `birth` varchar(20) DEFAULT NULL COMMENT '生日',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `updateTime` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of t_user_info
-- ----------------------------
BEGIN;
INSERT INTO `t_user_info` VALUES (1, 3, 'alexzhli', NULL, NULL, '0', '1997-10-01', '2022-05-16 02:04:32', NULL);
INSERT INTO `t_user_info` VALUES (2, 4, 'alexzhli', NULL, NULL, '0', '1997-10-01', '2022-05-16 02:07:33', NULL);
INSERT INTO `t_user_info` VALUES (3, 5, 'alexzhli', NULL, NULL, '0', '1997-10-01', '2022-05-16 02:11:47', NULL);
INSERT INTO `t_user_info` VALUES (4, 1, 'aaa', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_user_info` VALUES (5, 2, 'bbb', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_user_info` VALUES (6, 6, 'alexzhli', NULL, NULL, '0', '1997-10-01', '2022-06-11 18:31:19', NULL);
COMMIT;

-- ----------------------------
-- Table structure for t_user_moments
-- ----------------------------
DROP TABLE IF EXISTS `t_user_moments`;
CREATE TABLE `t_user_moments` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `userId` bigint DEFAULT NULL COMMENT '用户id',
  `type` varchar(5) DEFAULT NULL COMMENT '动态类型：0.视频 1.直播 2.专栏动态 ',
  `contentId` bigint DEFAULT NULL COMMENT '内容详情id',
  `createTime` datetime DEFAULT NULL,
  `updateTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of t_user_moments
-- ----------------------------
BEGIN;
INSERT INTO `t_user_moments` VALUES (1, 1, '0', 1, '2022-06-11 22:25:27', NULL);
INSERT INTO `t_user_moments` VALUES (2, 1, '0', 1, '2022-06-11 22:28:17', NULL);
INSERT INTO `t_user_moments` VALUES (3, 1, '0', 1, '2022-06-11 22:28:22', NULL);
INSERT INTO `t_user_moments` VALUES (4, 1, '0', 1, '2022-06-11 22:28:40', NULL);
INSERT INTO `t_user_moments` VALUES (5, 1, '0', 1, '2022-06-13 18:28:34', NULL);
INSERT INTO `t_user_moments` VALUES (6, 1, '0', 111, '2022-06-13 18:33:07', NULL);
INSERT INTO `t_user_moments` VALUES (7, 1, '0', 1212, '2022-06-13 18:33:53', NULL);
INSERT INTO `t_user_moments` VALUES (8, 1, '0', 1212, '2022-06-13 21:20:38', NULL);
INSERT INTO `t_user_moments` VALUES (9, 1, '0', 1212, '2022-06-13 21:21:51', NULL);
INSERT INTO `t_user_moments` VALUES (10, 1, '0', 1212, '2022-06-13 21:39:34', NULL);
INSERT INTO `t_user_moments` VALUES (11, 1, '0', 11, '2022-06-13 21:39:51', NULL);
INSERT INTO `t_user_moments` VALUES (12, 1, '0', 333, '2022-06-13 21:52:27', NULL);
INSERT INTO `t_user_moments` VALUES (13, 1, '0', 555, '2022-06-13 21:55:33', NULL);
INSERT INTO `t_user_moments` VALUES (14, 1, '0', 666, '2022-06-13 22:01:04', NULL);
INSERT INTO `t_user_moments` VALUES (15, 1, '0', 123321, '2022-06-17 20:43:24', NULL);
INSERT INTO `t_user_moments` VALUES (16, 1, '0', 123321, '2022-06-17 20:47:22', NULL);
COMMIT;

-- ----------------------------
-- Table structure for t_user_role
-- ----------------------------
DROP TABLE IF EXISTS `t_user_role`;
CREATE TABLE `t_user_role` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `userId` bigint DEFAULT NULL,
  `roleId` bigint DEFAULT NULL,
  `createTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of t_user_role
-- ----------------------------
BEGIN;
INSERT INTO `t_user_role` VALUES (1, 1, 2, NULL);
INSERT INTO `t_user_role` VALUES (2, 2, 1, NULL);
COMMIT;

-- ----------------------------
-- Table structure for t_video
-- ----------------------------
DROP TABLE IF EXISTS `t_video`;
CREATE TABLE `t_video` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `userId` varchar(500) NOT NULL,
  `url` varchar(500) NOT NULL,
  `thumbnail` varchar(500) NOT NULL COMMENT '封面链接',
  `title` varchar(255) NOT NULL,
  `type` varchar(5) NOT NULL,
  `duration` varchar(255) NOT NULL,
  `area` varchar(255) NOT NULL COMMENT '分区',
  `description` text,
  `createTime` datetime DEFAULT NULL,
  `updateTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of t_video
-- ----------------------------
BEGIN;
INSERT INTO `t_video` VALUES (1, '1', 'url123', 'thumbnail123', '测试视频', '0', '135', '1', '测试视频的描述', '2022-11-02 01:37:25', '2022-11-02 01:37:28');
INSERT INTO `t_video` VALUES (10, '2', '111', '111.com', 'alex的视频', '0', '135', '1', '我爱你中国', '2022-11-12 23:16:03', NULL);
INSERT INTO `t_video` VALUES (11, '2', '111', '111.com', 'alex的视频', '0', '135', '1', '我爱你中国', '2022-11-12 23:17:36', NULL);
COMMIT;

-- ----------------------------
-- Table structure for t_video_binary_picture
-- ----------------------------
DROP TABLE IF EXISTS `t_video_binary_picture`;
CREATE TABLE `t_video_binary_picture` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `videoId` bigint DEFAULT NULL,
  `frameNo` int DEFAULT NULL,
  `url` text,
  `videoTimestamp` bigint DEFAULT NULL,
  `createTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of t_video_binary_picture
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for t_video_coin
-- ----------------------------
DROP TABLE IF EXISTS `t_video_coin`;
CREATE TABLE `t_video_coin` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `userId` bigint NOT NULL,
  `videoId` bigint NOT NULL,
  `amount` int DEFAULT NULL COMMENT '投币数量',
  `createTime` datetime DEFAULT NULL,
  `updateTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of t_video_coin
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for t_video_collection
-- ----------------------------
DROP TABLE IF EXISTS `t_video_collection`;
CREATE TABLE `t_video_collection` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `userId` bigint NOT NULL,
  `videoId` bigint NOT NULL,
  `groupId` bigint NOT NULL,
  `createTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of t_video_collection
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for t_video_comment
-- ----------------------------
DROP TABLE IF EXISTS `t_video_comment`;
CREATE TABLE `t_video_comment` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `videoId` bigint NOT NULL,
  `userId` bigint NOT NULL,
  `comment` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci COMMENT '评论',
  `replyUserId` bigint DEFAULT NULL COMMENT '回复用户Id',
  `rootId` bigint DEFAULT NULL COMMENT '根节点评论Id',
  `createTime` datetime DEFAULT NULL,
  `updateTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of t_video_comment
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for t_video_like
-- ----------------------------
DROP TABLE IF EXISTS `t_video_like`;
CREATE TABLE `t_video_like` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `userId` bigint NOT NULL,
  `videoId` bigint NOT NULL,
  `createTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of t_video_like
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for t_video_operation
-- ----------------------------
DROP TABLE IF EXISTS `t_video_operation`;
CREATE TABLE `t_video_operation` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `userId` bigint DEFAULT NULL,
  `videoId` bigint DEFAULT NULL,
  `operationType` varchar(5) DEFAULT NULL,
  `createTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of t_video_operation
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for t_video_tag
-- ----------------------------
DROP TABLE IF EXISTS `t_video_tag`;
CREATE TABLE `t_video_tag` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `videoId` bigint NOT NULL,
  `tagId` bigint NOT NULL,
  `createTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of t_video_tag
-- ----------------------------
BEGIN;
INSERT INTO `t_video_tag` VALUES (1, 1, 1, '2022-11-02 01:38:20');
INSERT INTO `t_video_tag` VALUES (2, 1, 2, '2022-11-02 01:38:30');
COMMIT;

-- ----------------------------
-- Table structure for t_video_view
-- ----------------------------
DROP TABLE IF EXISTS `t_video_view`;
CREATE TABLE `t_video_view` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `videoId` bigint NOT NULL,
  `userId` bigint DEFAULT NULL,
  `clientId` varchar(500) DEFAULT NULL COMMENT '客户端Id',
  `ip` varchar(50) DEFAULT NULL,
  `createTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of t_video_view
-- ----------------------------
BEGIN;
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
