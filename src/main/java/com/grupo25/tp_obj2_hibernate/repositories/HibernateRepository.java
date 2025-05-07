package com.grupo25.tp_obj2_hibernate.repositories;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

/**
 * Clase abstracta que implementa la interfaz Repository y proporciona
 * operaciones CRUD básicas utilizando Hibernate Nativo.
 * 
 * @param <T> Tipo de entidad que maneja el repositorio
 * @author Grupo 25
 */
public abstract class HibernateRepository<T> implements Repository<T> {

    /**
     * Inyección de la SessionFactory de Hibernate para manejar las sesiones
     * de la base de datos.
     */
    @Autowired
    protected SessionFactory sessionFactory;

    /**
     * Clase de la entidad que maneja el repositorio. Se utiliza para
     * crear consultas y operaciones específicas de la entidad.
     */
    protected final Class<T> entityClass;

    protected HibernateRepository(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    /**
     * Método para guardar una entidad en la base de datos. Utiliza
     * transacciones para asegurar la integridad de los datos.
     * 
     * @param entity Entidad a guardar
     * @return La entidad guardada
     */
    @Override
    public T save(T entity) {
        Session session = sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();
        try {
            session.persist(entity);
            tx.commit();
            return entity;
        } catch (Exception e) {
            tx.rollback();
            throw e;
        }
    }

    /**
     * Método para buscar una entidad por su ID. Utiliza transacciones
     * para asegurar la integridad de los datos.
     * 
     * @param id ID de la entidad a buscar
     * @return La entidad encontrada, o un Optional vacío si no se encuentra
     */
    @Override
    public Optional<T> findById(int id) {
        Session session = sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();
        try {
            T entity = session.get(entityClass, id);
            tx.commit();
            return Optional.ofNullable(entity);
        } catch (Exception e) {
            tx.rollback();
            throw e;
        }
    }

    /**
     * Método para buscar todas las entidades de la base de datos. Utiliza
     * transacciones para asegurar la integridad de los datos.
     * 
     * @return Lista de todas las entidades encontradas
     */
    @Override
    public List<T> findAll() {
        Session session = sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();
        try {
            List<T> entities = session.createQuery("from " + entityClass.getSimpleName(), entityClass).list();
            tx.commit();
            return entities;
        } catch (Exception e) {
            tx.rollback();
            throw e;
        }
    }

    /**
     * Método para eliminar una entidad de la base de datos. Utiliza
     * transacciones para asegurar la integridad de los datos.
     * 
     * @param entity Entidad a eliminar
     */
    @Override
    public void delete(T entity) {
        Session session = sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();
        try {
            session.remove(entity);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        }
    }

    /**
     * Método para eliminar una entidad por su ID. Utiliza transacciones
     * para asegurar la integridad de los datos.
     * 
     * @param id ID de la entidad a eliminar
     */
    @Override
    public void deleteById(int id) {
        findById(id).ifPresent(this::delete);
    }

    /**
     * Método para actualizar una entidad en la base de datos. Utiliza
     * transacciones para asegurar la integridad de los datos.
     * 
     * @param entity Entidad a actualizar
     */
    @Override
    public void update(T entity) {
        Session session = sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();
        try {
            session.merge(entity);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        }
    }
}