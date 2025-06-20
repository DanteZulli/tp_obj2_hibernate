package com.grupo25.tp_obj2_hibernate.service;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import com.grupo25.tp_obj2_hibernate.model.entities.Revision;
import com.grupo25.tp_obj2_hibernate.repository.RevisionesRepository;
import com.grupo25.tp_obj2_hibernate.model.entities.Ticket;
import com.grupo25.tp_obj2_hibernate.model.entities.Usuario;
import com.grupo25.tp_obj2_hibernate.exception.RevisionesException;
import java.time.LocalDateTime;
import java.util.List;
import com.grupo25.tp_obj2_hibernate.repository.UsuarioRepository;

@Service
@RequiredArgsConstructor
public class RevisionesService {
    private final RevisionesRepository revisionesRepository;
    private final TicketService ticketService;
    private final UsuarioRepository usuarioRepository;

    public Revision crearRevision(int ticketId, String nombreUsuario, String campoModificado, String valorAnterior,
            String valorNuevo, String observaciones) {
        Ticket ticket = ticketService.obtenerTicket(ticketId);
        Usuario usuario = usuarioRepository.findByNombreUsuario(nombreUsuario)
                .orElseThrow(() -> new RevisionesException("Usuario no encontrado", "USER_NOT_FOUND"));
        Revision revision = new Revision();
        revision.setTicket(ticket);
        revision.setUsuarioModificacion(usuario);
        revision.setFechaCambio(LocalDateTime.now());
        revision.setCampoModificado(campoModificado);
        revision.setValorAnterior(valorAnterior);
        revision.setValorNuevo(valorNuevo);
        revision.setObservaciones(observaciones);
        return revisionesRepository.save(revision);
    }

    public List<Revision> obtenerRevisionesPorTicket(int ticketId) {
        return revisionesRepository.findByTicketId(ticketId);
    }

    public Revision obtenerRevision(int id) {
        return revisionesRepository.findById(id)
                .orElseThrow(() -> new RevisionesException("Revisión no encontrada"));
    }
}