package com.gestioncafe.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Transient;

@Entity
@Table(name = "detail_fournisseur")
public class DetailFournisseur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_fournisseur", nullable = false)
    private Fournisseur fournisseur;
    
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_matiere_premiere", nullable = false)
    private MatierePremiere matierePremiere;
    
    @Column(name = "quantite", nullable = false, precision = 10)
    private BigDecimal quantite;
    
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_unite", nullable = false)
    private Unite unite;
    
    @Column(name = "prix", nullable = false, precision = 10)
    private BigDecimal prix;
    
    @Column(name = "date_modification", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dateModification;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Fournisseur getFournisseur() {
        return fournisseur;
    }

    public void setFournisseur(Fournisseur fournisseur) {
        this.fournisseur = fournisseur;
    }

    public MatierePremiere getMatierePremiere() {
        return matierePremiere;
    }

    public void setMatierePremiere(MatierePremiere matierePremiere) {
        this.matierePremiere = matierePremiere;
    }

    public BigDecimal getQuantite() {
        return quantite;
    }

    public void setQuantite(BigDecimal quantite) {
        this.quantite = quantite;
    }

    public Unite getUnite() {
        return unite;
    }

    public void setUnite(Unite unite) {
        this.unite = unite;
    }

    public BigDecimal getPrix() {
        return prix;
    }

    public void setPrix(BigDecimal prix) {
        this.prix = prix;
    }

    public Date getDateModification() {
        return dateModification;
    }

    public void setDateModification(Date dateModification) {
        this.dateModification = dateModification;
    }

    @Transient
    public BigDecimal getPrixUnitaire() {
        if (prix == null || quantite == null || quantite.compareTo(BigDecimal.ZERO) == 0) {
            return null;
        }
        
        // Calcul du prix unitaire avec arrondi à 2 décimales
        return prix.divide(quantite, 2, RoundingMode.HALF_UP);
    }
}