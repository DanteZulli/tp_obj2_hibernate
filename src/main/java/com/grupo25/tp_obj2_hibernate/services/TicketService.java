package com.grupo25.tp_obj2_hibernate.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grupo25.tp_obj2_hibernate.model.dto.TicketDTO;
import com.grupo25.tp_obj2_hibernate.model.entities.Ticket;
import com.grupo25.tp_obj2_hibernate.model.entities.Usuario;
import com.grupo25.tp_obj2_hibernate.repositories.TicketRepository;
import com.grupo25.tp_obj2_hibernate.repositories.UsuarioRepository;

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

    @Autowired
    private UsuarioRepository usuarioRepository;

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
    public List<TicketDTO> getTodosLosTicketsPorUsuarioCreador(int usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + usuarioId));
        List<TicketDTO> ticketsPorUsuarioCreador = new ArrayList<>();
        for (Ticket ticket : ticketRepository.findByCreador(usuario)) {
            ticketsPorUsuarioCreador.add(new TicketDTO(ticket));
        }
        return ticketsPorUsuarioCreador;
    }

    /**
     * Asigna un ticket a un tecnico dado su ID.
     * 
     * @param id El ID del ticket.
     * @param idTecnico El ID del tecnico.
     * @return El ticket asignado.
     * 
     * @author Ariel Serato
     */
    public TicketDTO asignarTicketATecnico(int id, int idTecnico) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket no encontrado con ID: " + id));
        Usuario tecnico = usuarioRepository.findById(idTecnico)
                .orElseThrow(() -> new RuntimeException("Tecnico no encontrado con ID: " + idTecnico));
        ticket.setAsignado(tecnico);
        ticketRepository.save(ticket);
        return new TicketDTO(ticket);
    }
}