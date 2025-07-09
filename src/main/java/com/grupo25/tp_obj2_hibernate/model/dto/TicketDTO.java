package com.grupo25.tp_obj2_hibernate.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record TicketDTO(
    Integer id,
    
    @NotBlank(message = "El título no puede estar vacío")
    @Size(min = 5, max = 100, message = "El título debe tener entre 5 y 100 caracteres")
    String titulo,
    
    @NotBlank(message = "La descripción no puede estar vacía")
    @Size(min = 10, max = 1000, message = "La descripción debe tener entre 10 y 1000 caracteres")
    String descripcion,
    
    @NotBlank(message = "El estado no puede estar vacío")
    String estado,
    
    @NotBlank(message = "La prioridad no puede estar vacía")
    String prioridad,
    
    @NotNull(message = "La categoría es requerida")
    @Positive(message = "El ID de categoría debe ser positivo")
    Integer categoriaId
) {} 