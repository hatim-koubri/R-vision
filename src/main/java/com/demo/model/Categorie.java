package com.demo.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "categories")
@NamedQueries({
        @NamedQuery(name = "Categorie.toutLister", query = "SELECT c FROM Categorie c ORDER BY c.intitule")
})
public class Categorie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String intitule;

    @OneToMany(mappedBy = "categorie", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Produit> articles = new ArrayList<>();

    public Categorie() { }
    public Categorie(String intitule) { this.intitule = intitule; }

    // Le helper evite de mettre a jour un seul cote de la relation.
    public void ajouterArticle(Produit article) {
        articles.add(article);
        article.setCategorie(this);
    }

    public void retirerArticle(Produit article) {
        articles.remove(article);
        article.setCategorie(null);
    }

    public Long getId() { return id; }
    public String getIntitule() { return intitule; }
    public void setIntitule(String intitule) { this.intitule = intitule; }
    public List<Produit> getArticles() { return articles; }
}