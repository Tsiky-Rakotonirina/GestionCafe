package com.gestioncafe.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "approvisionnement")
public class Approvisionnement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_detail_fournisseur", nullable = false)
    private DetailFournisseur detailFournisseur;

    @ManyToOne
    @JoinColumn(name = "id_matiere_premiere", nullable = false)
    private MatierePremiere matierePremiere;

    @Column(nullable = false)
    private Double quantite;

    @Column(nullable = false)
    private Double total;

    @Column(name = "date_approvisionnement", nullable = false)
    private LocalDate dateApprovisionnement;

    @Column(name = "date_peremption", nullable = false)
    private LocalDate datePeremption;

    @Column(name = "reference_facture", nullable = false, length = 255)
    private String referenceFacture;

    // Constructeurs
    public Approvisionnement() {
    }

    public Approvisionnement(DetailFournisseur detailFournisseur, MatierePremiere matierePremiere, 
                           Double quantite, Double total, LocalDate dateApprovisionnement, 
                           String referenceFacture) {
        this.detailFournisseur = detailFournisseur;
        this.matierePremiere = matierePremiere;
        this.quantite = quantite;
        this.total = total;
        this.dateApprovisionnement = dateApprovisionnement;
        this.referenceFacture = referenceFacture;
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DetailFournisseur getDetailFournisseur() {
        return detailFournisseur;
    }

    public void setDetailFournisseur(DetailFournisseur detailFournisseur) {
        this.detailFournisseur = detailFournisseur;
    }

    public MatierePremiere getMatierePremiere() {
        return matierePremiere;
    }

    public void setMatierePremiere(MatierePremiere matierePremiere) {
        this.matierePremiere = matierePremiere;
    }

    public Double getQuantite() {
        return quantite;
    }

    public void setQuantite(Double quantite) {
        this.quantite = quantite;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public LocalDate getDateApprovisionnement() {
        return dateApprovisionnement;
    }

    public void setDateApprovisionnement(LocalDate dateApprovisionnement) {
        this.dateApprovisionnement = dateApprovisionnement;
    }

    public LocalDate getDatePeremption() {
        return datePeremption;
    }

    public void setDatePeremption(LocalDate datePeremption) {
        this.datePeremption = datePeremption;
    }

    public String getReferenceFacture() {
        return referenceFacture;
    }

    public void setReferenceFacture(String referenceFacture) {
        this.referenceFacture = referenceFacture;
    }

    @Override
    public String toString() {
        return "Approvisionnement{" +
                "id=" + id +
                ", detailFournisseur=" + detailFournisseur.getId() +
                ", matierePremiere=" + matierePremiere.getNom() +
                ", quantite=" + quantite +
                ", total=" + total +
                ", dateApprovisionnement=" + dateApprovisionnement +
                ", datePeremption=" + datePeremption +
                ", referenceFacture='" + referenceFacture + '\'' +
                '}';
    }
}