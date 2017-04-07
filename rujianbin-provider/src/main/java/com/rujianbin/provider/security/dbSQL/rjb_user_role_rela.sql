/*
Navicat MySQL Data Transfer

Source Server         : 192.168.91.228(3322单独)
Source Server Version : 50716
Source Host           : 192.168.91.228:3322
Source Database       : mytest

Target Server Type    : MYSQL
Target Server Version : 50716
File Encoding         : 65001

Date: 2017-04-01 15:13:49
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for rjb_user_role_rela
-- ----------------------------
DROP TABLE IF EXISTS `rjb_user_role_rela`;
CREATE TABLE `rjb_user_role_rela` (
  `user_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of rjb_user_role_rela
-- ----------------------------
INSERT INTO `rjb_user_role_rela` VALUES ('1', '1');
INSERT INTO `rjb_user_role_rela` VALUES ('1', '2');
