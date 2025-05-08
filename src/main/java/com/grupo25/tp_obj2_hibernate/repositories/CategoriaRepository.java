package com.grupo25.tp_obj2_hibernate.repositories;

import org.springframework.stereotype.Repository;

import com.grupo25.tp_obj2_hibernate.model.entities.Categoria;

/**
 * Repositorio para la entidad Categoria. Extiende de HibernateRepository para
 * utilizar las operaciones CRUD básicas.
 * 
 * @author Grupo 25
 */
@Repository
public class CategoriaRepository extends HibernateRepository<Categoria> {
    public CategoriaRepository() {
        super(Categoria.class);
    }
}