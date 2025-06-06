package com.grupo25.tp_obj2_hibernate.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.grupo25.tp_obj2_hibernate.model.entities.Ticket;
import com.grupo25.tp_obj2_hibernate.model.entities.Usuario;
import com.grupo25.tp_obj2_hibernate.repository.TicketRepository;
import com.grupo25.tp_obj2_hibernate.repository.UsuarioRepository;

/**
 * Clase de servicio para manejar la lógica de negocio relacionada con los
 * tickets.
 * 
 * @author Grupo 25
 */
@Service
public class TicketService {

    private TicketRepository ticketRepository;
    private UsuarioRepository usuarioRepository;

    public TicketService(TicketRepository ticketRepository,
            UsuarioRepository usuarioRepository) {
        this.ticketRepository = ticketRepository;
        this.usuarioRepository = usuarioRepository;
    }

    /**
     * Obtiene todos los tickets.
     * 
     * @return List<TicketDTO>
     * 
     * @author Ariel Serato
     */
    public List<Ticket> getTickets() {
        return ticketRepository.findAll();
    }

    /**
     * Crea un ticket utilizando variables por argumento.
     *
     * @param titulo, descripcion, estado, prioridad, fechaCreacion,fechaResolucion
     * @return TicketDTO
     * 
     * @author Ignacio Cruz
     */
    public Ticket crearTicket(String titulo, String descripcion, String estado, String prioridad) {
        Ticket ticket = new Ticket();
        ticket.setTitulo(titulo);
        ticket.setDescripcion(descripcion);
        ticket.setEstado(estado);
        ticket.setPrioridad(prioridad);
        ticket.setFechaCreacion(LocalDateTime.now());
        return ticketRepository.save(ticket);
    }

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
        return ticketRepository.findEstadoById(ticketId);
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
    public List<Ticket> getTodosLosTicketsPorUsuarioCreador(int usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + usuarioId));

        return ticketRepository.findByCreador(usuario);
    }

    /**
     * Asigna un ticket a un tecnico dado su ID.
     * 
     * @param id        El ID del ticket.
     * @param idTecnico El ID del tecnico.
     * @return El ticket asignado.
     * 
     * @author Ariel Serato
     */
    public Ticket asignarTicketATecnico(int id, int idTecnico) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket no encontrado con ID: " + id));
        Usuario tecnico = usuarioRepository.findById(idTecnico)
                .orElseThrow(() -> new RuntimeException("Tecnico no encontrado con ID: " + idTecnico));
        ticket.setAsignado(tecnico);
        return ticketRepository.save(ticket);
    }

    /**
     * Cambia la prioridad de un ticket.
     * 
     * @param id        El ID del ticket.
     * @param prioridad La prioridad del ticket.
     * @return El ticket actualizado.
     * 
     * @author Ariel Serato
     */
    public Ticket cambiarPrioridadTicket(int id, String prioridad) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket no encontrado con ID: " + id));
        ticket.setPrioridad(prioridad);
        return ticketRepository.save(ticket);
    }
}