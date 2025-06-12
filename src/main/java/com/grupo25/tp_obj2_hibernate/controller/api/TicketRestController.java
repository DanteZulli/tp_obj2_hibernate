package com.grupo25.tp_obj2_hibernate.controller.api;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.grupo25.tp_obj2_hibernate.model.entities.Ticket;
import com.grupo25.tp_obj2_hibernate.service.TicketService;
import com.grupo25.tp_obj2_hibernate.exception.TicketException;

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
public class TicketRestController {

    private final TicketService ticketService;
    
    @PostMapping("/crear")
    public ResponseEntity<Ticket> crearTicket(
            @RequestParam String titulo,
            @RequestParam String descripcion,
            @RequestParam String estado,
            @RequestParam String prioridad,
            @RequestParam Integer categoriaId,
            @AuthenticationPrincipal UserDetails userDetails) {
        log.debug("Creando nuevo ticket con titulo: {}", titulo);
        try {
            Ticket ticket = ticketService.crearTicket(titulo, descripcion, estado, prioridad, userDetails.getUsername(), categoriaId);
            return ResponseEntity.ok(ticket);
        } catch (TicketException e) {
            log.error("Error al crear el ticket: {}", e.getMessage(), e);
            throw e;
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ticket> modificarTicket(
            @PathVariable int id,
            @RequestParam String titulo,
            @RequestParam String descripcion,
            @RequestParam String estado,
            @RequestParam String prioridad,
            @RequestParam Integer categoriaId) {
        log.debug("Modificando ticket con ID: {}", id);
        try {
            Ticket ticket = ticketService.modificarTicket(id, titulo, descripcion, estado, prioridad, categoriaId);
            return ResponseEntity.ok(ticket);
        } catch (TicketException e) {
            log.error("Error al actualizar el ticket con ID: {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ticket> obtenerTicket(@PathVariable int id) {
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
    public ResponseEntity<String> obtenerEstadoTicket(@PathVariable int id) {
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
    public ResponseEntity<List<Ticket>> obtenerTodosLosTicketsPorUsuarioCreador(@PathVariable int id) {
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
    public ResponseEntity<Ticket> asignarTicketATecnico(@PathVariable int id, @PathVariable int idTecnico) {
        log.debug("Asignando ticket con ID: {} al técnico con ID: {}", id, idTecnico);
        try {
            Ticket ticket = ticketService.asignarTicketATecnico(id, idTecnico);
            return ResponseEntity.ok(ticket);
        } catch (TicketException e) {
            log.error("Error al asignar el ticket con ID: {} al tecnico con ID: {}: {}", id, idTecnico, e.getMessage(), e);
            throw e;
        }
    }

    @PutMapping("/{id}/tomar")
    public ResponseEntity<Ticket> tomarTicket(@PathVariable int id, @AuthenticationPrincipal UserDetails userDetails) {
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
    public ResponseEntity<Ticket> cambiarPrioridadTicket(@PathVariable int id, @RequestParam String prioridad) {
        log.debug("Cambiando prioridad del ticket con ID: {} a: {}", id, prioridad);
        try {
            Ticket ticket = ticketService.cambiarPrioridadTicket(id, prioridad);
            return ResponseEntity.ok(ticket);
        } catch (TicketException e) {
            log.error("Error al cambiar la prioridad del ticket con ID: {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }
}