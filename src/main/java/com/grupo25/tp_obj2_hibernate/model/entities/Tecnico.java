package com.grupo25.tp_obj2_hibernate.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tecnicos")
@PrimaryKeyJoinColumn(name = "usuario_id")
@Getter
@Setter
public class Tecnico extends Usuario {
    @Column(name = "nro_contacto")
    private String nroContacto;
    private String empresa;
    
    @ManyToOne
    @JoinColumn(name = "area_id")
    private Area area;
}