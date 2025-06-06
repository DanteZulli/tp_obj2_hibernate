package com.grupo25.tp_obj2_hibernate.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.grupo25.tp_obj2_hibernate.model.entities.Comentario;
import com.grupo25.tp_obj2_hibernate.model.entities.Ticket;
import com.grupo25.tp_obj2_hibernate.repository.ComentarioRepository;
import com.grupo25.tp_obj2_hibernate.repository.TicketRepository;

/**
 * Clase de servicio para manejar la lógica de negocio relacionada con los
 * comentarios.
 * 
 * @author Grupo 25
 */
@Service
public class ComentarioService {

    private TicketRepository ticketRepository;
    private ComentarioRepository comentarioRepository;

    public ComentarioService(TicketRepository ticketRepository,
            ComentarioRepository comentarioRepository) {
        this.ticketRepository = ticketRepository;
        this.comentarioRepository = comentarioRepository;
    }

    /**
     * Crea un comentario utilizando variables por argumento.
     *
     * @param idTicket
     * @param mensaje
     * @return ComentarioDTO
     * 
     * @author Ignacio Cruz
     */
    public Comentario crearComentario(String mensaje, int idTicket) {
        Comentario comentario = new Comentario();
        Ticket ticket = ticketRepository.findById(idTicket)
                .orElseThrow(() -> new RuntimeException("Ticket no encontrado con ID: " + idTicket));
        comentario.setTicket(ticket);
        comentario.setMensaje(mensaje);
        comentario.setFecha(LocalDateTime.now());
        return comentarioRepository.save(comentario);
    }

    /**
     * Obtiene todos los tickets creados por el usuario dado su ID.
     *
     * @param usuarioId El ID del usuario.
     * @return La lista de tickets.
     * 
     * @throws RuntimeException Si no se encuentra el ticket con el ID dado.
     * 
     * @author Ignacio Cruz
     */

    public List<Comentario> getTodosLosComentariosPorTicket(int ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket no encontrado con ID: " + ticketId));
        return comentarioRepository.findByTicketOrderByFecha(ticket);
    }

}
