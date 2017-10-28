CREATE DATABASE  IF NOT EXISTS `analisisf2017` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `analisisf2017`;
-- MySQL dump 10.16  Distrib 10.1.26-MariaDB, for debian-linux-gnu (x86_64)
--
-- Host: 127.0.0.1    Database: anf2017
-- ------------------------------------------------------
-- Server version	10.1.21-MariaDB-1~jessie

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `cuentas`
--

DROP TABLE IF EXISTS `cuentas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cuentas` (
  `id_cuenta` int(11) NOT NULL,
  `nombre` varchar(45) NOT NULL,
  `id_tipo_cuenta` int(11) NOT NULL,
  `id_tipo_estado` int(11) NOT NULL,
  `tipo_saldo` char(1) NOT NULL,
  `descripcion` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id_cuenta`),
  KEY `tipo_cuenta_idx` (`id_tipo_cuenta`),
  KEY `tipo_estado_idx` (`id_tipo_estado`),
  CONSTRAINT `tipo_cuenta` FOREIGN KEY (`id_tipo_cuenta`) REFERENCES `tipo_cuenta` (`id_tipo_cuenta`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `tipo_estado` FOREIGN KEY (`id_tipo_estado`) REFERENCES `tipo_estado` (`id_tipo_estado`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cuentas`
--

LOCK TABLES `cuentas` WRITE;
/*!40000 ALTER TABLE `cuentas` DISABLE KEYS */;
INSERT INTO `cuentas` VALUES (1,'ACTIVOS',1,1,'H',''),(2,'PASIVOS',2,1,'D',''),(3,'PATRIMONIO',3,1,'H',''),(4,'CUENTAS DE RESULTADO DEUDORAS',4,2,'D',''),(5,'CUENTAS DE RESULTADO ACREEDORAS',5,2,'H',''),(11,'ACTIVOS CORRIENTES',1,1,'H',''),(12,'ACTIVOS NO CORRIENTES',1,1,'H',''),(21,'PASIVOS CORRIENTES',2,1,'D',''),(22,'PASIVOS NO CORRIENTES',2,1,'D',''),(31,'PATRIMONIO DE LOS ACCIONISTAS',3,1,'H',''),(41,'COSTOS Y GASTOS',4,2,'D',''),(51,'PRODUCTOS DE OPERACION',5,2,'H',''),(1101,'EFECTIVO Y EQUIVALENTES',1,1,'H',''),(1102,'CUENTAS POR COBRAR',1,1,'H',''),(1105,'INVENTARIOS',1,1,'H',''),(1108,'GASTOS PAGADOS POR ANTICIPADOS',1,1,'H',''),(1201,'PROPIEDADES, PLANTA Y EQUIPO',1,1,'H',''),(1202,'DEPRECIACION ACUMULADAS',1,1,'D',''),(1205,'PROPIEDADES DE INVERSION',1,1,'H',''),(2101,'CUENTAS POR PAGAR',2,1,'D',''),(2102,'ACUMULADOS POR PAGAR',2,1,'D',''),(2205,'PASIVO POR RETIRO LABORAL',2,1,'D',''),(3101,'CAPITAL SOCIAL',3,1,'H',''),(3102,'SUPERAVIT POR REVALUACION',3,1,'H',''),(3103,'RESERVA LEGAL',3,1,'H',''),(3105,'UTILIDADES RETENIDAS',3,1,'H',''),(3106,'DEFICIT ACUMULADO (R)',3,1,'H',''),(4101,'COMPRAS',4,2,'D',''),(4102,'REBAJAS Y DEVOLUCIONES SOBRE COMPRAS(R)',4,2,'D',''),(4104,'GASTOS DE ADMINISTRACION',4,2,'D',''),(4105,'GASTOS DE VENTA',4,2,'D',''),(4107,'OTROS GASTOS',4,2,'D',''),(5101,'VENTAS',5,2,'H',''),(5102,'REBAJAS Y DEVOLUCIONES SOBRE VENTAS(R)',5,2,'H',''),(5104,'INGRESOS FINANCIEROS',5,2,'H',''),(5105,'OTROS INGRESOS',5,2,'H',''),(110101,'Caja',1,1,'H',''),(110102,'Bancos',1,1,'H',''),(110201,'Cuentas por Cobrar a Clientes',1,1,'H',''),(110203,'PRESTAMOS AL PERSONAL',1,1,'H',''),(110204,'Deudores Varios',1,1,'H',''),(110206,'Reserva para Cuentas Incobrables',1,1,'D',''),(110208,'IVA-Credito Fiscal',1,1,'H',''),(110209,'IVA-Percibido',1,1,'H',''),(110210,'IVA-Retenido',1,1,'H',''),(110501,'Inventario',1,1,'H',''),(110803,'Asuntos Pendientes',1,1,'H',''),(110804,'Seguros',1,1,'H',''),(110805,'Pago a Cuenta',1,1,'H',''),(110806,'Papeleria y Utiles',1,1,'H',''),(110807,'Bonificación a Clientes',1,1,'H',''),(110808,'Materiales ',1,1,'H',''),(110811,'Imp s/Renta Retenido ',1,1,'H',''),(110815,'Control Al Impuesto de Liquidez',1,1,'H',''),(110816,'Construccion ',1,1,'H',''),(110817,'Otros',1,1,'H',''),(120101,'Terrenos',1,1,'H',''),(120102,'Edificaciones',1,1,'H',''),(120103,'Instalaciones',1,1,'H',''),(120104,'Mobiliario y Equipo de Oficina',1,1,'H',''),(120105,'Vehículos',1,1,'H',''),(120106,'Otros',1,1,'H',''),(120201,'De Edificaciones',1,1,'D',''),(120202,'De Instalaciones',1,1,'D',''),(120203,'De Mobiliario y equipo',1,1,'D',''),(120204,'De Vehiculos',1,1,'D',''),(120205,'Otros',1,1,'D',''),(120501,'Terrenos',1,1,'H',''),(120502,'Edificaciones',1,1,'H',''),(210101,'PROVEEDORES',2,1,'D',''),(210102,'ACREEDORES VARIOS',2,1,'D',''),(210103,'Sobregiros Bancarios',2,1,'D',''),(210106,'Impúestos Por Pagar',2,1,'D',''),(210109,'Debito Fiscal',2,1,'D',''),(210207,'Retenciones por Pagar',2,1,'D',''),(220501,'Reserva laboral',2,1,'D',''),(310101,'Capital Social Suscrito',3,1,'H',''),(310201,'De Terrenos',3,1,'H',''),(310202,'De Edificaciones',3,1,'H',''),(310501,'Ejercicios Anteriores',3,1,'H',''),(310502,'Ejercicio Corriente',3,1,'H',''),(310601,'PERDIDA DE EJERCICIOS ANTERIORES',3,1,'H',''),(410101,'Compras Varias',4,2,'D',''),(410202,'Devoluciones sobre compras(R)',4,2,'D',''),(410401,'Sueldos',4,2,'D',''),(410402,'Vacaciones',4,2,'D',''),(410404,'Honorarios Profesionales',4,2,'D',''),(410405,'Indemnizaciones',4,2,'D',''),(410406,'Impuestos Fiscales',4,2,'D',''),(410409,'Cuota Instituto Salvadoreño del Seguro Social',4,2,'D',''),(410410,'Cuota AFP',4,2,'D',''),(410411,'Servicio de Agua',4,2,'D',''),(410412,'Energía Eléctrica',4,2,'D',''),(410413,'Combustible y Lubricantes',4,2,'D',''),(410414,'Papelería y Utiles',4,2,'D',''),(410420,'Inscripciones',4,2,'D',''),(410424,'Viáticos',4,2,'D',''),(410425,'Mantenimiento de Mobiliario y Equipo',4,2,'D',''),(410428,'Comuinicaciones',4,2,'D',''),(410430,'Depreciaciones',4,2,'D',''),(410433,'Atención al Personal',4,2,'D',''),(410438,'Cuota Insaford',4,2,'D',''),(410440,'Membresias',4,2,'D',''),(410441,'Mobiliario y equipo de Oficina',4,2,'D',''),(410501,'Sueldos',4,2,'D',''),(410502,'Vacaciones',4,2,'D',''),(410504,'Honorarios Profesionales',4,2,'D',''),(410505,'Indemnizaciones',4,2,'D',''),(410506,'Impuestos Fiscales',4,2,'D',''),(410507,'Impuestos Municipales',4,2,'D',''),(410509,'Cuota Instituto Salvadoreño del Seguro Social',4,2,'D',''),(410510,'Cuota AFP',4,2,'D',''),(410511,'Servicio de Agua',4,2,'D',''),(410513,'Combustible y Lubricantes',4,2,'D',''),(410514,'Papelería y Utiles',4,2,'D',''),(410515,'Seguros',4,2,'D',''),(410516,'Contribuciones y Donaciones',4,2,'D',''),(410517,'Fletes y Transportes',4,2,'D',''),(410518,'Comisiones',4,2,'D',''),(410519,'Bonificaciones',4,2,'D',''),(410520,'Inscripciones',4,2,'D',''),(410523,'Aseo y Limpieza',4,2,'D',''),(410524,'Viáticos',4,2,'D',''),(410525,'Mantenimiento de Mobiliario y Equipo',4,2,'D',''),(410526,'Mantenimiento de Local',4,2,'D',''),(410528,'Comunicaciones',4,2,'D',''),(410529,'Publicidad',4,2,'D',''),(410530,'Depreciaciones',4,2,'D',''),(410536,'Otros',4,2,'D',''),(410537,'Comisiones a Terceros',4,2,'D',''),(410538,'Mantenimiento y Reparación',4,2,'D',''),(410539,'Atención a Clientes',4,2,'D',''),(410540,'Cuota Insaforp',4,2,'D',''),(410542,'Material de Empaque',4,2,'D',''),(410544,'Accesorios para Vehiculos',4,2,'D',''),(410546,'No Deducible',4,2,'D',''),(510101,'Contado',5,2,'H',''),(510102,'Crediito ',5,2,'H',''),(510201,'Devolucion',5,2,'H',''),(510401,'Intereses',5,2,'H',''),(510501,'Otros',5,2,'H',''),(11010101,'Caja General',1,1,'H',''),(11010102,'Caja Chica',1,1,'H',''),(11010201,'Cuentas Corrientes',1,1,'H',''),(11010202,'Cuentas de ahorro',1,1,'H',''),(11020301,'Empleado A',1,1,'H',''),(11020302,'Empleado B',1,1,'H',''),(11020401,'Deudor A',1,1,'H',''),(11020601,'De Clientes',1,1,'D',''),(11080501,'Pago a Cuenta Ejercicio 201X',1,1,'H',''),(12050101,'Costo de Adquisición',1,1,'H',''),(21010101,'Proveedor A',2,1,'D',''),(21010102,'Proveedor B',2,1,'D',''),(21010201,'Acreedor A',2,1,'D',''),(21010202,'Acreedor B',2,1,'D',''),(21010229,'Aes Clesa y Cia S en C de c.v',2,1,'D',''),(21010237,'Compañia de Telecomunicaciones de el Salvador',2,1,'D',''),(21010241,'Telemovil de El Salvador',2,1,'D',''),(21010249,'Anda',2,1,'D',''),(21010301,'Asuntos Pendientes',2,1,'D',''),(21010601,'Iva Por pagar',2,1,'D',''),(21010602,'Impuesto sobre Renta Por Pagar',2,1,'D',''),(21010603,'Pago a Cuenta por Pagar',2,1,'D',''),(21010901,'Consumidor Final',2,1,'D',''),(21010902,'Credito Fiscal',2,1,'D',''),(21020701,'Cotizaciones ISSS y AFP',2,1,'D',''),(21020703,'ISR Retenido',2,1,'D',''),(21020705,'Otras Retenciones',2,1,'D',''),(31010101,'Capital Social Mínimo',3,1,'H',''),(31010102,'Capital Social Variable',3,1,'H',''),(41054601,'Impuesto Al Cheque',4,2,'D',''),(41054602,'Impuesto Por Telefonia',4,2,'D',''),(41054603,'Otros',4,2,'D',''),(110201001,'Cliente A',1,1,'H',''),(110201002,'Cliente B',1,1,'H',''),(1101020101,'Banco Agricola X',1,1,'H',''),(1101020102,'Banco Agricola A',1,1,'H',''),(1101020103,'Banco K',1,1,'H',''),(1101020104,'Banco M',1,1,'H',''),(1101020105,'Banco Scotiabank ',1,1,'H',''),(1101020201,'Banco Agricola N',1,1,'H',''),(1101020202,'Banco Agricola AA',1,1,'H','');
/*!40000 ALTER TABLE `cuentas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `empresas`
--

DROP TABLE IF EXISTS `empresas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `empresas` (
  `id_empresa` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,
  `giro` char(1) NOT NULL,
  `descripcion` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id_empresa`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `empresas`
--

LOCK TABLES `empresas` WRITE;
/*!40000 ALTER TABLE `empresas` DISABLE KEYS */;
/*!40000 ALTER TABLE `empresas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `movimientos`
--

DROP TABLE IF EXISTS `movimientos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `movimientos` (
  `id_movimiento` int(11) NOT NULL AUTO_INCREMENT,
  `id_empresa` int(11) NOT NULL,
  `id_cuenta` int(11) NOT NULL,
  `monto` decimal(10,4) NOT NULL,
  `fecha` date NOT NULL,
  `descripcion` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id_movimiento`),
  KEY `cuenta_idx` (`id_cuenta`),
  KEY `empresa_idx` (`id_empresa`),
  CONSTRAINT `cuenta` FOREIGN KEY (`id_cuenta`) REFERENCES `cuentas` (`id_cuenta`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `empresa` FOREIGN KEY (`id_empresa`) REFERENCES `empresas` (`id_empresa`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `movimientos`
--

LOCK TABLES `movimientos` WRITE;
/*!40000 ALTER TABLE `movimientos` DISABLE KEYS */;
/*!40000 ALTER TABLE `movimientos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `porcentajes`
--

DROP TABLE IF EXISTS `porcentajes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `porcentajes` (
  `id_porcentaje` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,
  `porcentaje` decimal(10,4) NOT NULL,
  `descripcion` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id_porcentaje`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `porcentajes`
--

LOCK TABLES `porcentajes` WRITE;
/*!40000 ALTER TABLE `porcentajes` DISABLE KEYS */;
INSERT INTO `porcentajes` VALUES (1,'iva',0.1300,NULL),(2,'Renta',0.2500,NULL),(3,'Reserva Legal',0.0700,NULL);
/*!40000 ALTER TABLE `porcentajes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tipo_cuenta`
--

DROP TABLE IF EXISTS `tipo_cuenta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tipo_cuenta` (
  `id_tipo_cuenta` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,
  `descripcion` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id_tipo_cuenta`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tipo_cuenta`
--

LOCK TABLES `tipo_cuenta` WRITE;
/*!40000 ALTER TABLE `tipo_cuenta` DISABLE KEYS */;
INSERT INTO `tipo_cuenta` VALUES (1,'Activo',NULL),(2,'Pasivo',NULL),(3,'Capital',NULL),(4,'Gasto',NULL),(5,'Ingreso',NULL);
/*!40000 ALTER TABLE `tipo_cuenta` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tipo_estado`
--

DROP TABLE IF EXISTS `tipo_estado`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tipo_estado` (
  `id_tipo_estado` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,
  `descripcion` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id_tipo_estado`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tipo_estado`
--

LOCK TABLES `tipo_estado` WRITE;
/*!40000 ALTER TABLE `tipo_estado` DISABLE KEYS */;
INSERT INTO `tipo_estado` VALUES (1,'Balance Gneral',NULL),(2,'Estado de Resultados',NULL);
/*!40000 ALTER TABLE `tipo_estado` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuarios`
--

DROP TABLE IF EXISTS `usuarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usuarios` (
  `id_usuario` int(11) NOT NULL AUTO_INCREMENT,
  `login` varchar(45) NOT NULL,
  `nombres` varchar(45) NOT NULL,
  `apellidos` varchar(45) NOT NULL,
  `clave` char(32) NOT NULL,
  `rol` char(1) NOT NULL,
  PRIMARY KEY (`id_usuario`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuarios`
--

LOCK TABLES `usuarios` WRITE;
/*!40000 ALTER TABLE `usuarios` DISABLE KEYS */;
INSERT INTO `usuarios` VALUES (1,'null','Javier','Santos','QueRico','A'),(2,'chente','Kevin','Martinez','paty<3','A'),(3,'cosita','Cristian','Chavez','esta','A');
/*!40000 ALTER TABLE `usuarios` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-10-27 20:37:52
