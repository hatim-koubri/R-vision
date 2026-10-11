package com.demo.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "commandes")
public class Commande {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date_saisie", nullable = false)
    private LocalDate dateSaisie;

    @OneToMany(mappedBy = "commande", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommandeProduit> lignes = new ArrayList<>();

    public Commande() { }
    public Commande(LocalDate dateSaisie) { this.dateSaisie = dateSaisie; }

    public void ajouterArticle(Produit article, int quantite) {
        lignes.add(new CommandeProduit(this, article, quantite));
    }

    public Long getId() { return id; }
    public LocalDate getDateSaisie() { return dateSaisie; }
    public void setDateSaisie(LocalDate dateSaisie) { this.dateSaisie = dateSaisie; }
    public List<CommandeProduit> getLignes() { return lignes; }
}