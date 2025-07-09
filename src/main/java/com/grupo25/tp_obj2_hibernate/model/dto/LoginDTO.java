package com.grupo25.tp_obj2_hibernate.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginDTO(
    @NotBlank(message = "El nombre de usuario no puede estar vacío")
    @Size(min = 4, max = 50, message = "El nombre de usuario debe tener entre 4 y 50 caracteres")
    String username,
    
    @NotBlank(message = "La contraseña no puede estar vacía")
    @Size(min = 4, message = "La contraseña debe tener al menos 4 caracteres")
    String password
) {} 