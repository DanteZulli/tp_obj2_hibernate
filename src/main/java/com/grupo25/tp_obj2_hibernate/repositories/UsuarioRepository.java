package com.grupo25.tp_obj2_hibernate.repositories;

import org.springframework.stereotype.Repository;
import com.grupo25.tp_obj2_hibernate.model.entities.Usuario;

/**
 * Repositorio para la entidad Usuario. Extiende de HibernateRepository para
 * utilizar las operaciones CRUD básicas.
 * 
 * @author Grupo 25
 */
@Repository
public class UsuarioRepository extends HibernateRepository<Usuario> {
    public UsuarioRepository() {
        super(Usuario.class);
    }
}