package com.grupo25.tp_obj2_hibernate.controller.api;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.grupo25.tp_obj2_hibernate.model.entities.Ticket;
import com.grupo25.tp_obj2_hibernate.service.TicketService;
import com.grupo25.tp_obj2_hibernate.model.entities.Usuario;
import com.grupo25.tp_obj2_hibernate.repository.UsuarioRepository;
import com.grupo25.tp_obj2_hibernate.exception.TicketException;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import com.grupo25.tp_obj2_hibernate.service.CategoriaService;

import lombok.extern.slf4j.Slf4j;

/**
 * Controlador REST para manejar las operaciones relacionadas con tickets.
 * 
 * @author Grupo 25
 */
@Slf4j
@RestController
@PreAuthorize("hasAnyRole('ROLE_TECNICO', 'ROLE_ADMIN')")
@RequestMapping("/api/tickets")
public class TicketRestController {

    private TicketService ticketService;
    private UsuarioRepository usuarioRepository;
    
    public TicketRestController(@Autowired TicketService ticketService, 
                              @Autowired UsuarioRepository usuarioRepository,
                              @Autowired CategoriaService categoriaService) {
        this.ticketService = ticketService;
        this.usuarioRepository = usuarioRepository;
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
            List<Ticket> tickets = ticketService.getAllTickets();
            return ResponseEntity.ok(tickets);
        } catch (TicketException e) {
            log.error("Error al obtener los tickets: {}", e.getMessage(), e);
            throw e; // Dejamos que el GlobalExceptionHandler maneje la respuesta
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
     * @param categoriaId	Indica el ID de la categoria del ticket
     * @param userDetails	Detalles del usuario autenticado
     * @return El ticket creado
     * 
     * @author Ignacio Cruz
     */
    @PostMapping("/crear")
    public ResponseEntity<Ticket> crearTicket(
            @RequestParam String titulo,
            @RequestParam String descripcion,
            @RequestParam String estado,
            @RequestParam String prioridad,
            @RequestParam Integer categoriaId,
            @AuthenticationPrincipal UserDetails userDetails) {
        try {
            Usuario usuario = usuarioRepository.findByNombreUsuario(userDetails.getUsername())
                    .orElseThrow(() -> new TicketException("Usuario no encontrado", "USER_NOT_FOUND"));
            Ticket ticket = ticketService.crearTicketCompleto(titulo, descripcion, estado, prioridad, usuario.getId(), categoriaId);
            return ResponseEntity.ok(ticket);
        } catch (TicketException e) {
            log.error("Error al crear el ticket: {}", e.getMessage(), e);
            throw e; // Dejamos que el GlobalExceptionHandler maneje la respuesta
        }
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
        } catch (TicketException e) {
            log.error("Error al obtener el estado del ticket con ID: {}: {}", id, e.getMessage(), e);
            throw e; // Dejamos que el GlobalExceptionHandler maneje la respuesta
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
        } catch (TicketException e) {
            log.error("Error al obtener los tickets creados por el usuario con ID: {}: {}", id, e.getMessage(), e);
            throw e; // Dejamos que el GlobalExceptionHandler maneje la respuesta
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
        } catch (TicketException e) {
            log.error("Error al asignar el ticket con ID: {} al tecnico con ID: {}: {}", id, idTecnico, e.getMessage(), e);
            throw e; // Dejamos que el GlobalExceptionHandler maneje la respuesta
        }
    }

    /**
     * Tomar un ticket asignándolo al técnico autenticado.
     * 
     * @param id El ID del ticket
     * @param userDetails Detalles del usuario autenticado
     * @return ResponseEntity con el ticket actualizado, o un error si no existe
     * 
     * @author Grupo 25
     */
    @PutMapping("/{id}/tomar")
    public ResponseEntity<Ticket> tomarTicket(@PathVariable("id") int id, @AuthenticationPrincipal UserDetails userDetails) {
        try {
            Usuario usuario = usuarioRepository.findByNombreUsuario(userDetails.getUsername())
                    .orElseThrow(() -> new TicketException("Usuario no encontrado", "USER_NOT_FOUND"));
            Ticket ticket = ticketService.asignarTicketATecnico(id, usuario.getId());
            return ResponseEntity.ok(ticket);
        } catch (TicketException e) {
            log.error("Error al tomar el ticket con ID: {}: {}", id, e.getMessage(), e);
            throw e; // Dejamos que el GlobalExceptionHandler maneje la respuesta
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
        } catch (TicketException e) {
            log.error("Error al cambiar la prioridad del ticket con ID: {}: {}", id, e.getMessage(), e);
            throw e; // Dejamos que el GlobalExceptionHandler maneje la respuesta
        }
    }

    /**
     * Actualiza un ticket existente.
     * 
     * @param id El ID del ticket a actualizar
     * @param requestBody Mapa con los campos a actualizar
     * @return ResponseEntity con el ticket actualizado, o un error si no existe
     * 
     * @author Grupo 25
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_TECNICO', 'ROLE_ADMIN')")
    public ResponseEntity<Ticket> actualizarTicket(
            @PathVariable("id") int id,
            @RequestBody Map<String, String> requestBody) {
        try {
            String titulo = requestBody.get("titulo");
            String descripcion = requestBody.get("descripcion");
            String estado = requestBody.get("estado");
            String prioridad = requestBody.get("prioridad");
            Integer categoriaId = Integer.parseInt(requestBody.get("categoriaId"));

            Ticket ticket = ticketService.actualizarTicket(id, titulo, descripcion, estado, prioridad, categoriaId);
            return ResponseEntity.ok(ticket);
        } catch (TicketException e) {
            log.error("Error al actualizar el ticket con ID: {}: {}", id, e.getMessage(), e);
            throw e; // Dejamos que el GlobalExceptionHandler maneje la respuesta
        } catch (NumberFormatException e) {
            throw new TicketException("El ID de categoría debe ser un número válido", "INVALID_CATEGORY_ID");
        }
    }

}