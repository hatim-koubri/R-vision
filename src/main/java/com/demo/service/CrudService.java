package com.demo.service;

import com.demo.dao.GenericDAO;

import java.io.Serializable;
import java.util.List;

public class CrudService<T, ID extends Serializable> {
    private final GenericDAO<T, ID> accesDonnees;

    public CrudService(Class<T> typeEntite) {
        this.accesDonnees = new GenericDAO<>(typeEntite);
    }

    public T getById(ID cle) { return accesDonnees.find(cle); }
    public List<T> getAll() { return accesDonnees.findAll(); }

    public T create(T entite) {
        verifierEntite(entite);
        return accesDonnees.create(entite);
    }

    public T update(T entite) {
        verifierEntite(entite);
        return accesDonnees.update(entite);
    }

    public void delete(T entite) {
        verifierEntite(entite);
        accesDonnees.delete(entite);
    }

    private void verifierEntite(T entite) {
        if (entite == null) throw new IllegalArgumentException("L'entite ne peut pas etre nulle.");
    }
}