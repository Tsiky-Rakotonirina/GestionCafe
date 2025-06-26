package com.gestioncafe.model.stock;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "matiere_premiere")
public class MatierePremiere {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String nom;

    @ManyToOne
    @JoinColumn(name = "id_unite", nullable = false)
    private Unite unite;

    @Column(nullable = false)
    private double stock;

    private String image;

    private Double delaiPeremption;

    // Getters et setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public Unite getUnite() { return unite; }
    public void setUnite(Unite unite) { this.unite = unite; }
    public double getStock() { return stock; }
    public void setStock(double stock) { this.stock = stock; }
    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }
    public Double getDelaiPeremption() { return delaiPeremption; }
    public void setDelaiPeremption(Double delaiPeremption) { this.delaiPeremption = delaiPeremption; }
}