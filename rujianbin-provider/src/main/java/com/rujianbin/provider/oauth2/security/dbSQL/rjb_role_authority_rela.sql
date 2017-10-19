/*
Navicat MySQL Data Transfer

Source Server         : 192.168.91.228(3322单独)
Source Server Version : 50716
Source Host           : 192.168.91.228:3322
Source Database       : mytest

Target Server Type    : MYSQL
Target Server Version : 50716
File Encoding         : 65001

Date: 2017-04-01 15:13:43
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for rjb_role_authority_rela
-- ----------------------------
DROP TABLE IF EXISTS `rjb_role_authority_rela`;
CREATE TABLE `rjb_role_authority_rela` (
  `role_id` bigint(20) NOT NULL,
  `authority_code` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of rjb_role_authority_rela
-- ----------------------------
INSERT INTO `rjb_role_authority_rela` VALUES ('1', 'p1:f1:read');
INSERT INTO `rjb_role_authority_rela` VALUES ('1', 'p1:f2:read');
INSERT INTO `rjb_role_authority_rela` VALUES ('2', 'p2:f3:edit');
INSERT INTO `rjb_role_authority_rela` VALUES ('3', 'p2:f4:edit');
