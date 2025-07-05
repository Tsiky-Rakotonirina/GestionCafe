package com.gestioncafe.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
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
import jakarta.persistence.Transient;

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
    private Double stock;
    
    private String image;


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

    public Double getStock() {
        return stock;
    }

    public void setStock(Double stock) {
        this.stock = stock;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


    @OneToMany(mappedBy = "matierePremiere")
    private List<DetailFournisseur> detailFournisseurs;
    
    @Transient
    public BigDecimal getPrixUnitaire() {
        if (detailFournisseurs == null || detailFournisseurs.isEmpty()) {
            return null;
        }
        
        // Trouver le détail fournisseur le plus récent
        DetailFournisseur dernierDetail = detailFournisseurs.stream()
            .max(Comparator.comparing(DetailFournisseur::getDateModification))
            .orElse(null);
        
        if (dernierDetail == null || dernierDetail.getPrix() == null || dernierDetail.getQuantite() == null 
                || dernierDetail.getQuantite().compareTo(BigDecimal.ZERO) == 0) {
            return null;
        }
        
        // Calculer le prix unitaire: prix / quantité
        return dernierDetail.getPrix().divide(dernierDetail.getQuantite(), 2, RoundingMode.HALF_UP);
    }
}