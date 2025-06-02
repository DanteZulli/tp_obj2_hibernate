package com.grupo25.tp_obj2_hibernate.model.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.sql.Timestamp;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "categorias")
@Getter
@Setter
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(min = 2, max = 50, message = "El nombre debe tener entre 2 y 50 caracteres")
    @Column(nullable = false)
    private String nombre;

    @Size(max = 200, message = "La descripción no puede tener más de 200 caracteres")
    private String descripcion;
    
    @OneToMany(mappedBy = "categoria")
    private List<Ticket> tickets;

    @Column(name = "create_at_categoria")
    @CreationTimestamp
    private Timestamp createAt;

    @Column(name = "update_at_categoria")
    @UpdateTimestamp
    private Timestamp updateAt;
}