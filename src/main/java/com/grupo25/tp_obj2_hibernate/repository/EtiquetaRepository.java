package com.grupo25.tp_obj2_hibernate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.grupo25.tp_obj2_hibernate.model.entities.Etiqueta;

/**
 * Repositorio para la entidad Etiqueta.
 * 
 * @author Grupo 25
 */
@Repository
public interface EtiquetaRepository extends JpaRepository<Etiqueta, Integer> {
}