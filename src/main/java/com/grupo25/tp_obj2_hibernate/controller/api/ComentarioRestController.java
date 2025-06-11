package com.grupo25.tp_obj2_hibernate.controller.api;

import java.util.List;
import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.grupo25.tp_obj2_hibernate.model.entities.Comentario;
import com.grupo25.tp_obj2_hibernate.model.entities.Ticket;
import com.grupo25.tp_obj2_hibernate.model.entities.Usuario;
import com.grupo25.tp_obj2_hibernate.service.ComentarioService;
import com.grupo25.tp_obj2_hibernate.service.TicketService;
import com.grupo25.tp_obj2_hibernate.service.UsuarioService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/comentarios")
@RequiredArgsConstructor
public class ComentarioRestController {

	private final ComentarioService comentarioService;
	private final TicketService ticketService;
	private final UsuarioService usuarioService;

	@PostMapping("/crear")
	public ResponseEntity<Comentario> crearComentario(
			@RequestParam String mensaje,
			@RequestParam int idTicket,
			@RequestParam int idUsuario) {
		log.debug("Creando nuevo comentario para el ticket: {}", idTicket);
		try {
			Comentario comentario = new Comentario();
			comentario.setMensaje(mensaje);
			comentario.setFecha(LocalDateTime.now());
			
			Ticket ticket = ticketService.obtenerTicket(idTicket);
			comentario.setTicket(ticket);
			
			Usuario usuario = usuarioService.obtenerUsuario(idUsuario);
			comentario.setUsuario(usuario);
			
			Comentario comentarioCreado = comentarioService.crearComentario(comentario);
			return ResponseEntity.ok(comentarioCreado);
		} catch (RuntimeException e) {
			log.error("Error al crear el comentario para el ticket con ID: {}", idTicket, e);
			return ResponseEntity.notFound().build();
		}
	}

	@PutMapping("/{comentarioId}")
	public ResponseEntity<Comentario> modificarComentario(
			@PathVariable int comentarioId,
			@RequestParam String mensaje,
			@RequestParam int idTicket,
			@RequestParam int idUsuario) {
		log.debug("Modificando comentario con ID: {}", comentarioId);
		try {
			Ticket ticket = ticketService.obtenerTicket(idTicket);
			Usuario usuario = usuarioService.obtenerUsuario(idUsuario);
			
			Comentario comentarioModificado = comentarioService.modificarComentario(
				comentarioId, 
				mensaje, 
				LocalDateTime.now(),
				ticket,
				usuario
			);
			return ResponseEntity.ok(comentarioModificado);
		} catch (RuntimeException e) {
			log.error("Error al modificar el comentario con ID: {}", comentarioId, e);
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping("/{comentarioId}")
	public ResponseEntity<Comentario> obtenerComentario(@PathVariable int comentarioId) {
		log.debug("Obteniendo comentario con ID: {}", comentarioId);
		try {
			Comentario comentario = comentarioService.obtenerComentario(comentarioId);
			return ResponseEntity.ok(comentario);
		} catch (RuntimeException e) {
			log.error("Error al obtener el comentario con ID: {}", comentarioId, e);
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping("/ticket/{idTicket}")
	public ResponseEntity<List<Comentario>> traerComentarios(@PathVariable int idTicket) {
		log.debug("Obteniendo todos los comentarios del ticket: {}", idTicket);
		try {
			List<Comentario> listaComentario = comentarioService.obtenerComentariosPorTicket(idTicket);
			return ResponseEntity.ok(listaComentario);
		} catch (RuntimeException e) {
			log.error("Error al obtener los comentarios del ticket con ID: {}", idTicket, e);
			return ResponseEntity.notFound().build();
		}
	}
}
