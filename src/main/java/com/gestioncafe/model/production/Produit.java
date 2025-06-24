package com.gestioncafe.model.production;

import com.gestioncafe.model.PackageProduit;
import jakarta.persistence.*;

@Entity
@Table(name = "produit")
public class Produit {
    @Id
    private Integer id;

    private String nom;

    private String description;

    private double stock;

    private String image;

    private double delaiPeremption;

    @OneToOne
    @JoinColumn(name = "id_package")
    private PackageProduit packageProduit;


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

    public double getStock() {
        return stock;
    }

    public void setStock(double stock) {
        this.stock = stock;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public double getDelaiPeremption() {
        return delaiPeremption;
    }

    public void setDelaiPeremption(double delaiPeremption) {
        this.delaiPeremption = delaiPeremption;
    }
}
