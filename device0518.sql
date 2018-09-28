/*
 Navicat Premium Data Transfer

 Source Server         : jianhe
 Source Server Type    : MySQL
 Source Server Version : 50721
 Source Host           : localhost
 Source Database       : device0518

 Target Server Type    : MySQL
 Target Server Version : 50721
 File Encoding         : utf-8

 Date: 05/23/2018 17:09:47 PM
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `truck_admin`
-- ----------------------------
DROP TABLE IF EXISTS `truck_admin`;
CREATE TABLE `truck_admin` (
  `admin_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '管理员id',
  `admin_name` varchar(50) NOT NULL COMMENT '管理员名',
  `password` varchar(50) NOT NULL COMMENT '管理员密码，MD5加密',
  `email` varchar(50) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `question` varchar(100) DEFAULT NULL COMMENT '找回密码问题',
  `answer` varchar(100) DEFAULT NULL COMMENT '找回密码答案',
  `role` int(4) NOT NULL COMMENT '角色0-管理员,1-超级管理员',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '最后一次更新时间',
  PRIMARY KEY (`admin_id`),
  UNIQUE KEY `user_name_unique` (`admin_name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `truck_admin`
-- ----------------------------
BEGIN;
INSERT INTO `truck_admin` VALUES ('1', 'admin', 'E10ADC3949BA59ABBE56E057F20F883E', '12345744489@qq.com', '1234568998', 'question', 'answer', '1', '2018-03-15 10:04:56', '2018-03-15 10:43:43'), ('2', 'admin234', 'E10ADC3949BA59ABBE56E057F20F883E', '1345744489@qq.com', '134568998', 'question', 'answer', '0', '2018-03-15 10:04:56', '2018-03-15 10:43:43'), ('3', 'admin3', 'E10ADC3949BA59ABBE56E057F20F883E', '145744489@qq.com', '14568998', 'question', 'answer', '0', '2018-03-15 10:04:56', '2018-03-15 10:43:43'), ('5', '123', '202CB962AC59075B964B07152D234B70', '123', '123456', null, null, '0', '2018-04-20 09:04:16', '2018-04-20 09:04:16'), ('6', 'admin2345', 'E10ADC3949BA59ABBE56E057F20F883E', null, null, null, null, '0', '2018-05-22 10:39:45', '2018-05-22 10:39:45'), ('7', 'admin23458', 'E10ADC3949BA59ABBE56E057F20F883E', null, null, '11', '22', '0', '2018-05-22 10:41:42', '2018-05-22 10:41:42'), ('8', 'admin123', '202CB962AC59075B964B07152D234B70', 'admin123', null, null, null, '0', '2018-05-22 13:57:44', '2018-05-22 13:57:44'), ('9', '1123', '202CB962AC59075B964B07152D234B70', '1123', null, null, null, '0', '2018-05-22 17:04:06', '2018-05-22 17:04:06'), ('10', '333', '310DCBBF4CCE62F762A2AAA148D556BD', '333', null, null, null, '0', '2018-05-22 17:21:23', '2018-05-22 17:21:23'), ('11', '李华', '202CB962AC59075B964B07152D234B70', '1111', null, null, null, '0', '2018-05-22 17:30:30', '2018-05-22 17:30:30'), ('12', '光头强', '202CB962AC59075B964B07152D234B70', '4114', null, null, null, '0', '2018-05-22 17:31:43', '2018-05-22 17:31:43'), ('13', '7878', '21C3134EE5EDCB618C4F9AAE358D73A7', '7878', null, null, null, '0', '2018-05-23 00:15:52', '2018-05-23 00:15:52'), ('14', '8989', 'B66DC44CD9882859D84670604AE276E6', '8989', null, null, null, '0', '2018-05-23 00:22:55', '2018-05-23 00:22:55'), ('15', 'ppp', 'F27F6F1C7C5CBF4E3E192E0A47B85300', 'ppp', null, null, null, '0', '2018-05-23 00:34:14', '2018-05-23 00:34:14'), ('16', 'ddd', '77963B7A931377AD4AB5AD6A9CD718AA', 'ddd', null, null, null, '0', '2018-05-23 00:34:43', '2018-05-23 00:34:43'), ('17', 'aaa', '47BCE5C74F589F4867DBD57E9CA9F808', 'aaa', null, null, null, '0', '2018-05-23 08:59:47', '2018-05-23 08:59:47'), ('18', '8080', 'E10ADC3949BA59ABBE56E057F20F883E', '8080@163.com', null, null, null, '0', '2018-05-23 10:24:23', '2018-05-23 10:24:23'), ('19', 'mmm', 'C4EFD5020CB49B9D3257FFA0FBCCC0AE', 'mmm', null, null, null, '0', '2018-05-23 11:13:49', '2018-05-23 11:13:49');
COMMIT;

-- ----------------------------
--  Table structure for `truck_device`
-- ----------------------------
DROP TABLE IF EXISTS `truck_device`;
CREATE TABLE `truck_device` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `admin_id` int(11) DEFAULT NULL,
  `product_id` int(11) DEFAULT NULL,
  `category_no` int(11) DEFAULT NULL COMMENT '0卡车，1挖机，2推土机，3装载机',
  `device_no` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Records of `truck_device`
-- ----------------------------
BEGIN;
INSERT INTO `truck_device` VALUES ('1', '1', '1', '1', '1', '2018-05-18 16:26:27', '2018-05-18 16:26:29'), ('2', '1', '2', '3', '2', '2018-05-18 22:26:50', '2018-05-18 22:26:50'), ('3', '1', '2', '3', '3', '2018-05-19 11:02:06', '2018-05-19 11:02:06'), ('4', '1', '1', '3', '4', '2018-05-19 11:02:25', '2018-05-19 11:02:25'), ('5', '1', '2', '3', '5', '2018-05-19 11:02:37', '2018-05-19 11:02:37'), ('6', '1', null, '2', '6', '2018-05-19 15:13:48', '2018-05-19 15:13:48'), ('7', '1', '2', '1', '9', '2018-05-19 15:23:09', '2018-05-19 15:28:58'), ('8', '1', null, '0', '23', '2018-05-19 16:58:42', '2018-05-22 17:46:14'), ('9', '1', null, '0', '955', '2018-05-22 17:46:09', '2018-05-22 19:02:01'), ('10', '1', null, '0', '1231', '2018-05-22 18:57:44', '2018-05-22 18:57:44'), ('11', '1', null, '1', '222', '2018-05-22 19:19:50', '2018-05-22 19:19:50'), ('12', '1', null, '1', '11', '2018-05-22 19:20:09', '2018-05-22 19:20:09'), ('13', '1', null, '3', '9999', '2018-05-22 19:20:37', '2018-05-22 19:22:01'), ('14', '1', null, '1', '121334', '2018-05-22 19:21:34', '2018-05-22 19:21:34'), ('15', '1', null, '0', '999', '2018-05-23 00:21:05', '2018-05-23 00:21:05'), ('16', '1', null, '0', '7', '2018-05-23 10:20:57', '2018-05-23 10:20:57'), ('17', '1', null, '0', '987', '2018-05-23 10:31:48', '2018-05-23 10:31:48');
COMMIT;

-- ----------------------------
--  Table structure for `truck_device_access_time`
-- ----------------------------
DROP TABLE IF EXISTS `truck_device_access_time`;
CREATE TABLE `truck_device_access_time` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `admin_id` int(11) DEFAULT NULL,
  `device_id` int(11) DEFAULT NULL,
  `driver_id` int(11) DEFAULT NULL COMMENT '卡车中间表id',
  `previous_id` int(11) DEFAULT NULL COMMENT '挖掘机中间表id',
  `address` varchar(100) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=107 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `truck_device_access_time`
-- ----------------------------
BEGIN;
INSERT INTO `truck_device_access_time` VALUES ('105', '10', '16', '58', '59', null, '2018-05-23 12:03:02', '2018-05-23 12:03:02'), ('106', '10', '8', '60', '59', null, '2018-05-23 12:05:06', '2018-05-23 12:05:06');
COMMIT;

-- ----------------------------
--  Table structure for `truck_device_admin`
-- ----------------------------
DROP TABLE IF EXISTS `truck_device_admin`;
CREATE TABLE `truck_device_admin` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `admin_id` int(11) DEFAULT NULL,
  `device_id` int(11) DEFAULT NULL,
  `status` int(6) DEFAULT NULL COMMENT '0未使用，1使用中。',
  `frequency` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=61 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Records of `truck_device_admin`
-- ----------------------------
BEGIN;
INSERT INTO `truck_device_admin` VALUES ('58', '18', '16', '1', '0', '2018-05-23 12:02:28', '2018-05-23 12:02:28'), ('59', '10', '1', '1', '2', '2018-05-23 12:02:55', '2018-05-23 12:05:06'), ('60', '2', '8', '1', '0', '2018-05-23 12:04:26', '2018-05-23 12:04:26');
COMMIT;

-- ----------------------------
--  Table structure for `truck_device_mileage`
-- ----------------------------
DROP TABLE IF EXISTS `truck_device_mileage`;
CREATE TABLE `truck_device_mileage` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `admin_id` int(11) DEFAULT NULL,
  `device_id` int(11) DEFAULT NULL,
  `driver_id` int(11) DEFAULT NULL,
  `device_mileage` bigint(20) NOT NULL,
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `normal_time` decimal(20,0) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `truck_device_mileage`
-- ----------------------------
BEGIN;
INSERT INTO `truck_device_mileage` VALUES ('19', '2', '8', null, '280', '2018-05-23 16:26:55', '2018-05-23 16:26:55', null), ('20', '2', '8', null, '290', '2018-05-23 16:27:03', '2018-05-23 16:27:03', null);
COMMIT;

-- ----------------------------
--  Table structure for `truck_device_oil`
-- ----------------------------
DROP TABLE IF EXISTS `truck_device_oil`;
CREATE TABLE `truck_device_oil` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `admin_id` int(11) DEFAULT NULL,
  `device_id` int(11) DEFAULT NULL,
  `driver_id` int(11) DEFAULT NULL,
  `device_oil` bigint(20) NOT NULL COMMENT '商品名称',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `price` decimal(20,2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `truck_device_oil`
-- ----------------------------
BEGIN;
INSERT INTO `truck_device_oil` VALUES ('13', '2', '8', null, '200', '2018-05-23 15:37:30', '2018-05-23 15:37:30', '200.00'), ('14', '3', '9', null, '200', '2018-05-23 15:37:30', '2018-05-23 15:37:30', '200.00'), ('15', '3', '9', null, '100', '2018-05-23 15:37:30', '2018-05-23 15:37:30', '200.00');
COMMIT;

-- ----------------------------
--  Table structure for `truck_device_work_time`
-- ----------------------------
DROP TABLE IF EXISTS `truck_device_work_time`;
CREATE TABLE `truck_device_work_time` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `admin_id` int(11) DEFAULT NULL,
  `device_id` int(11) DEFAULT NULL,
  `driver_id` int(11) DEFAULT NULL COMMENT '卡车中间表id',
  `previous_id` int(11) DEFAULT NULL COMMENT '挖掘机中间表id',
  `address` varchar(100) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `normal_time` decimal(20,2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `truck_device_work_time`
-- ----------------------------
BEGIN;
INSERT INTO `truck_device_work_time` VALUES ('2', '2', '8', null, null, '1', '2018-05-23 16:30:30', '2018-05-23 16:39:52', null), ('3', '2', '8', null, null, null, '2018-05-23 16:38:30', '2018-05-23 16:38:30', null);
COMMIT;

-- ----------------------------
--  Table structure for `truck_user`
-- ----------------------------
DROP TABLE IF EXISTS `truck_user`;
CREATE TABLE `truck_user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户表id',
  `user_name` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(50) NOT NULL COMMENT '用户密码，MD5加密',
  `email` varchar(50) NOT NULL,
  `phone` varchar(20) NOT NULL,
  `question` varchar(100) DEFAULT NULL COMMENT '找回密码问题',
  `answer` varchar(100) DEFAULT NULL COMMENT '找回密码答案',
  `role` int(4) NOT NULL COMMENT '角色0-普通用户,1-管理员，2-VIP用户',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '最后一次更新时间',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `user_name_unique` (`user_name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `truck_user`
-- ----------------------------
BEGIN;
INSERT INTO `truck_user` VALUES ('24', 'admin', '25F9E794323B453885F5181F1B624D0B', '22222@qq.com', '12345678998', '问题', '问题的答案', '1', '2018-03-14 18:03:17', '2018-03-14 18:22:05'), ('25', 'admin234', '25F9E794323B453885F5181F1B624D0B', '2222@qq.com', '12345678998', '问题', '问题的答案', '0', '2018-03-14 18:03:17', '2018-03-14 18:22:05');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
