package com.grupo25.tp_obj2_hibernate.repository;

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

    /**
     * Cuenta la cantidad de tickets por estado.
     * 
     * @param estado el estado de los tickets a contar
     * @return la cantidad de tickets con el estado especificado
     * @author Dante Zulli
     */
    long countByEstado(String estado);

    /**
     * Cuenta la cantidad de tickets por estado y creador.
     * 
     * @param estado el estado de los tickets a contar
     * @param creadorId el ID del creador de los tickets
     * @return la cantidad de tickets con el estado y creador especificados
     * @author Dante Zulli
     */
    long countByEstadoAndCreadorId(String estado, int creadorId);

    /**
     * Cuenta la cantidad de tickets por prioridad.
     * 
     * @param prioridad la prioridad de los tickets a contar
     * @return la cantidad de tickets con la prioridad especificada
     * @author Dante Zulli
     */
    long countByPrioridad(String prioridad);

    /**
     * Cuenta la cantidad de tickets por prioridad y creador.
     * 
     * @param prioridad la prioridad de los tickets a contar
     * @param creadorId el ID del creador de los tickets
     * @return la cantidad de tickets con la prioridad y creador especificados
     * @author Dante Zulli
     */
    long countByPrioridadAndCreadorId(String prioridad, int creadorId);
}
