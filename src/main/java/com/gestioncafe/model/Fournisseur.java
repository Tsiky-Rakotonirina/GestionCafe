package com.gestioncafe.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "fournisseur")
public class Fournisseur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String nom;
    
    private String contact;
    
    @Column(nullable = false)
    private Double frais;
    
    private String email;
    
    @OneToMany(mappedBy = "fournisseur", cascade = CascadeType.ALL)
    private List<DetailFournisseur> details;

    // Getters and Setters
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

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public Double getFrais() {
        return frais;
    }

    public void setFrais(Double frais) {
        this.frais = frais;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<DetailFournisseur> getDetails() {
        return details;
    }

    public void setDetails(List<DetailFournisseur> details) {
        this.details = details;
    }
}