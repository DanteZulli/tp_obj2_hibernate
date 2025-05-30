# Sistema de Gestión de Tickets - Segunda Entrega - Grupo 25 - OBJ2

## Requisitos Previos

- Java JDK 21
- MySQL 8.0 o superior

## Configuración de la Base de Datos

El proyecto está configurado para utilizar MySQL con los siguientes valores por defecto:

- URL: `jspring.datasource.url=jdbc:mysql://localhost:3306/tp_obj2_db?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=America/Argentina/Buenos_Aires`
- Usuario: `root`
- Contraseña: No definida (dejar en blanco)
- Driver: `com.mysql.cj.jdbc.Driver`
- Dialecto: `org.hibernate.dialect.MySQLDialect`
- La base de datos `tp_obj2_db` se creará automáticamente si no existe
- El esquema de la base de datos se generará automáticamente al iniciar la aplicación

## Configuración del Proyecto

1. Clonar el repositorio:
```bash
git clone [url-del-repositorio]
cd tp_obj2_hibernate
```

2. Verificar que MySQL esté corriendo en el puerto 3306

3. Si necesita modificar las credenciales de la base de datos, puede hacerlo en:
   - `src/main/resources/application.properties`

## Ejecutar el Proyecto

Puede ejecutar el proyecto usando el Maven wrapper incluido:

### En Linux/macOS:
```bash
./mvnw spring-boot:run
```

### En Windows:
```bash
mvnw.cmd spring-boot:run
```

o con el IDE de preferencia.

## Tecnologías Utilizadas

- Java 21
- Spring Boot 3.5.0
- Spring Data JPA
- MySQL Connector
- Lombok