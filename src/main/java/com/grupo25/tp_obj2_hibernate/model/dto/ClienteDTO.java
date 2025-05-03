package com.grupo25.tp_obj2_hibernate.model.dto;

import com.grupo25.tp_obj2_hibernate.model.entities.Cliente;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClienteDTO {
    private int id;
    private int nroCliente;
    private String nombre;
    private String email;
    private String plan;
    private boolean particular;

    public ClienteDTO(Cliente cliente) {
        this.id = cliente.getId();
        this.nroCliente = cliente.getNroCliente();
        this.nombre = cliente.getNombre();
        this.email = cliente.getEmail();
        this.plan = cliente.getPlan();
        this.particular = cliente.isParticular();
    }
}