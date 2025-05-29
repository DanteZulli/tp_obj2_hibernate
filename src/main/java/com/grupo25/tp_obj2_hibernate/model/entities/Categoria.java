package com.grupo25.tp_obj2_hibernate.model.entities;

import jakarta.persistence.*;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "categorias")
@Getter
@Setter
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(nullable = false)
    private String nombre;
    private String descripcion;
    
    @OneToMany(mappedBy = "categoria")
    private List<Ticket> tickets;
}