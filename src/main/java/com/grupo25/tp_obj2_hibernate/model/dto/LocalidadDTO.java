package com.grupo25.tp_obj2_hibernate.model.dto;

import com.grupo25.tp_obj2_hibernate.model.entities.Localidad;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocalidadDTO {
    private int id;
    private String nombre;
    private ProvinciaDTO provincia;

    public LocalidadDTO(Localidad localidad) {
        this.id = localidad.getId();
        this.nombre = localidad.getNombre();
        this.provincia = new ProvinciaDTO(localidad.getProvincia());
    }
}
