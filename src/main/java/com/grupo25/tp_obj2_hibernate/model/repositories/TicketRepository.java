package com.grupo25.tp_obj2_hibernate.model.repositories;

import java.util.List;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import com.grupo25.tp_obj2_hibernate.model.entities.Ticket;
import com.grupo25.tp_obj2_hibernate.model.entities.Usuario;

@Repository
public class TicketRepository extends HibernateRepository<Ticket> {
    public TicketRepository() {
        super(Ticket.class);
    }

    public List<Ticket> findByCreador(Usuario creador) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from Ticket where creador = :creador", Ticket.class)
                .setParameter("creador", creador)
                .list();
    }

    public List<Ticket> findByAsignado(Usuario asignado) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from Ticket where asignado = :asignado", Ticket.class)
                .setParameter("asignado", asignado)
                .list();
    }

    public List<Ticket> findByEstado(String estado) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from Ticket where estado = :estado", Ticket.class)
                .setParameter("estado", estado)
                .list();
    }
}