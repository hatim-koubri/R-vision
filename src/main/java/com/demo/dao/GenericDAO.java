package com.demo.dao;

import com.demo.util.JpaUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.io.Serializable;
import java.util.List;

public class GenericDAO<T, ID extends Serializable> {
    private final Class<T> typeEntite;

    public GenericDAO(Class<T> typeEntite) {
        this.typeEntite = typeEntite;
    }

    public T find(ID cle) {
        EntityManager gestionnaire = JpaUtil.getFactory().createEntityManager();
        try {
            return gestionnaire.find(typeEntite, cle);
        } finally {
            gestionnaire.close();
        }
    }

    public List<T> findAll() {
        EntityManager gestionnaire = JpaUtil.getFactory().createEntityManager();
        try {
            String jpql = "SELECT element FROM " + typeEntite.getSimpleName() + " element";
            return gestionnaire.createQuery(jpql, typeEntite).getResultList();
        } finally {
            gestionnaire.close();
        }
    }

    public T create(T entite) {
        return executerEcriture(gestionnaire -> {
            gestionnaire.persist(entite);
            return entite;
        });
    }

    public T update(T entite) {
        return executerEcriture(gestionnaire -> gestionnaire.merge(entite));
    }

    public void delete(T entite) {
        executerEcriture(gestionnaire -> {
            T rattachee = gestionnaire.contains(entite) ? entite : gestionnaire.merge(entite);
            gestionnaire.remove(rattachee);
            return null;
        });
    }

    public void deleteById(ID cle) {
        T entite = find(cle);
        if (entite != null) delete(entite);
    }

    private <R> R executerEcriture(OperationEntityManager<T, R> action) {
        EntityManager gestionnaire = JpaUtil.getFactory().createEntityManager();
        EntityTransaction transaction = gestionnaire.getTransaction();
        try {
            transaction.begin();
            R resultat = action.appliquer(gestionnaire);
            transaction.commit();
            return resultat;
        } catch (RuntimeException erreur) {
            if (transaction.isActive()) transaction.rollback();
            throw erreur;
        } finally {
            gestionnaire.close();
        }
    }

    private interface OperationEntityManager<T, R> {
        R appliquer(EntityManager gestionnaire);
    }
}