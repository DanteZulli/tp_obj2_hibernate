package com.grupo25.tp_obj2_hibernate.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.grupo25.tp_obj2_hibernate.model.dto.TicketDTO;
import com.grupo25.tp_obj2_hibernate.services.TicketService;

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

    @Autowired
    private TicketService ticketService;

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
    public ResponseEntity<List<TicketDTO>> getTicketsPorUsuarioCreador(@PathVariable("id") int id) {
        try {
            List<TicketDTO> tickets = ticketService.getTodosLosTicketsPorUsuarioCreador(id);            
            return ResponseEntity.ok(tickets);
        } catch (RuntimeException e) {
            log.error("Error al obtener los tickets creados por el usuario con el ID: {}", id, e);
            return ResponseEntity.notFound().build();
        }
    }

}