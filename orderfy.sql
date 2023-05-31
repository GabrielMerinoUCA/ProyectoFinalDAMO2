-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: us-cdbr-east-06.cleardb.net
-- Generation Time: May 31, 2023 at 05:28 AM
-- Server version: 5.6.50-log
-- PHP Version: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `heroku_856dfefc0b0fdac`
--

-- --------------------------------------------------------

--
-- Table structure for table `administrador`
--

CREATE TABLE `administrador` (
  `idAdministrador` int(11) NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `apellido` varchar(50) NOT NULL,
  `nombreUsuario` varchar(50) NOT NULL,
  `pwd` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `administrador`
--

INSERT INTO `administrador` (`idAdministrador`, `nombre`, `apellido`, `nombreUsuario`, `pwd`) VALUES
(4, 'Gabriel', 'Mayorga', 'admin', '1234');

-- --------------------------------------------------------

--
-- Table structure for table `cliente`
--

CREATE TABLE `cliente` (
  `idCliente` int(11) NOT NULL,
  `nombre` varchar(150) NOT NULL,
  `apellido` varchar(150) NOT NULL,
  `nombreUsuario` varchar(50) NOT NULL,
  `pwd` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `cliente`
--

INSERT INTO `cliente` (`idCliente`, `nombre`, `apellido`, `nombreUsuario`, `pwd`) VALUES
(4, 'kevonnnn', 'Perez', 'kPOOsxz', '12345'),
(24, 'gabriel', 'merino', 'gab123', '1234'),
(34, 'Ilan', 'Ruiz', 'IlanR', '1234'),
(44, 'Pepe', 'Samayoa', 'pepe123', '12344321'),
(54, 'Ilan', 'Ruiz', 'ilanRZ23', '123'),
(64, 'Carlos', 'Mendoza', 'CharlieM23', '1234'),
(74, 'Camilo', 'Arroliga', 'Cam23', '123'),
(84, 'Jaimito', 'Gonzales', 'Jaime23', '123'),
(94, 'Ezequiel', 'Hernandez', 'Ezzi23', '1234'),
(104, 'Luis', 'Aguilar', 'luisallo23', '1234'),
(134, 'Catarina', 'Loausiga', 'MDA21', '1234'),
(144, 'Manuel', 'Martinez', 'MMart23', '1234'),
(154, 'Kevin', 'Perez', 'KevPO23', '1234'),
(174, 'Avocado', 'Ramirez', 'Avocado', '1234');

-- --------------------------------------------------------

--
-- Table structure for table `orden`
--

CREATE TABLE `orden` (
  `idOrden` int(11) NOT NULL,
  `cantidad` int(11) NOT NULL,
  `horaPedido` time NOT NULL,
  `horaReclamo` time NOT NULL,
  `estado` int(11) NOT NULL,
  `idCliente` int(11) NOT NULL,
  `idProducto` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `orden`
--

INSERT INTO `orden` (`idOrden`, `cantidad`, `horaPedido`, `horaReclamo`, `estado`, `idCliente`, `idProducto`) VALUES
(4, 2, '12:00:21', '00:00:00', 0, 154, 44),
(14, 1, '11:23:21', '00:00:00', 1, 134, 44),
(24, 3, '14:12:32', '14:23:21', 2, 174, 44),
(34, 1, '10:00:00', '00:00:00', 0, 4, 44),
(44, 3, '14:12:32', '00:00:00', 0, 174, 44);

-- --------------------------------------------------------

--
-- Table structure for table `producto`
--

CREATE TABLE `producto` (
  `idProducto` int(11) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `precio` float NOT NULL,
  `imagen` blob NOT NULL,
  `descripcion` varchar(200) NOT NULL,
  `disponibilidad` tinyint(1) NOT NULL,
  `tiempoEstimado` int(11) NOT NULL,
  `idTienda` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `producto`
--

INSERT INTO `producto` (`idProducto`, `nombre`, `precio`, `imagen`, `descripcion`, `disponibilidad`, `tiempoEstimado`, `idTienda`) VALUES
(44, 'papas', 19, '', 'papas fritas', 1, 1, 4);

-- --------------------------------------------------------

--
-- Table structure for table `registro`
--

CREATE TABLE `registro` (
  `idRegistro` int(11) NOT NULL,
  `nombre` varchar(150) NOT NULL,
  `apellido` varchar(150) NOT NULL,
  `nombreUsuario` varchar(50) NOT NULL,
  `nombreTienda` varchar(50) NOT NULL,
  `pwd` varchar(50) NOT NULL,
  `horaApertura` time NOT NULL,
  `horaCierre` time NOT NULL,
  `estado` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `registro`
--

INSERT INTO `registro` (`idRegistro`, `nombre`, `apellido`, `nombreUsuario`, `nombreTienda`, `pwd`, `horaApertura`, `horaCierre`, `estado`) VALUES
(4, 'Ilan', 'Ruiz', 'IlanR', 'FIFA shop', '1234', '08:00:00', '21:00:00', 1),
(14, 'Katherine', 'Arauz', 'Kath23', 'Subway', '1234', '10:00:00', '21:00:00', 1),
(24, 'Luis', 'Miguel', 'Miguel23', 'Papa Jhon', '1234', '09:00:00', '21:00:00', 1),
(34, 'Macela', 'Hernandez', 'Marc23', 'American Donuts', '1234', '09:00:00', '19:00:00', 1),
(44, 'Nei', 'Robelo', 'Nei26', 'Papa Pepe', '1234', '08:00:00', '20:00:00', 1),
(54, 'Juan', 'Perez', 'JPerez1234', 'El rincon de don juan', '1234', '10:00:00', '12:00:00', 1),
(64, 'Juan1', 'Perez1', 'JPerezUno', 'El rincon de don juan1', '1234', '10:00:00', '12:00:00', 1),
(74, 'Juan2', 'Perez2', 'JPerezDos', 'El rincon de don juan2', '1234', '10:00:00', '12:00:00', 1),
(84, 'Juan3', 'Perez3', 'JPerezTres', 'El rincon de don juan3', '1234', '10:00:00', '12:00:00', 1);

-- --------------------------------------------------------

--
-- Table structure for table `tienda`
--

CREATE TABLE `tienda` (
  `idTienda` int(11) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `logo` blob NOT NULL,
  `horaCierre` time NOT NULL,
  `horaApertura` time NOT NULL,
  `estado` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `tienda`
--

INSERT INTO `tienda` (`idTienda`, `nombre`, `logo`, `horaCierre`, `horaApertura`, `estado`) VALUES
(4, 'FIFA shop', 0x4c4f41445f46494c452827433a557365727379756c6169446f776e6c6f616473666966612e6a70672729, '21:00:00', '08:00:00', 1),
(24, 'Buffalo', '', '21:00:00', '11:00:00', 0),
(34, 'Subway', '', '21:00:00', '10:00:00', 0),
(44, 'Papa Jhon', '', '21:00:00', '09:00:00', 0),
(54, 'American Donuts', '', '19:00:00', '09:00:00', 0),
(64, 'Papa Pepe', '', '20:00:00', '08:00:00', 0),
(74, 'El rincon de don juan', '', '12:00:00', '10:00:00', 0),
(84, 'El rincon de don juan1', '', '12:00:00', '10:00:00', 0),
(94, 'El rincon de don juan2', '', '12:00:00', '10:00:00', 0),
(104, 'El rincon de don juan3', '', '12:00:00', '10:00:00', 0);

-- --------------------------------------------------------

--
-- Table structure for table `vendedor`
--

CREATE TABLE `vendedor` (
  `idTienda` int(11) NOT NULL,
  `idVendedor` int(11) NOT NULL,
  `nombreUsuario` varchar(50) NOT NULL,
  `pwd` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `vendedor`
--

INSERT INTO `vendedor` (`idTienda`, `idVendedor`, `nombreUsuario`, `pwd`) VALUES
(84, 64, 'JPerezUno', '1234');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `administrador`
--
ALTER TABLE `administrador`
  ADD PRIMARY KEY (`idAdministrador`);

--
-- Indexes for table `cliente`
--
ALTER TABLE `cliente`
  ADD PRIMARY KEY (`idCliente`);

--
-- Indexes for table `orden`
--
ALTER TABLE `orden`
  ADD PRIMARY KEY (`idOrden`),
  ADD KEY `idCliente` (`idCliente`),
  ADD KEY `idProducto` (`idProducto`);

--
-- Indexes for table `producto`
--
ALTER TABLE `producto`
  ADD PRIMARY KEY (`idProducto`),
  ADD KEY `idTienda` (`idTienda`);

--
-- Indexes for table `registro`
--
ALTER TABLE `registro`
  ADD PRIMARY KEY (`idRegistro`);

--
-- Indexes for table `tienda`
--
ALTER TABLE `tienda`
  ADD PRIMARY KEY (`idTienda`);

--
-- Indexes for table `vendedor`
--
ALTER TABLE `vendedor`
  ADD PRIMARY KEY (`idVendedor`),
  ADD KEY `idTienda` (`idTienda`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `administrador`
--
ALTER TABLE `administrador`
  MODIFY `idAdministrador` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT for table `cliente`
--
ALTER TABLE `cliente`
  MODIFY `idCliente` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=234;

--
-- AUTO_INCREMENT for table `orden`
--
ALTER TABLE `orden`
  MODIFY `idOrden` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=54;

--
-- AUTO_INCREMENT for table `producto`
--
ALTER TABLE `producto`
  MODIFY `idProducto` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=74;

--
-- AUTO_INCREMENT for table `registro`
--
ALTER TABLE `registro`
  MODIFY `idRegistro` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=94;

--
-- AUTO_INCREMENT for table `tienda`
--
ALTER TABLE `tienda`
  MODIFY `idTienda` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=114;

--
-- AUTO_INCREMENT for table `vendedor`
--
ALTER TABLE `vendedor`
  MODIFY `idVendedor` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=94;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `orden`
--
ALTER TABLE `orden`
  ADD CONSTRAINT `orden_ibfk_1` FOREIGN KEY (`idCliente`) REFERENCES `cliente` (`idCliente`),
  ADD CONSTRAINT `orden_ibfk_2` FOREIGN KEY (`idProducto`) REFERENCES `producto` (`idProducto`);

--
-- Constraints for table `producto`
--
ALTER TABLE `producto`
  ADD CONSTRAINT `producto_ibfk_1` FOREIGN KEY (`idTienda`) REFERENCES `tienda` (`idTienda`) ON DELETE CASCADE;

--
-- Constraints for table `vendedor`
--
ALTER TABLE `vendedor`
  ADD CONSTRAINT `vendedor_ibfk_1` FOREIGN KEY (`idTienda`) REFERENCES `tienda` (`idTienda`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
