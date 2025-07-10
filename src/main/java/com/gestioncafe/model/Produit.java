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

    private String nom;

    private String description;

    @Column(nullable = false)
    private BigDecimal stock;

    private String image;

    @Column(name = "delai_peremption")
    private BigDecimal delaiPeremption;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_unite", nullable = false)
    private Unite unite;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_package", nullable = false)
    private PackageProduit packageProduit;

    @OneToMany(mappedBy = "produit")
    private List<PrixVenteProduit> prixVenteProduits;


    public Unite getUnite() {
        return unite;
    }

    public void setUnite(Unite unite) {
        this.unite = unite;
    }

    public PackageProduit getPackageProduit() {
        return packageProduit;
    }

    public void setPackageProduit(PackageProduit packageProduit) {
        this.packageProduit = packageProduit;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
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
    // Retourne le prix courant (dernier prix de la liste, ou null si vide)
    public java.math.BigDecimal getPrixCourant() {
        if (prixVenteProduits != null && !prixVenteProduits.isEmpty()) {
            // On suppose que la liste est triée par date croissante
            return prixVenteProduits.get(prixVenteProduits.size() - 1).getPrixVente();
        }
        return null;
    }
}
   
  

