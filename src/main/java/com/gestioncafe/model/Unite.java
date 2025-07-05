package com.gestioncafe.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "unite")
public class Unite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "nom", length = 50)
    private String nom; // kg, g, l, cl, ...
    
    @ManyToOne
    @JoinColumn(name = "categorie_unite_id")
    private CategorieUnite categorieUnite;
    
    @Column(name = "valeur_pr_norme", precision = 10)
    private Double valeurPrNorme; // valeur par rapport au norme

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

    public CategorieUnite getCategorieUnite() {
        return categorieUnite;
    }

    public void setCategorieUnite(CategorieUnite categorieUnite) {
        this.categorieUnite = categorieUnite;
    }

    public Double getValeurPrNorme() {
        return valeurPrNorme;
    }

    public void setValeurPrNorme(Double valeurPrNorme) {
        this.valeurPrNorme = valeurPrNorme;
    }
}