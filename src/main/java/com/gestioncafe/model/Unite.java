package com.gestioncafe.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "unite")
public class Unite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;

    @ManyToOne
    @JoinColumn(name = "categorie_unite_id")
    private CategorieUnite categorieUnite;

    @Column(name = "valeur_pr_norme")
    private BigDecimal valeurParNorme;

    public Unite() {}

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

    public BigDecimal getValeurParNorme() {
        return valeurParNorme;
    }

    public void setValeurParNorme(BigDecimal valeurParNorme) {
        this.valeurParNorme = valeurParNorme;
    }
}
