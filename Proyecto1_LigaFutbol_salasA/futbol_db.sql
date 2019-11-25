-- phpMyAdmin SQL Dump
-- version 4.8.5
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 25-11-2019 a las 08:39:21
-- Versión del servidor: 10.1.38-MariaDB
-- Versión de PHP: 7.3.3

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `futbol_db`
--

DELIMITER $$
--
-- Procedimientos
--
CREATE DEFINER=`root`@`localhost` PROCEDURE `CrearTablas` ()  NO SQL
BEGIN
  CREATE TABLE Equipos (ID_Equipo int NOT NULL, Nombre_Equipo varchar(50));
  CREATE TABLE Jugadores(ID_Jugador int NOT NULL, Nombre_Jugador varchar (50), Posicion varchar(50), ID_Equipo int NOT NULL, Nombre_Equipo varchar(50));
  CREATE TABLE Clasificacion(Posicion int, Nombre_Equipo varchar(50) NOT NULL, Victorias int , Derrotas int , Empates int , Puntos int);
  CREATE TABLE Partidos(EquipoA varchar(50), GolesA int, EquipoB varchar(50), GolesB int);
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `InsertarClasificacion` (IN `Posicion` INT(11), IN `Nombre_Equipo` VARCHAR(50), IN `Victorias` INT(11), IN `Derrotas` INT(11), IN `Empates` INT(11), IN `Puntos` INT(11))  NO SQL
BEGIN
	insert into clasificacion (Posicion,Nombre_Equipo,Victorias,Derrotas,Empates,Puntos) values 	(Posicion,Nombre_Equipo,Victorias,Derrotas,Empates,Puntos);
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `InsertarEquipos` (IN `ID_Equipo` INT(11), IN `Nombre_Equipo` VARCHAR(50))  NO SQL
BEGIN
	insert into equipos (ID_Equipo,Nombre_Equipo) values 	(ID_Equipo,Nombre_Equipo);
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `InsertarJugadores` (IN `ID_Jugador` INT(11), IN `Nombre_Jugador` VARCHAR(50), IN `Posicion` VARCHAR(50), IN `ID_Equipo` INT(11), IN `Nombre_Equipo` VARCHAR(50))  NO SQL
BEGIN
	insert into jugadores (ID_Jugador,Nombre_Jugador,Posicion,ID_Equipo,Nombre_Equipo) values 	(ID_Jugador,Nombre_Jugador,Posicion,ID_Equipo,Nombre_Equipo);
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `InsertarPartidos` (IN `EquipoA` VARCHAR(50), IN `GolesA` INT(11), IN `EquipoB` VARCHAR(50), IN `GolesB` INT(11))  NO SQL
BEGIN
	insert into partidos (EquipoA,GolesA,EquipoB,GolesB) values 	(EquipoA,GolesA,EquipoB,GolesB);
END$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `clasificacion`
--

CREATE TABLE `clasificacion` (
  `Posicion` int(11) DEFAULT NULL,
  `Nombre_Equipo` varchar(50) NOT NULL,
  `Victorias` int(11) DEFAULT NULL,
  `Derrotas` int(11) DEFAULT NULL,
  `Empates` int(11) DEFAULT NULL,
  `Puntos` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `clasificacion`
--

INSERT INTO `clasificacion` (`Posicion`, `Nombre_Equipo`, `Victorias`, `Derrotas`, `Empates`, `Puntos`) VALUES
(1, 'Movistar Inter', 0, 0, 0, 0),
(1, 'Barça', 0, 0, 0, 0),
(1, 'ElPozo Murcia', 0, 0, 0, 0),
(1, 'Palma Futsal', 0, 0, 0, 0),
(1, 'C.A Osasuna Magna', 0, 0, 0, 0),
(1, 'Viña Albali Valdepeñas', 0, 0, 0, 0);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `equipos`
--

CREATE TABLE `equipos` (
  `ID_Equipo` int(11) NOT NULL,
  `Nombre_Equipo` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `equipos`
--

INSERT INTO `equipos` (`ID_Equipo`, `Nombre_Equipo`) VALUES
(1, 'Movistar Inter'),
(2, 'Barça'),
(3, 'ElPozo Murcia'),
(4, 'Palma Futsal'),
(5, 'C.A Osasuna Magna'),
(6, 'Viña Albali Valdepeñas');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `jugadores`
--

CREATE TABLE `jugadores` (
  `ID_Jugador` int(11) NOT NULL,
  `Nombre_Jugador` varchar(50) DEFAULT NULL,
  `Posicion` varchar(50) DEFAULT NULL,
  `ID_Equipo` int(11) NOT NULL,
  `Nombre_Equipo` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
-- Error leyendo datos de la tabla futbol_db.jugadores: #1064 - Algo está equivocado en su sintax cerca 'FROM `futbol_db`.`jugadores`' en la linea 1

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `partidos`
--

CREATE TABLE `partidos` (
  `EquipoA` varchar(50) DEFAULT NULL,
  `GolesA` int(11) DEFAULT NULL,
  `EquipoB` varchar(50) DEFAULT NULL,
  `GolesB` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `partidos`
--

INSERT INTO `partidos` (`EquipoA`, `GolesA`, `EquipoB`, `GolesB`) VALUES
('El Pozo Murcia', 0, 'Movistar Inter', 0),
('Viña Albali Valdepeñas', 0, 'Barça', 0),
('C.A Osasuna Magna', 0, 'Palma Futsal', 0);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
