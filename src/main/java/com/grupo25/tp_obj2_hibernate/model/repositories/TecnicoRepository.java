package com.grupo25.tp_obj2_hibernate.model.repositories;

import java.util.List;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import com.grupo25.tp_obj2_hibernate.model.entities.Tecnico;
import com.grupo25.tp_obj2_hibernate.model.entities.Categoria;

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