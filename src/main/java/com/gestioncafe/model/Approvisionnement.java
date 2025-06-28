package com.gestioncafe.model;

import jakarta.persistence.*;

import java.time.LocalDate;

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

    public String getReferenceFacture() {
        return referenceFacture;
    }

    public void setReferenceFacture(String referenceFacture) {
        this.referenceFacture = referenceFacture;
    }

    // Méthode toString pour le débogage
    @Override
    public String toString() {
        return "Approvisionnement{" +
                "id=" + id +
                ", detailFournisseur=" + detailFournisseur.getId() +
                ", matierePremiere=" + matierePremiere.getNom() +
                ", quantite=" + quantite +
                ", total=" + total +
                ", dateApprovisionnement=" + dateApprovisionnement +
                ", referenceFacture='" + referenceFacture + '\'' +
                '}';
    }
}