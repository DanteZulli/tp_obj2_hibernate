package com.grupo25.tp_obj2_hibernate.model.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.sql.Timestamp;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "comentarios")
@Getter
@Setter
public class Comentario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @NotBlank(message = "El mensaje no puede estar vacío")
    @Size(min = 1, max = 500, message = "El mensaje debe tener entre 1 y 500 caracteres")
    @Column(nullable = false)
    private String mensaje;

    @NotNull(message = "La fecha es requerida")
    private LocalDateTime fecha;
    
    @NotNull(message = "El ticket es requerido")
    @ManyToOne
    @JoinColumn(name = "ticket_id", nullable = false)
    private Ticket ticket;
    
    @NotNull(message = "El usuario es requerido")
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Column(name = "create_at_comentario")
    @CreationTimestamp
    private Timestamp createAt;

    @Column(name = "update_at_comentario")
    @UpdateTimestamp
    private Timestamp updateAt;
}
