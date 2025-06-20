# 🛠️ Sistema de Gestión de Tickets - Grupo 25 (OBJ2) - Proyecto Spring Nivel 1

# 📋 Requisitos Previos
- Java JDK 21
- MySQL 8.0+
- Maven (opcional, solo si no usás el wrapper incluido)

## ⚙️ Configuración de la Base de Datos

El proyecto está configurado para conectarse a MySQL con los siguientes valores por defecto:

```properties
spring.datasource.url=jdbc:mysql://localhost:3307/tp_obj2_db?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=America/Argentina/Buenos_Aires
spring.datasource.username=root
spring.datasource.password=
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect
# ... entre otras properties más
```

- La base de datos `tp_obj2_db` se crea automáticamente si no existe (Gracias a JPA/Hibernate)

> 📌 **Nota:** Podés modificar estos valores en `src/main/resources/application.properties` (o pisarlos con un perfil y otro .properties) según tu entorno. \
> Asegurate de que coincidan con la configuración de tu servidor de bases de datos.

## 📊 Inicialización de Datos

El proyecto incluye un script de inicialización (`data.sql`) que carga datos de ejemplo en la base de datos. Para activarlo:

1. Descomentá las siguientes líneas en `src/main/resources/application.properties`:
```properties
spring.sql.init.mode=always
spring.jpa.defer-datasource-initialization=true
```

2. Comentá estas líneas nuevamente después de la primera ejecución para evitar que se reinicialice la base de datos en cada inicio.

> 📌 **Nota:** También podés ejecutar el script `data.sql` directamente en tu gestor de base de datos si preferís.

### 👥 Usuarios por Defecto

El script de inicialización crea los siguientes usuarios:

**Administradores:**
- Usuario: `jperez`
- Email: `juan@example.com`

**Técnicos:**
- Usuario: `mgarcia`
- Email: `maria@example.com`

**Clientes:**
- Usuario: `clopez`
- Email: `carlos@example.com`
- Usuario: `amartinez`
- Email: `ana@example.com`

> 🔑 **Importante:** La contraseña para todos los usuarios es `1234`

## 📫 Configuración de Correo Electrónico

El sistema envía notificaciones por correo electrónico para operaciones clave, como la creación y modificación de tickets. Esta funcionalidad es gestionada por `EmailService.java` y se dispara desde `TicketRestController.java`.

### 🧪 Pruebas de Envío para Profesores

Dado que los usuarios por defecto (cargados desde `data.sql`) utilizan dominios ficticios como `example.com`, se implementó un mecanismo para facilitar las pruebas:

1.  **Configurar destinatarios de prueba:**
    En el archivo `src/main/resources/application.properties`, podés especificar una lista de correos reales para recibir todas las notificaciones:
    ```properties
    email.default.recipients=profesor1@unla.com,tester@gmail.com
    ```
    El sistema se encargará de enviar los correos a estas direcciones, en adición a los destinatarios originales.

2.  **Alternativa:**
    También es posible modificar directamente los correos electrónicos de los usuarios en la base de datos una vez que ha sido inicializada.

### ⚠️ Advertencia sobre dominios no válidos

- **Notificaciones de error:** Si se intenta enviar un correo a una dirección no válida (ej. `clopez@example.com`) y no se ha configurado la lista `email.default.recipients`, el subsistema de correo de tu proveedor (ej. Gmail) enviará un correo de error a la cuenta que figura en `spring.mail.username`, informando que no se pudo realizar la entrega.

## 🚀 Ejecución del Proyecto

1. Cloná el repositorio:
```bash
git clone [url-del-repositorio]
cd tp_obj2_hibernate
```

1. Asegurate de que MySQL esté corriendo (por defecto en el puerto 3306).

2. Ejecutá la aplicación usando el Maven wrapper:

**En Linux/macOS**
```bash
./mvnw spring-boot:run
```

**En Windows:**
```bash
mvnw.cmd spring-boot:run
```

También podés correrla desde tu IDE (IntelliJ, Eclipse, etc.) ejecutando la clase main.

3. **Accedé a la aplicación:**
   - La aplicación estará disponible en: `http://localhost:8080`
   - Puerto por defecto: **8080**

## 🎨 Frontend Template

El proyecto incluye un template de Bootstrap administrativo ubicado en:
```
startbootstrap-sb-admin-gh-pages/
```

Este directorio contiene:
- **Template SB Admin**: Plantilla de Bootstrap para interfaces administrativas
- **Assets estáticos**: CSS, JavaScript, imágenes y componentes de UI
- **Páginas de ejemplo**: Login, tablas, gráficos, etc.
- **Componentes**: Formularios, navegación, dashboard y más

> 📌 **Nota:** Este template se utiliza como base para el diseño de la interfaz de usuario de la aplicación de gestión de tickets.

# 🧰 Tecnologías Utilizadas

## Backend
- Java 21
- Spring Boot 3.5.0
- MySQL & MySQL Connector
- Spring Data JPA, Hibernate, Validation, Security
- Lombok

## Frontend
- **SB Admin Template**: Template de Bootstrap para interfaces administrativas
- **Bootstrap 5**: Framework CSS para diseño responsivo
- **FontAwesome**: Iconos vectoriales
- **Chart.js**: Librería para gráficos y visualizaciones
- **DataTables**: Plugin de jQuery para tablas interactivas

## Puertos de la Aplicación
- **Backend (Spring Boot)**: Puerto `8080`
- **Base de datos (MySQL)**: Puerto `3307` 

> 🗂️ Para consultar todas las dependencias y versiones utilizadas, podés revisar el archivo pom.xml ubicado en la raíz del proyecto.