package com.grupo25.tp_obj2_hibernate.controller.api.swagger;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.grupo25.tp_obj2_hibernate.model.entities.Ticket;
import com.grupo25.tp_obj2_hibernate.model.dto.TicketDTO;
import com.grupo25.tp_obj2_hibernate.service.TicketService;
import com.grupo25.tp_obj2_hibernate.exception.TicketException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;

@Slf4j
@RestController
@RequestMapping("/api/swagger/tickets")
@RequiredArgsConstructor
@Tag(name = "Gestión de Tickets", description = "API para gestión de tickets usando DTOs con Record Class")
public class TicketSwaggerController {

    private final TicketService ticketService;

    @PostMapping("/crear")
    @Operation(summary = "Crear nuevo ticket", description = "Crea un nuevo ticket usando DTO")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ticket creado exitosamente", content = @Content(schema = @Schema(implementation = TicketDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "404", description = "Categoría no encontrada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<TicketDTO> crearTicket(
            @Parameter(description = "DTO con los datos del nuevo ticket", required = true) @RequestBody TicketDTO ticketDTO,
            @AuthenticationPrincipal UserDetails userDetails) {
        log.debug("Creando nuevo ticket con DTO: {}", ticketDTO);
        try {
            Ticket ticket = ticketService.crearTicket(
                    ticketDTO.titulo(),
                    ticketDTO.descripcion(),
                    ticketDTO.estado(),
                    ticketDTO.prioridad(),
                    userDetails.getUsername(),
                    ticketDTO.categoriaId());

            TicketDTO responseDTO = new TicketDTO(
                    ticket.getId(),
                    ticket.getTitulo(),
                    ticket.getDescripcion(),
                    ticket.getEstado(),
                    ticket.getPrioridad(),
                    ticket.getCategoria().getId());
            return ResponseEntity.ok(responseDTO);
        } catch (TicketException e) {
            log.error("Error al crear el ticket con DTO: {}", ticketDTO, e);
            throw e;
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener ticket por ID", description = "Recupera la información de un ticket específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ticket encontrado exitosamente", content = @Content(schema = @Schema(implementation = TicketDTO.class))),
            @ApiResponse(responseCode = "404", description = "Ticket no encontrado")
    })
    public ResponseEntity<TicketDTO> obtenerTicket(
            @Parameter(description = "ID del ticket a obtener", required = true, example = "1") @PathVariable int id) {
        log.debug("Obteniendo ticket con ID: {}", id);
        try {
            Ticket ticket = ticketService.obtenerTicket(id);
            TicketDTO responseDTO = new TicketDTO(
                    ticket.getId(),
                    ticket.getTitulo(),
                    ticket.getDescripcion(),
                    ticket.getEstado(),
                    ticket.getPrioridad(),
                    ticket.getCategoria().getId());
            return ResponseEntity.ok(responseDTO);
        } catch (TicketException e) {
            log.error("Error al obtener el ticket con ID: {}", id, e);
            throw e;
        }
    }
}