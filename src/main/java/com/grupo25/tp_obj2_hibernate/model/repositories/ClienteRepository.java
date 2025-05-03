package com.grupo25.tp_obj2_hibernate.model.repositories;

import org.springframework.stereotype.Repository;
import com.grupo25.tp_obj2_hibernate.model.entities.Cliente;

@Repository
public class ClienteRepository extends HibernateRepository<Cliente> {
    public ClienteRepository() {
        super(Cliente.class);
    }
}