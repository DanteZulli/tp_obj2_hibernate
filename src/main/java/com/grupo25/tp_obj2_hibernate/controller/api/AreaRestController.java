package com.grupo25.tp_obj2_hibernate.controller.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.grupo25.tp_obj2_hibernate.model.entities.Area;
import com.grupo25.tp_obj2_hibernate.service.AreaService;
import com.grupo25.tp_obj2_hibernate.exception.AreaException;

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
@RequestMapping("/api/areas")
@RequiredArgsConstructor
@Tag(name = "Legacy - Gestión de Áreas", description = "Endpoints existentes para gestión de áreas (antes de la implementación de Swagger)")
public class AreaRestController {

    private final AreaService areaService;

    @PostMapping("/crear")
    @Operation(summary = "Crear nueva área", description = "Crea una nueva área o departamento en el sistema de tickets")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Área creada exitosamente",
                    content = @Content(schema = @Schema(implementation = Area.class))),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<Area> crearArea(
            @Parameter(description = "Nombre de la nueva área o departamento", required = true, example = "Soporte Técnico")
            @RequestParam String nombre) {
        log.debug("Creando nueva área con nombre: {}", nombre);
        try {
            Area areaCreada = areaService.crearArea(nombre);
            return ResponseEntity.ok(areaCreada);
        } catch (AreaException e) {
            log.error("Error al crear la área con nombre: {}", nombre, e);
            throw e;
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Modificar área existente", description = "Modifica el nombre de un área o departamento existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Área modificada exitosamente",
                    content = @Content(schema = @Schema(implementation = Area.class))),
        @ApiResponse(responseCode = "404", description = "Área no encontrada"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
    public ResponseEntity<Area> modificarArea(
            @Parameter(description = "ID de la área a modificar", required = true, example = "1")
            @PathVariable int id, 
            @Parameter(description = "Nuevo nombre de la área", required = true, example = "Soporte Técnico Avanzado")
            @RequestParam String nombre) {
        log.debug("Modificando área con ID: {}", id);
        try {
            Area areaModificada = areaService.modificarArea(id, nombre);
            return ResponseEntity.ok(areaModificada);
        } catch (AreaException e) {
            log.error("Error al modificar el área con ID: {}", id, e);
            throw e;
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener área por ID", description = "Recupera la información de un área específica")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Área encontrada exitosamente",
                    content = @Content(schema = @Schema(implementation = Area.class))),
        @ApiResponse(responseCode = "404", description = "Área no encontrada")
    })
    public ResponseEntity<Area> obtenerArea(
            @Parameter(description = "ID de la área a obtener", required = true, example = "1")
            @PathVariable int id) {
        log.debug("Obteniendo área con ID: {}", id);
        try {
            Area area = areaService.obtenerArea(id);
            return ResponseEntity.ok(area);
        } catch (AreaException e) {
            log.error("Error al obtener el área con ID: {}", id, e);
            throw e;
        }
    }
}