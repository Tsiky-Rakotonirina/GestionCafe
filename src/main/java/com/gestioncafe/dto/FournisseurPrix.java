package com.gestioncafe.dto;

import java.math.BigDecimal;

public class FournisseurPrix {
    private Long idFournisseur;
    private String nomFournisseur;
    private BigDecimal prix;

    public FournisseurPrix(Integer idFournisseur, String nomFournisseur, BigDecimal prix) {
        this.idFournisseur = idFournisseur != null ? idFournisseur.longValue() : null;
        this.nomFournisseur = nomFournisseur;
        this.prix = prix;
    }

    public FournisseurPrix(Long idFournisseur, String nomFournisseur, BigDecimal prix) {
        this.idFournisseur = idFournisseur;
        this.nomFournisseur = nomFournisseur;
        this.prix = prix;
    }

    public Long getIdFournisseur() {
        return idFournisseur;
    }

    public void setIdFournisseur(Long idFournisseur) {
        this.idFournisseur = idFournisseur;
    }

    public String getNomFournisseur() {
        return nomFournisseur;
    }

    public void setNomFournisseur(String nomFournisseur) {
        this.nomFournisseur = nomFournisseur;
    }

    public BigDecimal getPrix() {
        return prix;
    }

    public void setPrix(BigDecimal prix) {
        this.prix = prix;
    }
}
