-- Insertar personas (3 masajistas, 3 clientes)
INSERT INTO Persona (DNI, Nombre, Correo) VALUES
('12345678A', 'Carlos', 'carlos@spa.com'),
('23456789B', 'Ana', 'ana@spa.com'),
('34567890C', 'Luis', 'luis@spa.com'),
('45678901D', 'Maria', 'maria@cliente.com'),
('56789012E', 'Pedro', 'pedro@cliente.com'),
('67890123F', 'Lucia', 'lucia@cliente.com');

-- Insertar masajistas
INSERT INTO Masajista (Id_Persona) VALUES
(1),
(2),
(3);

-- Insertar masajes
INSERT INTO Masaje (Nombre, Duracion, Precio) VALUES
('Relajante', 60, 40.00),
('Descontracturante', 45, 50.00),
('Piedras calientes', 90, 70.00);

-- Insertar clientes
INSERT INTO Cliente (Id_Persona, Telefono, Id_Masajista) VALUES
(4, '600111222', 1),  -- María con Carlos
(5, '600333444', 2),  -- Pedro con Ana
(6, '600555666', 1);  -- Lucía con Carlos

-- Asignar masajistas a masajes
INSERT INTO Capacitado (Id_Masaje, Id_Masajista) VALUES
(1, 1),  -- Carlos capacitado en Relajante
(2, 1),  -- Carlos capacitado en Descontracturante
(2, 2),  -- Ana capacitada en Descontracturante
(3, 2),  -- Ana capacitada en Piedras calientes
(1, 3);  -- Luis capacitado en Relajante
