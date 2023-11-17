-- MySQL dump 10.13  Distrib 8.0.21, for Win64 (x86_64)
--
-- Host: 52.78.138.141    Database: insite
-- ------------------------------------------------------
-- Server version	8.2.0

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `button`
--

DROP TABLE IF EXISTS `button`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `button` (
  `button_id` int NOT NULL AUTO_INCREMENT,
  `created_time` datetime(6) NOT NULL,
  `is_deleted` bit(1) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `application_id` int NOT NULL,
  PRIMARY KEY (`button_id`),
  KEY `FKg27o0wjfdae7i1gu46qlc3wp8` (`application_id`),
  CONSTRAINT `FKg27o0wjfdae7i1gu46qlc3wp8` FOREIGN KEY (`application_id`) REFERENCES `application` (`application_id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `button`
--

LOCK TABLES `button` WRITE;
/*!40000 ALTER TABLE `button` DISABLE KEYS */;
INSERT INTO `button` VALUES (1,'2023-11-13 13:49:45.525022',_binary '','검색버튼',3),(2,'2023-11-13 14:15:43.220584',_binary '\0','검색버튼 이지롱',3),(3,'2023-11-13 17:28:39.819997',_binary '\0','등록',8),(4,'2023-11-13 17:28:48.426108',_binary '\0','저장',8),(5,'2023-11-13 17:29:08.462782',_binary '\0','asdf',8),(6,'2023-11-13 23:01:21.892941',_binary '\0','하하버튼',3),(7,'2023-11-14 08:02:34.743874',_binary '\0','추가 버튼',7),(8,'2023-11-14 10:32:12.726265',_binary '\0','할로',10),(9,'2023-11-14 10:32:14.861592',_binary '\0','ㄴㅇㄹ',10),(10,'2023-11-14 10:32:16.825675',_binary '\0','ㅁㄴㅇㄻㄴㅇ',10),(11,'2023-11-14 12:08:13.201232',_binary '\0','로그인화면-닫기-버튼',6),(12,'2023-11-14 13:14:32.090670',_binary '\0','r',12),(13,'2023-11-14 14:33:48.537157',_binary '\0','전적검색버튼',15),(14,'2023-11-14 14:33:56.060206',_binary '\0','챔피언숙련도버튼',15),(15,'2023-11-14 15:15:00.037469',_binary '\0','',15),(16,'2023-11-15 11:50:41.439083',_binary '\0','메시지-보내기',6),(17,'2023-11-15 11:50:52.391699',_binary '\0','초대-링크-복사',6),(18,'2023-11-15 11:51:00.003019',_binary '\0','방-수정',6),(19,'2023-11-16 11:10:04.058349',_binary '\0','ㅇㅎㄹ',32),(20,'2023-11-16 11:10:09.285873',_binary '\0','ㄴㄴ',32),(21,'2023-11-16 11:10:11.888317',_binary '\0','ㄴㅇㅎ',32);
/*!40000 ALTER TABLE `button` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-11-17 17:48:29
