package com.grupo25.tp_obj2_hibernate.controller.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.grupo25.tp_obj2_hibernate.model.entities.Revision;
import com.grupo25.tp_obj2_hibernate.service.RevisionesService;
import com.grupo25.tp_obj2_hibernate.exception.RevisionesException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import java.util.List;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;

@Slf4j
@RestController
@RequestMapping("/api/revisiones")
@RequiredArgsConstructor
public class RevisionesRestController {
    private final RevisionesService revisionesService;

    @PostMapping("/crear")
    public ResponseEntity<Revision> crearRevision(
            @RequestParam int ticketId,
            @RequestParam String campoModificado,
            @RequestParam String valorAnterior,
            @RequestParam String valorNuevo,
            @RequestParam String observaciones,
            @AuthenticationPrincipal UserDetails userDetails) {
        log.debug("Creando revisión para ticket {} campo {} por usuario {}", ticketId, campoModificado,
                userDetails.getUsername());
        try {
            Revision revision = revisionesService.crearRevision(ticketId, userDetails.getUsername(), campoModificado,
                    valorAnterior, valorNuevo, observaciones);
            return ResponseEntity.ok(revision);
        } catch (RevisionesException e) {
            log.error("Error al crear la revisión: {}", e.getMessage(), e);
            throw e;
        }
    }

    @GetMapping("/ticket/{ticketId}")
    public ResponseEntity<List<Revision>> obtenerRevisionesPorTicket(@PathVariable int ticketId) {
        log.debug("Obteniendo revisiones para ticket {}", ticketId);
        try {
            List<Revision> revisiones = revisionesService.obtenerRevisionesPorTicket(ticketId);
            return ResponseEntity.ok(revisiones);
        } catch (RevisionesException e) {
            log.error("Error al obtener revisiones del ticket: {}", ticketId, e);
            throw e;
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Revision> obtenerRevision(@PathVariable int id) {
        log.debug("Obteniendo revisión con ID: {}", id);
        try {
            Revision revision = revisionesService.obtenerRevision(id);
            return ResponseEntity.ok(revision);
        } catch (RevisionesException e) {
            log.error("Error al obtener la revisión con ID: {}", id, e);
            throw e;
        }
    }
}