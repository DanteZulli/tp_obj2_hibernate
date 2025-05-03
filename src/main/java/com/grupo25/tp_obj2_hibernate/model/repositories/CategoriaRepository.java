package com.grupo25.tp_obj2_hibernate.model.repositories;

import org.springframework.stereotype.Repository;
import com.grupo25.tp_obj2_hibernate.model.entities.Categoria;

@Repository
public class CategoriaRepository extends HibernateRepository<Categoria> {
    public CategoriaRepository() {
        super(Categoria.class);
    }
}