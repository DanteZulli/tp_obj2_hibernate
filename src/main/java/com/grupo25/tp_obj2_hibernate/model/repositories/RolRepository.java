package com.grupo25.tp_obj2_hibernate.model.repositories;

import org.springframework.stereotype.Repository;
import com.grupo25.tp_obj2_hibernate.model.entities.Rol;

@Repository
public class RolRepository extends HibernateRepository<Rol> {
    public RolRepository() {
        super(Rol.class);
    }
}