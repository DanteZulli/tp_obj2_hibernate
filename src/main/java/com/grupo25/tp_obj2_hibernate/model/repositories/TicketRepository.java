package com.grupo25.tp_obj2_hibernate.model.repositories;

import java.util.List;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import com.grupo25.tp_obj2_hibernate.model.entities.Ticket;
import com.grupo25.tp_obj2_hibernate.model.entities.Usuario;

/**
 * Repositorio para la entidad Ticket. Extiende de HibernateRepository para
 * utilizar las operaciones CRUD básicas.
 * 
 * @author Grupo 25
 */
@Repository
public class TicketRepository extends HibernateRepository<Ticket> {
    public TicketRepository() {
        super(Ticket.class);
    }

    /**
     * Busca los tickets por el creador.
     * 
     * @param creador el usuario que creó el ticket
     * @return una lista de tickets creados por el usuario
     */
    public List<Ticket> findByCreador(Usuario creador) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from Ticket where creador = :creador", Ticket.class)
                .setParameter("creador", creador)
                .list();
    }

    /**
     * Busca los tickets por el asignado.
     * 
     * @param asignado el usuario al que se le asignó el ticket
     * @return una lista de tickets asignados al usuario
     */
    public List<Ticket> findByAsignado(Usuario asignado) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from Ticket where asignado = :asignado", Ticket.class)
                .setParameter("asignado", asignado)
                .list();
    }

    /**
     * Busca los tickets por el estado.
     * 
     * @param estado el estado del ticket
     * @return una lista de tickets con el estado especificado
     */
    public List<Ticket> findByEstado(String estado) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from Ticket where estado = :estado", Ticket.class)
                .setParameter("estado", estado)
                .list();
    }
}