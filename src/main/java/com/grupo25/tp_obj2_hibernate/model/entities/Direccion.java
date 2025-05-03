package com.grupo25.tp_obj2_hibernate.model.entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Direccion {
    private int id;
    private String calle;
    private String nro;
    private String localidad;
    private String provincia;
    private boolean fiscal;
}