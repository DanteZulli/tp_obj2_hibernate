package com.grupo25.tp_obj2_hibernate.model.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.grupo25.tp_obj2_hibernate.model.entities.Ticket;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketDTO {
    private int id;
    private String titulo;
    private String descripcion;
    private String estado;
    private String prioridad;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaResolucion;
    private UsuarioDTO creador;
    private UsuarioDTO asignado;
    private CategoriaDTO categoria;
    private List<EtiquetaDTO> etiquetas;
    private List<ComentarioDTO> comentarios;

    public TicketDTO(Ticket ticket) {
        this.id = ticket.getId();
        this.titulo = ticket.getTitulo();
        this.descripcion = ticket.getDescripcion();
        this.estado = ticket.getEstado();
        this.prioridad = ticket.getPrioridad();
        this.fechaCreacion = ticket.getFechaCreacion();
        this.fechaResolucion = ticket.getFechaResolucion();
        this.creador = new UsuarioDTO(ticket.getCreador());
        this.asignado = new UsuarioDTO(ticket.getAsignado());
        this.categoria = new CategoriaDTO(ticket.getCategoria());
        this.etiquetas = new ArrayList<>(ticket.getEtiquetas().stream().map(EtiquetaDTO::new).collect(Collectors.toList()));
        this.comentarios = new ArrayList<>(ticket.getComentarios().stream().map(ComentarioDTO::new).collect(Collectors.toList()));
    }

}
