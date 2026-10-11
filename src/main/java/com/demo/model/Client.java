package com.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "clients")
public class Client extends User {
    @Column(name = "date_inscription")
    private LocalDate inscritLe;

    public Client() { }
    public Client(String nomComplet, LocalDate inscritLe) {
        super(nomComplet);
        this.inscritLe = inscritLe;
    }
    public LocalDate getInscritLe() { return inscritLe; }
    public void setInscritLe(LocalDate inscritLe) { this.inscritLe = inscritLe; }
}