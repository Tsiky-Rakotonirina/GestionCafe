package com.gestioncafe.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "produit")
public class Produit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_type_produit", nullable = false)
    private TypeProduit typeProduit;

    @Column(nullable = false)
    private String nom;

    private String description;
    
    @Column(nullable = false)
    private BigDecimal stock;
    
    private String image;
    
    private Integer delaiPeremption;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public TypeProduit getTypeProduit() { return typeProduit; }
    public void setTypeProduit(TypeProduit typeProduit) { this.typeProduit = typeProduit; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public BigDecimal getStock() { return stock; }
    public void setStock(BigDecimal stock) { this.stock = stock; }
    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }
    public Integer getDelaiPeremption() { return delaiPeremption; }
    public void setDelaiPeremption(Integer delaiPeremption) { this.delaiPeremption = delaiPeremption; }
}