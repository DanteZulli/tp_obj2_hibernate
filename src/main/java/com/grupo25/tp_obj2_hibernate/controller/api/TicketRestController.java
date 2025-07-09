package com.grupo25.tp_obj2_hibernate.controller.api;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.grupo25.tp_obj2_hibernate.model.entities.Ticket;
import com.grupo25.tp_obj2_hibernate.service.TicketService;
import com.grupo25.tp_obj2_hibernate.service.EmailService;
import com.grupo25.tp_obj2_hibernate.exception.TicketException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;

@Slf4j
@RestController
@PreAuthorize("hasAnyRole('ROLE_TECNICO', 'ROLE_ADMIN')")
@RequestMapping("/api/tickets")
@RequiredArgsConstructor
@Tag(name = "Legacy - Gestión de Tickets", description = "Endpoints existentes para gestión de tickets (antes de la implementación de Swagger). Requiere rol de TÉCNICO o ADMIN")
public class TicketRestController {

    private final TicketService ticketService;
    private final EmailService emailService;

    @PostMapping("/crear")
    @Operation(summary = "Crear nuevo ticket", description = "Crea un nuevo ticket de soporte técnico y envía notificación por email")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Ticket creado exitosamente",
                    content = @Content(schema = @Schema(implementation = Ticket.class))),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
        @ApiResponse(responseCode = "404", description = "Categoría no encontrada"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<Ticket> crearTicket(
            @Parameter(description = "Título del ticket", required = true, example = "Problema con impresora")
            @RequestParam String titulo,
            @Parameter(description = "Descripción detallada del problema", required = true, example = "La impresora no responde y muestra error de papel")
            @RequestParam String descripcion,
            @Parameter(description = "Estado inicial del ticket", required = true, example = "Pendiente")
            @RequestParam String estado,
            @Parameter(description = "Prioridad del ticket", required = true, example = "Media")
            @RequestParam String prioridad,
            @Parameter(description = "ID de la categoría del ticket", required = true, example = "1")
            @RequestParam Integer categoriaId,
            @AuthenticationPrincipal UserDetails userDetails) {
        log.debug("Creando nuevo ticket con titulo: {}", titulo);
        try {
            Ticket ticket = ticketService.crearTicket(titulo, descripcion, estado, prioridad, userDetails.getUsername(),
                    categoriaId);

            String subject = "Ticket #" + ticket.getId() + " creado exitosamente";
            String text = "Hola " + ticket.getCreador().getNombre() + ",\n\n" +
                    "Tu ticket ha sido creado exitosamente con la siguiente información:\n" +
                    "ID: " + ticket.getId() + "\n" +
                    "Título: " + ticket.getTitulo() + "\n" +
                    "Prioridad: " + ticket.getPrioridad() + "\n" +
                    "Estado: " + ticket.getEstado() + "\n\n" +
                    "Gracias,\nEl equipo de soporte.";
            emailService.sendSimpleMessage(ticket.getCreador().getEmail(), subject, text);

            return ResponseEntity.ok(ticket);
        } catch (TicketException e) {
            log.error("Error al crear el ticket: {}", e.getMessage(), e);
            throw e;
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Modificar ticket existente", description = "Modifica la información de un ticket y envía notificaciones por email")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Ticket modificado exitosamente",
                    content = @Content(schema = @Schema(implementation = Ticket.class))),
        @ApiResponse(responseCode = "404", description = "Ticket no encontrado"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
    public ResponseEntity<Ticket> modificarTicket(
            @Parameter(description = "ID del ticket a modificar", required = true, example = "1")
            @PathVariable int id,
            @Parameter(description = "Nuevo título del ticket", required = true, example = "Problema con impresora HP")
            @RequestParam String titulo,
            @Parameter(description = "Nueva descripción del ticket", required = true, example = "La impresora HP no responde y muestra error de papel atascado")
            @RequestParam String descripcion,
            @Parameter(description = "Nuevo estado del ticket", required = true, example = "En Proceso")
            @RequestParam String estado,
            @Parameter(description = "Nueva prioridad del ticket", required = true, example = "Alta")
            @RequestParam String prioridad,
            @Parameter(description = "ID de la nueva categoría", required = true, example = "1")
            @RequestParam Integer categoriaId) {
        log.debug("Modificando ticket con ID: {}", id);
        try {
            Ticket ticket = ticketService.modificarTicket(id, titulo, descripcion, estado, prioridad, categoriaId);

            String subject = "Ticket #" + ticket.getId() + " ha sido modificado";
            String text = "Hola,\n\n" +
                    "El ticket #" + ticket.getId() + " ha sido modificado. La nueva información es:\n" +
                    "Título: " + ticket.getTitulo() + "\n" +
                    "Descripción: " + ticket.getDescripcion() + "\n" +
                    "Prioridad: " + ticket.getPrioridad() + "\n" +
                    "Estado: " + ticket.getEstado() + "\n\n" +
                    "Gracias,\nEl equipo de soporte.";

            emailService.sendSimpleMessage(ticket.getCreador().getEmail(), subject, text);

            if (ticket.getAsignado() != null) {
                emailService.sendSimpleMessage(ticket.getAsignado().getEmail(), subject, text);
            }

            return ResponseEntity.ok(ticket);
        } catch (TicketException e) {
            log.error("Error al actualizar el ticket con ID: {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener ticket por ID", description = "Recupera la información completa de un ticket específico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Ticket encontrado exitosamente",
                    content = @Content(schema = @Schema(implementation = Ticket.class))),
        @ApiResponse(responseCode = "404", description = "Ticket no encontrado")
    })
    public ResponseEntity<Ticket> obtenerTicket(
            @Parameter(description = "ID del ticket a obtener", required = true, example = "1")
            @PathVariable int id) {
        log.debug("Obteniendo ticket con ID: {}", id);
        try {
            Ticket ticket = ticketService.obtenerTicket(id);
            return ResponseEntity.ok(ticket);
        } catch (TicketException e) {
            log.error("Error al obtener el ticket con ID: {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }

    @GetMapping("/")
    @Operation(summary = "Obtener todos los tickets", description = "Recupera la lista completa de todos los tickets del sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Tickets encontrados exitosamente",
                    content = @Content(schema = @Schema(implementation = Ticket.class))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<List<Ticket>> obtenerTodosLosTickets() {
        log.debug("Obteniendo todos los tickets");
        try {
            List<Ticket> tickets = ticketService.obtenerTodosLosTickets();
            return ResponseEntity.ok(tickets);
        } catch (TicketException e) {
            log.error("Error al obtener los tickets: {}", e.getMessage(), e);
            throw e;
        }
    }

    @GetMapping("/{id}/estado")
    @Operation(summary = "Obtener estado de ticket", description = "Recupera únicamente el estado actual de un ticket")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Estado obtenido exitosamente"),
        @ApiResponse(responseCode = "404", description = "Ticket no encontrado")
    })
    public ResponseEntity<String> obtenerEstadoTicket(
            @Parameter(description = "ID del ticket para obtener su estado", required = true, example = "1")
            @PathVariable int id) {
        log.debug("Obteniendo estado del ticket con ID: {}", id);
        try {
            String estado = ticketService.obtenerEstadoTicket(id);
            return ResponseEntity.ok(estado);
        } catch (TicketException e) {
            log.error("Error al obtener el estado del ticket con ID: {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }

    @GetMapping("/creador/{id}")
    @Operation(summary = "Obtener tickets por creador", description = "Recupera todos los tickets creados por un usuario específico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Tickets encontrados exitosamente",
                    content = @Content(schema = @Schema(implementation = Ticket.class))),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public ResponseEntity<List<Ticket>> obtenerTodosLosTicketsPorUsuarioCreador(
            @Parameter(description = "ID del usuario creador", required = true, example = "1")
            @PathVariable int id) {
        log.debug("Obteniendo todos los tickets creados por el usuario con ID: {}", id);
        try {
            List<Ticket> tickets = ticketService.obtenerTodosLosTicketsPorUsuarioCreador(id);
            return ResponseEntity.ok(tickets);
        } catch (TicketException e) {
            log.error("Error al obtener los tickets creados por el usuario con ID: {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }

    @PutMapping("/{id}/asignar/{idTecnico}")
    @Operation(summary = "Asignar ticket a técnico", description = "Asigna un ticket específico a un técnico para su resolución")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Ticket asignado exitosamente",
                    content = @Content(schema = @Schema(implementation = Ticket.class))),
        @ApiResponse(responseCode = "404", description = "Ticket o técnico no encontrado"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
    public ResponseEntity<Ticket> asignarTicketATecnico(
            @Parameter(description = "ID del ticket a asignar", required = true, example = "1")
            @PathVariable int id, 
            @Parameter(description = "ID del técnico al que se asignará el ticket", required = true, example = "2")
            @PathVariable int idTecnico) {
        log.debug("Asignando ticket con ID: {} al técnico con ID: {}", id, idTecnico);
        try {
            Ticket ticket = ticketService.asignarTicketATecnico(id, idTecnico);
            return ResponseEntity.ok(ticket);
        } catch (TicketException e) {
            log.error("Error al asignar el ticket con ID: {} al tecnico con ID: {}: {}", id, idTecnico, e.getMessage(),
                    e);
            throw e;
        }
    }

    @PutMapping("/{id}/tomar")
    @Operation(summary = "Tomar ticket (auto-asignación)", description = "Permite que un técnico se auto-asigne un ticket disponible")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Ticket tomado exitosamente",
                    content = @Content(schema = @Schema(implementation = Ticket.class))),
        @ApiResponse(responseCode = "404", description = "Ticket no encontrado"),
        @ApiResponse(responseCode = "400", description = "Ticket ya asignado o no disponible")
    })
    public ResponseEntity<Ticket> tomarTicket(
            @Parameter(description = "ID del ticket a tomar", required = true, example = "1")
            @PathVariable int id, 
            @AuthenticationPrincipal UserDetails userDetails) {
        log.debug("Tomando ticket con ID: {} por el usuario: {}", id, userDetails.getUsername());
        try {
            Ticket ticket = ticketService.tomarTicket(id, userDetails.getUsername());
            return ResponseEntity.ok(ticket);
        } catch (TicketException e) {
            log.error("Error al tomar el ticket con ID: {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }

    @PutMapping("/{id}/prioridad")
    @Operation(summary = "Cambiar prioridad de ticket", description = "Modifica la prioridad de un ticket específico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Prioridad cambiada exitosamente",
                    content = @Content(schema = @Schema(implementation = Ticket.class))),
        @ApiResponse(responseCode = "404", description = "Ticket no encontrado"),
        @ApiResponse(responseCode = "400", description = "Prioridad inválida")
    })
    public ResponseEntity<Ticket> cambiarPrioridadTicket(
            @Parameter(description = "ID del ticket", required = true, example = "1")
            @PathVariable int id, 
            @Parameter(description = "Nueva prioridad del ticket", required = true, example = "Alta")
            @RequestParam String prioridad) {
        log.debug("Cambiando prioridad del ticket con ID: {} a: {}", id, prioridad);
        try {
            Ticket ticket = ticketService.cambiarPrioridadTicket(id, prioridad);
            return ResponseEntity.ok(ticket);
        } catch (TicketException e) {
            log.error("Error al cambiar la prioridad del ticket con ID: {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }

    @PutMapping("/{ticketId}/etiquetas/{etiquetaId}")
    @Operation(summary = "Agregar etiqueta a ticket", description = "Asocia una etiqueta específica a un ticket")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Etiqueta agregada exitosamente",
                    content = @Content(schema = @Schema(implementation = Ticket.class))),
        @ApiResponse(responseCode = "404", description = "Ticket o etiqueta no encontrado"),
        @ApiResponse(responseCode = "400", description = "Etiqueta ya asociada al ticket")
    })
    public ResponseEntity<Ticket> agregarEtiquetaATicket(
            @Parameter(description = "ID del ticket", required = true, example = "1")
            @PathVariable int ticketId, 
            @Parameter(description = "ID de la etiqueta a agregar", required = true, example = "1")
            @PathVariable int etiquetaId) {
        log.debug("Agregando etiqueta con ID: {} al ticket con ID: {}", etiquetaId, ticketId);
        try {
            Ticket ticket = ticketService.agregarEtiquetaATicket(ticketId, etiquetaId);
            return ResponseEntity.ok(ticket);
        } catch (TicketException e) {
            log.error("Error al agregar la etiqueta con ID: {} al ticket con ID: {}: {}", etiquetaId, ticketId,
                    e.getMessage(), e);
            throw e;
        }
    }

    @DeleteMapping("/{ticketId}/etiquetas/{etiquetaId}")
    @Operation(summary = "Quitar etiqueta de ticket", description = "Elimina la asociación entre una etiqueta y un ticket")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Etiqueta quitada exitosamente",
                    content = @Content(schema = @Schema(implementation = Ticket.class))),
        @ApiResponse(responseCode = "404", description = "Ticket o etiqueta no encontrado"),
        @ApiResponse(responseCode = "400", description = "Etiqueta no asociada al ticket")
    })
    public ResponseEntity<Ticket> quitarEtiquetaDeTicket(
            @Parameter(description = "ID del ticket", required = true, example = "1")
            @PathVariable int ticketId, 
            @Parameter(description = "ID de la etiqueta a quitar", required = true, example = "1")
            @PathVariable int etiquetaId) {
        log.debug("Quitando etiqueta con ID: {} del ticket con ID: {}", etiquetaId, ticketId);
        try {
            Ticket ticket = ticketService.quitarEtiquetaDeTicket(ticketId, etiquetaId);
            return ResponseEntity.ok(ticket);
        } catch (TicketException e) {
            log.error("Error al quitar la etiqueta con ID: {} del ticket con ID: {}: {}", etiquetaId, ticketId,
                    e.getMessage(), e);
            throw e;
        }
    }

    @GetMapping("/filtrar")
    @Operation(summary = "Filtrar tickets", description = "Filtra tickets según múltiples criterios de búsqueda")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Tickets filtrados exitosamente",
                    content = @Content(schema = @Schema(implementation = Ticket.class))),
        @ApiResponse(responseCode = "400", description = "Criterios de filtrado inválidos")
    })
    public ResponseEntity<List<Ticket>> filtrarTickets(
            @Parameter(description = "Fecha desde para filtrar (formato ISO)", required = false, example = "2024-01-01T00:00:00")
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaDesde,
            @Parameter(description = "Fecha hasta para filtrar (formato ISO)", required = false, example = "2024-12-31T23:59:59")
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaHasta,
            @Parameter(description = "ID de la categoría para filtrar", required = false, example = "1")
            @RequestParam(required = false) Integer categoriaId,
            @Parameter(description = "Estado del ticket para filtrar", required = false, example = "Pendiente")
            @RequestParam(required = false) String estado,
            @Parameter(description = "Lista de IDs de técnicos para filtrar", required = false, example = "[1, 2, 3]")
            @RequestParam(required = false) List<Integer> tecnicoIds,
            @Parameter(description = "Filtrar tickets sin asignar", required = false, example = "true")
            @RequestParam(required = false) Boolean sinAsignar,
            @Parameter(description = "Prioridad del ticket para filtrar", required = false, example = "Alta")
            @RequestParam(required = false) String prioridad) {
        log.debug(
                "Filtrando tickets con los siguientes criterios: fechaDesde={}, fechaHasta={}, categoriaId={}, estado={}, tecnicoIds={}, sinAsignar={}, prioridad={}",
                fechaDesde, fechaHasta, categoriaId, estado, tecnicoIds, sinAsignar, prioridad);
        try {
            List<Ticket> tickets = ticketService.filtrarTickets(fechaDesde, fechaHasta, categoriaId, estado, tecnicoIds,
                    sinAsignar, prioridad);
            return ResponseEntity.ok(tickets);
        } catch (TicketException e) {
            log.error("Error al filtrar tickets: {}", e.getMessage(), e);
            throw e;
        }
    }
}