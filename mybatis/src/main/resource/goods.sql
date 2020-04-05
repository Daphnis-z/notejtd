/*
 Navicat Premium Data Transfer

 Source Server         : 134.100-MySql
 Source Server Type    : MySQL
 Source Server Version : 50647
 Source Host           : 192.168.134.100:3306
 Source Schema         : shopping

 Target Server Type    : MySQL
 Target Server Version : 50647
 File Encoding         : 65001

 Date: 05/04/2020 10:38:25
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for goods
-- ----------------------------
DROP TABLE IF EXISTS `goods`;
CREATE TABLE `goods`  (
  `goods_id` int(11) NOT NULL AUTO_INCREMENT,
  `goods_name` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL,
  `price` double(10, 2) DEFAULT NULL,
  `count` int(11) DEFAULT NULL,
  PRIMARY KEY (`goods_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of goods
-- ----------------------------
INSERT INTO `goods` VALUES (1, 'mybatis-book', 30.50, 100);
INSERT INTO `goods` VALUES (2, 'JVM-Simple', 25.00, 80);

SET FOREIGN_KEY_CHECKS = 1;
