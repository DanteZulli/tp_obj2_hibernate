package com.grupo25.tp_obj2_hibernate.model.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.sql.Timestamp;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "direcciones")
@Getter
@Setter
public class Direccion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @NotBlank(message = "La calle no puede estar vacía")
    @Size(min = 2, max = 100, message = "La calle debe tener entre 2 y 100 caracteres")
    @Column(nullable = false)
    private String calle;

    @NotBlank(message = "El número no puede estar vacío")
    private String nro;
    
    @NotBlank(message = "El código postal no puede estar vacío")
    @Size(min = 4, max = 10, message = "El código postal debe tener entre 4 y 10 caracteres")
    @Column(name = "codigo_postal", nullable = false)
    private String codigoPostal;
    
    @NotNull(message = "La localidad es requerida")
    @ManyToOne
    @JoinColumn(name = "localidad_id", nullable = false)
    private Localidad localidad;
    
    private boolean fiscal;
    
    @Column(name = "create_at_direccion")
    @CreationTimestamp
    private Timestamp createAt;

    @Column(name = "update_at_direccion")
    @UpdateTimestamp
    private Timestamp updateAt;
}