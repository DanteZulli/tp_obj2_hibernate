package com.grupo25.tp_obj2_hibernate.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoriaDTO(
    Integer id,
    
    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(min = 2, max = 50, message = "El nombre debe tener entre 2 y 50 caracteres")
    String nombre,
    
    @Size(max = 200, message = "La descripción no puede tener más de 200 caracteres")
    String descripcion
) {} 