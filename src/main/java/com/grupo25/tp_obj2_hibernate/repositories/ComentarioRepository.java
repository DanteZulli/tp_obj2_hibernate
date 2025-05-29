package com.grupo25.tp_obj2_hibernate.repositories;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
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
public interface ComentarioRepository extends JpaRepository<Comentario, Integer> {

    /**
     * Busca los comentarios asociados a un ticket específico.
     * 
     * @param ticket El ticket del cual se desean obtener los comentarios.
     * @return Una lista de comentarios asociados al ticket.
     */
    List<Comentario> findByTicketOrderByFecha(Ticket ticket);

    
}