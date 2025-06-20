package com.grupo25.tp_obj2_hibernate.controller.api;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.grupo25.tp_obj2_hibernate.model.entities.Comentario;
import com.grupo25.tp_obj2_hibernate.service.ComentarioService;
import com.grupo25.tp_obj2_hibernate.exception.ComentarioException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;

@Slf4j
@RestController
@RequestMapping("/api/comentarios")
@RequiredArgsConstructor
public class ComentarioRestController {

	private final ComentarioService comentarioService;

	@PostMapping("/crear")
	public ResponseEntity<Comentario> crearComentario(
			@RequestParam String mensaje,
			@RequestParam int idTicket,
			@AuthenticationPrincipal UserDetails userDetails) {
		log.debug("Creando nuevo comentario para el ticket: {}", idTicket);
		try {
			Comentario comentarioCreado = comentarioService.crearComentario(mensaje, idTicket,
					userDetails.getUsername());
			return ResponseEntity.ok(comentarioCreado);
		} catch (ComentarioException e) {
			log.error("Error al crear el comentario para el ticket con ID: {}", idTicket, e);
			throw e;
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<Comentario> modificarComentario(
			@PathVariable int id,
			@RequestParam String mensaje,
			@RequestParam int idTicket,
			@AuthenticationPrincipal UserDetails userDetails) {
		log.debug("Modificando comentario con ID: {}", id);
		try {
			Comentario comentarioModificado = comentarioService.modificarComentario(id, mensaje, idTicket,
					userDetails.getUsername());
			return ResponseEntity.ok(comentarioModificado);
		} catch (ComentarioException e) {
			log.error("Error al modificar el comentario con ID: {}", id, e);
			throw e;
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<Comentario> obtenerComentario(@PathVariable int id) {
		log.debug("Obteniendo comentario con ID: {}", id);
		try {
			Comentario comentario = comentarioService.obtenerComentario(id);
			return ResponseEntity.ok(comentario);
		} catch (ComentarioException e) {
			log.error("Error al obtener el comentario con ID: {}", id, e);
			throw e;
		}
	}

	@GetMapping("/ticket/{idTicket}")
	public ResponseEntity<List<Comentario>> traerComentarios(@PathVariable int idTicket) {
		log.debug("Obteniendo todos los comentarios del ticket: {}", idTicket);
		try {
			List<Comentario> listaComentario = comentarioService.obtenerComentariosPorTicket(idTicket);
			return ResponseEntity.ok(listaComentario);
		} catch (ComentarioException e) {
			log.error("Error al obtener los comentarios del ticket con ID: {}", idTicket, e);
			throw e;
		}
	}
}
