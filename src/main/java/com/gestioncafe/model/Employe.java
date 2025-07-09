package com.gestioncafe.model;

import jakarta.persistence.*;

import java.sql.Date;


@Entity
@Table(name = "employe")
public class Employe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false)
    private String nom;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_genre", referencedColumnName = "id")
    private Genre genre;

    @Column(name = "date_naissance", nullable = false)
    private Date dateNaissance;

    @Column(nullable = false)
    private String contact;

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

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}