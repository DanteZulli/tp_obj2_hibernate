package com.grupo25.tp_obj2_hibernate.model.repositories;

import java.util.List;
import java.util.Optional;

public interface Repository<T> {
    T save(T entity);
    Optional<T> findById(int id);
    List<T> findAll();
    void delete(T entity);
    void deleteById(int id);
    void update(T entity);
}