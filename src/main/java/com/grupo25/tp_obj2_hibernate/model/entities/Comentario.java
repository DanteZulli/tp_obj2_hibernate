package com.grupo25.tp_obj2_hibernate.model.entities;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Comentario {
    private int id;
    private String mensaje;
    private LocalDateTime fecha;
    private Ticket ticket;
    private Usuario usuario;
}
