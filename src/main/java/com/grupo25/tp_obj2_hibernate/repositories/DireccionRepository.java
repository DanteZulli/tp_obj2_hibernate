package com.grupo25.tp_obj2_hibernate.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.grupo25.tp_obj2_hibernate.model.entities.Direccion;

/**
 * Repositorio para la entidad Direccion.
 * 
 * @author Grupo 25
 */
@Repository
public interface DireccionRepository extends JpaRepository<Direccion, Integer> {
}