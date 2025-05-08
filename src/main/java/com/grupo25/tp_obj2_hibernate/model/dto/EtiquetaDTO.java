package com.grupo25.tp_obj2_hibernate.model.dto;

import com.grupo25.tp_obj2_hibernate.model.entities.Etiqueta;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EtiquetaDTO {
    private int id;
    private String nombre;

    public EtiquetaDTO(Etiqueta etiqueta) {
        this.id = etiqueta.getId();
        this.nombre = etiqueta.getNombre();
    }
}
