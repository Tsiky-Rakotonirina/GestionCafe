package com.gestioncafe.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.List;

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

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_categorie_unite_id", nullable = false)
    private CategorieUnite categorieUnite;


    @Column(nullable = false)
    private Double stock;

    private String image;

    @OneToMany(mappedBy = "matierePremiere", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<DetailFournisseur> detailsFournisseur;

    @OneToMany(mappedBy = "matierePremiere", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<HistoriqueEstimation> historiquesEstimation;

    @OneToMany(mappedBy = "matierePremiere", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<SeuilMatierePremiere> seuils;

    // Getters et setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public CategorieUnite getCategorieUnite() {
        return categorieUnite;
    }

    public void setCategorieUnite(CategorieUnite categorieUnite) {
        this.categorieUnite = categorieUnite;
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