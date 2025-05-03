package com.grupo25.tp_obj2_hibernate.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.grupo25.tp_obj2_hibernate.model.services.TicketService;

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
}