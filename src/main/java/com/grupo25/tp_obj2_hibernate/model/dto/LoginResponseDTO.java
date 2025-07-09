package com.grupo25.tp_obj2_hibernate.model.dto;

public record LoginResponseDTO(
    String username,
    String nombre,
    String email,
    boolean esAdmin,
    String rol,
    String mensaje
) {} 