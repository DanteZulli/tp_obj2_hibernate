package com.grupo25.tp_obj2_hibernate.controller.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.grupo25.tp_obj2_hibernate.model.entities.Etiqueta;
import com.grupo25.tp_obj2_hibernate.service.EtiquetaService;
import com.grupo25.tp_obj2_hibernate.exception.EtiquetaException;

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
@RequestMapping("/api/etiquetas")
@RequiredArgsConstructor
@Tag(name = "Gestión de Etiquetas", description = "API para gestionar etiquetas de tickets del sistema de soporte técnico")
public class EtiquetaRestController {

    private final EtiquetaService etiquetaService;
    
    @PostMapping("/crear")
    @Operation(summary = "Crear nueva etiqueta", description = "Crea una nueva etiqueta para categorizar tickets")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Etiqueta creada exitosamente",
                    content = @Content(schema = @Schema(implementation = Etiqueta.class))),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<Etiqueta> crearEtiqueta(
            @Parameter(description = "Nombre de la nueva etiqueta", required = true, example = "Urgente")
            @RequestParam String nombre) {
        log.debug("Creando nueva etiqueta con nombre: {}", nombre);
        try {
            Etiqueta etiquetaCreada = etiquetaService.crearEtiqueta(nombre);
            return ResponseEntity.ok(etiquetaCreada);
        } catch (EtiquetaException e) {
            log.error("Error al crear la etiqueta: {}", nombre, e);
            throw e;
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Modificar etiqueta existente", description = "Modifica el nombre de una etiqueta existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Etiqueta modificada exitosamente",
                    content = @Content(schema = @Schema(implementation = Etiqueta.class))),
        @ApiResponse(responseCode = "404", description = "Etiqueta no encontrada"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
    public ResponseEntity<Etiqueta> modificarEtiqueta(
            @Parameter(description = "ID de la etiqueta a modificar", required = true, example = "1")
            @PathVariable int id, 
            @Parameter(description = "Nuevo nombre de la etiqueta", required = true, example = "Crítico")
            @RequestParam String nombre) {
        log.debug("Modificando etiqueta con ID: {}", id);
        try {
            Etiqueta etiqueta = etiquetaService.modificarEtiqueta(id, nombre);
            return ResponseEntity.ok(etiqueta);
        } catch (EtiquetaException e) {
            log.error("Error al actualizar la etiqueta: {}", nombre, e);
            throw e;
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener etiqueta por ID", description = "Recupera la información de una etiqueta específica")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Etiqueta encontrada exitosamente",
                    content = @Content(schema = @Schema(implementation = Etiqueta.class))),
        @ApiResponse(responseCode = "404", description = "Etiqueta no encontrada")
    })
    public ResponseEntity<Etiqueta> obtenerEtiqueta(
            @Parameter(description = "ID de la etiqueta a obtener", required = true, example = "1")
            @PathVariable int id) {
        log.debug("Obteniendo etiqueta con ID: {}", id);
        try {
            Etiqueta etiqueta = etiquetaService.obtenerEtiqueta(id);
            return ResponseEntity.ok(etiqueta);
        } catch (EtiquetaException e) {
            log.error("Error al obtener la etiqueta con ID: {}", id, e);
            throw e;
        }
    }
}