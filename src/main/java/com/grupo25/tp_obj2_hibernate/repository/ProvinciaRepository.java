package com.grupo25.tp_obj2_hibernate.repository;

import com.grupo25.tp_obj2_hibernate.model.entities.Provincia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para la entidad Provincia.
 * 
 * @author Grupo 25
 */
@Repository
public interface ProvinciaRepository extends JpaRepository<Provincia, Integer> {
}
