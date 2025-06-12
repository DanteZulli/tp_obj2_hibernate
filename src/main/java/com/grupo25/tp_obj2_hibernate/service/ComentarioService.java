package com.grupo25.tp_obj2_hibernate.service;

import org.springframework.stereotype.Service;

import com.grupo25.tp_obj2_hibernate.model.entities.Comentario;
import com.grupo25.tp_obj2_hibernate.model.entities.Ticket;
import com.grupo25.tp_obj2_hibernate.model.entities.Usuario;
import com.grupo25.tp_obj2_hibernate.repository.ComentarioRepository;
import com.grupo25.tp_obj2_hibernate.exception.ComentarioException;

import lombok.RequiredArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ComentarioService {

    private final ComentarioRepository comentarioRepository;
    private final TicketService ticketService;
    private final UsuarioService usuarioService;

    public Comentario crearComentario(String mensaje, int idTicket, int idUsuario) {
        Ticket ticket = ticketService.obtenerTicket(idTicket);
        Usuario usuario = usuarioService.obtenerUsuario(idUsuario);
        
        Comentario comentario = new Comentario();
        comentario.setMensaje(mensaje);
        comentario.setFecha(LocalDateTime.now());
        comentario.setTicket(ticket);
        comentario.setUsuario(usuario);
        
        return comentarioRepository.save(comentario);
    }

    public Comentario modificarComentario(int id, String mensaje, int idTicket, int idUsuario) {
        Comentario comentario = comentarioRepository.findById(id)
                .orElseThrow(() -> new ComentarioException("Comentario no encontrado con ID: " + id));
        
        Ticket ticket = ticketService.obtenerTicket(idTicket);
        Usuario usuario = usuarioService.obtenerUsuario(idUsuario);
        
        comentario.setMensaje(mensaje);
        comentario.setFecha(LocalDateTime.now());
        comentario.setTicket(ticket);
        comentario.setUsuario(usuario);
        
        return comentarioRepository.save(comentario);
    }

    public Comentario obtenerComentario(int id) {
        return comentarioRepository.findById(id)
                .orElseThrow(() -> new ComentarioException("Comentario no encontrado con ID: " + id));
    }

    public List<Comentario> obtenerComentariosPorTicket(int ticketId) {
        return comentarioRepository.findByTicketId(ticketId);
    }
}
