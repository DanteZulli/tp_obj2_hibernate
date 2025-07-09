package com.grupo25.tp_obj2_hibernate.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Configuración de OpenAPI/Swagger para la documentación de la API
 * Esta configuración se integra con Spring Security para manejar la
 * autenticación
 */
@Configuration
public class OpenApiConfig {

    @Bean
    OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Sistema de Gestión de Tickets - API's REST")
                        .description("Grupo 25 (OBJ2) - Spring Boot Nivel 2\n\n" +
                                "**Integrantes:**\n" +
                                "- Dante Zulli (dantezulli2004@gmail.com) [GitHub](https://github.com/DanteZulli)\n" +
                                "- Ignacio Cruz (ignaciocruz1@hotmail.com) [GitHub](https://github.com/IgnacioIA)")
                        .version("2.0")
                        .license(new License()
                                .name("MIT License")
                                .url("https://github.com/DanteZulli/tp_obj2_hibernate/blob/main/LICENSE")))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8080")
                                .description("Local - Puerto 8080")))
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .components(new Components()
                        .addSecuritySchemes("bearerAuth", new SecurityScheme()
                                .name("bearerAuth")
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .description("Autenticación por sesión de Spring Security\n\n" +
                                        "**Nota:** Esta API utiliza autenticación basada en sesiones. " +
                                        "Para probar endpoints protegidos, primero debes autenticarte " +
                                        "a través del formulario de login en `/login`")));
    }
}