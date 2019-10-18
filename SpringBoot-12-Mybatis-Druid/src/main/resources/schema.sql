
DROP TABLE IF EXISTS `t_city`;

CREATE TABLE `t_city` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `city_name` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;