package com.grupo25.tp_obj2_hibernate.model.dto;

import com.grupo25.tp_obj2_hibernate.model.entities.Tecnico;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class TecnicoDTO extends UsuarioDTO {
    private String nroContacto;
    private String empresa;
    private RolDTO rol;

    public TecnicoDTO(int id, String nombre, String email, Tecnico tecnico) {
        super(id, nombre, email);
        this.nroContacto = tecnico.getNroContacto();
        this.empresa = tecnico.getEmpresa();
        this.rol = new RolDTO(tecnico.getRol());
    }
}