/*
SQLyog Community v13.1.6 (64 bit)
MySQL - 8.0.22 : Database - atp-tour
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`atp-tour` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `atp-tour`;

/*Table structure for table `country` */

DROP TABLE IF EXISTS `country`;

CREATE TABLE `country` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `code_name` varchar(3) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_qjaglmkyk81ngsnu798q1snsm` (`code_name`),
  UNIQUE KEY `UK_llidyp77h6xkeokpbmoy710d4` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=218 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `country` */

insert  into `country`(`id`,`name`,`code_name`) values 
(1,'Afghanistan','AFG'),
(2,'Aland Islands','ALA'),
(3,'Albania','ALB'),
(4,'Algeria','DZA'),
(5,'American Samoa','ASM'),
(6,'Andorra','AND'),
(7,'Angola','AGO'),
(8,'Anguilla','AIA'),
(9,'Antarctica','ATA'),
(10,'Antigua and Barbuda','ATG'),
(11,'Argentina','ARG'),
(12,'Armenia','ARM'),
(13,'Aruba','ABW'),
(14,'Australia','AUS'),
(15,'Austria','AUT'),
(16,'Azerbaijan','AZE'),
(17,'Bahamas','BHS'),
(18,'Bahrain','BHR'),
(19,'Bangladesh','BGD'),
(20,'Barbados','BRB'),
(21,'Belarus','BLR'),
(22,'Belgium','BEL'),
(23,'Belize','BLZ'),
(24,'Benin','BEN'),
(25,'Bermuda','BMU'),
(26,'Bhutan','BTN'),
(27,'Bolivia','BOL'),
(28,'Bosnia and Herzegovina','BIH'),
(29,'Botswana','BWA'),
(30,'Bouvet Island','BVT'),
(31,'Brazil','BRA'),
(32,'Brunei','BRN'),
(33,'Bulgaria','BGR'),
(34,'Burkina Faso','BFA'),
(35,'Burundi','BDI'),
(36,'Cambodia','KHM'),
(37,'Cameroon','CMR'),
(38,'Canada','CAN'),
(39,'Cape Verde','CPV'),
(40,'Cayman Islands','CYM'),
(41,'Central African Republic','CAF'),
(42,'Chad','TCD'),
(43,'Chile','CHL'),
(44,'China','CHN'),
(45,'Christmas Island','CXR'),
(46,'Colombia','COL'),
(47,'Comoros','COM'),
(48,'Congo','COG'),
(49,'Costa Rica','CRI'),
(50,'Ivory Coast','CIV'),
(51,'Croatia','HRV'),
(52,'Cuba','CUB'),
(53,'Cyprus','CYP'),
(54,'Czech Republic','CZE'),
(55,'Democratic Republic of the Congo','COD'),
(56,'Denmark','DNK'),
(57,'Djibouti','DJI'),
(58,'Dominica','DMA'),
(59,'Dominican Republic','DOM'),
(60,'Ecuador','ECU'),
(61,'Egypt','EGY'),
(62,'El Salvador','SLV'),
(63,'Equatorial Guinea','GNQ'),
(64,'Eritrea','ERI'),
(65,'Estonia','EST'),
(66,'Ethiopia','ETH'),
(67,'Falkland Islands (Malvinas)','FLK'),
(68,'Faroe Islands','FRO'),
(69,'Fiji','FJI'),
(70,'Finland','FIN'),
(71,'France','FRA'),
(72,'French Guiana','GUF'),
(73,'Gabon','GAB'),
(74,'Gambia','GMB'),
(75,'Georgia','GEO'),
(76,'Germany','DEU'),
(77,'Ghana','GHA'),
(78,'Gibraltar','GIB'),
(79,'Greece','GRC'),
(80,'Greenland','GRL'),
(81,'Grenada','GRD'),
(82,'Guatemala','GTM'),
(83,'Guinea','GIN'),
(84,'Guinea-Bissau','GNB'),
(85,'Guyana','GUY'),
(86,'Haiti','HTI'),
(87,'Honduras','HND'),
(88,'Hong Kong','HKG'),
(89,'Hungary','HUN'),
(90,'Iceland','ISL'),
(91,'India','IND'),
(92,'Indonesia','IDN'),
(93,'Iran','IRN'),
(94,'Iraq','IRQ'),
(95,'Ireland','IRL'),
(96,'Israel','ISR'),
(97,'Italy','ITA'),
(98,'Jamaica','JAM'),
(99,'Japan','JPN'),
(100,'Jordan','JOR'),
(101,'Kazakhstan','KAZ'),
(102,'Kenya','KEN'),
(103,'Kiribati','KIR'),
(104,'Kuwait','KWT'),
(105,'Kyrgyzstan','KGZ'),
(106,'Laos','LAO'),
(107,'Latvia','LVA'),
(108,'Lebanon','LBN'),
(109,'Lesotho','LSO'),
(110,'Liberia','LBR'),
(111,'Libya','LBY'),
(112,'Liechtenstein','LIE'),
(113,'Lithuania','LTU'),
(114,'Luxembourg','LUX'),
(115,'Macao','MAC'),
(116,'Macedonia','MKD'),
(117,'Madagascar','MDG'),
(118,'Malawi','MWI'),
(119,'Malaysia','MYS'),
(120,'Maldives','MDV'),
(121,'Mali','MLI'),
(122,'Malta','MLT'),
(123,'Marshall Islands','MHL'),
(124,'Mauritania','MRT'),
(125,'Mauritius','MUS'),
(126,'Mexico','MEX'),
(127,'Micronesia','FSM'),
(128,'Moldava','MDA'),
(129,'Monaco','MCO'),
(130,'Mongolia','MNG'),
(131,'Montenegro','MNE'),
(132,'Morocco','MAR'),
(133,'Mozambique','MOZ'),
(134,'Myanmar (Burma)','MMR'),
(135,'Namibia','NAM'),
(136,'Nauru','NRU'),
(137,'Nepal','NPL'),
(138,'Netherlands','NLD'),
(139,'New Zealand','NZL'),
(140,'Nicaragua','NIC'),
(141,'Niger','NER'),
(142,'Nigeria','NGA'),
(143,'Niue','NIU'),
(144,'North Korea','PRK'),
(145,'Norway','NOR'),
(146,'Oman','OMN'),
(147,'Pakistan','PAK'),
(148,'Palau','PLW'),
(149,'Palestine','PSE'),
(150,'Panama','PAN'),
(151,'Papua New Guinea','PNG'),
(152,'Paraguay','PRY'),
(153,'Peru','PER'),
(154,'Phillipines','PHL'),
(155,'Poland','POL'),
(156,'Portugal','PRT'),
(157,'Puerto Rico','PRI'),
(158,'Qatar','QAT'),
(159,'Romania','ROU'),
(160,'Russia','RUS'),
(161,'Rwanda','RWA'),
(162,'Saint Helena','SHN'),
(163,'Saint Kitts and Nevis','KNA'),
(164,'Saint Lucia','LCA'),
(165,'Saint Martin','MAF'),
(166,'Saint Vincent and the Grenadines','VCT'),
(167,'Samoa','WSM'),
(168,'San Marino','SMR'),
(169,'Sao Tome and Principe','STP'),
(170,'Saudi Arabia','SAU'),
(171,'Senegal','SEN'),
(172,'Serbia','SRB'),
(173,'Seychelles','SYC'),
(174,'Sierra Leone','SLE'),
(175,'Singapore','SGP'),
(176,'Sint Maarten','SXM'),
(177,'Slovakia','SVK'),
(178,'Slovenia','SVN'),
(179,'Solomon Islands','SLB'),
(180,'Somalia','SOM'),
(181,'South Africa','ZAF'),
(182,'South Korea','KOR'),
(183,'South Sudan','SSD'),
(184,'Spain','ESP'),
(185,'Sri Lanka','LKA'),
(186,'Sudan','SDN'),
(187,'Suriname','SUR'),
(188,'Swaziland','SWZ'),
(189,'Sweden','SWE'),
(190,'Switzerland','CHE'),
(191,'Syria','SYR'),
(192,'Taiwan','TWN'),
(193,'Tajikistan','TJK'),
(194,'Tanzania','TZA'),
(195,'Thailand','THA'),
(196,'Timor-Leste (East Timor)','TLS'),
(197,'Togo','TGO'),
(198,'Tonga','TON'),
(199,'Trinidad and Tobago','TTO'),
(200,'Tunisia','TUN'),
(201,'Turkey','TUR'),
(202,'Turkmenistan','TKM'),
(203,'Tuvalu','TUV'),
(204,'Uganda','UGA'),
(205,'Ukraine','UKR'),
(206,'United Arab Emirates','ARE'),
(207,'United Kingdom','GBR'),
(208,'United States','USA'),
(209,'Uruguay','URY'),
(210,'Uzbekistan','UZB'),
(211,'Vanuatu','VUT'),
(212,'Vatican City','VAT'),
(213,'Venezuela','VEN'),
(214,'Vietnam','VNM'),
(215,'Yemen','YEM'),
(216,'Zambia','ZMB'),
(217,'Zimbabwe','ZWE');

/*Table structure for table `hibernate_sequence` */

DROP TABLE IF EXISTS `hibernate_sequence`;

CREATE TABLE `hibernate_sequence` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `hibernate_sequence` */

/*Table structure for table `income` */

DROP TABLE IF EXISTS `income`;

CREATE TABLE `income` (
  `tournament_id` bigint NOT NULL,
  `player_id` bigint NOT NULL,
  `points` int NOT NULL,
  PRIMARY KEY (`player_id`,`tournament_id`),
  KEY `FK2qsav6jc4ec82j2le8wqw8ao2` (`tournament_id`),
  CONSTRAINT `FK2qsav6jc4ec82j2le8wqw8ao2` FOREIGN KEY (`tournament_id`) REFERENCES `tournament` (`id`) ON DELETE CASCADE,
  CONSTRAINT `FKfn93de2vu5i82ktmk5mafd4v0` FOREIGN KEY (`player_id`) REFERENCES `player` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `income` */

/*Table structure for table `matches` */

DROP TABLE IF EXISTS `matches`;

CREATE TABLE `matches` (
  `tournament_id` bigint NOT NULL,
  `first_player_id` bigint NOT NULL,
  `second_player_id` bigint NOT NULL,
  `match_date` date DEFAULT NULL,
  `round` varchar(20) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `result` varchar(5) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `winner` bigint DEFAULT NULL,
  PRIMARY KEY (`first_player_id`,`second_player_id`,`tournament_id`),
  KEY `FK6u6jn45m2juuk50mg6hxn71p7` (`tournament_id`),
  KEY `FKewgwnebofvc7qlgsmcl857pej` (`second_player_id`),
  KEY `FKkppbgwpkxnl87rwghjt0ixul` (`winner`),
  CONSTRAINT `FK6u6jn45m2juuk50mg6hxn71p7` FOREIGN KEY (`tournament_id`) REFERENCES `tournament` (`id`) ON DELETE CASCADE,
  CONSTRAINT `FKbrm5ntcktw2k6gv0d34g7kda8` FOREIGN KEY (`first_player_id`) REFERENCES `player` (`id`) ON DELETE CASCADE,
  CONSTRAINT `FKewgwnebofvc7qlgsmcl857pej` FOREIGN KEY (`second_player_id`) REFERENCES `player` (`id`) ON DELETE CASCADE,
  CONSTRAINT `FKkppbgwpkxnl87rwghjt0ixul` FOREIGN KEY (`winner`) REFERENCES `player` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `matches` */

/*Table structure for table `player` */

DROP TABLE IF EXISTS `player`;

CREATE TABLE `player` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `first_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `last_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `birth_country` bigint DEFAULT NULL,
  `date_of_birth` date DEFAULT NULL,
  `current_points` int NOT NULL,
  `live_points` int NOT NULL,
  `player_rank` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK6ggkfjebbcngyeuqld687u86w` (`birth_country`),
  CONSTRAINT `FK6ggkfjebbcngyeuqld687u86w` FOREIGN KEY (`birth_country`) REFERENCES `country` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `player` */

/*Table structure for table `role` */

DROP TABLE IF EXISTS `role`;

CREATE TABLE `role` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(15) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_8sewwnpamngi6b1dwaa88askk` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `role` */

insert  into `role`(`id`,`name`) values 
(1,'ADMIN'),
(2,'USER');

/*Table structure for table `statistics` */

DROP TABLE IF EXISTS `statistics`;

CREATE TABLE `statistics` (
  `id` bigint NOT NULL,
  `tournament_id` bigint NOT NULL,
  `first_player_id` bigint NOT NULL,
  `second_player_id` bigint NOT NULL,
  `first_player_points` int NOT NULL,
  `second_player_points` int NOT NULL,
  `first_player_aces` int NOT NULL,
  `second_player_aces` int NOT NULL,
  `first_player_break_points` int NOT NULL,
  `second_player_break_points` int NOT NULL,
  `first_player_first_serves_in` int NOT NULL,
  `second_player_first_serves_in` int NOT NULL,
  `first_player_second_serves_in` int NOT NULL,
  `second_player_second_serves_in` int NOT NULL,
  PRIMARY KEY (`id`,`first_player_id`,`second_player_id`,`tournament_id`),
  UNIQUE KEY `UK_2dkacp2voh933clsp1xfd07do` (`first_player_id`,`second_player_id`,`tournament_id`),
  CONSTRAINT `FKtep0401cv1oal6nhij1upa0d9` FOREIGN KEY (`first_player_id`, `second_player_id`, `tournament_id`) REFERENCES `matches` (`first_player_id`, `second_player_id`, `tournament_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `statistics` */

/*Table structure for table `tournament` */

DROP TABLE IF EXISTS `tournament`;

CREATE TABLE `tournament` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `start_date` date DEFAULT NULL,
  `completion_date` date DEFAULT NULL,
  `host_country` bigint DEFAULT NULL,
  `tournament_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_hswi99dqfqk68pblbp3xorngk` (`name`),
  KEY `FK2nlm8g0q6vv838mrsrm16biux` (`host_country`),
  CONSTRAINT `FK2nlm8g0q6vv838mrsrm16biux` FOREIGN KEY (`host_country`) REFERENCES `country` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `tournament` */

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `first_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `last_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `username` varchar(30) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `password` varchar(60) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `enabled` bit(1) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_sb8bbouer5wak8vyiiy4pf2bx` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `user` */

insert  into `user`(`id`,`first_name`,`last_name`,`username`,`password`,`enabled`) values 
(1,'John','Doe','admin@admin.com','$2a$10$ESmmbPIEDzR4zQiILof5A.ri5kBJ6WjI2exWkBce1qWyNoqcfdmfm','');

/*Table structure for table `users_roles` */

DROP TABLE IF EXISTS `users_roles`;

CREATE TABLE `users_roles` (
  `user_id` bigint NOT NULL,
  `role_id` bigint NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `FKt4v0rrweyk393bdgt107vdx0x` (`role_id`),
  CONSTRAINT `FKgd3iendaoyh04b95ykqise6qh` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE,
  CONSTRAINT `FKt4v0rrweyk393bdgt107vdx0x` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `users_roles` */

insert  into `users_roles`(`user_id`,`role_id`) values 
(1,1);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
