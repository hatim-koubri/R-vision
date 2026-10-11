package com.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "fournisseurs")
public class Fournisseur extends User {
    @Column(length = 180)
    private String adresseLivraison;

    public Fournisseur() { }
    public Fournisseur(String nomComplet, String adresseLivraison) {
        super(nomComplet);
        this.adresseLivraison = adresseLivraison;
    }
    public String getAdresseLivraison() { return adresseLivraison; }
    public void setAdresseLivraison(String adresseLivraison) { this.adresseLivraison = adresseLivraison; }
}