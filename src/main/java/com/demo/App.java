package com.demo;

import com.demo.model.Categorie;
import com.demo.model.Client;
import com.demo.model.Commande;
import com.demo.model.Fournisseur;
import com.demo.model.Produit;
import com.demo.service.CrudService;
import com.demo.service.ProduitService;
import com.demo.service.ProduitServiceImpl;
import com.demo.util.JpaUtil;
import com.demo.util.PaginationResult;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.math.BigDecimal;
import java.time.LocalDate;

import java.util.List;

public class App {
    public static void main(String[] args) {
        CrudService<Categorie, Long> serviceCategories = new CrudService<>(Categorie.class);
        CrudService<Produit, Long> serviceProduits = new CrudService<>(Produit.class);
        CrudService<Client, Long> serviceClients = new CrudService<>(Client.class);
        CrudService<Fournisseur, Long> serviceFournisseurs = new CrudService<>(Fournisseur.class);
        CrudService<Commande, Long> serviceCommandes = new CrudService<>(Commande.class);
        ProduitService rechercheProduits = new ProduitServiceImpl();

        try {
            Categorie papeterie = serviceCategories.create(new Categorie("Papeterie artisanale"));
            Categorie accessoires = serviceCategories.create(new Categorie("Accessoires de bureau"));

            Produit carnet = serviceProduits.create(new Produit("Carnet Atlas", new BigDecimal("42.50"), papeterie));
            Produit stylo = serviceProduits.create(new Produit("Stylo Safran", new BigDecimal("18.90"), papeterie));
            Produit lampe = serviceProduits.create(new Produit("Lampe Noria", new BigDecimal("185.00"), accessoires));
            Produit support = serviceProduits.create(new Produit("Support Zellige", new BigDecimal("76.00"), accessoires));
            serviceProduits.create(new Produit("Porte-documents Rif", new BigDecimal("119.00"), accessoires));

            serviceClients.create(new Client("Imane Berrada", LocalDate.of(2025, 2, 12)));
            serviceFournisseurs.create(new Fournisseur("Cooperative Tazrout", "Meknes, Maroc"));
            enregistrerCommande(carnet, stylo);

            System.out.println("\n--- Named Query : articles de papeterie ---");
            afficher(rechercheProduits.rechercherParCategorie(papeterie.getId()));

            System.out.println("\n--- Named Query : recherche du mot 'atlas' ---");
            afficher(rechercheProduits.chercherParLibelle("atlas"));

            System.out.println("\n--- Criteria API et pagination ---");
            PaginationResult<Produit> tranche = rechercheProduits.filtrer(
                    "", new BigDecimal("40.00"), new BigDecimal("130.00"), 1, 2);
            System.out.println("Page " + tranche.getPageCourante() + "/" + tranche.getTotalPages()
                    + " | articles correspondants : " + tranche.getTotalElements());
            afficher(tranche.getElements());

            System.out.println("\n--- CRUD : modification puis suppression ---");
            Produit articleModifie = serviceProduits.getById(support.getId());
            articleModifie.setTarif(new BigDecimal("79.00"));
            serviceProduits.update(articleModifie);
            Produit articleProvisoire = serviceProduits.create(
                    new Produit("Echantillon temporaire", new BigDecimal("1.00"), accessoires));
            serviceProduits.delete(articleProvisoire);
            System.out.println("Support mis a jour : " + serviceProduits.getById(support.getId()));
            System.out.println("Article provisoire supprime.");


            System.out.println("\n--- Heritage JOINED : utilisateurs ---");
            List<com.demo.model.User> comptes = new CrudService<>(com.demo.model.User.class).getAll();
            for (com.demo.model.User compte : comptes) {
                System.out.println(compte.getClass().getSimpleName() + " : " + compte.getNomComplet());
            }
            System.out.println("Commande creee avec l'identifiant : " + serviceCommandes.getAll().get(0).getId());
        } finally {
            JpaUtil.shutdown();
        }
    }

    private static void enregistrerCommande(Produit premier, Produit second) {
        EntityManager gestionnaire = JpaUtil.getFactory().createEntityManager();
        EntityTransaction transaction = gestionnaire.getTransaction();
        try {
            transaction.begin();
            Produit articlePrincipal = gestionnaire.find(Produit.class, premier.getId());
            Produit articleComplementaire = gestionnaire.find(Produit.class, second.getId());
            Commande nouvelle = new Commande(LocalDate.now());
            nouvelle.ajouterArticle(articlePrincipal, 3);
            nouvelle.ajouterArticle(articleComplementaire, 2);
            gestionnaire.persist(nouvelle);
            transaction.commit();
        } catch (RuntimeException erreur) {
            if (transaction.isActive()) transaction.rollback();
            throw erreur;
        } finally {
            gestionnaire.close();
        }
    }

    private static void afficher(List<Produit> articles) {
        for (Produit article : articles) System.out.println("- " + article);
    }
}