package com.grupo25.tp_obj2_hibernate.model.dto;

import com.grupo25.tp_obj2_hibernate.model.entities.Tecnico;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TecnicoDTO {
    private int id;
    private String nombre;
    private String email;
    private String nroContacto;
    private String empresa;
    private AreaDTO area;

    public TecnicoDTO(Tecnico tecnico) {
        this.id = tecnico.getId();
        this.nombre = tecnico.getNombre();
        this.email = tecnico.getEmail();
        this.nroContacto = tecnico.getNroContacto();
        this.empresa = tecnico.getEmpresa();
        this.area = new AreaDTO(tecnico.getArea());
    }
}