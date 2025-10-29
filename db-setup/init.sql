-- Crear la base de datos si no existe
IF NOT EXISTS (SELECT name FROM sys.databases WHERE name = N'UFCDB')
BEGIN
    CREATE DATABASE UFCDB;
END
GO

USE UFCDB;
GO

-- Crear la tabla de Peleadores
IF NOT EXISTS (SELECT * FROM sysobjects WHERE name='Fighters' and xtype='U')
BEGIN
    CREATE TABLE Fighters (
        FighterID INT PRIMARY KEY IDENTITY(1,1),
        FirstName NVARCHAR(100) NOT NULL,
        LastName NVARCHAR(100) NOT NULL,
        Nickname NVARCHAR(100),
        WeightClass NVARCHAR(50),
        Wins INT DEFAULT 0,
        Losses INT DEFAULT 0,
        Draws INT DEFAULT 0
    );
END
GO

-- Insertar algunos datos de ejemplo si la tabla está vacía
IF (SELECT COUNT(*) FROM Fighters) = 0
BEGIN
    INSERT INTO Fighters (FirstName, LastName, Nickname, WeightClass, Wins, Losses, Draws)
    VALUES
    ('Islam', 'Makhachev', 'N/A', 'Lightweight', 26, 1, 0),
    ('Jon', 'Jones', 'Bones', 'Heavyweight', 27, 1, 0),
    ('Alex', 'Pereira', 'Poatan', 'Light Heavyweight', 10, 2, 0),
    ('Ilia', 'Topuria', 'El Matador', 'Featherweight', 15, 0, 0),
    ('Sean', 'OMalley', 'Suga', 'Bantamweight', 18, 1, 0);
END
GO