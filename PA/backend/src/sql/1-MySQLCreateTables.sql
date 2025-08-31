-- Desactivar la comprobación de claves foráneas
SET FOREIGN_KEY_CHECKS = 0;

-- Primero, eliminar las tablas existentes, si es necesario.
DROP TABLE IF EXISTS Puja;
DROP TABLE IF EXISTS Producto;
DROP TABLE IF EXISTS Categoria;
DROP TABLE IF EXISTS User;

-- Activar nuevamente la comprobación de claves foráneas
SET FOREIGN_KEY_CHECKS = 1;

-- Crear la tabla User.
CREATE TABLE User (
    id BIGINT NOT NULL AUTO_INCREMENT,
    userName VARCHAR(60) COLLATE latin1_bin NOT NULL,
    password VARCHAR(60) NOT NULL,
    firstName VARCHAR(60) NOT NULL,
    lastName VARCHAR(60) NOT NULL,
    email VARCHAR(60) NOT NULL,
    role TINYINT NOT NULL,
    CONSTRAINT UserPK PRIMARY KEY (id),
    CONSTRAINT UserNameUniqueKey UNIQUE (userName)
) ENGINE = InnoDB;

CREATE INDEX UserIndexByUserName ON User (userName);

-- Crear la tabla Categoria.
CREATE TABLE Categoria (
    id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(60) NOT NULL,
    CONSTRAINT CategoryPK PRIMARY KEY (id),
    CONSTRAINT CategoryNameUniqueKey UNIQUE (name)
) ENGINE = InnoDB;

-- Crear la tabla Producto.
CREATE TABLE Producto (
    id BIGINT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(60) COLLATE latin1_bin NOT NULL,
    descripcion VARCHAR(2000) NOT NULL,
    fechaPublicacion DATETIME NOT NULL,
    fechaFinPuja DATETIME NOT NULL,
    precioSalida DECIMAL(11, 2) NOT NULL,
    valorActual DECIMAL(11, 2) NOT NULL,
    informacionEnvio VARCHAR(2000) NOT NULL,
    categoriaId BIGINT NOT NULL,
    userId BIGINT NOT NULL,
    version BIGINT NOT NULL,

    CONSTRAINT ProductoPK PRIMARY KEY (id),
    CONSTRAINT ProductoCategoriaFK FOREIGN KEY (categoriaId) REFERENCES Categoria (id),
    CONSTRAINT ProductoUserFK FOREIGN KEY (userId) REFERENCES User (id)
) ENGINE = InnoDB;

-- Crear la tabla Puja.
CREATE TABLE Puja (
    id BIGINT NOT NULL AUTO_INCREMENT,
    userId BIGINT NOT NULL,
    productoId BIGINT NOT NULL,
    valor DECIMAL(11, 2) NOT NULL,
    fecha DATETIME NOT NULL,
    CONSTRAINT PujaPK PRIMARY KEY (id),
    CONSTRAINT fk_pujaUser FOREIGN KEY (userId) REFERENCES User (id),
    CONSTRAINT fk_pujaProducto FOREIGN KEY (productoId) REFERENCES Producto (id)
) ENGINE = InnoDB;

-- Añadir el campo pujaGanadoraId en Producto
ALTER TABLE Producto
    ADD COLUMN pujaGanadoraId BIGINT NULL;

-- Añadir la restricción para pujaGanadoraId en Producto
ALTER TABLE Producto
    ADD CONSTRAINT ProductoPujaGanadoraFK
    FOREIGN KEY (pujaGanadoraId)
    REFERENCES Puja (id);
