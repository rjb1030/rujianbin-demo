/*
Navicat MySQL Data Transfer

Source Server         : 192.168.91.228(3322单独)
Source Server Version : 50716
Source Host           : 192.168.91.228:3322
Source Database       : mytest

Target Server Type    : MYSQL
Target Server Version : 50716
File Encoding         : 65001

Date: 2017-04-01 15:13:18
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for rjb_user
-- ----------------------------
DROP TABLE IF EXISTS `rjb_user`;
CREATE TABLE `rjb_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date` datetime NOT NULL,
  `name` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of rjb_user
-- ----------------------------
INSERT INTO `rjb_user` VALUES ('1', '2017-03-31 16:34:28', '汝建斌', 'rjb', 'e10adc3949ba59abbe56e057f20f883e');
