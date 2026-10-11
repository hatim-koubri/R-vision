package com.demo.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

@Entity
@Table(name = "commande_produit")
public class CommandeProduit {
    @EmbeddedId
    private CommandeProduitId cle = new CommandeProduitId();

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("commandeId")
    @JoinColumn(name = "commande_id", nullable = false)
    private Commande commande;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("produitId")
    @JoinColumn(name = "produit_id", nullable = false)
    private Produit produit;

    @Column(nullable = false)
    private int quantite;

    public CommandeProduit() { }
    public CommandeProduit(Commande commande, Produit produit, int quantite) {
        this.commande = commande;
        this.produit = produit;
        this.quantite = quantite;
    }

    public CommandeProduitId getCle() { return cle; }
    public Commande getCommande() { return commande; }
    public Produit getProduit() { return produit; }
    public int getQuantite() { return quantite; }
    public void setQuantite(int quantite) { this.quantite = quantite; }
}