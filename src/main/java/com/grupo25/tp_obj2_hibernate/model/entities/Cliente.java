package com.grupo25.tp_obj2_hibernate.model.entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Cliente extends Usuario {
    private int nroCliente;
    private String plan;
    private boolean particular;
    private Direccion direccion;
}
