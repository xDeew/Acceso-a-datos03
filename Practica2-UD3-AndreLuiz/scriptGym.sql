CREATE DATABASE IF NOT EXISTS gimnasiodb;
USE gimnasiodb;

-- Creación de tabla Clientes
DROP TABLE IF EXISTS `clientes`;
CREATE TABLE `clientes` (
  `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(50) NOT NULL,
  `direccion` VARCHAR(50) DEFAULT NULL,
  `telefono` VARCHAR(20) DEFAULT NULL,
  `email` VARCHAR(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Creación de tabla Entrenadores
DROP TABLE IF EXISTS `entrenadores`;
CREATE TABLE `entrenadores` (
  `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(50) NOT NULL,
  `especialidad` VARCHAR(50) DEFAULT NULL,
  `horario` VARCHAR(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Creación de tabla Clases
DROP TABLE IF EXISTS `clases`;
CREATE TABLE `clases` (
  `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(50) NOT NULL,
  `id_entrenador` INT(10) UNSIGNED NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_id_entrenador` (`id_entrenador`),
  CONSTRAINT `fk_entrenadores_clases` FOREIGN KEY (`id_entrenador`) REFERENCES `entrenadores` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

ALTER TABLE clases
ADD COLUMN descripcion VARCHAR(10000);

 
DROP TABLE IF EXISTS `suscripciones`;
CREATE TABLE `suscripciones` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `tipo` varchar(50) NOT NULL,
  `duracion` int(11) NOT NULL,
  `costo` float NOT NULL,
  `id_cliente` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_cliente` (`id_cliente`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

DROP TABLE IF EXISTS `entrenador_clase`;
CREATE TABLE `entrenador_clase` (
  `id_entrenador` INT(10) UNSIGNED NOT NULL,
  `id_clase` INT(10) UNSIGNED NOT NULL,
  PRIMARY KEY (`id_entrenador`, `id_clase`),
  CONSTRAINT `fk_entrenador_clase_entrenadores` FOREIGN KEY (`id_entrenador`) REFERENCES `entrenadores` (`id`),
  CONSTRAINT `fk_entrenador_clase_clases` FOREIGN KEY (`id_clase`) REFERENCES `clases` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

