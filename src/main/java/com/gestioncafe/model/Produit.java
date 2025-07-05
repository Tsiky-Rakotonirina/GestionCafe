package com.gestioncafe.model;

import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "produit")
public class Produit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nom", nullable = false, length = 255)
    private String nom;

    @Column(name = "description", length = 500)
    private String description;

    @Column(name = "stock", nullable = false, precision = 10)
    private BigDecimal stock;

    @Column(name = "image", length = 255)
    private String image;

    @Column(name = "delai_peremption", precision = 10)
    private BigDecimal delaiPeremption;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_unite", nullable = false)
    private Unite unite;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_package", nullable = false)
    private Package pack;

    // Relation avec PrixVenteProduit (One-to-Many ou Many-to-One selon votre logique)
    @OneToMany(mappedBy = "produit")
    private List<PrixVenteProduit> prixVenteProduits;

    // Getters and Setters
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

    public List<PrixVenteProduit> getPrixVenteProduits() { return prixVenteProduits; }
    public void setPrixVenteProduits(List<PrixVenteProduit> prixVenteProduits) { this.prixVenteProduits = prixVenteProduits; }
}