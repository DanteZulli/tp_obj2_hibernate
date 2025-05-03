package com.grupo25.tp_obj2_hibernate.model.entities;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Ticket {
    private int id;
    private String titulo;
    private String descripcion;
    private String estado;
    private String prioridad;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaResolucion;
    private Usuario creador;
    private Usuario asignado;
    private Categoria categoria;
    private List<Etiqueta> etiquetas;
    private List<Comentario> comentarios;
}