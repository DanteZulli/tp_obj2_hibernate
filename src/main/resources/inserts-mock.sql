-- Script de inserciones para poblar las tablas de la base de datos con datos de prueba.

-- Inserciones para la tabla 'Rol'
INSERT INTO Rol (id, nombre) VALUES (1, 'Administrador');
INSERT INTO Rol (id, nombre) VALUES (2, 'Agente de Soporte');
INSERT INTO Rol (id, nombre) VALUES (3, 'Usuario Regular');

-- Inserciones para la tabla 'Direccion'
INSERT INTO Direccion (id, calle, nro, localidad, provincia, fiscal) VALUES 
(1, 'Av. Siempre Viva', '742', 'Springfield', 'Desconocida', FALSE),
(2, 'Calle Falsa', '123', 'Nowhere', 'Otra', TRUE),
(3, 'Belgrano', '555', 'Temperley', 'Buenos Aires', FALSE);

-- Inserciones para la tabla 'Usuario'
INSERT INTO Usuario (id, nombre, email, es_admin, tipo_usuario, nro_cliente, plan, particular, nro_contacto, empresa, rol_id, direccion_id) VALUES 
(1, 'Homero Simpson', 'homero@example.com', FALSE, 'CLIENTE', 1001, 'Básico', TRUE, NULL, NULL, NULL, 1),
(2, 'Lisa Simpson', 'lisa@example.com', TRUE, 'CLIENTE', 1002, 'Premium', FALSE, NULL, NULL, NULL, 2),
(3, 'Marge Simpson', 'marge@example.com', FALSE, 'CLIENTE', 1003, 'Familiar', TRUE, NULL, NULL, NULL, 3),
(4, 'Ned Flanders', 'ned@example.com', FALSE, 'TECNICO', NULL, NULL, NULL, '1144445555', 'Los Técnicos S.R.L.', 2, 1),
(5, 'Barney Gumble', 'barney@example.com', FALSE, 'TECNICO', NULL, NULL, NULL, '1166667777', 'Soporte Total', 2, 2),
(6, 'Seymour Skinner', 'skinner@example.com', TRUE, 'TECNICO', NULL, NULL, NULL, '1188889999', 'AyudaYA', 1, 3),
(7, 'Milhouse Van Houten', 'milhouse@example.com', FALSE, 'CLIENTE', 1004, 'Básico', TRUE, NULL, NULL, NULL, 1);

-- Inserciones para la tabla 'Categoria'
INSERT INTO Categoria (id, nombre, descripcion) VALUES 
(1, 'Hardware', 'Problemas relacionados con componentes físicos.'),
(2, 'Software', 'Problemas relacionados con aplicaciones y sistemas operativos.'),
(3, 'Red', 'Problemas de conectividad y acceso a internet.');

-- Inserciones para la tabla 'Ticket'
INSERT INTO Ticket (id, titulo, descripcion, estado, prioridad, fecha_creacion, fecha_resolucion, creador_id, asignado_id, categoria_id) VALUES 
(1, 'No enciende la PC', 'La computadora no da señales de vida al presionar el botón de encendido.', 'Abierto', 'Alta', '2025-05-03 10:00:00', NULL, 1, 4, 1),
(2, 'Error al guardar archivo', 'Al intentar guardar un documento, la aplicación muestra un error.', 'En progreso', 'Media', '2025-05-03 11:30:00', NULL, 3, 5, 2),
(3, 'Internet lento', 'La conexión a internet está muy lenta y las páginas tardan en cargar.', 'Resuelto', 'Baja', '2025-05-02 15:45:00', '2025-05-03 09:00:00', 7, 4, 3);

-- Inserciones para la tabla 'Etiqueta'
INSERT INTO Etiqueta (id, nombre) VALUES 
(1, 'Urgente'),
(2, 'PC'),
(3, 'Conexión');

-- Inserciones para la tabla 'Comentario'
INSERT INTO Comentario (id, mensaje, fecha, ticket_id, usuario_id) VALUES 
(1, 'He revisado la fuente de alimentación y parece estar fallando.', '2025-05-03 10:15:00', 1, 4),
(2, 'Estoy investigando el error específico que muestra la aplicación.', '2025-05-03 12:00:00', 2, 5),
(3, 'Se reinició el módem y la velocidad mejoró.', '2025-05-03 08:55:00', 3, 1);

-- Inserciones para la tabla de relación Ticket-Etiqueta
INSERT INTO Ticket_Etiqueta (Ticket_id, Etiqueta_id) VALUES 
(1, 1),
(1, 2),
(3, 3);
