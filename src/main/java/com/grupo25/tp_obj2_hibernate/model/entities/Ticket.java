package com.grupo25.tp_obj2_hibernate.model.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.sql.Timestamp;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
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
    
    @NotBlank(message = "El título no puede estar vacío")
    @Size(min = 5, max = 100, message = "El título debe tener entre 5 y 100 caracteres")
    @Column(nullable = false)
    private String titulo;

    @NotBlank(message = "La descripción no puede estar vacía")
    @Size(min = 10, max = 1000, message = "La descripción debe tener entre 10 y 1000 caracteres")
    private String descripcion;

    @NotBlank(message = "El estado no puede estar vacío")
    private String estado;

    @NotBlank(message = "La prioridad no puede estar vacía")
    private String prioridad;
    
    @NotNull(message = "La fecha de creación es requerida")
    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;
    
    @Column(name = "fecha_resolucion")
    private LocalDateTime fechaResolucion;
    
    @NotNull(message = "El creador es requerido")
    @ManyToOne
    @JoinColumn(name = "creador_id", nullable = false)
    private Usuario creador;
    
    @ManyToOne
    @JoinColumn(name = "asignado_id")
    private Usuario asignado;
    
    @NotNull(message = "La categoría es requerida")
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
    
    @Column(name = "create_at_ticket")
    @CreationTimestamp
    private Timestamp createAt;

    @Column(name = "update_at_ticket")
    @UpdateTimestamp
    private Timestamp updateAt;
}