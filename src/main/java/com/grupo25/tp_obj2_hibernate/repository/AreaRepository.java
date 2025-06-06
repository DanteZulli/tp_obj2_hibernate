package com.grupo25.tp_obj2_hibernate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.grupo25.tp_obj2_hibernate.model.entities.Area;

/**
 * Repositorio para la entidad Area.
 * 
 * @author Grupo 25
 */
@Repository
public interface AreaRepository extends JpaRepository<Area, Integer> {
}