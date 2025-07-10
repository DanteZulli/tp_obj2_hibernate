package com.grupo25.tp_obj2_hibernate.controller.api.swagger;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.grupo25.tp_obj2_hibernate.exception.ComentarioException;
import com.grupo25.tp_obj2_hibernate.model.dto.ComentarioDTO;
import com.grupo25.tp_obj2_hibernate.model.entities.Comentario;
import com.grupo25.tp_obj2_hibernate.service.ComentarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/swagger/comentarios")
@RequiredArgsConstructor
@Tag(name = "Gestión de Comentarios", description = "API para gestión de comentarios usando DTOs con Record Class")
public class ComentarioSwaggerController {
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
	public ResponseEntity<ComentarioDTO> crearComentario(
			@Parameter(description = "Mensaje del comentario", required = true, example = "Ticket en proceso de revisión")
			@RequestParam String mensaje,
			@Parameter(description = "ID del ticket donde se creará el comentario", required = true, example = "1")
			@RequestParam int idTicket,
			@AuthenticationPrincipal UserDetails userDetails) {
		log.debug("Creando nuevo comentario para el ticket: {}", idTicket);
		try {
			Comentario comentarioCreado = comentarioService.crearComentario(mensaje, idTicket,
					userDetails.getUsername());
			ComentarioDTO comenDTO = new ComentarioDTO(comentarioCreado.getId(),comentarioCreado.getMensaje(),comentarioCreado.getFecha());
			return ResponseEntity.ok(comenDTO);
		} catch (ComentarioException e) {
			log.error("Error al crear el comentario para el ticket con ID: {}", idTicket, e);
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
	public ResponseEntity<ComentarioDTO> obtenerComentario(
			@Parameter(description = "ID del comentario a obtener", required = true, example = "1")
			@PathVariable int id) {
		log.debug("Obteniendo comentario con ID: {}", id);
		try {
			Comentario comentario = comentarioService.obtenerComentario(id);
			ComentarioDTO comenDTO = new ComentarioDTO(comentario.getId(),comentario.getMensaje(),comentario.getFecha());
			return ResponseEntity.ok(comenDTO);
		} catch (ComentarioException e) {
			log.error("Error al obtener el comentario con ID: {}", id, e);
			throw e;
		}
	}
}
