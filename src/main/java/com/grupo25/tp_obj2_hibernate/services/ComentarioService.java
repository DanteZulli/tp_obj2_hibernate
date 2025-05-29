package com.grupo25.tp_obj2_hibernate.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grupo25.tp_obj2_hibernate.model.dto.ComentarioDTO;
import com.grupo25.tp_obj2_hibernate.model.entities.Comentario;
import com.grupo25.tp_obj2_hibernate.model.entities.Ticket;
import com.grupo25.tp_obj2_hibernate.repositories.ComentarioRepository;
import com.grupo25.tp_obj2_hibernate.repositories.TicketRepository;

/**
 * Clase de servicio para manejar la lógica de negocio relacionada con los
 * comentarios.
 * 
 * @author Grupo 25
 */
@Service
public class ComentarioService {

    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private ComentarioRepository comentarioRepository;

    /**
     * Crea un comentario utilizando variables por argumento.
     *
     * @param idTicket , mensaje
     * @return ComentarioDTO
     * 
     * 
     * 
     * @author Ignacio Cruz
     */
    public ComentarioDTO crearComentario(String mensaje, int idTicket) {
        Comentario comentario = new Comentario();
        Ticket ticket = ticketRepository.findById(idTicket).orElseThrow();
        comentario.setTicket(ticket);
        comentario.setMensaje(mensaje);
        comentario.setFecha(LocalDateTime.now());
        return new ComentarioDTO(comentarioRepository.save(comentario));
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

    public List<ComentarioDTO> getTodosLosComentariosPorTicket(int ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket no encontrado con ID: " + ticketId));
        List<ComentarioDTO> comentarioPorTicket = new ArrayList<>();
        for (Comentario comentario : comentarioRepository.findByTicketOrderByFecha(ticket)) {
            comentarioPorTicket.add(new ComentarioDTO(comentario));
        }
        return comentarioPorTicket;
    }

}
