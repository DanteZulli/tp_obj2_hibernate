package com.grupo25.tp_obj2_hibernate.model.dto;

import com.grupo25.tp_obj2_hibernate.model.entities.Rol;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RolDTO {
    private int id;
    private String nombre;

    public RolDTO(Rol rol) {
        this.id = rol.getId();
        this.nombre = rol.getNombre();
    }
}