package com.grupo25.tp_obj2_hibernate.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.grupo25.tp_obj2_hibernate.model.entities.Ticket;
import com.grupo25.tp_obj2_hibernate.model.entities.Usuario;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer> {

    List<Ticket> findByCreador(Usuario creador);

    List<Ticket> findByAsignado(Usuario asignado);

    List<Ticket> findByEstado(String estado);

    String findEstadoById(int id);

    long countByEstado(String estado);

    long countByEstadoAndCreadorId(String estado, int creadorId);

    long countByPrioridad(String prioridad);

    long countByPrioridadAndCreadorId(String prioridad, int creadorId);
}
