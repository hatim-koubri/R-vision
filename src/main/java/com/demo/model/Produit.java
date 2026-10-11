package com.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "produits")
@NamedQueries({
        @NamedQuery(name = "Produit.toutLister", query = "SELECT p FROM Produit p ORDER BY p.libelle"),
        @NamedQuery(name = "Produit.parCategorie", query = "SELECT p FROM Produit p WHERE p.categorie.id = :identifiantCategorie ORDER BY p.libelle"),
        @NamedQuery(name = "Produit.chercherLibelle", query = "SELECT p FROM Produit p WHERE LOWER(p.libelle) LIKE LOWER(CONCAT('%', :motif, '%')) ORDER BY p.libelle")
})
public class Produit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String libelle;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal tarif;

    @Column(name = "date_creation", nullable = false)
    private LocalDate creeLe;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "categorie_id", nullable = false)
    private Categorie categorie;

    public Produit() { }
    public Produit(String libelle, BigDecimal tarif, Categorie categorie) {
        this.libelle = libelle;
        this.tarif = tarif;
        this.categorie = categorie;
        this.creeLe = LocalDate.now();
    }

    public Long getId() { return id; }
    public String getLibelle() { return libelle; }
    public void setLibelle(String libelle) { this.libelle = libelle; }
    public BigDecimal getTarif() { return tarif; }
    public void setTarif(BigDecimal tarif) { this.tarif = tarif; }
    public LocalDate getCreeLe() { return creeLe; }
    public void setCreeLe(LocalDate creeLe) { this.creeLe = creeLe; }
    public Categorie getCategorie() { return categorie; }
    public void setCategorie(Categorie categorie) { this.categorie = categorie; }

    @Override
    public String toString() {
        return "Produit{id=" + id + ", libelle='" + libelle + "', tarif=" + tarif + "}";
    }
}