# 🎫 Swagger/OpenAPI - Sistema de Gestión de Tickets

## 📋 Descripción
Esta aplicación utiliza **SpringDoc OpenAPI** para generar documentación automática de la API REST. Swagger UI proporciona una interfaz web interactiva para explorar y probar los endpoints de la API.

**🎓 Proyecto:** Spring Boot Nivel 2 - Objetos 2  
**👥 Grupo 25:** Ignacio Cruz & Dante Zulli  
**🏫 Cátedra:** Objetos 2

## 🚀 Acceso a Swagger UI

Una vez que la aplicación esté ejecutándose, puedes acceder a Swagger UI a través de:

- **🎨 Interfaz Web**: http://localhost:8080/swagger-ui.html
- **📄 Documentación JSON**: http://localhost:8080/v3/api-docs
- **🏠 Aplicación Principal**: http://localhost:8080

## Características Configuradas

### 1. Configuración de Seguridad
- Swagger UI está configurado para funcionar con Spring Security
- Los endpoints de Swagger son accesibles sin autenticación para facilitar el desarrollo
- La documentación incluye información sobre autenticación Bearer Token

### 2. Configuración de la Interfaz
- **Ordenamiento**: Los endpoints se ordenan por método HTTP y luego alfabéticamente
- **Expansión**: Los endpoints están colapsados por defecto para mejor organización
- **Try it out**: Habilitado para probar endpoints directamente desde la interfaz
- **Duración de requests**: Muestra el tiempo de respuesta de cada petición

### 3. Información de la API
- **🎫 Título**: Sistema de Gestión de Tickets - API REST
- **📝 Descripción**: Spring Boot Nivel 2 - Trabajo práctico para Objetos 2
- **📦 Versión**: 2.0.0
- **👥 Contacto**: Grupo 25 - Objetos 2 (dantezulli2004@gmail.com)
- **🔗 Repositorio**: https://github.com/grupo25/tp_obj2_hibernate

## Cómo Documentar Nuevos Endpoints

### 1. Anotaciones Básicas

```java
@Tag(name = "Nombre del Grupo", description = "Descripción del grupo de endpoints")
@RestController
@RequestMapping("/api/ejemplo")
public class EjemploController {

    @Operation(summary = "Resumen del endpoint", description = "Descripción detallada")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Operación exitosa"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos"),
        @ApiResponse(responseCode = "404", description = "Recurso no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Ejemplo> obtenerEjemplo(
            @Parameter(description = "ID del ejemplo", required = true, example = "1")
            @PathVariable int id) {
        // Implementación
    }
}
```

### 2. Anotaciones de Parámetros

```java
@Parameter(description = "Descripción del parámetro", required = true, example = "valor ejemplo")
@RequestParam String parametro

@Parameter(description = "ID del recurso", required = true, example = "123")
@PathVariable int id
```

### 3. Anotaciones de Respuesta

```java
@ApiResponse(responseCode = "200", description = "Operación exitosa",
            content = @Content(schema = @Schema(implementation = MiClase.class)))
@ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
@ApiResponse(responseCode = "500", description = "Error interno del servidor")
```

## Configuración en application.properties

```properties
# Rutas de la documentación
springdoc.api-docs.path=/v3/api-docs
springdoc.swagger-ui.path=/swagger-ui.html

# Configuración de la interfaz
springdoc.swagger-ui.operationsSorter=method
springdoc.swagger-ui.tagsSorter=alpha
springdoc.swagger-ui.doc-expansion=none
springdoc.swagger-ui.try-it-out-enabled=true
springdoc.swagger-ui.display-request-duration=true

# Tipos de contenido por defecto
springdoc.default-produces-media-type=application/json
springdoc.default-consumes-media-type=application/json
```

## 📚 Endpoints Documentados

### 🏢 Áreas
- `POST /api/areas/crear` - Crear nueva área
- `PUT /api/areas/{id}` - Modificar área existente
- `GET /api/areas/{id}` - Obtener área por ID

### 🎫 Tickets
- `POST /api/tickets/crear` - Crear nuevo ticket
- `PUT /api/tickets/{id}` - Modificar ticket
- `GET /api/tickets/{id}` - Obtener ticket por ID
- `GET /api/tickets/` - Obtener todos los tickets
- `PUT /api/tickets/{id}/asignar/{idTecnico}` - Asignar ticket a técnico
- `PUT /api/tickets/{id}/tomar` - Tomar ticket (auto-asignación)
- `PUT /api/tickets/{id}/prioridad` - Cambiar prioridad
- `GET /api/tickets/filtrar` - Filtrar tickets por criterios
- Y muchos más...

### 🏷️ Categorías, Etiquetas, Comentarios, Revisiones
- Endpoints CRUD completos para cada entidad
- Gestión de relaciones entre entidades
- Filtros y búsquedas avanzadas

## 🔐 Autenticación

La API utiliza autenticación basada en sesiones de Spring Security. Para probar endpoints protegidos:

1. **👤 Autenticación**: Primero autentícate a través del formulario de login en `/login`
2. **🧪 Pruebas**: Luego usa Swagger UI para probar los endpoints
3. **🍪 Sesión**: Los cookies de sesión se mantendrán automáticamente

### 👥 Usuarios de Prueba (cargados desde data.sql)
- **Admin**: `jperez` / `1234`
- **Técnico**: `mgarcia` / `1234`
- **Cliente**: `clopez` / `1234` o `amartinez` / `1234`

## ⚙️ Personalización

### 🎨 Cambiar Información de la API

Edita la clase `OpenApiConfig.java` para modificar:
- 📝 Título y descripción
- 👥 Información de contacto
- 📄 Licencia
- 🖥️ Servidores disponibles

### Agregar Nuevos Servidores

```java
.servers(List.of(
    new Server().url("http://localhost:8080").description("Desarrollo"),
    new Server().url("https://api.produccion.com").description("Producción")
))
```

### Configurar Autenticación JWT

Si implementas JWT en el futuro, actualiza la configuración de seguridad:

```java
.addSecuritySchemes("bearerAuth", new SecurityScheme()
    .name("bearerAuth")
    .type(SecurityScheme.Type.HTTP)
    .scheme("bearer")
    .bearerFormat("JWT"))
```

## 🔧 Troubleshooting

### 🚫 Swagger UI no se carga
- ✅ Verifica que la aplicación esté ejecutándose en el puerto 8080
- 📋 Revisa los logs de Spring Boot para errores
- 📦 Asegúrate de que las dependencias de SpringDoc estén en el classpath

### 📝 Endpoints no aparecen
- 🎯 Verifica que los controladores tengan la anotación `@RestController`
- 🔗 Asegúrate de que los métodos tengan anotaciones HTTP (`@GetMapping`, `@PostMapping`, etc.)
- ⚠️ Revisa que no haya errores de compilación

### 🔐 Problemas de autenticación
- 🛡️ Verifica la configuración de Spring Security
- ✅ Asegúrate de que los endpoints de Swagger estén permitidos sin autenticación
- 📋 Revisa los logs de seguridad para más detalles

---

## 🎓 Créditos

**Grupo 25 - Objetos 2**  
👨‍💻 **Ignacio Cruz**: [@IgnacioIA](https://github.com/IgnacioIA)  
👨‍💻 **Dante Zulli**: [@DanteZulli](https://github.com/DanteZulli)  

**🏫 Cátedra:** Objetos 2  
**📚 Proyecto:** Spring Boot Nivel 2  
**🔗 Repositorio:** https://github.com/grupo25/tp_obj2_hibernate 