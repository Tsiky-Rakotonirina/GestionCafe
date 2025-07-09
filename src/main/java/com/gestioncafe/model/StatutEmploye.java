package com.gestioncafe.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "statut_employe")
public class StatutEmploye {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_employe", referencedColumnName = "id")
    private Employe employe;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_statut", referencedColumnName = "id")
    private Statut statut;

    @Column(name = "date_statut", nullable = false)
    private LocalDateTime dateStatut;

    // Getters & Setters

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Employe getEmploye() {
        return this.employe;
    }

    public void setEmploye(Employe employe) {
        this.employe = employe;
    }

    public Statut getStatut() {
        return this.statut;
    }

    public void setStatut(Statut statut) {
        this.statut = statut;
    }

    public LocalDateTime getDateStatut() {
        return this.dateStatut;
    }

    public void setDateStatut(LocalDateTime dateStatut) {
        this.dateStatut = dateStatut;
    }
}
