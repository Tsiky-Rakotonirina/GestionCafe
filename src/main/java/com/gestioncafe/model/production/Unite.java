package com.gestioncafe.model.production;

import jakarta.persistence.*;

@Entity
@Table(name = "unite")
public class Unite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nom;

    @Column(name = "valeur_pr_norme")
    private double valeurParNorme;

    @ManyToOne
    @JoinColumn(name = "categorie_unite_id")
    private CategorieUnite categorieUnite;

    // Getters et setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public double getValeurParNorme() {
        return valeurParNorme;
    }

    public void setValeurParNorme(double valeurParNorme) {
        this.valeurParNorme = valeurParNorme;
    }

    public CategorieUnite getCategorieUnite() {
        return categorieUnite;
    }

    public void setCategorieUnite(CategorieUnite categorieUnite) {
        this.categorieUnite = categorieUnite;
    }
}
