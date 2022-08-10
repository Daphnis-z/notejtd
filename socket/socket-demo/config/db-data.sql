SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for account
-- ----------------------------
DROP TABLE IF EXISTS `account`;
CREATE TABLE `account`  (
  `user_id` varchar(20) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `user_name` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL,
  `user_pwd` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL,
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of account
-- ----------------------------
INSERT INTO `account` VALUES ('202201', 'admin', 'inms123456');
INSERT INTO `account` VALUES ('202202', 'user', '2022user');

-- ----------------------------
-- Table structure for say
-- ----------------------------
DROP TABLE IF EXISTS `say`;
CREATE TABLE `say`  (
  `people` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL,
  `sentence` varchar(2048) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of say
-- ----------------------------
INSERT INTO `say` VALUES ('William Shakespeare', 'Do not , for one repulse , give up the purpose that you resolved to effect .');
INSERT INTO `say` VALUES ('Thomas Edison', 'I want to bring out the secrets of nature and apply them for the happiness of man . ');
INSERT INTO `say` VALUES ('F. W . Nietzsche', 'If you would go up high , then use your own legs !');
INSERT INTO `say` VALUES ('Davy de La Pailleterie', 'Living without an aim is like sailing without a compass.');
INSERT INTO `say` VALUES ('Mark Twain', 'The man with a new idea is a crank until the idea succeeds .');

SET FOREIGN_KEY_CHECKS = 1;
