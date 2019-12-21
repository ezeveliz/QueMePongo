-- MySQL dump 10.13  Distrib 8.0.17, for Win64 (x86_64)
--
-- Host: localhost    Database: unisocial
-- ------------------------------------------------------
-- Server version	5.6.45-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

USE database_queMePongo;
--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;

CREATE TABLE `usuario` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(255) DEFAULT NULL,
  `apellido` varchar(255) DEFAULT NULL,
  `telefono` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `tipo` int,
  `usuario` varchar(255) NOT NULL UNIQUE,
  `contraseña` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
  ) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario`() VALUES (1,'admin1','admin1','1231314411','admin1@admin.com',1,'admin1','admin'),(2,'admin2','admin2','12222222','admin2@admin.com',1,'admin2','admin'),(3,'admin3','admin3','1235666666','admin3@admin.com',1,'admin3','admin');
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `atuendo`
--

DROP TABLE IF EXISTS `atuendo`;

CREATE TABLE `atuendo` (
  `id` int NOT NULL AUTO_INCREMENT,
  `id_usuario` int,
  `nro_sugerencia` int NOT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id`)
  ) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `atuendo` WRITE;
/*!40000 ALTER TABLE `atuendo` DISABLE KEYS */;
/*!40000 ALTER TABLE `atuendo` ENABLE KEYS */;
UNLOCK TABLES;


--
-- Table structure for table `frecuencia`
--

DROP TABLE IF EXISTS `frecuencia`;

CREATE TABLE `frecuencia` (
  `id` int NOT NULL AUTO_INCREMENT,
  `inicio` varchar(255),
  `tipo` varchar(255),
  PRIMARY KEY (`id`)
  ) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `frecuencia`
--

LOCK TABLES `frecuencia` WRITE;
/*!40000 ALTER TABLE `frecuencia` DISABLE KEYS */;
/*!40000 ALTER TABLE `frecuencia` ENABLE KEYS */;
UNLOCK TABLES;

DROP TABLE IF EXISTS `tipo_prenda`;

CREATE TABLE `tipo_prenda` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nivel_calor` int NOT NULL,
  PRIMARY KEY (`id`)
  ) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tipo_prenda`
--

LOCK TABLES `tipo_prenda` WRITE;
/*!40000 ALTER TABLE `tipo_prenda` DISABLE KEYS */;
/*!40000 ALTER TABLE `tipo_prenda` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `preferenciasDTO`
--

DROP TABLE IF EXISTS `preferenciasDTO`;

CREATE TABLE `preferenciasDTO` (

  `id` int NOT NULL AUTO_INCREMENT,
  `id_usuario` int,
  `tela` varchar(100),
  `color1` varchar(100),
  `color2` varchar(100),
  `modificador_calor` int,
  `calor_cuello` int,
  `calor_manos`int,
  `calor_cabeza`int,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `archivo`
--

LOCK TABLES `preferenciasDTO` WRITE;
/*!40000 ALTER TABLE `preferenciasDTO` DISABLE KEYS */;
INSERT INTO `preferenciasDTO` VALUES (1,1,'seda','rojo','verde',0,0,0,0),(2,1,'cuero','rojo','verde',0,0,0,0);
/*!40000 ALTER TABLE `preferenciasDTO` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `evento`
--

DROP TABLE IF EXISTS `evento`;

CREATE TABLE `evento` (
  `id` int NOT NULL AUTO_INCREMENT,
  `id_usuario` int NOT NULL,
  `id_atuendo` int,
  `id_frecuencia` int,
  `descripcion` varchar(255),
  `tipo` varchar(255),
  `horario` varchar(255),
  `temperatura` int,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id`),
  FOREIGN KEY (`id_atuendo`) REFERENCES `atuendo` (`id`),
  FOREIGN KEY (`id_frecuencia`) REFERENCES `frecuencia` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `evento`
--

LOCK TABLES `evento` WRITE;
/*!40000 ALTER TABLE `evento` DISABLE KEYS */;

/*!40000 ALTER TABLE `evento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `guardarropa`
--

DROP TABLE IF EXISTS `guardarropa`;

CREATE TABLE `guardarropa` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(255),
  `descripcion` varchar(255),
  `disponible`  tinyint,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `guardarropa`
--

LOCK TABLES `guardarropa` WRITE;
/*!40000 ALTER TABLE `guardarropa` DISABLE KEYS */;
INSERT INTO `guardarropa`() VALUES (1,'Guardarropa1','Es un alto Guardarropas',1),(2,'Guardarropa2','Este guardarropas es para irte de gira',1),(3,'Guardarropa1','Este guardarropas es de entrenamiento',1);
/*!40000 ALTER TABLE `guardarropa` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario_guardarropa`
--

DROP TABLE IF EXISTS `usuario_guardarropa`;

CREATE TABLE `usuario_guardarropa` (
  `id_usuario` int,
  `id_guardarropa` int,
  PRIMARY KEY (`id_usuario`,`id_guardarropa`),
  FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id`),
  FOREIGN KEY (`id_guardarropa`) REFERENCES `guardarropa` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `evento_usuario`
--

LOCK TABLES `usuario_guardarropa` WRITE;
/*!40000 ALTER TABLE `usuario_guardarropa` DISABLE KEYS */;
INSERT INTO `usuario_guardarropa`() VALUES (1,1),(1,2),(1,3);
/*!40000 ALTER TABLE `usuario_guardarropa` ENABLE KEYS */;
UNLOCK TABLES;


DROP TABLE IF EXISTS `categoria`;

CREATE TABLE `categoria` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(255),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `categoria`
--

LOCK TABLES `categoria` WRITE;
/*!40000 ALTER TABLE `categoria` DISABLE KEYS */;
INSERT INTO `categoria` VALUES (1,'Torso'),(2,'Piernas'),(3,'Pies'),(4,'Cabeza'),(5,'Accesorio');
/*!40000 ALTER TABLE `categoria` ENABLE KEYS */;
UNLOCK TABLES;


DROP TABLE IF EXISTS `prenda`;

CREATE TABLE `prenda` (
  `id` int NOT NULL AUTO_INCREMENT,
  `id_guardarropa` int,
  `categoria` int,
  `nombre` varchar(100),
  `id_tipo_prenda` int,
  `tipo_evento` varchar(255),
  `tela` varchar(100),
  `id_categoria` int,
  `color_primario` varchar(100),
  `color_secundario` varchar(100),
  `foto` LONGTEXT, 
  `nivel_calor` int,
  `disponible` tinyint,
  
  PRIMARY KEY (`id`),
  FOREIGN KEY (`id_guardarropa`) REFERENCES `guardarropa` (`id`),
  FOREIGN KEY (`id_categoria`) REFERENCES `categoria` (`id`),
  FOREIGN KEY (`id_tipo_prenda`) REFERENCES `tipo_prenda` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `prenda`
--

LOCK TABLES `prenda` WRITE;
/*!40000 ALTER TABLE `prenda` DISABLE KEYS */;
/*!40000 ALTER TABLE `prenda` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `atuendo_prenda_basico`
--

DROP TABLE IF EXISTS `atuendo_prenda_basico`;

CREATE TABLE `atuendo_prenda_basico` (
  `id_atuendo` int,
  `id_prenda` int,
  PRIMARY KEY (`id_atuendo`,`id_prenda`),
  FOREIGN KEY (`id_atuendo`) REFERENCES `atuendo` (`id`),
  FOREIGN KEY (`id_prenda`) REFERENCES `prenda` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `atuendo_prenda_basico`
--

LOCK TABLES `atuendo_prenda_basico` WRITE;
/*!40000 ALTER TABLE `atuendo_prenda_basico` DISABLE KEYS */;
/*!40000 ALTER TABLE `atuendo_prenda_basico` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `atuendo_prenda_abrigo`
--

DROP TABLE IF EXISTS `atuendo_prenda_abrigo`;

CREATE TABLE `atuendo_prenda_abrigo` (
  `id_atuendo` int,
  `id_prenda` int,
  PRIMARY KEY (`id_atuendo`,`id_prenda`),
  FOREIGN KEY (`id_atuendo`) REFERENCES `atuendo` (`id`),
  FOREIGN KEY (`id_prenda`) REFERENCES `prenda` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `atuendo_prenda_abrigo`
--

LOCK TABLES `atuendo_prenda_abrigo` WRITE;
/*!40000 ALTER TABLE `atuendo_prenda_abrigo` DISABLE KEYS */;
/*!40000 ALTER TABLE `atuendo_prenda_abrigo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `atuendo_prenda_accesorios`
--

DROP TABLE IF EXISTS `atuendo_prenda_accesorios`;

CREATE TABLE `atuendo_prenda_accesorios` (
  `id_atuendo` int,
  `id_prenda` int,
  PRIMARY KEY (`id_atuendo`,`id_prenda`),
  FOREIGN KEY (`id_atuendo`) REFERENCES `atuendo` (`id`),
  FOREIGN KEY (`id_prenda`) REFERENCES `prenda` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `atuendo_prenda_accesorios`
--

LOCK TABLES `atuendo_prenda_accesorios` WRITE;
/*!40000 ALTER TABLE `atuendo_prenda_accesorios` DISABLE KEYS */;
/*!40000 ALTER TABLE `atuendo_prenda_accesorios` ENABLE KEYS */;
UNLOCK TABLES;


--
-- Table structure for table `tipo_prenda`
--

DROP TABLE IF EXISTS `tipo_prenda`;

CREATE TABLE `tipo_prenda` (
  `id` int NOT NULL AUTO_INCREMENT,
  `id_prenda` int,
  `categoria` int,
  `nivel_calor` int,
  `tipo` varchar(255),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tipo_prenda`
--

LOCK TABLES `tipo_prenda` WRITE;
/*!40000 ALTER TABLE `tipo_prenda` DISABLE KEYS */;
/*!40000 ALTER TABLE `tipo_prenda` ENABLE KEYS */;
UNLOCK TABLES;


--
-- Table structure for table `usuarios_mediosenum`
--

DROP TABLE IF EXISTS `usuario_mediosenum`;

CREATE TABLE `usuario_mediosenum` (
  `Usuario_id` int ,
  `mediosenum` int ,
  PRIMARY KEY (`mediosenum`,`Usuario_id`), 
  FOREIGN KEY (`Usuario_id`) REFERENCES `usuario` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `usuarios_mediosenum`
--

LOCK TABLES `usuario_mediosenum` WRITE;
/*!40000 ALTER TABLE `usuario_mediosenum` DISABLE KEYS */;

/*!40000 ALTER TABLE `usuario_mediosenum` ENABLE KEYS */;
UNLOCK TABLES;


/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-08-29 20:37:05

