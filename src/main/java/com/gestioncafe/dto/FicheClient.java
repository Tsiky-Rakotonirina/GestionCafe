package com.gestioncafe.dto;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalTime;

public class FicheClient {
    
    // Informations de base
    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private String contact;
    private Integer age;
    
    // Statistiques de vente
    private Long nbVenteTotal;
    private BigDecimal caTotal;
    
    // Analyse des produits
    private String produitPlusAchete;
    private Integer quantiteProduitPlusAchete;
    
    // Analyse des dépenses
    private BigDecimal depenseMoyenneParCommande;
    private BigDecimal depenseMin;
    private BigDecimal depenseMax;
    
    // Analyse temporelle
    private Integer intervalleJourVente; // en jours
    private String jourPrefere; // jour de la semaine
    private String trancheHorairePrefere; // ex: "Matin (8h-12h)"
    
    // Constructeurs
    public FicheClient() {}
    
    public FicheClient(Long id, String nom, String prenom, String email, String contact, Integer age) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.contact = contact;
        this.age = age;
    }
    
    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    
    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getContact() { return contact; }
    public void setContact(String contact) { this.contact = contact; }
    
    public Integer getAge() { return age; }
    public void setAge(Integer age) { this.age = age; }
    
    public Long getNbVenteTotal() { return nbVenteTotal; }
    public void setNbVenteTotal(Long nbVenteTotal) { this.nbVenteTotal = nbVenteTotal; }
    
    public BigDecimal getCaTotal() { return caTotal; }
    public void setCaTotal(BigDecimal caTotal) { this.caTotal = caTotal; }
    
    public String getProduitPlusAchete() { return produitPlusAchete; }
    public void setProduitPlusAchete(String produitPlusAchete) { this.produitPlusAchete = produitPlusAchete; }
    
    public Integer getQuantiteProduitPlusAchete() { return quantiteProduitPlusAchete; }
    public void setQuantiteProduitPlusAchete(Integer quantiteProduitPlusAchete) { this.quantiteProduitPlusAchete = quantiteProduitPlusAchete; }
    
    public BigDecimal getDepenseMoyenneParCommande() { return depenseMoyenneParCommande; }
    public void setDepenseMoyenneParCommande(BigDecimal depenseMoyenneParCommande) { this.depenseMoyenneParCommande = depenseMoyenneParCommande; }
    
    public BigDecimal getDepenseMin() { return depenseMin; }
    public void setDepenseMin(BigDecimal depenseMin) { this.depenseMin = depenseMin; }
    
    public BigDecimal getDepenseMax() { return depenseMax; }
    public void setDepenseMax(BigDecimal depenseMax) { this.depenseMax = depenseMax; }
    
    public Integer getIntervalleJourVente() { return intervalleJourVente; }
    public void setIntervalleJourVente(Integer intervalleJourVente) { this.intervalleJourVente = intervalleJourVente; }
    
    public String getJourPrefere() { return jourPrefere; }
    public void setJourPrefere(String jourPrefere) { this.jourPrefere = jourPrefere; }
    
    public String getTrancheHorairePrefere() { return trancheHorairePrefere; }
    public void setTrancheHorairePrefere(String trancheHorairePrefere) { this.trancheHorairePrefere = trancheHorairePrefere; }
    
    // Méthodes utilitaires
    public String getNomComplet() {
        return this.prenom + " " + this.nom;
    }
    
    public String getStatutClient() {
        if (nbVenteTotal == null || nbVenteTotal == 0) {
            return "Nouveau client";
        } else if (nbVenteTotal <= 2) {
            return "Client occasionnel";
        } else if (nbVenteTotal <= 5) {
            return "Client régulier";
        } else {
            return "Client fidèle";
        }
    }
    
    public String getCategorieDepense() {
        if (caTotal == null || caTotal.compareTo(BigDecimal.ZERO) == 0) {
            return "Aucune dépense";
        } else if (caTotal.compareTo(new BigDecimal("10000")) < 0) {
            return "Petit acheteur";
        } else if (caTotal.compareTo(new BigDecimal("50000")) < 0) {
            return "Acheteur moyen";
        } else {
            return "Gros acheteur";
        }
    }
    
    @Override
    public String toString() {
        return "FicheClient{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", email='" + email + '\'' +
                ", nbVenteTotal=" + nbVenteTotal +
                ", caTotal=" + caTotal +
                '}';
    }
}