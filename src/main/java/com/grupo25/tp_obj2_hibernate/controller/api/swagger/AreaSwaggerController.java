package com.grupo25.tp_obj2_hibernate.controller.api.swagger;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.grupo25.tp_obj2_hibernate.model.entities.Area;
import com.grupo25.tp_obj2_hibernate.model.dto.AreaDTO;
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
@RequestMapping("/api/swagger/areas")
@RequiredArgsConstructor
@Tag(name = "Gestión de Áreas", description = "API para gestión de áreas usando DTOs con Record Class")
public class AreaSwaggerController {

    private final AreaService areaService;

    @PostMapping("/crear")
    @Operation(summary = "Crear nueva área", description = "Crea una nueva área usando DTO")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Área creada exitosamente", content = @Content(schema = @Schema(implementation = AreaDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<AreaDTO> crearArea(
            @Parameter(description = "DTO con los datos de la nueva área", required = true) @RequestBody AreaDTO areaDTO) {
        log.debug("Creando nueva área con DTO: {}", areaDTO);
        try {
            Area areaCreada = areaService.crearArea(areaDTO.nombre());
            AreaDTO responseDTO = new AreaDTO(areaCreada.getId(), areaCreada.getNombre());
            return ResponseEntity.ok(responseDTO);
        } catch (AreaException e) {
            log.error("Error al crear la área con DTO: {}", areaDTO, e);
            throw e;
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener área por ID", description = "Recupera la información de un área específica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Área encontrada exitosamente", content = @Content(schema = @Schema(implementation = AreaDTO.class))),
            @ApiResponse(responseCode = "404", description = "Área no encontrada")
    })
    public ResponseEntity<AreaDTO> obtenerArea(
            @Parameter(description = "ID de la área a obtener", required = true, example = "1") @PathVariable int id) {
        log.debug("Obteniendo área con ID: {}", id);
        try {
            Area area = areaService.obtenerArea(id);
            AreaDTO responseDTO = new AreaDTO(area.getId(), area.getNombre());
            return ResponseEntity.ok(responseDTO);
        } catch (AreaException e) {
            log.error("Error al obtener el área con ID: {}", id, e);
            throw e;
        }
    }
}