package com.gestioncafe.model;

import jakarta.persistence.*;

import java.math.BigDecimal;

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
    private BigDecimal valeurPrNorme; // valeur par rapport au norme

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

    public BigDecimal getValeurPrNorme() {
        return valeurPrNorme;
    }

    public void setValeurPrNorme(BigDecimal valeurPrNorme) {
        this.valeurPrNorme = valeurPrNorme;
    }
}
