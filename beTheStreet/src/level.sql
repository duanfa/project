DROP TABLE IF EXISTS `level`;

CREATE TABLE `level` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `bonus_stage` int(11) NOT NULL,
  `completeNum` int(11) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `desc2` varchar(255) DEFAULT NULL,
  `greenRatio` int(11) NOT NULL,
  `isChallenge` tinyint(1) NOT NULL,
  `level` int(11) NOT NULL,
  `path` varchar(255) DEFAULT NULL,
  `redRatio` int(11) NOT NULL,
  `regular_stage` int(11) NOT NULL,
  `shortdesc` varchar(255) DEFAULT NULL,
  `shortdesc2` varchar(255) DEFAULT NULL,
  `sumcoins` int(11) NOT NULL,
  `thumbnail_path` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `title2` varchar(255) DEFAULT NULL,
  `yellowRatio` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

LOCK TABLES `level` WRITE;
/*!40000 ALTER TABLE `level` DISABLE KEYS */;
INSERT INTO `level` VALUES (1,1,15,'Search Street View to locate your favorite hangout spot. Then submit a screenshot.','Snap and send in a photo of friends hangin\' out.',80,0,1,NULL,5,1,'Hangout Spot','Hangout Photo',5,NULL,'Level 1','Level 1 Challenge',15),(2,1,7,'Search Street View to find 2 trash cans or recycle bins.','How\'s your aim? Send in a photo of trash as it is flying into a trashcan or recycle bin.',80,0,2,NULL,5,2,'Trash Cans','Flying Trash',10,NULL,'Level 2','Level 2 Challenge',15),(3,1,2,'Search Street View to find 3 storm drains','Storm drain say what? Send in a photo of you or a friend pointing to the message printed on any Bay Area storm drain.',80,0,3,NULL,5,3,'Storm Drains','Storm Drain',15,NULL,'Level 3','Level 3 Challenge',15),(4,1,1,'Search Street View to find 3 shops that sell either used or reusable stuff','Trash or treasure? Send in a photo of an old object being re-used in a new or different way.',80,0,4,NULL,5,2,'Re-use Shops','Creative Re-use',20,NULL,'Level 4','Level 4 Challenge',15),(5,1,1,'Search Street View to find a group of 3 or more people working together','Join or create a group and compete against rival groups to earn real life prizes.',80,0,5,NULL,5,1,'Teamwork','Join a Group',25,NULL,'Level 5','Level 5 Challenge',15),(6,1,0,'Do you know what is the single most commonly littered item in the world? Send us a photo of one',NULL,80,1,6,NULL,5,0,'Most Littered',NULL,30,NULL,'Trivia Challenge 1',NULL,15);
/*!40000 ALTER TABLE `level` ENABLE KEYS */;
UNLOCK TABLES;
