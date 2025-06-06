-- Crear esquema si no existe
CREATE SCHEMA IF NOT EXISTS tp_obj2_db;

-- Usar el esquema
USE tp_obj2_db;

-- Verificar si las tablas existen
SET @tables_exist = (
    SELECT COUNT(*) 
    FROM information_schema.tables 
    WHERE table_schema = 'tp_obj2_db' 
    AND table_name IN ('usuarios', 'tecnicos', 'clientes')
);

-- Solo ejecutar los scripts de datos si las tablas no existen
SET @should_init = IF(@tables_exist = 0, 1, 0); 