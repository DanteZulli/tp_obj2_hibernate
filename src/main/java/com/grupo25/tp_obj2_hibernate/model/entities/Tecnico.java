package com.grupo25.tp_obj2_hibernate.model.entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Tecnico extends Usuario {
    private String nroContacto;
    private String empresa;
    private Rol rol;
}