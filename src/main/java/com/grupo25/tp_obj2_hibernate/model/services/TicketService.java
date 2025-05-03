package com.grupo25.tp_obj2_hibernate.model.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grupo25.tp_obj2_hibernate.model.repositories.TicketRepository;

/**
 * Clase de servicio para manejar la lógica de negocio relacionada con los
 * tickets.
 * 
 * @author Grupo 25
 */
@Service
public class TicketService {
    @Autowired
    private TicketRepository ticketRepository;

    /**
     * Obtiene el estado de un ticket dado su ID.
     * 
     * @param ticketId El ID del ticket.
     * @return El estado del ticket.
     * 
     * @throws RuntimeException Si no se encuentra el ticket con el ID dado.
     * 
     * @author Dante Zulli
     */
    public String getEstadoTicket(int ticketId) {
        return ticketRepository.findById(ticketId)
                .map(ticket -> ticket.getEstado())
                .orElseThrow(() -> new RuntimeException("Ticket no encontrado con ID: " + ticketId));
    }
}