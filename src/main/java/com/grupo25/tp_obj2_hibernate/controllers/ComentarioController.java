package com.grupo25.tp_obj2_hibernate.controllers;


import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.grupo25.tp_obj2_hibernate.model.dto.ComentarioDTO;
import com.grupo25.tp_obj2_hibernate.model.dto.TicketDTO;
import com.grupo25.tp_obj2_hibernate.services.ComentarioService;

import lombok.extern.slf4j.Slf4j;

/**
 * Controlador REST para manejar las operaciones relacionadas con comentarios.
 * 
 * @author Grupo 25
 */
@Slf4j
@RestController
@RequestMapping("/api/comentarios")
public class ComentarioController {
	
	  @Autowired
	    private ComentarioService comentarioService;
	  
	  /**
	     * Crea un nuevo comentario.
	     * 
	     * @param idTicket	id del ticket al que se asigna un comentario 
	     * @param mensaje  El mensaje que se quiere ingresar al ticket
	     * @return El comentario creado
	     * 
	     * @author Ignacio Cruz
	     */
	    @PostMapping("/crear")
	    public ResponseEntity<ComentarioDTO> crearComentario(
	            @RequestParam String mensaje,
	            @RequestParam int idTicket) {
	    	ComentarioDTO comentario = comentarioService.crearComentario(mensaje,idTicket);
	        return ResponseEntity.ok(comentario);
	    }
	    
	    /**
	     * traigo los comentarios asociados a un ticket.
	     * 
	     * @param ticketId	El id del ticket que necesitamos sus comentarios
	     * @return Lista de comentarios relacionado al ticket
	     * 
	     * @author Ignacio Cruz
	     */
	    @GetMapping("/traerComent/{idTicket}")
	    public ResponseEntity<List<ComentarioDTO>> crearTicket(@PathVariable int idTicket) {
	    	 try {
	    		 List<ComentarioDTO> listaComentario = comentarioService.getTodosLosComentariosPorTicket(idTicket);
	 	        return ResponseEntity.ok(listaComentario);
	         } catch (RuntimeException e) {
	             log.error("Error al obtener los comentarios del ticket con el ID: {}", idTicket, e);
	             return ResponseEntity.notFound().build();
	         }
	    	
	    }

}
