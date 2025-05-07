package com.grupo25.tp_obj2_hibernate.repositories;

import java.util.List;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import com.grupo25.tp_obj2_hibernate.model.entities.Tecnico;
import com.grupo25.tp_obj2_hibernate.model.entities.Categoria;

/**
 * Repositorio para la entidad Rol. Extiende de HibernateRepository para
 * utilizar las operaciones CRUD básicas.
 * 
 * @author Grupo 25
 */
@Repository
public class TecnicoRepository extends HibernateRepository<Tecnico> {
    public TecnicoRepository() {
        super(Tecnico.class);
    }

    public List<Tecnico> findByEspecialidad(Categoria especialidad) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from Tecnico t where :especialidad member of t.especialidades", Tecnico.class)
                .setParameter("especialidad", especialidad)
                .list();
    }
}