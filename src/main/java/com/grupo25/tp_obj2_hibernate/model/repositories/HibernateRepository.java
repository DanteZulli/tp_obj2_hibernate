package com.grupo25.tp_obj2_hibernate.model.repositories;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public abstract class HibernateRepository<T> implements Repository<T> {
    
    @Autowired
    protected SessionFactory sessionFactory;
    
    protected final Class<T> entityClass;
    
    protected HibernateRepository(Class<T> entityClass) {
        this.entityClass = entityClass;
    }
    
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
    
    @Override
    public void deleteById(int id) {
        findById(id).ifPresent(this::delete);
    }
    
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