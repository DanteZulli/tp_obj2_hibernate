package com.grupo25.tp_obj2_hibernate.service;

import org.springframework.stereotype.Service;

import com.grupo25.tp_obj2_hibernate.model.entities.Comentario;
import com.grupo25.tp_obj2_hibernate.model.entities.Ticket;
import com.grupo25.tp_obj2_hibernate.model.entities.Usuario;
import com.grupo25.tp_obj2_hibernate.repository.ComentarioRepository;

import lombok.RequiredArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ComentarioService {

    private final ComentarioRepository comentarioRepository;

    public Comentario crearComentario(Comentario comentario) {
        return comentarioRepository.save(comentario);
    }

    public Comentario modificarComentario(int comentarioId, String mensaje, LocalDateTime fecha, Ticket ticket, Usuario usuario) {
        Comentario comentario = comentarioRepository.findById(comentarioId)
                .orElseThrow(() -> new RuntimeException("Comentario no encontrado con ID: " + comentarioId));
        
        comentario.setMensaje(mensaje);
        comentario.setFecha(fecha);
        comentario.setTicket(ticket);
        comentario.setUsuario(usuario);
        
        return comentarioRepository.save(comentario);
    }

    public Comentario obtenerComentario(int comentarioId) {
        return comentarioRepository.findById(comentarioId)
                .orElseThrow(() -> new RuntimeException("Comentario no encontrado con ID: " + comentarioId));
    }

    public List<Comentario> obtenerComentariosPorTicket(int ticketId) {
        return comentarioRepository.findByTicketId(ticketId);
    }

}
