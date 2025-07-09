package com.grupo25.tp_obj2_hibernate.controller.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.grupo25.tp_obj2_hibernate.model.entities.Revision;
import com.grupo25.tp_obj2_hibernate.service.RevisionesService;
import com.grupo25.tp_obj2_hibernate.exception.RevisionesException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import java.util.List;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;

@Slf4j
@RestController
@RequestMapping("/api/revisiones")
@RequiredArgsConstructor
@Tag(name = "Gestión de Revisiones", description = "API para gestionar el historial de revisiones y cambios en tickets del sistema de soporte técnico")
public class RevisionesRestController {
    private final RevisionesService revisionesService;

    @PostMapping("/crear")
    @Operation(summary = "Crear nueva revisión", description = "Crea un registro de revisión para documentar cambios en un ticket")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Revisión creada exitosamente",
                    content = @Content(schema = @Schema(implementation = Revision.class))),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
        @ApiResponse(responseCode = "404", description = "Ticket no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<Revision> crearRevision(
            @Parameter(description = "ID del ticket que se está revisando", required = true, example = "1")
            @RequestParam int ticketId,
            @Parameter(description = "Campo que fue modificado", required = true, example = "estado")
            @RequestParam String campoModificado,
            @Parameter(description = "Valor anterior del campo", required = true, example = "Pendiente")
            @RequestParam String valorAnterior,
            @Parameter(description = "Nuevo valor del campo", required = true, example = "En Proceso")
            @RequestParam String valorNuevo,
            @Parameter(description = "Observaciones sobre el cambio realizado", required = true, example = "Ticket asignado a técnico para resolución")
            @RequestParam String observaciones,
            @AuthenticationPrincipal UserDetails userDetails) {
        log.debug("Creando revisión para ticket {} campo {} por usuario {}", ticketId, campoModificado,
                userDetails.getUsername());
        try {
            Revision revision = revisionesService.crearRevision(ticketId, userDetails.getUsername(), campoModificado,
                    valorAnterior, valorNuevo, observaciones);
            return ResponseEntity.ok(revision);
        } catch (RevisionesException e) {
            log.error("Error al crear la revisión: {}", e.getMessage(), e);
            throw e;
        }
    }

    @GetMapping("/ticket/{ticketId}")
    @Operation(summary = "Obtener revisiones por ticket", description = "Recupera el historial completo de revisiones de un ticket específico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Revisiones encontradas exitosamente",
                    content = @Content(schema = @Schema(implementation = Revision.class))),
        @ApiResponse(responseCode = "404", description = "Ticket no encontrado")
    })
    public ResponseEntity<List<Revision>> obtenerRevisionesPorTicket(
            @Parameter(description = "ID del ticket para obtener su historial de revisiones", required = true, example = "1")
            @PathVariable int ticketId) {
        log.debug("Obteniendo revisiones para ticket {}", ticketId);
        try {
            List<Revision> revisiones = revisionesService.obtenerRevisionesPorTicket(ticketId);
            return ResponseEntity.ok(revisiones);
        } catch (RevisionesException e) {
            log.error("Error al obtener revisiones del ticket: {}", ticketId, e);
            throw e;
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener revisión por ID", description = "Recupera la información de una revisión específica")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Revisión encontrada exitosamente",
                    content = @Content(schema = @Schema(implementation = Revision.class))),
        @ApiResponse(responseCode = "404", description = "Revisión no encontrada")
    })
    public ResponseEntity<Revision> obtenerRevision(
            @Parameter(description = "ID de la revisión a obtener", required = true, example = "1")
            @PathVariable int id) {
        log.debug("Obteniendo revisión con ID: {}", id);
        try {
            Revision revision = revisionesService.obtenerRevision(id);
            return ResponseEntity.ok(revision);
        } catch (RevisionesException e) {
            log.error("Error al obtener la revisión con ID: {}", id, e);
            throw e;
        }
    }
}