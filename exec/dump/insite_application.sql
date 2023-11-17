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
-- Table structure for table `application`
--

DROP TABLE IF EXISTS `application`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `application` (
  `application_id` int NOT NULL,
  `application_token` varchar(60) NOT NULL,
  `application_url` varchar(255) NOT NULL,
  `created_time` datetime(6) NOT NULL,
  `is_deleted` bit(1) NOT NULL,
  `name` varchar(255) NOT NULL,
  `member_id` int NOT NULL,
  PRIMARY KEY (`application_id`),
  KEY `FKg29uoyxw2bkpcsrxt99j4yfw4` (`member_id`),
  CONSTRAINT `FKg29uoyxw2bkpcsrxt99j4yfw4` FOREIGN KEY (`member_id`) REFERENCES `member` (`member_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `application`
--

LOCK TABLES `application` WRITE;
/*!40000 ALTER TABLE `application` DISABLE KEYS */;
INSERT INTO `application` VALUES (3,'ac59cbfc-5bb0-4c0f-8150-86be8e7a9819','moduo.kr','2023-11-13 13:44:09.615617',_binary '\0','모듀오입니다.',2),(6,'87114d66-9228-4e89-80f6-7a4f94663a6b','https://rollinghoney.com','2023-11-13 16:14:43.776315',_binary '\0','꿀스라이팅',2),(7,'150ee4c3-ee5e-4eff-9b7d-97a5c269a5b2','http://moduo.kr','2023-11-13 16:31:49.961594',_binary '\0','모두오',4),(8,'e0e4fa76-07c3-4c6b-a8de-a5d11d1fce53','https://naver.com','2023-11-13 17:25:39.568684',_binary '\0','모두오',5),(10,'01aaab09-e072-4867-922a-1798a61fc453','naver.com','2023-11-14 10:09:58.106131',_binary '\0','네이버',9),(11,'a578c18f-d4ba-46ae-b73a-e1d3cfde67dc','www.ssafybank.com','2023-11-14 10:52:43.394940',_binary '\0','싸피방크',4),(12,'508a2449-dcbd-4ac1-9849-a2046d29562f','ewq','2023-11-14 13:12:32.369334',_binary '\0','qw',1),(13,'27c9adb4-7ce8-40f8-9068-bdfafce35020','www.moduo.kr','2023-11-14 13:17:11.363723',_binary '\0','모두오',2),(15,'451a2e9c-fc9c-4f3f-aa92-7c2625faecc5','https://your.gg','2023-11-14 14:17:50.315864',_binary '\0','유어지지',14),(16,'f5996274-17f1-4ac5-804f-17196e7e11d1','https://ollinghoney.com/','2023-11-14 14:28:02.810361',_binary '\0','꿀스라이팅',9),(17,'49f47876-dbb0-4f4b-a1c5-e8af3d2418ee','https://www.rollinghoney.com','2023-11-14 16:33:07.754740',_binary '\0','',1),(19,'84fac1ba-892e-4ff6-8435-cc5e9077001f','','2023-11-14 17:35:45.363539',_binary '\0','',14),(20,'30667c9a-1abd-457e-a93b-aa5a446f5998','https://moduo.kr','2023-11-14 17:56:42.908544',_binary '\0','moduo.kr',14),(23,'58d27a83-0fbc-4bd2-84b7-97405ec09329','https://dgrr.live','2023-11-14 18:17:11.440806',_binary '\0','dgrr',22),(25,'c4245dc0-c041-4164-a7bc-5f6dcaa720e5','https://dgrr.live','2023-11-14 18:17:34.494071',_binary '\0','데구르르',21),(26,'400c4215-0495-4c78-9c86-347dbc8654ea','https://dev.dgrr.live','2023-11-14 18:18:27.605341',_binary '\0','dgrr-dev',22),(27,'e69051ee-4e6c-485a-be8c-1619922d8a90','https://moduo.kr','2023-11-14 20:17:49.682038',_binary '\0','모듀오',14),(28,'7fa5becc-b78f-42ca-9b44-911b8b10e9bf','https://naver.com','2023-11-15 11:47:44.176793',_binary '\0','네이버',5),(30,'605aa093-e0f7-4559-a9ed-88a9fdaeda2f','https://dgrr.live','2023-11-15 16:44:40.080826',_binary '\0','데구르르',29),(32,'d698ba29-59f7-49b4-a23d-b43570de392b','https://showeat.kr/','2023-11-16 11:09:05.268811',_binary '\0','쑈잇',31),(34,'21e37106-769e-4dfe-bb55-c9443ba0ff07','musiq.site','2023-11-16 13:59:40.953944',_binary '\0','뮤지끄',33),(35,'bb386ecb-88a4-4110-b1fd-5acce9d835c3','https://naver.com','2023-11-16 17:03:09.115200',_binary '\0','네이버',14),(37,'3ca44419-d8b4-4bf4-8506-71b9454ccc8c','https://rollinghoney.com/','2023-11-16 17:11:10.491994',_binary '\0','꿀라',36);
/*!40000 ALTER TABLE `application` ENABLE KEYS */;
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
