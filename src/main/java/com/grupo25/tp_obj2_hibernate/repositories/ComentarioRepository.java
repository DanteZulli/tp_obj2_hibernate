package com.grupo25.tp_obj2_hibernate.repositories;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import com.grupo25.tp_obj2_hibernate.model.entities.Comentario;
import com.grupo25.tp_obj2_hibernate.model.entities.Ticket;

/**
 * Repositorio para la entidad Comentario. Extiende de HibernateRepository para
 * utilizar las operaciones CRUD básicas.
 * 
 * @author Grupo 25
 */
@Repository
public class ComentarioRepository extends HibernateRepository<Comentario> {
    public ComentarioRepository() {
        super(Comentario.class);
    }

    /**
     * Busca los comentarios asociados a un ticket específico.
     * 
     * @param ticket El ticket del cual se desean obtener los comentarios.
     * @return Una lista de comentarios asociados al ticket.
     */
    public List<Comentario> findByTicket(Ticket ticket) {
        Session session = sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();
        try {
        	List<Comentario> comentarios = session.createQuery("from Comentario where ticket = :ticket order by fecha", Comentario.class)
                    .setParameter("ticket", ticket)
                    .list();
        	tx.commit();
            return comentarios;
			
		} catch (Exception e) {
			tx.rollback();
            throw e;
		}
        
    }
    

    
    
}