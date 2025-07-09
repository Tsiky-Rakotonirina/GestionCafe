package com.gestioncafe.dto;

import java.time.LocalDate;

public class ClientSearchDto {
    
    private String nom;
    private String prenom;
    private Integer ageMin;
    private Integer ageMax;
    private LocalDate dateAdhesion;
    
    // Constructeurs
    public ClientSearchDto() {}
    
    public ClientSearchDto(String nom, String prenom, Integer ageMin, Integer ageMax, LocalDate dateAdhesion) {
        this.nom = nom;
        this.prenom = prenom;
        this.ageMin = ageMin;
        this.ageMax = ageMax;
        this.dateAdhesion = dateAdhesion;
    }
    
    // Getters et Setters
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    
    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }
    
    public Integer getAgeMin() { return ageMin; }
    public void setAgeMin(Integer ageMin) { this.ageMin = ageMin; }
    
    public Integer getAgeMax() { return ageMax; }
    public void setAgeMax(Integer ageMax) { this.ageMax = ageMax; }
    
    public LocalDate getDateAdhesion() { return dateAdhesion; }
    public void setDateAdhesion(LocalDate dateAdhesion) { this.dateAdhesion = dateAdhesion; }
}