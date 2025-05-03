package com.grupo25.tp_obj2_hibernate.model.repositories;

import org.springframework.stereotype.Repository;
import com.grupo25.tp_obj2_hibernate.model.entities.Direccion;

/**
 * Repositorio para la entidad Direccion. Extiende de HibernateRepository para
 * utilizar las operaciones CRUD básicas.
 * 
 * @author Grupo 25
 */
@Repository
public class DireccionRepository extends HibernateRepository<Direccion> {
    public DireccionRepository() {
        super(Direccion.class);
    }
}