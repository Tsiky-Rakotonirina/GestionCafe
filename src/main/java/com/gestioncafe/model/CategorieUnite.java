package com.gestioncafe.model;

import jakarta.persistence.*;

@Entity
@Table(name = "categorie_unite")
public class CategorieUnite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;

    private String norme;

    public CategorieUnite() {}

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

    public String getNorme() {
        return norme;
    }

    public void setNorme(String norme) {
        this.norme = norme;
    }
}
