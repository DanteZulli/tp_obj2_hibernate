package com.grupo25.tp_obj2_hibernate.model.entities;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Etiqueta {
    private int id;
    private String nombre;
    private List<Ticket> tickets;
}
