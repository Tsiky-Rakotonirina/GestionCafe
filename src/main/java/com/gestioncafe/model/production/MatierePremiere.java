package com.gestioncafe.model.production;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "matiere_premiere")
public class MatierePremiere {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String nom;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_unite", nullable = false)
    private Unite unite;

    private String image;

    @Column
    private Double stock;

    @OneToMany(mappedBy = "matierePremiere", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetailFournisseur> detailsFournisseur;

    @OneToMany(mappedBy = "matierePremiere", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HistoriqueEstimation> historiquesEstimation;

    @OneToMany(mappedBy = "matierePremiere", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SeuilMatierePremiere> seuils;

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

    public Unite getUnite() {
        return unite;
    }

    public void setUnite(Unite unite) {
        this.unite = unite;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Double getStock() {
        return stock;
    }

    public void setStock(Double stock) {
        this.stock = stock;
    }

    public List<DetailFournisseur> getDetailsFournisseur() {
        return detailsFournisseur;
    }

    public void setDetailsFournisseur(List<DetailFournisseur> detailsFournisseur) {
        this.detailsFournisseur = detailsFournisseur;
    }

    public List<HistoriqueEstimation> getHistoriquesEstimation() {
        return historiquesEstimation;
    }

    public void setHistoriquesEstimation(List<HistoriqueEstimation> historiquesEstimation) {
        this.historiquesEstimation = historiquesEstimation;
    }

    public List<SeuilMatierePremiere> getSeuils() {
        return seuils;
    }

    public void setSeuils(List<SeuilMatierePremiere> seuils) {
        this.seuils = seuils;
    }
}