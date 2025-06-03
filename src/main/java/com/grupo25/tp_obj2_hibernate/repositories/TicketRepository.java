package com.grupo25.tp_obj2_hibernate.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.grupo25.tp_obj2_hibernate.model.entities.Ticket;
import com.grupo25.tp_obj2_hibernate.model.entities.Usuario;

/**
 * Repositorio para la entidad Ticket.
 * 
 * @author Grupo 25
 */
@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer> {
    /**
     * Busca los tickets por el creador.
     * 
     * @param creador el usuario que creó el ticket
     * @return una lista de tickets creados por el usuario
     */
    List<Ticket> findByCreador(Usuario creador);

    /**
     * Busca los tickets por el asignado.
     * 
     * @param asignado el usuario al que se le asignó el ticket
     * @return una lista de tickets asignados al usuario
     */
    List<Ticket> findByAsignado(Usuario asignado);

    /**
     * Busca los tickets por el estado.
     * 
     * @param estado el estado del ticket
     * @return una lista de tickets con el estado especificado
     */
    List<Ticket> findByEstado(String estado);

    /**
     * Devuelve el estado del ticket por su ID.
     * 
     * @param id el ID del ticket
     * @return el estado del ticket
     */
    String findEstadoById(int id);
}
