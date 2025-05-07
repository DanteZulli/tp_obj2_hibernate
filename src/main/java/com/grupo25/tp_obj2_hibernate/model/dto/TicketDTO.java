package com.grupo25.tp_obj2_hibernate.model.dto;

import java.time.LocalDateTime;

import com.grupo25.tp_obj2_hibernate.model.entities.Categoria;
import com.grupo25.tp_obj2_hibernate.model.entities.Cliente;
import com.grupo25.tp_obj2_hibernate.model.entities.Tecnico;
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
    // TODO: Agregar Categoria, Etiquetas y Comentarios al mappeo
    // como está hecho en TecnicoDTO

    public TicketDTO(Ticket ticket) {
        this.id = ticket.getId();
        this.titulo = ticket.getTitulo();
        this.descripcion = ticket.getDescripcion();
        this.estado = ticket.getEstado();
        this.prioridad = ticket.getPrioridad();
        this.fechaCreacion = ticket.getFechaCreacion();
        this.fechaResolucion = ticket.getFechaResolucion();
    }

}
