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
@Table(name = "revisiones")
@Getter
@Setter
public class Revision {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @NotNull(message = "La fecha de cambio es requerida")
    @Column(name = "fecha_cambio", nullable = false)
    private LocalDateTime fechaCambio;
    
    @NotBlank(message = "El campo modificado no puede estar vacío")
    @Column(name = "campo_modificado")
    private String campoModificado;
    
    @Column(name = "valor_anterior")
    private String valorAnterior;
    
    @Column(name = "valor_nuevo")
    private String valorNuevo;
    
    @Size(max = 500, message = "Las observaciones no pueden tener más de 500 caracteres")
    private String observaciones;
    
    @NotNull(message = "El ticket es requerido")
    @ManyToOne
    @JoinColumn(name = "ticket_id", nullable = false)
    private Ticket ticket;
    
    @NotNull(message = "El usuario que realizó la modificación es requerido")
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuarioModificacion;
    
    @Column(name = "create_at_revision")
    @CreationTimestamp
    private Timestamp createAt;

    @Column(name = "update_at_revision")
    @UpdateTimestamp
    private Timestamp updateAt;
}
