package com.gestioncafe.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "v_client_lib")
public class VClientLib {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "nom")
    private String nom;

    @Column(name = "prenom")
    private String prenom;

    @Column(name = "nom_genre")
    private String nomGenre;

    @Column(name = "contact")
    private String contact;

    @Column(name = "email")
    private String email;

    @Column(name = "image")
    private String image;

    @Column(name = "age")
    private Integer age;

    @Column(name = "date_adhesion")
    private LocalDate dateAdhesion;

    @Column(name = "chiffre_affaire_total")
    private BigDecimal chiffreAffaireTotal;

    @Column(name = "nb_vente_total")
    private Long nbVenteTotal;

    // Constructeurs
    public VClientLib() {
    }

    // Getters et Setters


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

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNomGenre() {
        return nomGenre;
    }

    public void setNomGenre(String nomGenre) {
        this.nomGenre = nomGenre;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public LocalDate getDateAdhesion() {
        return dateAdhesion;
    }

    public void setDateAdhesion(LocalDate dateAdhesion) {
        this.dateAdhesion = dateAdhesion;
    }

    public BigDecimal getChiffreAffaireTotal() {
        return chiffreAffaireTotal;
    }

    public void setChiffreAffaireTotal(BigDecimal chiffreAffaireTotal) {
        this.chiffreAffaireTotal = chiffreAffaireTotal;
    }

    public Long getNbVenteTotal() {
        return nbVenteTotal;
    }

    public void setNbVenteTotal(Long nbVenteTotal) {
        this.nbVenteTotal = nbVenteTotal;
    }
}