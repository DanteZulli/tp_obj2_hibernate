package com.grupo25.tp_obj2_hibernate.model.entities;

import jakarta.persistence.*;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "provincias")
@Getter
@Setter
public class Provincia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(nullable = false)
    private String nombre;
    
    @OneToMany(mappedBy = "provincia")
    private List<Localidad> localidades;
}
