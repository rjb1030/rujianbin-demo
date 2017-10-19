/*
Navicat MySQL Data Transfer

Source Server         : 192.168.91.228(3322单独)
Source Server Version : 50716
Source Host           : 192.168.91.228:3322
Source Database       : mytest

Target Server Type    : MYSQL
Target Server Version : 50716
File Encoding         : 65001

Date: 2017-04-01 15:13:31
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for rjb_role
-- ----------------------------
DROP TABLE IF EXISTS `rjb_role`;
CREATE TABLE `rjb_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of rjb_role
-- ----------------------------
INSERT INTO `rjb_role` VALUES ('1', 'admin');
INSERT INTO `rjb_role` VALUES ('2', 'admin2');
INSERT INTO `rjb_role` VALUES ('3', 'admin3');
