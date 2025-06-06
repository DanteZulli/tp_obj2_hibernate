-- Provincias
INSERT INTO provincias (nombre) VALUES 
('Buenos Aires'),
('Córdoba'),
('Santa Fe');

-- Localidades
INSERT INTO localidades (nombre, provincia_id) VALUES 
('La Plata', 1),
('Córdoba Capital', 2),
('Rosario', 3);

-- Direcciones
INSERT INTO direcciones (calle, nro, codigo_postal, localidad_id, fiscal) VALUES 
('Calle 1', '123', '1900', 1, true),
('Av. Colón', '500', '5000', 2, true),
('Av. Pellegrini', '1000', '2000', 3, false);

-- Áreas
INSERT INTO areas (nombre) VALUES 
('Soporte Técnico'),
('Desarrollo'),
('Infraestructura');

-- Categorías
INSERT INTO categorias (nombre, descripcion) VALUES 
('Hardware', 'Problemas relacionados con componentes físicos'),
('Software', 'Problemas relacionados con aplicaciones y sistemas'),
('Redes', 'Problemas relacionados con conectividad');

-- Etiquetas
INSERT INTO etiquetas (nombre) VALUES 
('Urgente'),
('Bug'),
('Mejora'),
('Consulta');

-- Usuarios (Técnicos)
INSERT INTO usuarios (nombre, nombre_usuario, contrasenia, email, es_admin) VALUES 
('Juan Pérez', 'jperez', '$2y$10$.KjybsrGy2vFvfeofh.cG.qsByH3lNsobFzf3/sM402XfLSzXgkUW', 'juan@example.com', true),
('María García', 'mgarcia', '$2y$10$.KjybsrGy2vFvfeofh.cG.qsByH3lNsobFzf3/sM402XfLSzXgkUW', 'maria@example.com', false);

-- Técnicos
INSERT INTO tecnicos (usuario_id, nro_contacto, empresa, area_id) VALUES 
(1, '1234567890', 'Tech Solutions', 1),
(2, '0987654321', 'IT Services', 2);

-- Usuarios (Clientes)
INSERT INTO usuarios (nombre, nombre_usuario, contrasenia, email, es_admin) VALUES 
('Carlos López', 'clopez', '$2y$10$.KjybsrGy2vFvfeofh.cG.qsByH3lNsobFzf3/sM402XfLSzXgkUW', 'carlos@example.com', false),
('Ana Martínez', 'amartinez', '$2y$10$.KjybsrGy2vFvfeofh.cG.qsByH3lNsobFzf3/sM402XfLSzXgkUW', 'ana@example.com', false);

-- Clientes
INSERT INTO clientes (usuario_id, nro_cliente, plan, particular, direccion_id) VALUES 
(3, 1001, 'Premium', true, 1),
(4, 1002, 'Business', false, 2);

-- Tickets
INSERT INTO tickets (titulo, descripcion, estado, prioridad, fecha_creacion, creador_id, asignado_id, categoria_id) VALUES 
('Problema con PC', 'La computadora no enciende', 'ABIERTO', 'ALTA', '2024-03-15 10:00:00', 3, 1, 1),
('Error en aplicación', 'La aplicación se cierra inesperadamente', 'EN_PROGRESO', 'MEDIA', '2024-03-15 11:00:00', 4, 2, 2);

-- Comentarios
INSERT INTO comentarios (mensaje, fecha, ticket_id, usuario_id) VALUES 
('Intenté reiniciar la PC pero no funcionó', '2024-03-15 10:05:00', 1, 3),
('Por favor, necesito ayuda urgente', '2024-03-15 10:10:00', 1, 3),
('Estoy revisando el problema', '2024-03-15 10:15:00', 1, 1);

-- Revisiones
INSERT INTO revisiones (fecha_cambio, campo_modificado, valor_anterior, valor_nuevo, observaciones, ticket_id, usuario_id) VALUES 
('2024-03-15 10:20:00', 'estado', 'ABIERTO', 'EN_PROGRESO', 'Iniciando diagnóstico del hardware', 1, 1),
('2024-03-15 11:20:00', 'prioridad', 'MEDIA', 'ALTA', 'El error es más crítico de lo que parecía', 2, 2);

-- Relación Ticket-Etiqueta
INSERT INTO ticket_etiqueta (ticket_id, etiqueta_id) VALUES 
(1, 1),
(1, 2),
(2, 3); 