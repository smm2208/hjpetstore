-- This file isn't used by this project now,
-- because Hibernate will generate the DB Schema automatically.

use hjpetstore;

DROP TABLE IF EXISTS `hjpetstore`.`category`;
CREATE TABLE  `hjpetstore`.`category` (
  `categoryId` bigint(20) NOT NULL auto_increment,
  `version` int(11) NOT NULL,
  `categoryName` varchar(80) NOT NULL,
  `categoryDesc` varchar(255) default NULL,
  PRIMARY KEY  (`categoryId`),
  UNIQUE KEY `categoryName` (`categoryName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `hjpetstore`.`banner`;
CREATE TABLE  `hjpetstore`.`banner` (
  `bannerId` bigint(20) NOT NULL auto_increment,
  `version` int(11) NOT NULL,
  `favCategoryId` bigint(20) NOT NULL,
  `bannerName` varchar(255) default NULL,
  PRIMARY KEY  (`bannerId`),
  KEY `FK762A6B4C3B30784E` (`favCategoryId`),
  CONSTRAINT `FK762A6B4C3B30784E` FOREIGN KEY (`favCategoryId`) REFERENCES `category` (`categoryId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `hjpetstore`.`account`;
CREATE TABLE  `hjpetstore`.`account` (
  `accountId` bigint(20) NOT NULL auto_increment,
  `version` int(11) NOT NULL,
  `username` varchar(80) NOT NULL,
  `password` varchar(80) NOT NULL,
  `firstname` varchar(80) NOT NULL,
  `lastname` varchar(80) NOT NULL,
  `email` varchar(80) NOT NULL,
  `status` varchar(2) NOT NULL,
  `addr1` varchar(80) NOT NULL,
  `addr2` varchar(40) NOT NULL,
  `city` varchar(80) NOT NULL,
  `state` varchar(80) NOT NULL,
  `zipcode` varchar(20) NOT NULL,
  `country` varchar(20) NOT NULL,
  `phone` varchar(80) NOT NULL,
  `langPreference` varchar(80) NOT NULL,
  `favCategoryId` bigint(20) default NULL,
  `displayMylist` bit(1) default NULL,
  `displayBanner` bit(1) default NULL,
  PRIMARY KEY  (`accountId`),
  UNIQUE KEY `username` (`username`),
  KEY `FK_favCategoryId` (`favCategoryId`),
  CONSTRAINT `FK_favCategoryId` FOREIGN KEY (`favCategoryId`) REFERENCES `category` (`categoryId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `hjpetstore`.`product`;
CREATE TABLE  `hjpetstore`.`product` (
  `productId` bigint(20) NOT NULL auto_increment,
  `version` int(11) NOT NULL,
  `productNumber` varchar(10) NOT NULL,
  `categoryId` bigint(20) NOT NULL,
  `productName` varchar(80) default NULL,
  `productDesc` varchar(255) default NULL,
  PRIMARY KEY  (`productId`),
  UNIQUE KEY `productNumber` (`productNumber`),
  KEY `FK50C664CFA741D2F3` (`categoryId`),
  CONSTRAINT `FK50C664CFA741D2F3` FOREIGN KEY (`categoryId`) REFERENCES `category` (`categoryId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `hjpetstore`.`supplier`;
CREATE TABLE  `hjpetstore`.`supplier` (
  `supplierId` bigint(20) NOT NULL auto_increment,
  `version` int(11) NOT NULL,
  `supplierName` varchar(80) NOT NULL,
  `status` varchar(2) NOT NULL,
  `addr1` varchar(80) default NULL,
  `addr2` varchar(80) default NULL,
  `city` varchar(80) default NULL,
  `state` varchar(80) default NULL,
  `zipcode` varchar(20) default NULL,
  `country` varchar(20) default NULL,
  `phone` varchar(80) NOT NULL,
  PRIMARY KEY  (`supplierId`),
  UNIQUE KEY `supplierName` (`supplierName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `hjpetstore`.`orders`;
CREATE TABLE  `hjpetstore`.`orders` (
  `orderId` bigint(20) NOT NULL auto_increment,
  `version` int(11) NOT NULL,
  `userId` bigint(20) NOT NULL,
  `orderDate` datetime NOT NULL,
  `shipAddr1` varchar(80) NOT NULL,
  `shipAddr2` varchar(80) NOT NULL,
  `shipCity` varchar(80) NOT NULL,
  `shipState` varchar(80) NOT NULL,
  `shipZipcode` varchar(20) NOT NULL,
  `shipCountry` varchar(20) NOT NULL,
  `billAddr1` varchar(80) NOT NULL,
  `billAddr2` varchar(40) NOT NULL,
  `billCity` varchar(80) NOT NULL,
  `billState` varchar(80) NOT NULL,
  `billZipcode` varchar(20) NOT NULL,
  `billCountry` varchar(20) NOT NULL,
  `courier` varchar(80) NOT NULL,
  `totalPrice` decimal(10,2) default NULL,
  `billToFirstname` varchar(80) NOT NULL,
  `billToLastname` varchar(80) NOT NULL,
  `shipToFirstname` varchar(80) NOT NULL,
  `shipToLastname` varchar(80) NOT NULL,
  `cardNumber` varchar(80) NOT NULL,
  `expireDate` varchar(7) NOT NULL,
  `cardType` varchar(80) NOT NULL,
  `shipStatus` varchar(20) NOT NULL,
  `locale` varchar(5) NOT NULL,
  PRIMARY KEY  (`orderId`),
  KEY `FK8D444F05BC594D77` (`userId`),
  CONSTRAINT `FK8D444F05BC594D77` FOREIGN KEY (`userId`) REFERENCES `account` (`accountId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `hjpetstore`.`item`;
CREATE TABLE  `hjpetstore`.`item` (
  `itemId` bigint(20) NOT NULL auto_increment,
  `version` int(11) NOT NULL,
  `itemName` varchar(10) NOT NULL,
  `productId` bigint(20) NOT NULL,
  `listPrice` decimal(10,2) default NULL,
  `unitCost` decimal(10,2) default NULL,
  `supplierId` bigint(20) default NULL,
  `status` varchar(2) default NULL,
  `attr1` varchar(80) default NULL,
  `attr2` varchar(80) default NULL,
  `attr3` varchar(80) default NULL,
  `attr4` varchar(80) default NULL,
  `attr5` varchar(80) default NULL,
  PRIMARY KEY  (`itemId`),
  UNIQUE KEY `itemName` (`itemName`),
  KEY `FK_productId` (`productId`),
  KEY `FK22EF33C998510F` (`supplierId`),
  CONSTRAINT `FK22EF33C998510F` FOREIGN KEY (`supplierId`) REFERENCES `supplier` (`supplierId`),
  CONSTRAINT `FK_productId` FOREIGN KEY (`productId`) REFERENCES `product` (`productId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `hjpetstore`.`lineitem`;
CREATE TABLE  `hjpetstore`.`lineitem` (
  `lineItemId` bigint(20) NOT NULL auto_increment,
  `version` int(11) NOT NULL,
  `quantity` int(11) NOT NULL,
  `lineNumber` int(11) NOT NULL,
  `orderId` bigint(20) NOT NULL,
  `itemId` bigint(20) NOT NULL,
  `lineItemPosition` int(11) default NULL,
  PRIMARY KEY  (`lineItemId`),
  KEY `FK4AAEE94796902BDD` (`itemId`),
  KEY `FK4AAEE94775B30A5B` (`orderId`),
  CONSTRAINT `FK4AAEE94775B30A5B` FOREIGN KEY (`orderId`) REFERENCES `orders` (`orderId`),
  CONSTRAINT `FK4AAEE94796902BDD` FOREIGN KEY (`itemId`) REFERENCES `item` (`itemId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `hjpetstore`.`inventory`;
CREATE TABLE  `hjpetstore`.`inventory` (
  `InventoryId` bigint(20) NOT NULL,
  `version` int(11) NOT NULL,
  `dateAdded` datetime NOT NULL,
  `quantity` int(11) NOT NULL,
  PRIMARY KEY  (`InventoryId`),
  KEY `FKFF02393CC0C05BE6` (`InventoryId`),
  CONSTRAINT `FKFF02393CC0C05BE6` FOREIGN KEY (`InventoryId`) REFERENCES `item` (`itemId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

commit;
