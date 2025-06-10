-- Tabla Persona
CREATE TABLE Persona (
    Id SERIAL PRIMARY KEY,
    DNI VARCHAR(9) NOT NULL UNIQUE CHECK (DNI ~ '^[0-9]{8}[A-Z]$'),
    Nombre VARCHAR NOT NULL,
    Correo VARCHAR UNIQUE
);

-- Tabla Masajista
CREATE TABLE Masajista (
    Id_Persona BIGINT PRIMARY KEY,
    FOREIGN KEY (Id_Persona) REFERENCES Persona(Id) ON DELETE CASCADE ON UPDATE CASCADE
);

-- Tabla Masaje
CREATE TABLE Masaje (
    Id SERIAL PRIMARY KEY,
    Nombre VARCHAR NOT NULL,
    Duracion INT NOT NULL,
    Precio NUMERIC(10, 2) NOT NULL CHECK (Precio > 0)
);

-- Tabla Cliente
CREATE TABLE Cliente (
    Id_Persona BIGINT PRIMARY KEY,
    Telefono VARCHAR UNIQUE,
    Id_Masajista BIGINT CHECK (Id_Masajista <> Id_Persona),
    FOREIGN KEY (Id_Persona) REFERENCES Persona(Id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (Id_Masajista) REFERENCES Masajista(Id_Persona) ON DELETE SET NULL ON UPDATE CASCADE
);

-- Tabla Capacitado
CREATE TABLE Capacitado (
    Id_Masaje BIGINT,
    Id_Masajista BIGINT,
    PRIMARY KEY (Id_Masaje, Id_Masajista),
    FOREIGN KEY (Id_Masaje) REFERENCES Masaje(Id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (Id_Masajista) REFERENCES Masajista(Id_Persona) ON DELETE CASCADE ON UPDATE CASCADE
);
