package com.grupo25.tp_obj2_hibernate.repositories;

import java.util.List;
import java.util.Optional;

/**
 * Interfaz genérica para todos los repositorios de la aplicación (Hibernate
 * Nativo)
 * 
 * @param <T> Tipo de entidad que maneja el repositorio
 * @author Grupo 25
 */
public interface Repository<T> {
    T save(T entity);

    Optional<T> findById(int id);

    List<T> findAll();

    void delete(T entity);

    void deleteById(int id);

    void update(T entity);
}