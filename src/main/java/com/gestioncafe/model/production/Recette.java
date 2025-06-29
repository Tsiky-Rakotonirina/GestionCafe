package com.gestioncafe.model.production;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
public class Recette {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_produit")
    private Produit produit;

    private BigDecimal quantiteProduite;
    private BigDecimal tempsFabrication;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    public BigDecimal getQuantiteProduite() {
        return quantiteProduite;
    }

    public void setQuantiteProduite(BigDecimal quantiteProduite) {
        this.quantiteProduite = quantiteProduite;
    }

    public BigDecimal getTempsFabrication() {
        return tempsFabrication;
    }

    public void setTempsFabrication(BigDecimal tempsFabrication) {
        this.tempsFabrication = tempsFabrication;
    }
}