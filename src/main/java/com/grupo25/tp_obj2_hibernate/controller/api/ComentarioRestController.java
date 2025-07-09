package com.grupo25.tp_obj2_hibernate.controller.api;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.grupo25.tp_obj2_hibernate.model.entities.Comentario;
import com.grupo25.tp_obj2_hibernate.service.ComentarioService;
import com.grupo25.tp_obj2_hibernate.exception.ComentarioException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;

@Slf4j
@RestController
@RequestMapping("/api/comentarios")
@RequiredArgsConstructor
@Tag(name = "Gestión de Comentarios", description = "API para gestionar comentarios en tickets del sistema de soporte técnico")
public class ComentarioRestController {

	private final ComentarioService comentarioService;

	@PostMapping("/crear")
	@Operation(summary = "Crear nuevo comentario", description = "Crea un nuevo comentario en un ticket específico")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Comentario creado exitosamente",
					content = @Content(schema = @Schema(implementation = Comentario.class))),
		@ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
		@ApiResponse(responseCode = "404", description = "Ticket no encontrado"),
		@ApiResponse(responseCode = "500", description = "Error interno del servidor")
	})
	public ResponseEntity<Comentario> crearComentario(
			@Parameter(description = "Mensaje del comentario", required = true, example = "Ticket en proceso de revisión")
			@RequestParam String mensaje,
			@Parameter(description = "ID del ticket donde se creará el comentario", required = true, example = "1")
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
	@Operation(summary = "Modificar comentario existente", description = "Modifica el contenido de un comentario existente")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Comentario modificado exitosamente",
					content = @Content(schema = @Schema(implementation = Comentario.class))),
		@ApiResponse(responseCode = "404", description = "Comentario no encontrado"),
		@ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
		@ApiResponse(responseCode = "403", description = "No autorizado para modificar este comentario")
	})
	public ResponseEntity<Comentario> modificarComentario(
			@Parameter(description = "ID del comentario a modificar", required = true, example = "1")
			@PathVariable int id,
			@Parameter(description = "Nuevo mensaje del comentario", required = true, example = "Ticket actualizado - en proceso de resolución")
			@RequestParam String mensaje,
			@Parameter(description = "ID del ticket asociado", required = true, example = "1")
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
	@Operation(summary = "Obtener comentario por ID", description = "Recupera la información de un comentario específico")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Comentario encontrado exitosamente",
					content = @Content(schema = @Schema(implementation = Comentario.class))),
		@ApiResponse(responseCode = "404", description = "Comentario no encontrado")
	})
	public ResponseEntity<Comentario> obtenerComentario(
			@Parameter(description = "ID del comentario a obtener", required = true, example = "1")
			@PathVariable int id) {
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
	@Operation(summary = "Obtener comentarios por ticket", description = "Recupera todos los comentarios asociados a un ticket específico")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Comentarios encontrados exitosamente",
					content = @Content(schema = @Schema(implementation = Comentario.class))),
		@ApiResponse(responseCode = "404", description = "Ticket no encontrado")
	})
	public ResponseEntity<List<Comentario>> traerComentarios(
			@Parameter(description = "ID del ticket para obtener sus comentarios", required = true, example = "1")
			@PathVariable int idTicket) {
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
