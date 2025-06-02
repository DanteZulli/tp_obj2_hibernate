package com.grupo25.tp_obj2_hibernate.model.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "clientes")
@PrimaryKeyJoinColumn(name = "usuario_id")
@Getter
@Setter
public class Cliente extends Usuario {
    @Positive(message = "El número de cliente debe ser positivo")
    @Column(name = "nro_cliente", unique = true)
    private int nroCliente;

    @NotBlank(message = "El plan no puede estar vacío")
    private String plan;
    
    private boolean particular;
    
    @NotNull(message = "La dirección es requerida")
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "direccion_id")
    private Direccion direccion;
}
