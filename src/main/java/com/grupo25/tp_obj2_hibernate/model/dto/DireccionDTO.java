package com.grupo25.tp_obj2_hibernate.model.dto;

import com.grupo25.tp_obj2_hibernate.model.entities.Direccion;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DireccionDTO {
    private int id;
    private String calle;
    private String numero;
    private String localidad;
    private String provincia;

    public DireccionDTO(Direccion direccion) {
        this.id = direccion.getId();
        this.calle = direccion.getCalle();
        this.numero = direccion.getNro();
        this.localidad = direccion.getLocalidad();
        this.provincia = direccion.getProvincia();
    }
}