package com.gestioncafe.model;

import java.util.Comparator;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "matiere_premiere")
public class MatierePremiere {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String nom;
    
    @ManyToOne
    @JoinColumn(name = "id_unite", nullable = false)
    private Unite unite;
    
    @Column(nullable = false)
    private Double seuilMin;
    
    @Column(nullable = false)
    private Double seuilMax;
    
    @Column(nullable = false)
    private Double stock;
    
    // private String image;
    
    @Column(name = "delai_peremption")
    private Integer delaiPeremption;

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

    public Unite getUnite() {
        return unite;
    }

    public void setUnite(Unite unite) {
        this.unite = unite;
    }

    public Double getSeuilMin() {
        return seuilMin;
    }

    public void setSeuilMin(Double seuilMin) {
        this.seuilMin = seuilMin;
    }

    public Double getSeuilMax() {
        return seuilMax;
    }

    public void setSeuilMax(Double seuilMax) {
        this.seuilMax = seuilMax;
    }

    public Double getStock() {
        return stock;
    }

    public void setStock(Double stock) {
        this.stock = stock;
    }

    // public String getImage() {
    //     return image;
    // }

    // public void setImage(String image) {
    //     this.image = image;
    // }

    public Integer getDelaiPeremption() {
        return delaiPeremption;
    }

    public void setDelaiPeremption(Integer delaiPeremption) {
        this.delaiPeremption = delaiPeremption;
    }

    @OneToMany(mappedBy = "matierePremiere")
    private List<DetailFournisseur> detailFournisseurs;
    
    @Transient
    public Double getDernierPrixUnitaire() {
        if (detailFournisseurs == null || detailFournisseurs.isEmpty()) {
            return null;
        }
        return detailFournisseurs.stream()
            .max(Comparator.comparing(DetailFournisseur::getDateModification))
            .map(DetailFournisseur::getPrixUnitaire)
            .orElse(null);
    }
}