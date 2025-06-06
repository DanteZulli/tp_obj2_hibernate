package com.grupo25.tp_obj2_hibernate.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.grupo25.tp_obj2_hibernate.model.entities.Ticket;
import com.grupo25.tp_obj2_hibernate.service.TicketService;

import lombok.extern.slf4j.Slf4j;

/**
 * Controlador REST para manejar las operaciones relacionadas con tickets.
 * 
 * @author Grupo 25
 */
@Slf4j
@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    private TicketService ticketService;
    
    public TicketController(@Autowired TicketService ticketService) {
        this.ticketService = ticketService;
    }

    /**
     * Obtiene todos los tickets.
     * 
     * @return ResponseEntity con el listado de tickets, o un error si no existe
     * 
     * @author Ariel Serato
     */

    @GetMapping("/")
    public ResponseEntity<List<Ticket>> getTickets() {
        try {
            List<Ticket> tickets = ticketService.getTickets();
            return ResponseEntity.ok(tickets);
        } catch (RuntimeException e) {
            log.error("Error al obtener los tickets", e);
            return ResponseEntity.notFound().build();
        }
    }
    
    
    /**
     * Crea un nuevo ticket.
     * 
     * @param titulo     El titulo del ticket
     * @param descripcion      La descripcion del ticket
     * @param estado       El estado del ticket
     * @param prioridad 	Indica si el ticket es urgente o no
     * @param fechaCreacion	Indica la fecha de creacion del ticket
     * @param
     * @return El ticket creado
     * 
     * @author Ignacio Cruz
     */
    @PostMapping("/crear")
    public ResponseEntity<Ticket> crearTicket(
            @RequestParam String titulo,
            @RequestParam String descripcion,
            @RequestParam String estado,
            @RequestParam String prioridad) {
        Ticket ticket = ticketService.crearTicket(titulo, descripcion, estado, prioridad);
        return ResponseEntity.ok(ticket);
    }

    /**
     * Obtiene el estado de un ticket por su ID.
     * 
     * @param id El ID del ticket
     * @return ResponseEntity con el estado del ticket si se encuentra, o un error si no existe
     * 
     * @throws RuntimeException si no se encuentra el ticket con el ID dado
     * 
     * @author Dante Zulli
     */
    @GetMapping("/{id}/estado")
    public ResponseEntity<String> getEstadoTicket(@PathVariable("id") int id) {
        try {
            String estado = ticketService.getEstadoTicket(id);
            return ResponseEntity.ok(estado);
        } catch (RuntimeException e) {
            log.error("Error al obtener el estado del ticket con ID: {}", id, e);
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Obtiene los tickets creados por un usuario dado su ID.
     * 
     * @param id El ID del usuario
     * @return ResponseEntity con el listado de tickets si se encuentra, o un error si no existe
     * 
     * @throws RuntimeException si no se encuentra el ticket con el ID dado
     * 
     * @author Ignacio Cruz
     */
    @GetMapping("/creador/{id}")
    public ResponseEntity<List<Ticket>> getTicketsPorUsuarioCreador(@PathVariable("id") int id) {
        try {
            List<Ticket> tickets = ticketService.getTodosLosTicketsPorUsuarioCreador(id);            
            return ResponseEntity.ok(tickets);
        } catch (RuntimeException e) {
            log.error("Error al obtener los tickets creados por el usuario con el ID: {}", id, e);
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Asignar un ticket a un tecnico.
     * 
     * @param id El ID del ticket
     * @param idTecnico El ID del tecnico
     * @return ResponseEntity con el ticket asignado, o un error si no existe
     * 
     * @author Ariel Serato
     */
    @PutMapping("/{id}/asignar/{idTecnico}")
    public ResponseEntity<Ticket> asignarTicketATecnico(@PathVariable("id") int id, @PathVariable("idTecnico") int idTecnico) {
        try {
            Ticket ticket = ticketService.asignarTicketATecnico(id, idTecnico);
            return ResponseEntity.ok(ticket);
        } catch (RuntimeException e) {
            log.error("Error al asignar el ticket con ID: {} al tecnico con ID: {}", id, idTecnico, e);
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Cambiar la prioridad de un ticket.
     * 
     * @param id El ID del ticket
     * @param prioridad La prioridad del ticket
     * @return ResponseEntity con el ticket actualizado, o un error si no existe
     * 
     * @author Ariel Serato
     */
    @PutMapping("/{id}/prioridad")
    public ResponseEntity<Ticket> cambiarPrioridadTicket(@PathVariable("id") int id, @RequestParam String prioridad) {
        try {
            Ticket ticket = ticketService.cambiarPrioridadTicket(id, prioridad);
            return ResponseEntity.ok(ticket);
        } catch (RuntimeException e) {
            log.error("Error al cambiar la prioridad del ticket con ID: {} a {}", id, prioridad, e);
            return ResponseEntity.notFound().build();
        }
    }

}