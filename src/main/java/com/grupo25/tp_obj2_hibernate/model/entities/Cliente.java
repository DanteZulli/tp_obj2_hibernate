package com.grupo25.tp_obj2_hibernate.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "clientes")
@PrimaryKeyJoinColumn(name = "usuario_id")
@Getter
@Setter
public class Cliente extends Usuario {
    @Column(name = "nro_cliente", unique = true)
    private int nroCliente;
    private String plan;
    private boolean particular;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "direccion_id")
    private Direccion direccion;
}
