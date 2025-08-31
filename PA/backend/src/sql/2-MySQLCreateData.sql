-- ----------------------------------------------------------------------------
-- Put here INSERT statements for inserting data required by the application
-- in the "paproject" database.
-------------------------------------------------------------------------------

INSERT INTO User (userName, password, firstName, lastName, email, role)
    VALUES
        ('usuario1', '$2a$10$V5QjEd9hDENFwC2bMUmgGehZoffn/JkyLJzpkRC2ChESG4C9a5Oye', 'Usuario', '1', 'usuario1@pa-gei.udc.es', 0),
        ('usuario2', '$2a$10$V5QjEd9hDENFwC2bMUmgGehZoffn/JkyLJzpkRC2ChESG4C9a5Oye', 'Usuario', '2', 'usuario2@pa-gei.udc.es', 0),
        ('usuario3', '$2a$10$V5QjEd9hDENFwC2bMUmgGehZoffn/JkyLJzpkRC2ChESG4C9a5Oye', 'Usuario', '3', 'usuario3@pa-gei.udc.es', 0);

INSERT INTO Categoria (name) VALUES ('Portátiles');
INSERT INTO Categoria (name) VALUES ('Pantallas');

INSERT INTO Producto (nombre, descripcion, fechaPublicacion, fechaFinPuja, precioSalida, valorActual, informacionEnvio, categoriaId, userId, version)
    VALUES
        ('Portátil 1', 'Portátil gama alta', NOW(), '2024-03-01 00:00:00', 500.00, 500.00, 'Envío en 24h', 1, 1, 0),
        ('Portátil 2', 'Portátil gama media', NOW(), '2025-09-01 00:00:00', 10.00, 10.00, 'Envío en 48h', 1, 1, 0),
        ('Pantalla 1', 'Pantalla 27 pulgadas', NOW(), '2025-10-01 00:00:00', 200.00, 200.00, 'Recogida en tienda disponible', 2, 1, 0),
        ('Pantalla 2', 'Pantalla 24 pulgadas', NOW(), '2025-11-01 00:00:00', 150.00, 150.00, 'Entrega en 72h', 2, 1, 0);

-- Trabajo Tutelado

INSERT INTO User (userName, password, firstName, lastName, email, role)
    VALUES
        ('testuser1', '$2a$10$V5QjEd9hDENFwC2bMUmgGehZoffn/JkyLJzpkRC2ChESG4C9a5Oye', 'TestUser', '1', 'testuser1@pa-gei.udc.es', 0),
        ('testuser2', '$2a$10$V5QjEd9hDENFwC2bMUmgGehZoffn/JkyLJzpkRC2ChESG4C9a5Oye', 'TestUser', '2', 'testuser2@pa-gei.udc.es', 0);

INSERT INTO Categoria (name) VALUES ('Test');

INSERT INTO Producto (nombre, descripcion, fechaPublicacion, fechaFinPuja, precioSalida, valorActual, informacionEnvio, categoriaId, userId, version)
    VALUES
        ('Test Product 1', 'Producto del TT', NOW(), '2025-12-31 23:59:00', 10.00, 10.00, 'Envío en 24h', 3, 5, 0);
