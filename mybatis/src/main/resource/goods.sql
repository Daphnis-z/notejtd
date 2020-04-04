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

 Date: 04/04/2020 18:02:01
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for goods
-- ----------------------------
DROP TABLE IF EXISTS `goods`;
CREATE TABLE `goods`  (
  `goods_id` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `goods_name` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL,
  `price` double(10, 2) DEFAULT NULL,
  `count` int(11) DEFAULT NULL,
  PRIMARY KEY (`goods_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of goods
-- ----------------------------
INSERT INTO `goods` VALUES ('G001', 'mybatis-book', 30.50, 100);
INSERT INTO `goods` VALUES ('G002', 'JVM-Simple', 25.00, 80);

SET FOREIGN_KEY_CHECKS = 1;
