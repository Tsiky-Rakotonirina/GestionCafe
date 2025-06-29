package com.gestioncafe.model.production;

import com.gestioncafe.model.PackageProduit;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "produit")
public class Produit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

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

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
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
}
