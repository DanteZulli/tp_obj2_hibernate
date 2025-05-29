package com.grupo25.tp_obj2_hibernate.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "direcciones")
@Getter
@Setter
public class Direccion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(nullable = false)
    private String calle;
    private String nro;
    private String localidad;
    private String provincia;
    private boolean fiscal;
}