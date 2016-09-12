CREATE DATABASE  IF NOT EXISTS `test_ssh` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `test_ssh`;
-- MySQL dump 10.13  Distrib 5.7.9, for osx10.9 (x86_64)
--
-- Host: localhost    Database: test_ssh
-- ------------------------------------------------------
-- Server version	5.7.10

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `dic_sex`
--

DROP TABLE IF EXISTS `dic_sex`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dic_sex` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '性别编号',
  `name` varchar(2) NOT NULL COMMENT '性别',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dic_sex`
--

LOCK TABLES `dic_sex` WRITE;
/*!40000 ALTER TABLE `dic_sex` DISABLE KEYS */;
INSERT INTO `dic_sex` VALUES (1,'男'),(2,'女');
/*!40000 ALTER TABLE `dic_sex` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `persistent_logins`
--

DROP TABLE IF EXISTS `persistent_logins`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `persistent_logins` (
  `username` varchar(64) NOT NULL COMMENT '用户名',
  `series` varchar(64) NOT NULL COMMENT '唯一标志',
  `token` varchar(64) NOT NULL COMMENT '安全码',
  `last_used` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后登录时间',
  PRIMARY KEY (`series`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统 - 免登录用户记录表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `persistent_logins`
--

LOCK TABLES `persistent_logins` WRITE;
/*!40000 ALTER TABLE `persistent_logins` DISABLE KEYS */;
/*!40000 ALTER TABLE `persistent_logins` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_log`
--

DROP TABLE IF EXISTS `sys_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '日志编号',
  `userid` int(11) DEFAULT NULL COMMENT '用户编号',
  `username` varchar(45) DEFAULT NULL COMMENT '用户名称',
  `class` varchar(45) DEFAULT NULL COMMENT '类',
  `method` varchar(45) DEFAULT NULL COMMENT '方法',
  `create_time` datetime DEFAULT NULL COMMENT '产生时间',
  `log_level` varchar(5) DEFAULT NULL COMMENT '日志级别',
  `message` longtext COMMENT '日志内容',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统 - 日志表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_log`
--

LOCK TABLES `sys_log` WRITE;
/*!40000 ALTER TABLE `sys_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_menu`
--

DROP TABLE IF EXISTS `sys_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '菜单编号',
  `title` varchar(32) NOT NULL COMMENT '菜单标题',
  `level_code` varchar(300) DEFAULT NULL COMMENT '菜单层级编码',
  `parent` int(11) DEFAULT NULL COMMENT '父级菜单编号',
  `sort` int(5) DEFAULT NULL COMMENT '展示顺序',
  `type` varchar(10) DEFAULT NULL COMMENT '菜单类型',
  `url` varchar(400) DEFAULT NULL COMMENT '页面链接',
  `method` varchar(10) DEFAULT NULL COMMENT '页面链接访问方式',
  `Authority` varchar(100) DEFAULT NULL COMMENT '菜单标志',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='系统 - 菜单表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_menu`
--

LOCK TABLES `sys_menu` WRITE;
/*!40000 ALTER TABLE `sys_menu` DISABLE KEYS */;
INSERT INTO `sys_menu` VALUES (1,'张争洋2',NULL,11,11,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `sys_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role`
--

DROP TABLE IF EXISTS `sys_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_role` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色编号',
  `NAME` varchar(32) DEFAULT NULL COMMENT '角色名称',
  `Authority` varchar(100) DEFAULT NULL COMMENT '角色标志',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='系统 - 角色表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role`
--

LOCK TABLES `sys_role` WRITE;
/*!40000 ALTER TABLE `sys_role` DISABLE KEYS */;
INSERT INTO `sys_role` VALUES (1,'管理员','ROLE_ADMIN');
/*!40000 ALTER TABLE `sys_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role_menu`
--

DROP TABLE IF EXISTS `sys_role_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_role_menu` (
  `ROLE_ID` int(11) NOT NULL COMMENT '角色编号',
  `MENU_ID` int(11) NOT NULL COMMENT '菜单编号',
  PRIMARY KEY (`MENU_ID`,`ROLE_ID`),
  KEY `FKtebbbnr3cvixu9eowja2wo4w3` (`ROLE_ID`),
  CONSTRAINT `fk_sys_role_menu_menu_id` FOREIGN KEY (`MENU_ID`) REFERENCES `sys_menu` (`id`),
  CONSTRAINT `fk_sys_role_menu_role_id` FOREIGN KEY (`ROLE_ID`) REFERENCES `sys_role` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统 - 角色菜单关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role_menu`
--

LOCK TABLES `sys_role_menu` WRITE;
/*!40000 ALTER TABLE `sys_role_menu` DISABLE KEYS */;
INSERT INTO `sys_role_menu` VALUES (1,1);
/*!40000 ALTER TABLE `sys_role_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user`
--

DROP TABLE IF EXISTS `sys_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_user` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户编号',
  `USERNAME` varchar(50) DEFAULT NULL COMMENT '用户姓名',
  `PASSWORD` varchar(50) DEFAULT NULL COMMENT '密码',
  `enabled` tinyint(1) DEFAULT '1' COMMENT '是否启用',
  `Account_Non_Expired` tinyint(1) DEFAULT '1' COMMENT '账号是否没有过期',
  `Account_Non_Locked` tinyint(1) DEFAULT '1' COMMENT '账号是否没有锁定',
  `Credentials_Non_Expired` tinyint(1) DEFAULT '1' COMMENT '密码是否没有过期',
  `sex_id` int(11) DEFAULT NULL COMMENT '性别',
  `birthday` date DEFAULT NULL COMMENT '生日',
  `email` varchar(45) DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(11) DEFAULT NULL COMMENT '手机',
  `address` varchar(45) DEFAULT NULL COMMENT '住址',
  `photo` varchar(45) DEFAULT NULL COMMENT '头像',
  `create_time` datetime DEFAULT NULL COMMENT '注册时间',
  PRIMARY KEY (`ID`),
  KEY `fk_sys_user_dic_sex_idx` (`sex_id`),
  CONSTRAINT `fk_sys_user_dic_sex` FOREIGN KEY (`sex_id`) REFERENCES `dic_sex` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='系统 - 用户表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user`
--

LOCK TABLES `sys_user` WRITE;
/*!40000 ALTER TABLE `sys_user` DISABLE KEYS */;
INSERT INTO `sys_user` VALUES (1,'admin','21232f297a57a5a743894a0e4a801fc3',1,1,1,1,1,'1990-12-22','javazzy@163.com','15210827503','翠明新城',NULL,'2016-01-01 01:01:01'),(2,'anonymous',NULL,0,1,1,1,1,'1990-12-22',NULL,NULL,NULL,NULL,NULL),(3,'test','test',1,1,1,1,1,'1990-12-22',NULL,NULL,NULL,NULL,NULL),(4,'yujing','23d8d34825e8dd3b36d66d91d86bf806',0,1,1,1,2,NULL,NULL,NULL,NULL,NULL,NULL),(5,'baxiaodan','eed833cbcec1fa08193d9177ecae8a90',1,0,1,1,2,NULL,NULL,NULL,NULL,NULL,NULL),(6,'zhengli','a1be2ffdd44a8df6ecb33f58bc5f175b',1,0,1,1,2,NULL,NULL,NULL,NULL,NULL,NULL),(7,'zhengli','a1be2ffdd44a8df6ecb33f58bc5f175b',1,1,1,1,2,NULL,NULL,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `sys_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user_role`
--

DROP TABLE IF EXISTS `sys_user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_user_role` (
  `USER_ID` int(11) NOT NULL COMMENT '用户编号',
  `ROLE_ID` int(11) NOT NULL COMMENT '角色编号',
  PRIMARY KEY (`ROLE_ID`,`USER_ID`),
  KEY `FKtmxktnsbxjqx4o0ek2ysfcomj` (`USER_ID`),
  CONSTRAINT `fk_sys_user_role_role_id` FOREIGN KEY (`ROLE_ID`) REFERENCES `sys_role` (`ID`),
  CONSTRAINT `fk_sys_user_role_user_id` FOREIGN KEY (`USER_ID`) REFERENCES `sys_user` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统 - 用户角色关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user_role`
--

LOCK TABLES `sys_user_role` WRITE;
/*!40000 ALTER TABLE `sys_user_role` DISABLE KEYS */;
INSERT INTO `sys_user_role` VALUES (1,1);
/*!40000 ALTER TABLE `sys_user_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `test`
--

DROP TABLE IF EXISTS `test`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `test` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(45) DEFAULT NULL COMMENT '名称',
  `age` int(11) DEFAULT NULL COMMENT '年龄',
  `birthday` date DEFAULT NULL COMMENT '生日',
  `content` longtext COMMENT '个人简介',
  `last_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `height` float DEFAULT NULL COMMENT '身高',
  `img` blob COMMENT '头像',
  `is_delete` tinyint(4) DEFAULT NULL COMMENT '是否已删除',
  `money` double(6,4) DEFAULT NULL COMMENT '资产',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK1chowmbf27f0cbp7on9ysvhjo` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='测试表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `test`
--

LOCK TABLES `test` WRITE;
/*!40000 ALTER TABLE `test` DISABLE KEYS */;
INSERT INTO `test` VALUES (1,'zzy7',4,NULL,NULL,'2016-04-12 15:01:59',NULL,NULL,NULL,NULL),(2,'fjhg',4,NULL,NULL,'2016-04-12 15:07:13',NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `test` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-09-12 23:32:36
