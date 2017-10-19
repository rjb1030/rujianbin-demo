/*
Navicat MySQL Data Transfer

Source Server         : 192.168.91.228(3322单独)
Source Server Version : 50716
Source Host           : 192.168.91.228:3322
Source Database       : mytest

Target Server Type    : MYSQL
Target Server Version : 50716
File Encoding         : 65001

Date: 2017-04-01 15:13:37
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for rjb_authority
-- ----------------------------
DROP TABLE IF EXISTS `rjb_authority`;
CREATE TABLE `rjb_authority` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `authority_code` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of rjb_authority
-- ----------------------------
INSERT INTO `rjb_authority` VALUES ('1', 'p1:f1:read');
INSERT INTO `rjb_authority` VALUES ('2', 'p1:f2:read');
INSERT INTO `rjb_authority` VALUES ('3', 'p2:f3:edit');
INSERT INTO `rjb_authority` VALUES ('4', 'p2:f4:edit');
