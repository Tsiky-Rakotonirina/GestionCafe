package com.gestioncafe.model;

import jakarta.persistence.*;
import java.sql.*;

@Entity
@Table(name = "employe") 
public class Employe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long id_candidat;
    private Date dateRecrutement;
    private String nom;
    @ManyToOne
    @JoinColumn(name = "id_genre")
    private Genre genre;
    private Date dateNaissance;
    private String contact;
    private String image;
    private String referenceCv;
    public Employe() {
    }
    public Employe(Long id_candidat, Date dateRecrutement, String nom, Genre genre, Date dateNaissance, String contact,
            String image, String referenceCv) {
        this.id_candidat = id_candidat;
        this.dateRecrutement = dateRecrutement;
        this.nom = nom;
        this.genre = genre;
        this.dateNaissance = dateNaissance;
        this.contact = contact;
        this.image = image;
        this.referenceCv = referenceCv;
    }
    public Employe(Long id, Long id_candidat, Date dateRecrutement, String nom, Genre genre, Date dateNaissance,
            String contact, String image, String referenceCv) {
        this.id = id;
        this.id_candidat = id_candidat;
        this.dateRecrutement = dateRecrutement;
        this.nom = nom;
        this.genre = genre;
        this.dateNaissance = dateNaissance;
        this.contact = contact;
        this.image = image;
        this.referenceCv = referenceCv;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getId_candidat() {
        return id_candidat;
    }
    public void setId_candidat(Long id_candidat) {
        this.id_candidat = id_candidat;
    }
    public Date getDateRecrutement() {
        return dateRecrutement;
    }
    public void setDateRecrutement(Date dateRecrutement) {
        this.dateRecrutement = dateRecrutement;
    }
    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public Genre getGenre() {
        return genre;
    }
    public void setGenre(Genre genre) {
        this.genre = genre;
    }
    public Date getDateNaissance() {
        return dateNaissance;
    }
    public void setDateNaissance(Date dateNaissance) {
        this.dateNaissance = dateNaissance;
    }
    public String getContact() {
        return contact;
    }
    public void setContact(String contact) {
        this.contact = contact;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public String getReferenceCv() {
        return referenceCv;
    }
    public void setReferenceCv(String referenceCv) {
        this.referenceCv = referenceCv;
    }
}
