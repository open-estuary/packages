/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : ec

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2017-09-18 18:15:54
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for r_ec_brand
-- ----------------------------
DROP TABLE IF EXISTS `r_ec_brand`;
CREATE TABLE `r_ec_brand` (
  `nBrandID` int(64) NOT NULL AUTO_INCREMENT COMMENT '品牌ID',
  `sBrandName` varchar(64) NOT NULL COMMENT '品牌名称',
  PRIMARY KEY (`nBrandID`),
  KEY `PRIMARY_nBrandID` (`nBrandID`) USING BTREE COMMENT '使用品牌id作为主键索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='品牌信息';

-- ----------------------------
-- Table structure for r_ec_cartsku
-- ----------------------------
DROP TABLE IF EXISTS `r_ec_cartsku`;
CREATE TABLE `r_ec_cartsku` (
  `nUserID` int(64) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `nProductID` int(64) NOT NULL,
  `nQuantity` int(64) NOT NULL,
  PRIMARY KEY (`nUserID`,`nProductID`),
  KEY `PRIMARY_nUserID` (`nUserID`) USING BTREE COMMENT '使用用户id作为主键索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='购物车SKU';

-- ----------------------------
-- Table structure for r_ec_category
-- ----------------------------
DROP TABLE IF EXISTS `r_ec_category`;
CREATE TABLE `r_ec_category` (
  `nCategoryID` int(64) NOT NULL AUTO_INCREMENT COMMENT '商品种类ID',
  `nLevel` int(64) NOT NULL COMMENT '几级类目，从一级类目开始',
  `nParentCategoryID` int(64) NOT NULL COMMENT '当前商品类目所在的父类目，如果为0表示当前为一级类目。',
  `sCategoryName` varchar(64) NOT NULL COMMENT '商品种类',
  PRIMARY KEY (`nCategoryID`),
  KEY `PRIMARY_nCategoryID` (`nCategoryID`) USING BTREE COMMENT '使用分类id作为主键索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品分类';

-- ----------------------------
-- Table structure for r_ec_deliveryinfo
-- ----------------------------
DROP TABLE IF EXISTS `r_ec_deliveryinfo`;
CREATE TABLE `r_ec_deliveryinfo` (
  `sDeliveryID` varchar(64) NOT NULL COMMENT '配送编号',
  `sExpressCompany` varchar(32) DEFAULT NULL COMMENT '配送公司',
  `nDeliveryPrice` decimal(10,2) NOT NULL COMMENT '配送价格',
  `cStatus` enum('4','3','2','1') NOT NULL DEFAULT '1' COMMENT '配送状态:1：已收件;2：在途;3：待签收;4：已签收',
  PRIMARY KEY (`sDeliveryID`),
  KEY `PRIMARY_sDeliveryID` (`sDeliveryID`) USING BTREE COMMENT ' 使用配送编号作为主键索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='配送信息';

-- ----------------------------
-- Table structure for r_ec_image
-- ----------------------------
DROP TABLE IF EXISTS `r_ec_image`;
CREATE TABLE `r_ec_image` (
  `nImageID` int(64) NOT NULL AUTO_INCREMENT,
  `nPosition` int(64) DEFAULT NULL,
  `nAttachment_content_type` enum('1','0') DEFAULT '0' COMMENT '附件类型：0：image；1：jpg',
  `sAttachment_file_name` varchar(32) DEFAULT NULL COMMENT '附件名称',
  `sType` varchar(32) DEFAULT NULL,
  `sAttachment_updated_at` datetime DEFAULT NULL,
  `nAttachment_width` int(32) DEFAULT NULL COMMENT '附件宽度',
  `nAttachment_height` int(32) DEFAULT NULL COMMENT '附件长度',
  `sAlt` varchar(32) DEFAULT NULL,
  `sViewable_type` varchar(32) DEFAULT NULL,
  `sMini_url` varchar(32) DEFAULT NULL COMMENT 'mini图片的url',
  `sSmall_url` varchar(32) DEFAULT NULL COMMENT 'small图片的url',
  `sLarge_url` varchar(32) DEFAULT NULL COMMENT 'big图片的url',
  `sProduct_url` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`nImageID`),
  KEY `PRIMARY_nImageID` (`nImageID`) USING BTREE COMMENT '使用image的id作为主键索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='图片附件信息';

-- ----------------------------
-- Table structure for r_ec_orderinfo
-- ----------------------------
DROP TABLE IF EXISTS `r_ec_orderinfo`;
CREATE TABLE `r_ec_orderinfo` (
  `sOrderID` varchar(64) NOT NULL COMMENT '订单ID\r\n由系统自动生成，生成订单编号时包含日期.\r\n',
  `nUserID` int(64) NOT NULL COMMENT '下单人。\r\n关联R_EC_UserInfo表获取到需要显示的用户信息\r\n',
  `sParentOrderID` varchar(64) DEFAULT NULL COMMENT '父订单\r\n默认为空\r\n',
  `cPaymentMethod` enum('3','2','1','0') DEFAULT '0' COMMENT '付款方式:0：信用支付;1：货到付款;2：银行卡支付;3：白条',
  `sPaymentMethodTitle` varchar(255) DEFAULT NULL COMMENT '付款方式描述',
  `nDiscount` float DEFAULT NULL COMMENT '折扣',
  `nTotalQuantity` int(64) NOT NULL COMMENT '总数量',
  `nTotalPrice` decimal(10,2) NOT NULL COMMENT '总价',
  `sVersion` varchar(64) DEFAULT NULL COMMENT '订单version',
  `nAddressNo` int(64) DEFAULT NULL COMMENT '收货地址。\r\n关联到R_EC_UserDeliveryAddress获取收货地址\r\n',
  `cStatus` enum('4','3','2','1','0') NOT NULL DEFAULT '0' COMMENT '订单状态:0：未支付;1：已支付未发货;2：已发货;3：已接收;4：已关闭',
  `sDate_created` datetime NOT NULL COMMENT '订单创建时间\r\n格式:YYYY-MM-DD HH:MM:SS\r\n',
  `sDate_Modified` datetime NOT NULL COMMENT '订单修改时间\r\n格式:YYYY-MM-DD HH:MM:SS\r\n',
  `sDate_Paid` datetime DEFAULT NULL COMMENT '订单支付时间\r\n格式:YYYY-MM-DD HH:MM:SS\r\n',
  `sDate_Completed` datetime DEFAULT NULL COMMENT '订单完成时间',
  `sCustomerMark` varchar(255) DEFAULT NULL COMMENT '用户留言',
  `sDeliveryID` varchar(64) DEFAULT NULL COMMENT '配送编号。\r\n定义为索引\r\n',
  PRIMARY KEY (`sOrderID`),
  KEY `PRIMAEY_sOrderID` (`sOrderID`) USING BTREE COMMENT '使用订单id作为主键索引',
  KEY `PRIMARY_nUserID` (`nUserID`) USING BTREE COMMENT '使用用户id作为主键索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单信息';

-- ----------------------------
-- Table structure for r_ec_ordersku
-- ----------------------------
DROP TABLE IF EXISTS `r_ec_ordersku`;
CREATE TABLE `r_ec_ordersku` (
  `sOrderID` varchar(64) NOT NULL COMMENT '订单ID',
  `nSKUID` int(64) NOT NULL COMMENT '商品ID',
  `nQuantity` int(64) NOT NULL COMMENT '数量',
  `nOrigPrice` decimal(10,2) NOT NULL COMMENT '商品原价',
  `nDiscount` float DEFAULT NULL COMMENT '折扣',
  `sCurrency` varchar(16) DEFAULT NULL COMMENT '货币类型',
  `nPrice` decimal(10,2) NOT NULL COMMENT '销售价格',
  PRIMARY KEY (`sOrderID`,`nSKUID`),
  KEY `PRIMARY_sOrderID` (`sOrderID`) USING BTREE COMMENT '使用订单id作为主键索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单SKU';

-- ----------------------------
-- Table structure for r_ec_shoppingcart
-- ----------------------------
DROP TABLE IF EXISTS `r_ec_shoppingcart`;
CREATE TABLE `r_ec_shoppingcart` (
  `nUserID` int(64) NOT NULL COMMENT ' 用户id',
  `sUpdateTime` datetime NOT NULL COMMENT '附件更新时间\r\n格式：YYYY-MM-DD HH:MM:SS\r\n',
  `nDiscount` float DEFAULT NULL COMMENT '折扣',
  `nTotalQuantity` int(64) NOT NULL COMMENT '总数量',
  `sCurrency` varchar(16) DEFAULT NULL COMMENT '货币类型',
  `nTotalPrice` decimal(10,2) NOT NULL COMMENT '总价',
  PRIMARY KEY (`nUserID`),
  KEY `PRIMARY_nUserID` (`nUserID`) USING BTREE COMMENT '使用用户id作为主键索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='购物车';

-- ----------------------------
-- Table structure for r_ec_sku
-- ----------------------------
DROP TABLE IF EXISTS `r_ec_sku`;
CREATE TABLE `r_ec_sku` (
  `nSPUID` int(64) DEFAULT NULL,
  `nSKUID` int(64) NOT NULL COMMENT '商品id',
  `nColor` varchar(16) DEFAULT NULL COMMENT '颜色',
  `sSize` varchar(16) DEFAULT NULL COMMENT '尺寸',
  `nPrice` decimal(10,2) NOT NULL COMMENT '商品价格',
  `nDisplay_price` decimal(10,2) NOT NULL COMMENT '商品展示价格',
  `nInventory` int(64) NOT NULL COMMENT '库存数量',
  `nDiscount` float DEFAULT NULL COMMENT '折扣',
  `sCurrency` varchar(16) DEFAULT NULL COMMENT '货币类型',
  PRIMARY KEY (`nSKUID`),
  KEY `PRIMARY_nProductID` (`nSKUID`) USING BTREE COMMENT '使用产品id作为主键索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for r_ec_spu
-- ----------------------------
DROP TABLE IF EXISTS `r_ec_spu`;
CREATE TABLE `r_ec_spu` (
  `nSPUID` int(64) NOT NULL AUTO_INCREMENT COMMENT '商品ID',
  `sSPUName` varchar(64) NOT NULL COMMENT '商品名称',
  `sAvailable_on` varchar(20) DEFAULT NULL,
  `slug` varchar(128) DEFAULT NULL,
  `sMeta_description` varchar(255) DEFAULT NULL,
  `sMeta_keywords` varchar(255) DEFAULT NULL,
  `nCategoryID` int(64) NOT NULL COMMENT '商品种类ID',
  `nBrandID` int(64) DEFAULT NULL COMMENT '品牌ID',
  `nImageID` int(64) DEFAULT NULL COMMENT 'Image ID',
  `sDescription` varchar(255) DEFAULT NULL COMMENT '描述信息',
  PRIMARY KEY (`nSPUID`),
  KEY `PRIMARY_nProductID` (`nSPUID`) USING BTREE COMMENT ' 使用产品id作为主键索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品信息';

-- ----------------------------
-- Table structure for r_ec_userbankinfo
-- ----------------------------
DROP TABLE IF EXISTS `r_ec_userbankinfo`;
CREATE TABLE `r_ec_userbankinfo` (
  `nUserID` int(64) NOT NULL COMMENT '用户id',
  `sBank` enum('3','2','1','0') NOT NULL DEFAULT '0' COMMENT '绑定卡对应的银行。0：中国银行;1：农业银行;2：工商银行;3：建设银行...',
  `sCardType` enum('1','2') NOT NULL DEFAULT '1' COMMENT '银行卡种类。1：借记卡;2：信用卡',
  `sCardNumber` varchar(32) NOT NULL COMMENT ' 银行卡号',
  `sCurrency` varchar(16) DEFAULT NULL COMMENT '货币种类',
  PRIMARY KEY (`sCardNumber`),
  KEY `PRIMARY_nUserID` (`nUserID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户银行卡信息';

-- ----------------------------
-- Table structure for r_ec_userdeliveryaddress
-- ----------------------------
DROP TABLE IF EXISTS `r_ec_userdeliveryaddress`;
CREATE TABLE `r_ec_userdeliveryaddress` (
  `nUserID` int(64) NOT NULL COMMENT '用户ID',
  `nAddressNo` int(64) NOT NULL COMMENT '用户地址编号',
  `sFirstName` varchar(32) DEFAULT '' COMMENT '收件人First Name',
  `sLastName` varchar(32) DEFAULT '' COMMENT '收件人Last Name',
  `sAddress` varchar(128) NOT NULL COMMENT '收货地址',
  `sCity` varchar(16) DEFAULT '' COMMENT '城市',
  `sProvince` varchar(16) DEFAULT '' COMMENT '省份',
  `sCountry` varchar(16) DEFAULT '' COMMENT '国家',
  `sEmail` varchar(16) DEFAULT '' COMMENT '收件人邮箱地址',
  `sPhoneNo` varchar(16) NOT NULL DEFAULT '' COMMENT '收货地手机号',
  PRIMARY KEY (`nAddressNo`),
  KEY `PRIMARY_nUserID` (`nUserID`) USING BTREE COMMENT '使用用户id作为主键索引',
  KEY `PRIMARY_sEmail` (`sEmail`) USING BTREE COMMENT '使用用户email作为主键索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户配送地址';

-- ----------------------------
-- Table structure for r_ec_userinfo
-- ----------------------------
DROP TABLE IF EXISTS `r_ec_userinfo`;
CREATE TABLE `r_ec_userinfo` (
  `nUserID` int(64) NOT NULL AUTO_INCREMENT COMMENT '用户ID，由系统自动生成',
  `sLoginName` varchar(32) NOT NULL DEFAULT '' COMMENT '登陆名,不能为空，并且不能重复',
  `sLoginPassword` varchar(32) NOT NULL DEFAULT '' COMMENT '登陆密码\r\n密码需要加密后保存\r\n',
  `sFirstName` varchar(32) DEFAULT '' COMMENT '用户First Name',
  `sLastName` varchar(32) DEFAULT '' COMMENT '用户Last Name',
  `sPhoneNo` varchar(16) DEFAULT '' COMMENT '手机号码\r\n格式：13800138000\r\n       (021)12345678\r\n       (0086)13800138000\r\n',
  `sEmailAddress` varchar(32) DEFAULT '' COMMENT '电子邮箱',
  `cGender` enum('2','1','0') DEFAULT '1' COMMENT '性别\r\n0：男\r\n1：女\r\n2：其它\r\n',
  `sBirthday` date DEFAULT NULL COMMENT '生日\r\n格式：YYYY-MM-DD\r\n',
  `sRegisterTime` datetime(6) NOT NULL COMMENT '注册时间\r\n格式：YYYY-MM-DD HH:MM:SS\r\n',
  `sPayPassword` varchar(32) DEFAULT '' COMMENT '支付密码。\r\n保存时需要加密保存\r\n',
  PRIMARY KEY (`nUserID`),
  UNIQUE KEY `UNIQUE_sLoginName` (`sLoginName`) USING BTREE COMMENT '此唯一索引主要保证用户名的唯一性',
  KEY `PRIMARY_nUserID` (`nUserID`) USING BTREE COMMENT '使用用户id作为主键索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户基本信息表';
