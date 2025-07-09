package com.grupo25.tp_obj2_hibernate.controller.api.swagger;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.grupo25.tp_obj2_hibernate.model.entities.Etiqueta;
import com.grupo25.tp_obj2_hibernate.model.dto.EtiquetaDTO;
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
@RequestMapping("/api/swagger/etiquetas")
@RequiredArgsConstructor
@Tag(name = "Gestión de Etiquetas", description = "API para gestión de etiquetas usando DTOs con Record Class")
public class EtiquetaSwaggerController {

    private final EtiquetaService etiquetaService;

    @PostMapping("/crear")
    @Operation(summary = "Crear nueva etiqueta", description = "Crea una nueva etiqueta usando DTO")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Etiqueta creada exitosamente", content = @Content(schema = @Schema(implementation = EtiquetaDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<EtiquetaDTO> crearEtiqueta(
            @Parameter(description = "DTO con los datos de la nueva etiqueta", required = true) @RequestBody EtiquetaDTO etiquetaDTO) {
        log.debug("Creando nueva etiqueta con DTO: {}", etiquetaDTO);
        try {
            Etiqueta etiquetaCreada = etiquetaService.crearEtiqueta(etiquetaDTO.nombre());
            EtiquetaDTO responseDTO = new EtiquetaDTO(etiquetaCreada.getId(), etiquetaCreada.getNombre());
            return ResponseEntity.ok(responseDTO);
        } catch (EtiquetaException e) {
            log.error("Error al crear la etiqueta con DTO: {}", etiquetaDTO, e);
            throw e;
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener etiqueta por ID", description = "Recupera la información de una etiqueta específica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Etiqueta encontrada exitosamente", content = @Content(schema = @Schema(implementation = EtiquetaDTO.class))),
            @ApiResponse(responseCode = "404", description = "Etiqueta no encontrada")
    })
    public ResponseEntity<EtiquetaDTO> obtenerEtiqueta(
            @Parameter(description = "ID de la etiqueta a obtener", required = true, example = "1") @PathVariable int id) {
        log.debug("Obteniendo etiqueta con ID: {}", id);
        try {
            Etiqueta etiqueta = etiquetaService.obtenerEtiqueta(id);
            EtiquetaDTO responseDTO = new EtiquetaDTO(etiqueta.getId(), etiqueta.getNombre());
            return ResponseEntity.ok(responseDTO);
        } catch (EtiquetaException e) {
            log.error("Error al obtener la etiqueta con ID: {}", id, e);
            throw e;
        }
    }
}