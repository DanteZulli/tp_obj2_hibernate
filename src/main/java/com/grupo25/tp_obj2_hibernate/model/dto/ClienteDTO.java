package com.grupo25.tp_obj2_hibernate.model.dto;

import com.grupo25.tp_obj2_hibernate.model.entities.Cliente;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class ClienteDTO extends UsuarioDTO {
    private String plan;
    private boolean particular;
    private DireccionDTO direccion;

    public ClienteDTO(int id, String nombre, String email, Cliente cliente) {
        super(id, nombre, email);
        this.plan = cliente.getPlan();
        this.particular = cliente.isParticular();
        this.direccion = new DireccionDTO(cliente.getDireccion());
    }
}