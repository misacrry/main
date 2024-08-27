/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50729
Source Host           : localhost:3306
Source Database       : cloud

Target Server Type    : MYSQL
Target Server Version : 50729
File Encoding         : 65001

Date: 2024-08-19 08:52:29
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for ancillary_control_data
-- ----------------------------
DROP TABLE IF EXISTS `ancillary_control_data`;
CREATE TABLE `ancillary_control_data` (
  `environmentT` double(16,4) DEFAULT NULL COMMENT '环境温度',
  `environmentH` double(16,4) DEFAULT NULL COMMENT '环境湿度',
  `airConditionerT` double(16,4) DEFAULT NULL COMMENT '空调温度'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='辅控数据';

-- ----------------------------
-- Records of ancillary_control_data
-- ----------------------------

-- ----------------------------
-- Table structure for battery_cell
-- ----------------------------
DROP TABLE IF EXISTS `battery_cell`;
CREATE TABLE `battery_cell` (
  `volLimit` tinyint(4) DEFAULT NULL COMMENT '电池单体电压越限报警',
  `volRangeLimit` tinyint(4) DEFAULT NULL COMMENT '电池单体电压极差越限报警',
  `tmpLimit` tinyint(4) DEFAULT NULL COMMENT '电池单体温度越限报警',
  `tmpRangeLimit` tinyint(4) DEFAULT NULL COMMENT '电池单体温度极差越限报警'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='电池单体';

-- ----------------------------
-- Records of battery_cell
-- ----------------------------

-- ----------------------------
-- Table structure for battery_cluster
-- ----------------------------
DROP TABLE IF EXISTS `battery_cluster`;
CREATE TABLE `battery_cluster` (
  `batteryClusterV` double(16,4) DEFAULT NULL COMMENT '电池簇电压',
  `batteryClusterI` double(16,4) DEFAULT NULL COMMENT '电池簇电流',
  `batteryClusterSoc` double(16,4) DEFAULT NULL COMMENT '电池簇SOC',
  `batteryClusterSoh` double(16,4) DEFAULT NULL COMMENT '电池簇SOH',
  `batteryClusterAbleEnergy` double(16,4) DEFAULT NULL COMMENT '电池簇可充电量',
  `batteryClusterAbleDsEnergy` double(16,4) DEFAULT NULL COMMENT '电池簇可放电量',
  `cellMaxV` double(16,4) DEFAULT NULL COMMENT '电芯最高电压',
  `cellMinV` double(16,4) DEFAULT NULL COMMENT '电芯最低电压',
  `cellMaxT` double(16,4) DEFAULT NULL COMMENT '电芯最高温度',
  `cellMinT` double(16,4) DEFAULT NULL COMMENT '电芯最低温度',
  `cellMaxVolPos` double(16,4) DEFAULT NULL COMMENT '电芯最高电压位置',
  `cellMinVolPos` double(16,4) DEFAULT NULL COMMENT '电芯最高电压位置',
  `cellMaxTmpPos` double(16,4) DEFAULT NULL COMMENT '电芯最高温度位置',
  `cellMiniTmpPos` double(16,4) DEFAULT NULL COMMENT '电芯最低温度位置',
  `dcSwitchState` double(16,4) DEFAULT NULL COMMENT '直流开关状态（接触器状态）',
  `actorBatVoltLimit` tinyint(4) DEFAULT NULL COMMENT '电池簇电压越限报警',
  `actorBatCurLimit` tinyint(4) DEFAULT NULL COMMENT '电池簇电流越限报警',
  `resInsLimit` tinyint(4) DEFAULT NULL COMMENT '绝缘电阻越限报警',
  `intCirLimit` tinyint(4) DEFAULT NULL COMMENT '簇间环流越限报警',
  `actorBatLoopAbnl` tinyint(4) DEFAULT NULL COMMENT '电池簇充放电回路异常',
  `actorModlVoltLimit` tinyint(4) DEFAULT NULL COMMENT '电池模块电压越限报警'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='电池簇';

-- ----------------------------
-- Records of battery_cluster
-- ----------------------------

-- ----------------------------
-- Table structure for bms
-- ----------------------------
DROP TABLE IF EXISTS `bms`;
CREATE TABLE `bms` (
  `bmsTotWarning` tinyint(4) DEFAULT NULL COMMENT 'BMS告警总',
  `bmsTotFault` tinyint(4) DEFAULT NULL COMMENT 'BMS故障总',
  `batteryStackVoltLimit` tinyint(4) DEFAULT NULL COMMENT '电池堆电压越限',
  `batteryStackCurLimit` tinyint(4) DEFAULT NULL COMMENT '电池堆电流越限'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bms
-- ----------------------------

-- ----------------------------
-- Table structure for communication
-- ----------------------------
DROP TABLE IF EXISTS `communication`;
CREATE TABLE `communication` (
  `substationComState` tinyint(4) DEFAULT NULL COMMENT '储能站通信状态'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='通信';

-- ----------------------------
-- Records of communication
-- ----------------------------

-- ----------------------------
-- Table structure for fire_control
-- ----------------------------
DROP TABLE IF EXISTS `fire_control`;
CREATE TABLE `fire_control` (
  `fireControlAlarm` tinyint(4) DEFAULT NULL COMMENT '消防报警',
  `gasAlarm` tinyint(4) DEFAULT NULL COMMENT '可燃气体探测器报警'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='消防';

-- ----------------------------
-- Records of fire_control
-- ----------------------------

-- ----------------------------
-- Table structure for grid_connection_point
-- ----------------------------
DROP TABLE IF EXISTS `grid_connection_point`;
CREATE TABLE `grid_connection_point` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `phaseABLineV` float DEFAULT NULL,
  `phaseBCLineV` float DEFAULT NULL,
  `phaseCALineV` float DEFAULT NULL,
  `phaseAV` float DEFAULT NULL,
  `phaseBV` float DEFAULT NULL,
  `phaseCV` float DEFAULT NULL,
  `phaseAI` float DEFAULT NULL,
  `phaseBI` float DEFAULT NULL,
  `phaseCI` float DEFAULT NULL,
  `activeP` float DEFAULT NULL,
  `reactiveP` float DEFAULT NULL,
  `powerFactor` float DEFAULT NULL,
  `switchStatus` enum('') DEFAULT NULL,
  `totalChargeElectricity` float DEFAULT NULL,
  `totalDischargeElectricity` float DEFAULT NULL,
  `totalChargePrice` float DEFAULT NULL,
  `totalDischargePrice` float DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='并网点\r\n';

-- ----------------------------
-- Records of grid_connection_point
-- ----------------------------

-- ----------------------------
-- Table structure for pcs
-- ----------------------------
DROP TABLE IF EXISTS `pcs`;
CREATE TABLE `pcs` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `TotW` float DEFAULT NULL,
  `TotVar` float DEFAULT NULL,
  `PPV_phsAB` float DEFAULT NULL,
  `PPV_phsBC` float DEFAULT NULL,
  `PPV_phsCA` float DEFAULT NULL,
  `PhV_phsA` float DEFAULT NULL,
  `PhV_phsB` float DEFAULT NULL,
  `PhV_phsC` float DEFAULT NULL,
  `A_phsA` float DEFAULT NULL,
  `A_phsB` float DEFAULT NULL,
  `A_phsC` float DEFAULT NULL,
  `TotPF` float DEFAULT NULL,
  `Fre` int(11) DEFAULT NULL,
  `PhV_DC` float DEFAULT NULL,
  `A_DC` float DEFAULT NULL,
  `AbleMaxTotCH` float DEFAULT NULL,
  `AbleMaxToDi` float DEFAULT NULL,
  `CharCapDay` float DEFAULT NULL,
  `DiscCapDay` float DEFAULT NULL,
  `ACSwitchStatus` enum('') DEFAULT NULL,
  `DCSwitchStatus` enum('') DEFAULT NULL,
  `RunStatus` enum('') DEFAULT NULL,
  `CharStatus` enum('') DEFAULT NULL,
  `AccumulatedChargeCap` float DEFAULT NULL,
  `AccumulatedDischargeCap` float DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of pcs
-- ----------------------------

-- ----------------------------
-- Table structure for storage_cell
-- ----------------------------
DROP TABLE IF EXISTS `storage_cell`;
CREATE TABLE `storage_cell` (
  `batteryArrayV` double(16,4) DEFAULT NULL COMMENT '电池阵列电压',
  `batteryArrayI` double(16,4) DEFAULT NULL COMMENT '电池阵列电流',
  `batteryArraySoc` double(16,4) DEFAULT NULL COMMENT '电池阵列SOC',
  `batteryArraySoh` double(16,4) DEFAULT NULL COMMENT '电池阵列SOH',
  `batteryArrayAbleCharCap` double(16,4) DEFAULT NULL COMMENT '电池阵列可充电量',
  `batteryArrayAbleDiscCap` double(16,4) DEFAULT NULL COMMENT '电池阵列可放电量',
  `batteryArrayMaxV` double(16,4) DEFAULT NULL COMMENT '电池阵列最高电压',
  `batteryArrayMinV` double(16,4) DEFAULT NULL COMMENT '电池阵列最低电压',
  `batteryArrayMaxT` double(16,4) DEFAULT NULL COMMENT '电池阵列最高温度',
  `batteryArrayMinT` double(16,4) DEFAULT NULL COMMENT '电池阵列最低温度'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='电池阵列';

-- ----------------------------
-- Records of storage_cell
-- ----------------------------

-- ----------------------------
-- Table structure for substation
-- ----------------------------
DROP TABLE IF EXISTS `substation`;
CREATE TABLE `substation` (
  `id` int(11) NOT NULL,
  `TotW` float DEFAULT NULL,
  `TotVar` float DEFAULT NULL,
  `CharCapDay` float DEFAULT NULL,
  `DiscCapDay` float DEFAULT NULL,
  `AccumulatedChargeCap` float DEFAULT NULL,
  `AccumulatedDischargeCap` float DEFAULT NULL,
  `AccumulatedChargeTime` float DEFAULT NULL,
  `AccumulatedDischargeTime` float DEFAULT NULL,
  `DailyCgCnt` int(11) DEFAULT NULL,
  `MonthlyCgCnt` int(11) DEFAULT NULL,
  `StationStatus` enum('') DEFAULT NULL,
  `SOC` enum('') DEFAULT NULL,
  `AccumulatedRunningTime` float DEFAULT NULL,
  `AbleCharCap` float DEFAULT NULL,
  `AbleDiscCap` float DEFAULT NULL,
  `AbleEnergy` float DEFAULT NULL,
  `AbleDsEnergy` float DEFAULT NULL,
  `OverallEfficiency` float DEFAULT NULL,
  `RunningFactor` float DEFAULT NULL,
  `UtilRateInd` float DEFAULT NULL,
  `StorageScrapRate` float DEFAULT NULL,
  `UtilFactor` float DEFAULT NULL,
  `AccumulatedRev` float DEFAULT NULL,
  `TodayRev` float DEFAULT NULL,
  `YesterdayRev` float DEFAULT NULL,
  `MonthlyRev` float DEFAULT NULL,
  `YearlyRev` float DEFAULT NULL,
  `equivalentChargeDischargeTime` int(11) DEFAULT NULL,
  `latitude` int(11) DEFAULT NULL,
  `longitude` int(11) DEFAULT NULL,
  `operationTime` datetime DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='储能站';

-- ----------------------------
-- Records of substation
-- ----------------------------
INSERT INTO `substation` VALUES ('1', '11', '22', '33', '44', '55', '66', '77', '88', '99', '12', '', null, '1234', '14', '15', '16', '17', '18', '19', '20', '21', '22', '23', '24', '25', '26', '27', '28', '29', '30', '2024-08-13 17:00:06', '32');

-- ----------------------------
-- Table structure for sub_transformer
-- ----------------------------
DROP TABLE IF EXISTS `sub_transformer`;
CREATE TABLE `sub_transformer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `hPhaseAV` float DEFAULT NULL,
  `hPhaseBV` float DEFAULT NULL,
  `hPhaseCV` float DEFAULT NULL,
  `hPhaseAI` float DEFAULT NULL,
  `hPhaseBI` float DEFAULT NULL,
  `hPhaseCI` float DEFAULT NULL,
  `hActiveP` float DEFAULT NULL,
  `hReactiveP` float DEFAULT NULL,
  `hPowerIndex` float DEFAULT NULL,
  `lPhaseAV` float DEFAULT NULL,
  `lPhaseBV` float DEFAULT NULL,
  `lPhaseCV` float DEFAULT NULL,
  `lPhaseAI` float DEFAULT NULL,
  `lPhaseBI` float DEFAULT NULL,
  `lPhaseCI` float DEFAULT NULL,
  `lActiveP` float DEFAULT NULL,
  `lReactiveP` float DEFAULT NULL,
  `lPowerIndex` float DEFAULT NULL,
  `hSwitchStatus` enum('') DEFAULT NULL,
  `lSwitchStatus` enum('') DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='储能变压器';

-- ----------------------------
-- Records of sub_transformer
-- ----------------------------

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `addr` varchar(255) DEFAULT NULL,
  `age` int(11) NOT NULL DEFAULT '0',
  `name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `phone` varchar(11) NOT NULL,
  `wx_openid` varchar(255) DEFAULT NULL,
  `wx_nickname` varchar(255) DEFAULT NULL,
  `role_id` int(5) DEFAULT NULL,
  PRIMARY KEY (`id`,`phone`),
  UNIQUE KEY `UK_589idila9li6a4arw1t8ht1gx` (`phone`),
  KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('9', null, '60', 'Wqq', '123', '', null, null, '1');
INSERT INTO `user` VALUES ('12', '', '20', 'Cui', 'Test', '16060640680', null, null, '2');
INSERT INTO `user` VALUES ('13', null, '20', 'RBQ', 'operation', '15050540510', null, null, '3');
INSERT INTO `user` VALUES ('14', null, '0', 'cy', '13579', '13851834251', null, null, '1');
INSERT INTO `user` VALUES ('15', null, '0', '110', '110', '11011001100', null, null, '2');
INSERT INTO `user` VALUES ('16', null, '0', '120', '120', '12011221200', null, null, '3');
INSERT INTO `user` VALUES ('17', null, '0', '13', '13', '12345678912', null, null, '4');
INSERT INTO `user` VALUES ('18', null, '0', 'Jack', '0212', '19090990517', null, null, '1');
INSERT INTO `user` VALUES ('19', null, '0', 'YGSB', '0331', '13325509711', null, null, '2');
INSERT INTO `user` VALUES ('20', null, '0', 'rsdw', 'rsdw', 'rsdw', null, null, '1');
INSERT INTO `user` VALUES ('21', '11', '11', '11', '$2a$10$y2IC0.Qj2O4.Dy29769yI.DDmt4s.gHCH1DYMqwM2wt2uBodo4Ggm', '11', null, null, null);
INSERT INTO `user` VALUES ('22', '11', '11', '11', '$2a$10$vkECe2hON3Hc9KYKuAeVxulXVyupnGqnqzEfj0Z/gOuK2gsDoBWhG', '22', null, null, null);
