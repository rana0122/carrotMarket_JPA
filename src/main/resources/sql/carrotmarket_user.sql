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
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `location` varchar(255) DEFAULT NULL,
  `latitude` double DEFAULT NULL,
  `longitude` double DEFAULT NULL,
  `profile_image` varchar(255) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `user_group` enum('ADMIN','GENERAL') DEFAULT NULL,
  `locked_yn` char(1) NOT NULL DEFAULT 'N',
  `radius_km` double DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `chk_locked_yn` CHECK ((`locked_yn` in (_utf8mb4'Y',_utf8mb4'N')))
) ENGINE=InnoDB AUTO_INCREMENT=1021 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'John Doe','1','john.doe@example.com','서울 송파구 가락본동',37.495586073466,127.121767713047,'/profileImages/john.doe@example.com.jpg','2024-11-18 08:55:58','GENERAL','N',84),(2,'Jane Doe','1','jane.doe@example.com','서울 영등포구 당산동4가',37.5290653986881,126.899075891843,'/profileImages/jane.doe@example.com.jpg','2024-11-18 08:55:58','GENERAL','N',71),(3,'Jim Beam','1','jim.beam@example.com','서울 성동구 마조로9길',37.5612600745879,127.04039629329,'/profileImages/jim.beam@example.com.jpg','2024-11-18 08:55:58','GENERAL','N',92),(4,'Jill Valentine','1','jill.valentine@example.com','서울 영등포구 신풍로2길',37.4997815296946,126.907677983744,'/profileImages/jill.valentine@example.com.jpg','2024-11-18 08:55:58','GENERAL','N',20),(5,'Jack Reacher','1','jack.reacher@example.com','서울 영등포구 신길1동',37.5111452107739,126.921413655672,'/profileImages/jack.reacher@example.com.jpg','2024-11-18 08:55:58','GENERAL','N',25),(6,'김수현','11','rana0122@naver.com','서울특별시 서초구 양재동 227-1',37.4696261,127.033314,'/profileImages/rana0122@naver.com.jpg','2024-11-18 08:55:58','GENERAL','N',90),(7,'사용자1','1','user1@example.com','서울 송파구 송파1동',37.5062229082525,127.109316959721,'/profileImages/user1@example.com.jpg','2024-11-18 08:55:58','GENERAL','N',50),(8,'사용자2','1','user2@example.com','인천 남동구 논현1동',37.4056917351436,126.729318789009,'/profileImages/user2@example.com.jpg','2024-11-18 08:55:58','GENERAL','Y',50),(9,'사용자3','1','user3@example.com','인천 연수구 송도2동',37.4032746079109,126.641696771317,'/profileImages/user3@example.com.jpg','2024-11-18 08:55:58','GENERAL','N',50),(10,'사용자4','1','user4@example.com','서울 영등포구 신길1동',37.5111452107739,126.921413655672,'/profileImages/user4@example.com.jpg','2024-11-18 08:55:58','GENERAL','N',50),(11,'사용자5','1','user5@example.com','부산 영도구 영선동4가 1044-6',35.0779153529382,129.04528226002,'/profileImages/user5@example.com.jpg','2024-11-18 08:55:58','GENERAL','N',50),(12,'사용자6','1','user6@example.com','경기 성남시 중원구 성남동',37.4353291482246,127.138944267643,'/profileImages/user6@example.com.jpg','2024-11-18 08:55:58','GENERAL','N',50),(13,'사용자7','1','user7@example.com','서울특별시 서초구 양재동 227-1',37.4696261,127.033314,'/profileImages/user7@example.com.jpg','2024-11-18 08:55:58','GENERAL','N',50),(14,'사용자8','1','user8@example.com','경기 성남시 분당구 판교동',37.3898680691971,127.09878136729,'/profileImages/user8@example.com.jpg','2024-11-18 08:55:58','GENERAL','N',50),(15,'사용자9','1','user9@example.com','경기 평택시 고덕동',37.0495355509024,127.032124505836,'/profileImages/user9@example.com.jpg','2024-11-18 08:55:58','GENERAL','N',50),(16,'사용자10','1','user10@example.com','경기 용인시 처인구 역북동',37.2462035848036,127.188111660691,'/profileImages/user10@example.com.jpg','2024-11-18 08:55:58','GENERAL','Y',50),(17,'사용자11','1','user11@example.com','서울 마포구 아현동',37.5544867895968,126.955727151387,'/profileImages/user11@example.com.jpg','2024-11-18 08:55:58','GENERAL','N',50),(18,'사용자12','1','user12@example.com','서울 마포구 아현동',37.5544867895968,126.955727151387,'/profileImages/user12@example.com.jpg','2024-11-18 08:55:58','GENERAL','Y',50),(19,'사용자13','1','user13@example.com','서울 중구 약수동',37.5524332632787,127.009003709806,'/profileImages/user13@example.com.jpg','2024-11-18 08:55:58','GENERAL','Y',50),(20,'사용자14','1','user14@example.com','서울 서초구 방배1동',37.4833697664522,126.994491495079,'/profileImages/user14@example.com.jpg','2024-11-18 08:55:58','GENERAL','N',50),(21,'사용자15','1','user15@example.com','서울 금천구 독산2동',37.4661325181371,126.899775480394,'/profileImages/user15@example.com.jpg','2024-11-18 08:55:58','GENERAL','N',50),(1020,'관리자','1','admin@naver.com','대한민국 서울특별시 서초구 양재동 227-1',37.4696261,127.033314,'/profileImages/admin@naver.com.jpg',NULL,'ADMIN','N',50);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-12-03 17:01:34
