-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: localhost    Database: carrotmarket
-- ------------------------------------------------------
-- Server version	8.0.34

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
-- Table structure for table `report`
--

DROP TABLE IF EXISTS `report`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `report` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `product_id` bigint DEFAULT NULL,
  `reporter_id` bigint DEFAULT NULL,
  `category_id` bigint DEFAULT NULL,
  `details` text,
  `status` enum('PENDING','RESOLVED') DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `resolved_at` timestamp NULL DEFAULT NULL,
  `admin_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `product_id` (`product_id`),
  KEY `reporter_id` (`reporter_id`),
  KEY `admin_id` (`admin_id`),
  KEY `category_id` (`category_id`),
  CONSTRAINT `report_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
  CONSTRAINT `report_ibfk_2` FOREIGN KEY (`reporter_id`) REFERENCES `user` (`id`),
  CONSTRAINT `report_ibfk_3` FOREIGN KEY (`admin_id`) REFERENCES `user` (`id`),
  CONSTRAINT `report_ibfk_4` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=173 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `report`
--

LOCK TABLES `report` WRITE;
/*!40000 ALTER TABLE `report` DISABLE KEYS */;
INSERT INTO `report` VALUES (152,95,6,6,'신고합니다','PENDING','2024-12-03 04:09:45',NULL,NULL),(153,27,16,10,'허위매물로 의심','PENDING','2024-12-03 06:35:08',NULL,NULL),(154,25,16,10,'깨진걸 판매함 환불 안해줌','PENDING','2024-12-03 06:35:35',NULL,NULL),(155,83,16,7,'렌탈비를 받겠다고 함. 판매는 안한다고 함 신고!','PENDING','2024-12-03 06:36:05',NULL,NULL),(156,87,6,8,'내가 판 물건 재판매함 전문판매업자로 보임','PENDING','2024-12-03 06:36:46',NULL,NULL),(157,45,6,9,'카메라가 동작 안되서 환불해달라 했는데 안해줌. 신고!','PENDING','2024-12-03 06:37:40',NULL,NULL),(158,114,6,7,'알고보니 다단계임 신고!','PENDING','2024-12-03 06:38:10',NULL,NULL),(159,67,6,9,'못써보게 해서 우선 샀는데 머리에 안맞음 근데 환불 안해주고 강매시킴 신고!','PENDING','2024-12-03 06:38:45',NULL,NULL),(160,39,19,8,'내가 판 물건 재판매함 신고!','RESOLVED','2024-12-03 06:39:36','2024-12-03 06:56:07',NULL),(161,81,19,10,'물건이 부서졌는데 몰래 판매함 환불 안해줌 신고!','PENDING','2024-12-03 06:40:20',NULL,NULL),(162,111,8,6,'수상함 신고!','RESOLVED','2024-12-03 06:41:54','2024-12-03 06:55:24',NULL),(163,120,8,11,'연락 안됨 신고!','PENDING','2024-12-03 06:42:46',NULL,NULL),(164,109,8,8,'전에 거래했던 매물인데 다시 팜 신고!','PENDING','2024-12-03 06:43:50',NULL,NULL),(165,61,16,6,'사진 속에 마약이 있어요..\r\n확대해서 보면 보임..','PENDING','2024-12-03 06:45:48',NULL,NULL),(166,71,16,7,'이 정도는 무료나눔해야 해요.\r\n품질이 정말 별로에요','PENDING','2024-12-03 06:47:15',NULL,NULL),(167,59,16,11,'아무리 중고거래라지만\r\nesc키가 빠져있고, 때가 저렇게 많은 키보드를 파는 것은 인간된 도리가 아닌거 같습니다.','RESOLVED','2024-12-03 06:48:49','2024-12-03 06:55:42',NULL),(168,83,20,10,'실물로 보니 장식용임.\r\n현재 경찰에 신고함\r\n','PENDING','2024-12-03 06:50:29',NULL,NULL),(169,98,20,8,'게시물을 보고 연락했는데 게시물말고 다른 새 물건을 판매하려함.','PENDING','2024-12-03 06:51:24',NULL,NULL),(170,49,18,11,'사무실에서 게임을 어떻게 하는거죠?\r\n','RESOLVED','2024-12-03 06:53:11','2024-12-03 06:55:11',NULL),(171,103,18,7,'렌탈해준다고 함. 신고','RESOLVED','2024-12-03 06:54:07','2024-12-03 06:54:57',NULL),(172,39,8,6,'알고보니 마약 판매임 신고!','RESOLVED','2024-12-03 06:56:55','2024-12-03 06:57:19',NULL);
/*!40000 ALTER TABLE `report` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-12-03 17:01:35
