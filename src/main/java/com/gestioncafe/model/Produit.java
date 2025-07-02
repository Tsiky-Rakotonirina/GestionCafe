package com.gestioncafe.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "produit")
public class Produit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;

    private String description;

    private BigDecimal stock;

    private String image;

    @Column(name = "delai_peremption")
    private BigDecimal delaiPeremption;

    @ManyToOne
    @JoinColumn(name = "id_unite")
    private Unite unite;

    @ManyToOne
    @JoinColumn(name = "id_package")
    private Package pack;

    public Produit() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getStock() {
        return stock;
    }

    public void setStock(BigDecimal stock) {
        this.stock = stock;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public BigDecimal getDelaiPeremption() {
        return delaiPeremption;
    }

    public void setDelaiPeremption(BigDecimal delaiPeremption) {
        this.delaiPeremption = delaiPeremption;
    }

    public Unite getUnite() {
        return unite;
    }

    public void setUnite(Unite unite) {
        this.unite = unite;
    }

    public Package getPack() {
        return pack;
    }

    public void setPack(Package pack) {
        this.pack = pack;
    }
}
