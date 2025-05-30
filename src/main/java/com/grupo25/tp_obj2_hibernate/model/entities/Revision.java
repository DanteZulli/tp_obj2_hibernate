package com.grupo25.tp_obj2_hibernate.model.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;
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
    
    @Column(name = "fecha_cambio", nullable = false)
    private LocalDateTime fechaCambio;
    
    @Column(name = "campo_modificado")
    private String campoModificado;
    
    @Column(name = "valor_anterior")
    private String valorAnterior;
    
    @Column(name = "valor_nuevo")
    private String valorNuevo;
    
    private String observaciones;
    
    @ManyToOne
    @JoinColumn(name = "ticket_id", nullable = false)
    private Ticket ticket;
    
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuarioModificacion;
}
