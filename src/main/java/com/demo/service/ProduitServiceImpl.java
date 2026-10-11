package com.demo.service;

import com.demo.model.Produit;
import com.demo.util.JpaUtil;
import com.demo.util.PaginationResult;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ProduitServiceImpl implements ProduitService {
    @Override
    public List<Produit> rechercherParCategorie(Long idCategorie) {
        EntityManager gestionnaire = JpaUtil.getFactory().createEntityManager();
        try {
            return gestionnaire.createNamedQuery("Produit.parCategorie", Produit.class)
                    .setParameter("identifiantCategorie", idCategorie).getResultList();
        } finally {
            gestionnaire.close();
        }
    }

    @Override
    public List<Produit> chercherParLibelle(String motif) {
        EntityManager gestionnaire = JpaUtil.getFactory().createEntityManager();
        try {
            return gestionnaire.createNamedQuery("Produit.chercherLibelle", Produit.class)
                    .setParameter("motif", motif == null ? "" : motif.trim()).getResultList();
        } finally {
            gestionnaire.close();
        }
    }

    @Override
    public PaginationResult<Produit> filtrer(String texte, BigDecimal tarifMin, BigDecimal tarifMax,
                                              int page, int taille) {
        if (page < 1 || taille < 1) throw new IllegalArgumentException("Page et taille doivent etre positives.");
        if (tarifMin != null && tarifMax != null && tarifMin.compareTo(tarifMax) > 0) {
            throw new IllegalArgumentException("Le tarif minimum depasse le tarif maximum.");
        }

        EntityManager gestionnaire = JpaUtil.getFactory().createEntityManager();
        try {
            CriteriaBuilder constructeur = gestionnaire.getCriteriaBuilder();
            CriteriaQuery<Produit> requete = constructeur.createQuery(Produit.class);
            Root<Produit> produit = requete.from(Produit.class);
            List<Predicate> filtres = creerFiltres(constructeur, produit, texte, tarifMin, tarifMax);
            requete.select(produit).where(filtres.toArray(new Predicate[0]));
            requete.orderBy(constructeur.asc(produit.get("libelle")), constructeur.asc(produit.get("id")));

            TypedQuery<Produit> liste = gestionnaire.createQuery(requete);
            liste.setFirstResult((page - 1) * taille);
            liste.setMaxResults(taille);

            CriteriaQuery<Long> comptage = constructeur.createQuery(Long.class);
            Root<Produit> racineComptage = comptage.from(Produit.class);
            List<Predicate> filtresComptage = creerFiltres(constructeur, racineComptage, texte, tarifMin, tarifMax);
            comptage.select(constructeur.count(racineComptage)).where(filtresComptage.toArray(new Predicate[0]));
            long total = gestionnaire.createQuery(comptage).getSingleResult();
            return new PaginationResult<>(liste.getResultList(), page, taille, total);
        } finally {
            gestionnaire.close();
        }
    }

    private List<Predicate> creerFiltres(CriteriaBuilder constructeur, Root<Produit> produit,
                                          String texte, BigDecimal tarifMin, BigDecimal tarifMax) {
        List<Predicate> filtres = new ArrayList<>();
        if (texte != null && !texte.trim().isEmpty()) {
            String motif = "%" + texte.trim().toLowerCase(Locale.ROOT) + "%";
            filtres.add(constructeur.like(constructeur.lower(produit.<String>get("libelle")), motif));
        }
        if (tarifMin != null) filtres.add(constructeur.ge(produit.<BigDecimal>get("tarif"), tarifMin));
        if (tarifMax != null) filtres.add(constructeur.le(produit.<BigDecimal>get("tarif"), tarifMax));
        return filtres;
    }
}