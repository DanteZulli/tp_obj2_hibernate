package com.grupo25.tp_obj2_hibernate.model.repositories;

import java.util.List;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import com.grupo25.tp_obj2_hibernate.model.entities.Comentario;
import com.grupo25.tp_obj2_hibernate.model.entities.Ticket;

@Repository
public class ComentarioRepository extends HibernateRepository<Comentario> {
    public ComentarioRepository() {
        super(Comentario.class);
    }

    public List<Comentario> findByTicket(Ticket ticket) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from Comentario where ticket = :ticket order by fecha", Comentario.class)
                .setParameter("ticket", ticket)
                .list();
    }
}