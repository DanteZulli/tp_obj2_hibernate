package com.grupo25.tp_obj2_hibernate.model.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ComentarioDTO (
	    Integer id,
	    
	    @NotBlank(message = "El comentario no puede estar vacío")
	    @Size(min = 2, max = 500, message = "El mensaje debe tener entre 2 y 50 caracteres")
	    String mensaje,
	    
	    @NotNull(message = "La fecha no puede ser nula")
	    LocalDateTime fecha
	) {}
