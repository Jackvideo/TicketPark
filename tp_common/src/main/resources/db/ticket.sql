/*
 Navicat Premium Data Transfer

 Source Server         : db1
 Source Server Type    : MySQL
 Source Server Version : 80033
 Source Host           : localhost:3306
 Source Schema         : ticket

 Target Server Type    : MySQL
 Target Server Version : 80033
 File Encoding         : 65001

 Date: 25/12/2023 11:00:42
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for comments
-- ----------------------------
DROP TABLE IF EXISTS `comments`;
CREATE TABLE `comments`  (
  `movieid` int(0) NULL DEFAULT NULL COMMENT '电影ID',
  `userid` int(0) NOT NULL COMMENT '用户ID',
  `comment` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '评论',
  UNIQUE INDEX `userid`(`userid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for movies
-- ----------------------------
DROP TABLE IF EXISTS `movies`;
CREATE TABLE `movies`  (
  `movieid` int(0) NOT NULL AUTO_INCREMENT COMMENT '电影ID',
  `moviename` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '电影名',
  `description` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '描述',
  `ticket_price` double NULL DEFAULT NULL COMMENT '票价',
  `start_time` date NOT NULL COMMENT '上映时间',
  `type` int(0) NULL DEFAULT NULL COMMENT '类别',
  `image_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '图片URL',
  `like_count` int(0) NULL DEFAULT 0,
  `comment_count` int(0) NULL DEFAULT 0,
  PRIMARY KEY (`movieid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of movies
-- ----------------------------
INSERT INTO `movies` VALUES (1, '你的名字', '煞笔二次元', 1000, '2012-01-20', 1, '1231231', NULL, NULL);

-- ----------------------------
-- Table structure for orders
-- ----------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders`  (
  `orderid` int(0) NOT NULL AUTO_INCREMENT COMMENT '订单ID',
  `userid` int(0) NULL DEFAULT NULL COMMENT '用户ID',
  `movieid` int(0) NULL DEFAULT NULL COMMENT '电影ID',
  `ticketprice` double NULL DEFAULT NULL COMMENT '票价',
  `orderstate` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '订单状态',
  `ordertime` timestamp(0) NULL DEFAULT NULL COMMENT '订单时间',
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户名',
  `moviename` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '电影名',
  PRIMARY KEY (`orderid`) USING BTREE,
  UNIQUE INDEX `orderid`(`orderid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of orders
-- ----------------------------
INSERT INTO `orders` VALUES (1, 20000, 1, 1000, 'FINISHED', '2023-12-11 22:49:54', 'jack', '你的名字');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `userid` int(0) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `username` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户名',
  `password` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '密码',
  `balance` double NULL DEFAULT NULL COMMENT '余额',
  PRIMARY KEY (`userid`) USING BTREE,
  UNIQUE INDEX `userid`(`userid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 20044 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (20000, 'jack', '123456', 99000);
INSERT INTO `user` VALUES (20001, 'user0002', 'password0002', 1500);
INSERT INTO `user` VALUES (20002, 'user0003', 'password0003', 2000);
INSERT INTO `user` VALUES (20003, 'user0004', 'password0004', 2500);
INSERT INTO `user` VALUES (20004, 'user0005', 'password0005', 3000);
INSERT INTO `user` VALUES (20005, 'user0006', 'password0006', 3500);
INSERT INTO `user` VALUES (20006, 'user0007', 'password0007', 4000);
INSERT INTO `user` VALUES (20007, 'user0008', 'password0008', 4500);
INSERT INTO `user` VALUES (20008, 'user0009', 'password0009', 5000);
INSERT INTO `user` VALUES (20009, 'user0010', 'password0010', 5500);
INSERT INTO `user` VALUES (20010, 'user0011', 'password0011', 6000);
INSERT INTO `user` VALUES (20011, 'user0012', 'password0012', 6500);
INSERT INTO `user` VALUES (20012, 'user0013', 'password0013', 7000);
INSERT INTO `user` VALUES (20013, 'user0014', 'password0014', 7500);
INSERT INTO `user` VALUES (20014, 'user0015', 'password0015', 8000);
INSERT INTO `user` VALUES (20015, 'user0016', 'password0016', 8500);
INSERT INTO `user` VALUES (20016, 'user0017', 'password0017', 9000);
INSERT INTO `user` VALUES (20017, 'user0018', 'password0018', 9500);
INSERT INTO `user` VALUES (20018, 'user0019', 'password0019', 10000);
INSERT INTO `user` VALUES (20019, 'user0020', 'password0020', 10500);
INSERT INTO `user` VALUES (20020, 'user0021', 'password0021', 11000);
INSERT INTO `user` VALUES (20021, 'user0022', 'password0022', 11500);
INSERT INTO `user` VALUES (20022, 'user0023', 'password0023', 12000);
INSERT INTO `user` VALUES (20023, 'user0024', 'password0024', 15000);
INSERT INTO `user` VALUES (20024, 'user0025', 'password0025', 18000);
INSERT INTO `user` VALUES (20025, 'user0026', 'password0026', 21000);
INSERT INTO `user` VALUES (20026, 'user0027', 'password0027', 24000);
INSERT INTO `user` VALUES (20027, 'user0028', 'password0028', 27000);
INSERT INTO `user` VALUES (20028, 'user0029', 'password0029', 30000);
INSERT INTO `user` VALUES (20029, 'user0030', 'password0030', 33000);
INSERT INTO `user` VALUES (20030, 'user0031', 'password0031', 36000);
INSERT INTO `user` VALUES (20031, 'user0032', 'password0032', 39000);
INSERT INTO `user` VALUES (20032, 'user0033', 'password0033', 42000);
INSERT INTO `user` VALUES (20033, 'user0034', 'password0034', 45000);
INSERT INTO `user` VALUES (20034, 'user0035', 'password0035', 202);
INSERT INTO `user` VALUES (20035, 'user0036', 'password0036', 202);
INSERT INTO `user` VALUES (20036, 'user0037', 'password0037', 1500);
INSERT INTO `user` VALUES (20037, 'user0038', 'password0038', 15000);
INSERT INTO `user` VALUES (20038, 'user0039', 'password0039', 2000);
INSERT INTO `user` VALUES (20039, 'user0040', 'password0040', 2000);
INSERT INTO `user` VALUES (20040, 'user0041', 'password0041', 2000);
INSERT INTO `user` VALUES (20041, 'user0042', 'password0042', 2000);
INSERT INTO `user` VALUES (20042, 'user0043', 'password0043', 2000);
INSERT INTO `user` VALUES (20043, 'user0044', 'password0044', 2000);
INSERT INTO `user` VALUES (20044, 'Pewdiepie1', '123456', NULL);

SET FOREIGN_KEY_CHECKS = 1;
