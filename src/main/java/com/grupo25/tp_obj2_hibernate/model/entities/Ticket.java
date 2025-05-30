package com.grupo25.tp_obj2_hibernate.model.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tickets")
@Getter
@Setter
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(nullable = false)
    private String titulo;
    private String descripcion;
    private String estado;
    private String prioridad;
    
    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;
    
    @Column(name = "fecha_resolucion")
    private LocalDateTime fechaResolucion;
    
    @ManyToOne
    @JoinColumn(name = "creador_id", nullable = false)
    private Usuario creador;
    
    @ManyToOne
    @JoinColumn(name = "asignado_id")
    private Usuario asignado;
    
    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;
    
    @ManyToMany
    @JoinTable(
        name = "ticket_etiqueta",
        joinColumns = @JoinColumn(name = "ticket_id"),
        inverseJoinColumns = @JoinColumn(name = "etiqueta_id")
    )
    private List<Etiqueta> etiquetas;
    
    @OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL)
    private List<Comentario> comentarios;
    
    @OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL)
    private List<Revision> revisiones;
}