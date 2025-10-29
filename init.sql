-- init.sql
CREATE DATABASE ufcdb;
GO

USE ufcdb;
GO

CREATE TABLE peleadores (
                            id BIGINT IDENTITY(1,1) PRIMARY KEY,
                            nombre NVARCHAR(150) NOT NULL,
                            alias NVARCHAR(100),
                            categoria NVARCHAR(50),
                            pesoKg DECIMAL(5,2),
                            alturaCm DECIMAL(6,2),
                            alcanceCm DECIMAL(6,2),
                            nacionalidad NVARCHAR(100),
                            victorias INT DEFAULT 0,
                            derrotas INT DEFAULT 0,
                            empates INT DEFAULT 0,
                            fechaNacimiento DATE
);
GO

-- Inserta algunos registros de ejemplo
INSERT INTO peleadores (nombre, alias, categoria, pesoKg, alturaCm, alcanceCm, nacionalidad, victorias, derrotas, empates, fechaNacimiento)
VALUES
    ('Juan Perez','El Destructor','Welterweight',77.1,180,185,'Ecuador',12,3,0,'1990-05-14'),
    ('Carlos López','La Pantera','Lightweight',70.5,175,178,'Perú',15,2,1,'1992-03-02');
GO
