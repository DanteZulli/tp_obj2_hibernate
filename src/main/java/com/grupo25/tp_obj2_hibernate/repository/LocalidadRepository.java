package com.grupo25.tp_obj2_hibernate.repository;

import com.grupo25.tp_obj2_hibernate.model.entities.Localidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para la entidad Localidad.
 * 
 * @author Grupo 25
 */
@Repository
public interface LocalidadRepository extends JpaRepository<Localidad, Integer> {
}
