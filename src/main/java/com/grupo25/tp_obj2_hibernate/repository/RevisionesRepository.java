package com.grupo25.tp_obj2_hibernate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.grupo25.tp_obj2_hibernate.model.entities.Revision;
import java.util.List;

@Repository
public interface RevisionesRepository extends JpaRepository<Revision, Integer> {
    List<Revision> findByTicketId(int ticketId);
}