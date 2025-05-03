package com.grupo25.tp_obj2_hibernate.model.entities;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class Usuario {
    private int id;
    private String nombre;
    private String email;
    private boolean esAdmin;
}