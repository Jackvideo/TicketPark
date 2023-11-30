
-- create database `ticket` manually

-- drop database if exists ticket;
use ticket;
drop table if exists movies;
CREATE TABLE `movies` (
                          `movieid` int NOT NULL COMMENT '电影ID',
                          `moviename` varchar(20) DEFAULT NULL COMMENT '电影名',
                          `description` varchar(100) DEFAULT NULL COMMENT '描述',
                          `ticket_price` double DEFAULT NULL COMMENT '票价',
                          `start_time` date not null comment '上映时间',
                          `type` int DEFAULT NULL COMMENT '类别',
                          `image_url` varchar(255) DEFAULT NULL COMMENT '图片URL',
                          PRIMARY KEY (`movieid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;




drop table if exists user;
CREATE TABLE `user` (
                        `userid` int NOT NULL UNIQUE COMMENT '用户ID',
                        `account` varchar(20) DEFAULT NULL COMMENT '账户',
                        `username` varchar(20) DEFAULT NULL COMMENT '用户名',
                        `password` varchar(20) DEFAULT NULL COMMENT '密码',
                        `balance` double DEFAULT NULL COMMENT '余额',
                        PRIMARY KEY (`userid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `user` VALUES (20000, '0001', 'user0001', 'password0001', 1000.00);
INSERT INTO `user` VALUES (20001, '0002', 'user0002', 'password0002', 1500.00);
INSERT INTO `user` VALUES (20002, '0003', 'user0003', 'password0003', 2000.00);
INSERT INTO `user` VALUES (20003, '0004', 'user0004', 'password0004', 2500.00);
INSERT INTO `user` VALUES (20004, '0005', 'user0005', 'password0005', 3000.00);
INSERT INTO `user` VALUES (20005, '0006', 'user0006', 'password0006', 3500.00);
INSERT INTO `user` VALUES (20006, '0007', 'user0007', 'password0007', 4000.00);
INSERT INTO `user` VALUES (20007, '0008', 'user0008', 'password0008', 4500.00);
INSERT INTO `user` VALUES (20008, '0009', 'user0009', 'password0009', 5000.00);
INSERT INTO `user` VALUES (20009, '0010', 'user0010', 'password0010', 5500.00);
INSERT INTO `user` VALUES (20010, '0011', 'user0011', 'password0011', 6000.00);
INSERT INTO `user` VALUES (20011, '0012', 'user0012', 'password0012', 6500.00);
INSERT INTO `user` VALUES (20012, '0013', 'user0013', 'password0013', 7000.00);
INSERT INTO `user` VALUES (20013, '0014', 'user0014', 'password0014', 7500.00);
INSERT INTO `user` VALUES (20014, '0015', 'user0015', 'password0015', 8000.00);
INSERT INTO `user` VALUES (20015, '0016', 'user0016', 'password0016', 8500.00);
INSERT INTO `user` VALUES (20016, '0017', 'user0017', 'password0017', 9000.00);
INSERT INTO `user` VALUES (20017, '0018', 'user0018', 'password0018', 9500.00);
INSERT INTO `user` VALUES (20018, '0019', 'user0019', 'password0019', 10000.00);
INSERT INTO `user` VALUES (20019, '0020', 'user0020', 'password0020', 10500.00);
INSERT INTO `user` VALUES (20020, '0021', 'user0021', 'password0021', 11000.00);
INSERT INTO `user` VALUES (20021, '0022', 'user0022', 'password0022', 11500.00);
INSERT INTO `user` VALUES (20022, '0023', 'user0023', 'password0023', 12000.00);
INSERT INTO `user` VALUES (20023, '0024', 'user0024', 'password0024', 15000.00);
INSERT INTO `user` VALUES (20024, '0025', 'user0025', 'password0025', 18000.00);
INSERT INTO `user` VALUES (20025, '0026', 'user0026', 'password0026', 21000.00);
INSERT INTO `user` VALUES (20026, '0027', 'user0027', 'password0027', 24000.00);
INSERT INTO `user` VALUES (20027, '0028', 'user0028', 'password0028', 27000.00);
INSERT INTO `user` VALUES (20028, '0029', 'user0029', 'password0029', 30000.00);
INSERT INTO `user` VALUES (20029, '0030', 'user0030', 'password0030', 33000.00);
INSERT INTO `user` VALUES (20030, '0031', 'user0031', 'password0031', 36000.00);
INSERT INTO `user` VALUES (20031, '0032', 'user0032', 'password0032', 39000.00);
INSERT INTO `user` VALUES (20032, '0033', 'user0033', 'password0033', 42000.00);
INSERT INTO `user` VALUES (20033, '0034', 'user0034', 'password0034', 45000.00);
INSERT INTO `user` VALUES (20034, '0035', 'user0035', 'password0035', 202.00);
INSERT INTO `user` VALUES (20035, '0036', 'user0036', 'password0036', 202.00);
INSERT INTO `user` VALUES (20036, '0037', 'user0037', 'password0037', 1500.00);
INSERT INTO `user` VALUES (20037, '0038', 'user0038', 'password0038', 15000.00);
INSERT INTO `user` VALUES (20038, '0039', 'user0039', 'password0039', 2000.00);
INSERT INTO `user` VALUES (20039, '0040', 'user0040', 'password0040', 2000.00);
INSERT INTO `user` VALUES (20040, '0041', 'user0041', 'password0041', 2000.00);
INSERT INTO `user` VALUES (20041, '0042', 'user0042', 'password0042', 2000.00);
INSERT INTO `user` VALUES (20042, '0043', 'user0043', 'password0043', 2000.00);
INSERT INTO `user` VALUES (20043, '0044', 'user0044', 'password0044', 2000.00);

drop table if exists `like&comments_counts`;
CREATE TABLE `like&comments_counts` (
                                        `movieid` int DEFAULT NULL COMMENT '电影ID',
                                        `likes_counts` int DEFAULT NULL COMMENT '点赞量',
                                        `comments_counts` int DEFAULT NULL COMMENT '评论量'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

drop table if exists comments;
CREATE TABLE `comments` (
                            `userid` int NOT NULL UNIQUE COMMENT '用户ID',
                            `comments` varchar(50) DEFAULT NULL COMMENT '评论'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

drop table if exists orders;
CREATE TABLE `orders` (
                          `orderid` int NOT NULL UNIQUE COMMENT '订单ID',
                          `userid` int DEFAULT NULL COMMENT '用户ID',
                          `movieid` int DEFAULT NULL COMMENT '电影ID',
                          `ticketprice` double DEFAULT NULL COMMENT '票价',
                          `orderstate` varchar(20) DEFAULT NULL COMMENT '订单状态',
                          `ordertime` timestamp NULL DEFAULT NULL COMMENT '订单时间',
                          PRIMARY KEY (`orderid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


