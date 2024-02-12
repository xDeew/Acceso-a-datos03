CREATE DATABASE IF NOT EXISTS gimnasio;
USE gimnasio;

-- Creación de la tabla Clientes
CREATE TABLE IF NOT EXISTS clientes
(
    id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(50) NOT NULL,
    direccion VARCHAR(50),
    telefono VARCHAR(20),
    email VARCHAR(50) UNIQUE
);

-- Creación de la tabla Suscripciones
CREATE TABLE IF NOT EXISTS suscripciones
(
    id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    tipo VARCHAR(50) NOT NULL,
    duracion INT NOT NULL,
    costo FLOAT NOT NULL,
    id_cliente INT UNSIGNED NOT NULL
);

ALTER TABLE suscripciones
ADD CONSTRAINT UNIQUE (id_cliente); -- Relacion 1-1 con clientes

-- Creación de la tabla Entrenadores
CREATE TABLE IF NOT EXISTS entrenadores
(
    id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(50) NOT NULL,
    especialidad VARCHAR(50),
    horario VARCHAR(50)
);

-- Creación de la tabla Equipamiento
CREATE TABLE IF NOT EXISTS equipamiento
(
    id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    tipo VARCHAR(50) NOT NULL,
    estado VARCHAR(50),
    costo  decimal(10, 2)
);

ALTER TABLE equipamiento
ADD COLUMN id_clase INT UNSIGNED; -- Relacion 1-N con clase

-- Creación de la tabla Clases
CREATE TABLE IF NOT EXISTS clases
(
    id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(50) NOT NULL,
    horario VARCHAR(50) NOT NULL,
    tipo VARCHAR(50) NOT NULL,
    id_entrenador INT UNSIGNED NOT NULL
);

-- Creación de la tabla Reservas
CREATE TABLE IF NOT EXISTS reservas
(
    id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP(),
    id_cliente INT UNSIGNED NOT NULL,
    id_clase INT UNSIGNED,
    id_equipamiento INT UNSIGNED
);

CREATE TABLE IF NOT EXISTS cliente_entrenador
(
    id_cliente INT UNSIGNED,
    id_entrenador INT UNSIGNED,
    fecha_inicio DATE,
    PRIMARY KEY (id_cliente, id_entrenador)
);

CREATE TABLE IF NOT EXISTS entrenador_equipamiento
(
    id_entrenador INT UNSIGNED,
    id_equipamiento INT UNSIGNED,
    frecuencia_uso VARCHAR(50),
    PRIMARY KEY (id_entrenador, id_equipamiento)
);

CREATE TABLE IF NOT EXISTS cliente_clase
(
    id_cliente INT UNSIGNED,
    id_clase INT UNSIGNED,
    asistencia TINYINT,
    PRIMARY KEY (id_cliente, id_clase)
);

CREATE TABLE IF NOT EXISTS cliente_equipamiento
(
    id_cliente INT UNSIGNED,
    id_equipamiento INT UNSIGNED,
    duracion_uso INT,
    PRIMARY KEY (id_cliente, id_equipamiento)
);

CREATE TABLE IF NOT EXISTS clase_reserva
(
    id_clase INT UNSIGNED,
    id_reserva INT UNSIGNED,
    PRIMARY KEY (id_clase, id_reserva)
);

CREATE TABLE IF NOT EXISTS entrenador_clase
(
    id_entrenador INT UNSIGNED,
    id_clase INT UNSIGNED,
    PRIMARY KEY (id_entrenador, id_clase)
);
