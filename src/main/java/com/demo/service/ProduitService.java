package com.demo.service;

import com.demo.model.Produit;
import com.demo.util.PaginationResult;

import java.math.BigDecimal;
import java.util.List;

public interface ProduitService {
    List<Produit> rechercherParCategorie(Long idCategorie);
    List<Produit> chercherParLibelle(String motif);
    PaginationResult<Produit> filtrer(String texte, BigDecimal tarifMin, BigDecimal tarifMax, int page, int taille);
}