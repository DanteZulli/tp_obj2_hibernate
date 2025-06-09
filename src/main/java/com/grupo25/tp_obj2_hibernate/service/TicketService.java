package com.grupo25.tp_obj2_hibernate.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.grupo25.tp_obj2_hibernate.model.entities.Ticket;
import com.grupo25.tp_obj2_hibernate.model.entities.Usuario;
import com.grupo25.tp_obj2_hibernate.model.entities.Categoria;
import com.grupo25.tp_obj2_hibernate.repository.TicketRepository;
import com.grupo25.tp_obj2_hibernate.repository.UsuarioRepository;
import com.grupo25.tp_obj2_hibernate.exception.TicketException;

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
    private CategoriaService categoriaService;

    public TicketService(TicketRepository ticketRepository,
            UsuarioRepository usuarioRepository, CategoriaService categoriaService) {
        this.ticketRepository = ticketRepository;
        this.usuarioRepository = usuarioRepository;
        this.categoriaService = categoriaService;
    }

    /**
     * Obtiene todos los tickets.
     * 
     * @return List<TicketDTO>
     * 
     * @author Ariel Serato
     */
    public List<Ticket> getAllTickets() {
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
                .orElseThrow(() -> new TicketException("Usuario no encontrado con ID: " + usuarioId, "USER_NOT_FOUND"));

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
                .orElseThrow(() -> new TicketException("Ticket no encontrado con ID: " + id, "TICKET_NOT_FOUND"));
        Usuario tecnico = usuarioRepository.findById(idTecnico)
                .orElseThrow(() -> new TicketException("Tecnico no encontrado con ID: " + idTecnico, "TECHNICIAN_NOT_FOUND"));
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
                .orElseThrow(() -> new TicketException("Ticket no encontrado con ID: " + id, "TICKET_NOT_FOUND"));
        ticket.setPrioridad(prioridad);
        return ticketRepository.save(ticket);
    }

    /**
     * Cuenta la cantidad de tickets por estado.
     * 
     * @param estado el estado de los tickets a contar
     * @return la cantidad de tickets con el estado especificado
     * @author Dante Zulli
     */
    public long contarTicketsPorEstado(String estado) {
        return ticketRepository.countByEstado(estado);
    }

    /**
     * Cuenta la cantidad de tickets por estado y creador.
     * 
     * @param estado el estado de los tickets a contar
     * @param creadorId el ID del creador de los tickets
     * @return la cantidad de tickets con el estado y creador especificados
     * @author Dante Zulli
     */
    public long contarTicketsPorEstadoYCreador(String estado, int creadorId) {
        return ticketRepository.countByEstadoAndCreadorId(estado, creadorId);
    }

    /**
     * Cuenta la cantidad de tickets por prioridad.
     * 
     * @param prioridad la prioridad de los tickets a contar
     * @return la cantidad de tickets con la prioridad especificada
     * @author Dante Zulli
     */
    public long contarTicketsPorPrioridad(String prioridad) {
        return ticketRepository.countByPrioridad(prioridad);
    }

    /**
     * Cuenta la cantidad de tickets por prioridad y creador.
     * 
     * @param prioridad la prioridad de los tickets a contar
     * @param creadorId el ID del creador de los tickets
     * @return la cantidad de tickets con la prioridad y creador especificados
     * @author Dante Zulli
     */
    public long contarTicketsPorPrioridadYCreador(String prioridad, int creadorId) {
        return ticketRepository.countByPrioridadAndCreadorId(prioridad, creadorId);
    }

    /**
     * Obtiene todos los tickets asignados a un usuario específico.
     *
     * @param usuarioId El ID del usuario asignado.
     * @return La lista de tickets asignados al usuario.
     * 
     * @throws RuntimeException Si no se encuentra el usuario con el ID dado.
     * 
     * @author Dante Zulli
     */
    public List<Ticket> getTodosLosTicketsPorUsuarioAsignado(int usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new TicketException("Usuario no encontrado con ID: " + usuarioId, "USER_NOT_FOUND"));

        return ticketRepository.findByAsignado(usuario);
    }

    /**
     * Obtiene un ticket por su ID.
     * 
     * @param id El ID del ticket.
     * @return El ticket encontrado.
     * 
     * @throws RuntimeException Si no se encuentra el ticket con el ID dado.
     * 
     * @author Grupo 25
     */
    public Ticket getTicketPorId(int id) {
        return ticketRepository.findById(id)
                .orElseThrow(() -> new TicketException("Ticket no encontrado con ID: " + id, "TICKET_NOT_FOUND"));
    }

    /**
     * Actualiza un ticket completo.
     * 
     * @param ticket El ticket con los datos actualizados.
     * @return El ticket actualizado.
     * 
     * @author Grupo 25
     */
    public Ticket actualizarTicket(Ticket ticket) {
        // Verificar que el ticket existe
        ticketRepository.findById(ticket.getId())
                .orElseThrow(() -> new TicketException("Ticket no encontrado con ID: " + ticket.getId(), "TICKET_NOT_FOUND"));
        
        return ticketRepository.save(ticket);
    }

    /**
     * Crea un ticket con creador y categoría.
     *
     * @param titulo El título del ticket
     * @param descripcion La descripción del ticket
     * @param estado El estado del ticket
     * @param prioridad La prioridad del ticket
     * @param creadorId El ID del usuario creador
     * @param categoriaId El ID de la categoría
     * @return El ticket creado
     * 
     * @author Grupo 25
     */
    public Ticket crearTicketCompleto(String titulo, String descripcion, String estado, String prioridad, int creadorId, int categoriaId) {
        Usuario creador = usuarioRepository.findById(creadorId)
                .orElseThrow(() -> new TicketException("Usuario creador no encontrado con ID: " + creadorId, "USER_NOT_FOUND"));
        
        Categoria categoria = categoriaService.getCategoriaPorId(categoriaId);
        
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

    /**
     * Cambia el estado de un ticket.
     * 
     * @param id El ID del ticket.
     * @param estado El nuevo estado del ticket.
     * @return El ticket actualizado.
     * 
     * @author Grupo 25
     */
    public Ticket cambiarEstadoTicket(int id, String estado) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new TicketException("Ticket no encontrado con ID: " + id, "TICKET_NOT_FOUND"));
        ticket.setEstado(estado);
        if ("RESUELTO".equals(estado)) {
            ticket.setFechaResolucion(LocalDateTime.now());
        }
        return ticketRepository.save(ticket);
    }

    /**
     * Actualiza un ticket con los campos específicos.
     * 
     * @param id El ID del ticket a actualizar
     * @param titulo El nuevo título del ticket
     * @param descripcion La nueva descripción del ticket
     * @param estado El nuevo estado del ticket
     * @param prioridad La nueva prioridad del ticket
     * @param categoriaId El ID de la nueva categoría
     * @return El ticket actualizado
     * 
     * @author Grupo 25
     */
    public Ticket actualizarTicket(int id, String titulo, String descripcion, String estado, String prioridad, Integer categoriaId) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new TicketException("Ticket no encontrado con ID: " + id, "TICKET_NOT_FOUND"));
        
        ticket.setTitulo(titulo);
        ticket.setDescripcion(descripcion);
        ticket.setEstado(estado);
        ticket.setPrioridad(prioridad);
        
        if (categoriaId != null) {
            Categoria categoria = categoriaService.getCategoriaPorId(categoriaId);
            ticket.setCategoria(categoria);
        }
        
        if ("RESUELTO".equals(estado) && ticket.getFechaResolucion() == null) {
            ticket.setFechaResolucion(LocalDateTime.now());
        }
        
        return ticketRepository.save(ticket);
    }
}