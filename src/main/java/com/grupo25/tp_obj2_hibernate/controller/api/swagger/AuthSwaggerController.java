package com.grupo25.tp_obj2_hibernate.controller.api.swagger;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;

import com.grupo25.tp_obj2_hibernate.model.dto.LoginDTO;
import com.grupo25.tp_obj2_hibernate.model.dto.LoginResponseDTO;
import com.grupo25.tp_obj2_hibernate.model.entities.Cliente;
import com.grupo25.tp_obj2_hibernate.model.entities.Tecnico;
import com.grupo25.tp_obj2_hibernate.model.entities.Usuario;
import com.grupo25.tp_obj2_hibernate.repository.UsuarioRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/swagger/auth")
@RequiredArgsConstructor
@Tag(name = "Autenticación", description = "API para autenticación de usuarios usando DTOs con Record Class")
public class AuthSwaggerController {

    private final AuthenticationManager authenticationManager;
    private final UsuarioRepository usuarioRepository;

    @PostMapping("/login")
    @Operation(summary = "Iniciar sesión", description = "Autentica un usuario y retorna información de sesión")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login exitoso", content = @Content(schema = @Schema(implementation = LoginResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "401", description = "Credenciales incorrectas"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<LoginResponseDTO> login(
            @Parameter(description = "DTO con las credenciales de login", required = true) @RequestBody LoginDTO loginDTO) {
        log.debug("Intentando login para usuario: {}", loginDTO.username());
        
        try {
            // Crear token de autenticación
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    loginDTO.username(), 
                    loginDTO.password()
            );
            
            // Intentar autenticar
            Authentication authentication = authenticationManager.authenticate(authToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            
            // Obtener información del usuario
            Usuario usuario = usuarioRepository.findByNombreUsuario(loginDTO.username())
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
            
            // Determinar el rol del usuario
            String rol = "USER";
            if (usuario.isEsAdmin()) {
                rol = "ADMIN";
            } else if (usuario instanceof Cliente) {
                rol = "CLIENTE";
            } else if (usuario instanceof Tecnico) {
                rol = "TECNICO";
            }
            
            LoginResponseDTO response = new LoginResponseDTO(
                    usuario.getNombreUsuario(),
                    usuario.getNombre(),
                    usuario.getEmail(),
                    usuario.isEsAdmin(),
                    rol,
                    "Login exitoso"
            );
            
            log.info("Login exitoso para usuario: {}", loginDTO.username());
            return ResponseEntity.ok(response);
            
        } catch (AuthenticationException e) {
            log.warn("Login fallido para usuario: {}", loginDTO.username());
            LoginResponseDTO errorResponse = new LoginResponseDTO(
                    loginDTO.username(),
                    null,
                    null,
                    false,
                    null,
                    "Credenciales incorrectas"
            );
            return ResponseEntity.status(401).body(errorResponse);
        } catch (Exception e) {
            log.error("Error durante el login para usuario: {}", loginDTO.username(), e);
            LoginResponseDTO errorResponse = new LoginResponseDTO(
                    loginDTO.username(),
                    null,
                    null,
                    false,
                    null,
                    "Error interno del servidor"
            );
            return ResponseEntity.status(500).body(errorResponse);
        }
    }
} 