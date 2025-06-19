package com.grupo25.tp_obj2_hibernate.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.grupo25.tp_obj2_hibernate.model.entities.Ticket;
import com.grupo25.tp_obj2_hibernate.model.entities.Usuario;
import com.grupo25.tp_obj2_hibernate.model.entities.Categoria;
import com.grupo25.tp_obj2_hibernate.model.entities.Etiqueta;
import com.grupo25.tp_obj2_hibernate.repository.TicketRepository;
import com.grupo25.tp_obj2_hibernate.repository.UsuarioRepository;
import com.grupo25.tp_obj2_hibernate.exception.TicketException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;
    private final UsuarioRepository usuarioRepository;
    private final CategoriaService categoriaService;
    private final EtiquetaService etiquetaService;

    public Ticket crearTicket(String titulo, String descripcion, String estado, String prioridad, String nombreUsuario,
            Integer categoriaId) {
        Usuario creador = usuarioRepository.findByNombreUsuario(nombreUsuario)
                .orElseThrow(() -> new TicketException("Usuario creador no encontrado", "USER_NOT_FOUND"));
        Categoria categoria = categoriaService.obtenerCategoria(categoriaId);

        Ticket ticket = new Ticket();
        ticket.setTitulo(titulo);
        ticket.setDescripcion(descripcion);
        ticket.setEstado(estado);
        ticket.setPrioridad(prioridad);
        ticket.setFechaCreacion(LocalDateTime.now());
        ticket.setCreador(creador);
        ticket.setCategoria(categoria);
        return ticketRepository.save(ticket);
    }

    public Ticket modificarTicket(int id, String titulo, String descripcion, String estado, String prioridad,
            Integer categoriaId) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new TicketException("Ticket no encontrado con ID: " + id, "TICKET_NOT_FOUND"));

        ticket.setTitulo(titulo);
        ticket.setDescripcion(descripcion);
        ticket.setEstado(estado);
        ticket.setPrioridad(prioridad);

        if (categoriaId != null) {
            Categoria categoria = categoriaService.obtenerCategoria(categoriaId);
            ticket.setCategoria(categoria);
        }

        return ticketRepository.save(ticket);
    }

    public Ticket obtenerTicket(int id) {
        return ticketRepository.findById(id)
                .orElseThrow(() -> new TicketException("Ticket no encontrado con ID: " + id, "TICKET_NOT_FOUND"));
    }

    public List<Ticket> obtenerTodosLosTickets() {
        return ticketRepository.findAll();
    }

    public String obtenerEstadoTicket(int id) {
        return ticketRepository.findEstadoById(id);
    }

    public List<Ticket> obtenerTodosLosTicketsPorUsuarioCreador(int usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new TicketException("Usuario no encontrado con ID: " + usuarioId, "USER_NOT_FOUND"));

        return ticketRepository.findByCreador(usuario);
    }

    public Ticket asignarTicketATecnico(int id, int idTecnico) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new TicketException("Ticket no encontrado con ID: " + id, "TICKET_NOT_FOUND"));
        Usuario tecnico = usuarioRepository.findById(idTecnico)
                .orElseThrow(() -> new TicketException("Tecnico no encontrado con ID: " + idTecnico,
                        "TECHNICIAN_NOT_FOUND"));
        ticket.setAsignado(tecnico);
        return ticketRepository.save(ticket);
    }

    public Ticket tomarTicket(int id, String nombreUsuario) {
        Usuario tecnico = usuarioRepository.findByNombreUsuario(nombreUsuario)
                .orElseThrow(() -> new TicketException("Usuario no encontrado", "USER_NOT_FOUND"));
        return asignarTicketATecnico(id, tecnico.getId());
    }

    public Ticket cambiarPrioridadTicket(int id, String prioridad) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new TicketException("Ticket no encontrado con ID: " + id, "TICKET_NOT_FOUND"));
        ticket.setPrioridad(prioridad);
        return ticketRepository.save(ticket);
    }

    public long contarTicketsPorEstado(String estado) {
        return ticketRepository.countByEstado(estado);
    }

    public long contarTicketsPorEstadoYCreador(String estado, int creadorId) {
        return ticketRepository.countByEstadoAndCreadorId(estado, creadorId);
    }

    public long contarTicketsPorPrioridad(String prioridad) {
        return ticketRepository.countByPrioridad(prioridad);
    }

    public long contarTicketsPorPrioridadYCreador(String prioridad, int creadorId) {
        return ticketRepository.countByPrioridadAndCreadorId(prioridad, creadorId);
    }

    public List<Ticket> obtenerTodosLosTicketsPorUsuarioAsignado(int usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new TicketException("Usuario no encontrado con ID: " + usuarioId, "USER_NOT_FOUND"));

        return ticketRepository.findByAsignado(usuario);
    }

    public Ticket cambiarEstadoTicket(int id, String estado) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new TicketException("Ticket no encontrado con ID: " + id, "TICKET_NOT_FOUND"));
        ticket.setEstado(estado);
        if ("RESUELTO".equals(estado)) {
            ticket.setFechaResolucion(LocalDateTime.now());
        }
        return ticketRepository.save(ticket);
    }

    public Ticket agregarEtiquetaATicket(int ticketId, int etiquetaId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new TicketException("Ticket no encontrado con ID: " + ticketId, "TICKET_NOT_FOUND"));
        Etiqueta etiqueta = etiquetaService.obtenerEtiqueta(etiquetaId);
        if (!ticket.getEtiquetas().contains(etiqueta)) {
            ticket.getEtiquetas().add(etiqueta);
        }
        return ticketRepository.save(ticket);
    }

    public Ticket quitarEtiquetaDeTicket(int ticketId, int etiquetaId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new TicketException("Ticket no encontrado con ID: " + ticketId, "TICKET_NOT_FOUND"));
        Etiqueta etiqueta = etiquetaService.obtenerEtiqueta(etiquetaId);
        ticket.getEtiquetas().remove(etiqueta);
        return ticketRepository.save(ticket);
    }
}