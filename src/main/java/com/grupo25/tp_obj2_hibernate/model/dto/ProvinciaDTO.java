package com.grupo25.tp_obj2_hibernate.model.dto;

import com.grupo25.tp_obj2_hibernate.model.entities.Provincia;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProvinciaDTO {
    private int id;
    private String nombre;

    public ProvinciaDTO(Provincia provincia) {
        this.id = provincia.getId();
        this.nombre = provincia.getNombre();
    }
}
