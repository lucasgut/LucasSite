-- phpMyAdmin SQL Dump
-- version 3.4.11.1deb2
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Jul 28, 2013 at 08:00 PM
-- Server version: 5.5.31
-- PHP Version: 5.4.4-14

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `lucasgg`
--

-- --------------------------------------------------------

--
-- Table structure for table `CONFIG_DATA`
--

CREATE TABLE IF NOT EXISTS `CONFIG_DATA` (
  `CONFIG_KEY` varchar(20) NOT NULL DEFAULT '',
  `CONFIG_VALUE` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`CONFIG_KEY`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `DOWNLOADS`
--

CREATE TABLE IF NOT EXISTS `DOWNLOADS` (
  `DOWNLOAD_NUM` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `SUBJECT` varchar(255) NOT NULL DEFAULT '',
  `LAST_MODIFIED` date NOT NULL DEFAULT '0000-00-00',
  `DESCRIPTION` mediumtext,
  `DOWNLOAD_URL` text,
  `DOWNLOAD_IMG` mediumblob,
  PRIMARY KEY (`DOWNLOAD_NUM`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=48 ;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
