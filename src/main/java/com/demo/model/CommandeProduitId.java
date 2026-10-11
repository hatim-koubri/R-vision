package com.demo.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class CommandeProduitId implements Serializable {
    @Column(name = "commande_id")
    private Long commandeId;

    @Column(name = "produit_id")
    private Long produitId;

    public CommandeProduitId() { }
    public CommandeProduitId(Long commandeId, Long produitId) {
        this.commandeId = commandeId;
        this.produitId = produitId;
    }

    public Long getCommandeId() { return commandeId; }
    public void setCommandeId(Long commandeId) { this.commandeId = commandeId; }
    public Long getProduitId() { return produitId; }
    public void setProduitId(Long produitId) { this.produitId = produitId; }

    @Override
    public boolean equals(Object autre) {
        if (this == autre) return true;
        if (!(autre instanceof CommandeProduitId)) return false;
        CommandeProduitId cle = (CommandeProduitId) autre;
        return Objects.equals(commandeId, cle.commandeId) && Objects.equals(produitId, cle.produitId);
    }

    @Override
    public int hashCode() { return Objects.hash(commandeId, produitId); }
}