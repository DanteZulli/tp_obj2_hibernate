package com.grupo25.tp_obj2_hibernate.model.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tecnicos")
@PrimaryKeyJoinColumn(name = "usuario_id")
@Getter
@Setter
public class Tecnico extends Usuario {
    @NotBlank(message = "El número de contacto no puede estar vacío")
    @Column(name = "nro_contacto")
    private String nroContacto;

    @NotBlank(message = "La empresa no puede estar vacía")
    private String empresa;
    
    @NotNull(message = "El área es requerida")
    @ManyToOne
    @JoinColumn(name = "area_id")
    private Area area;
}