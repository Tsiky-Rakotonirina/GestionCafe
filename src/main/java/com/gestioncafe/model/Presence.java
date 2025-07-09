package com.gestioncafe.model;

import jakarta.persistence.*;

import java.sql.Date;
import java.time.LocalDateTime;

@Entity
@Table(name = "presence")
public class Presence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long idEmploye;
    private Date datePresence;
    private LocalDateTime dateArrivee;
    private boolean estPresent;

    public Presence() {
    }

    public Presence(Long idEmploye, Date datePresence, LocalDateTime dateArrivee, boolean estPresent) {
        this.idEmploye = idEmploye;
        this.datePresence = datePresence;
        this.dateArrivee = dateArrivee;
        this.estPresent = estPresent;
    }

    public Presence(Long id, Long idEmploye, Date datePresence, LocalDateTime dateArrivee, boolean estPresent) {
        this.id = id;
        this.idEmploye = idEmploye;
        this.datePresence = datePresence;
        this.dateArrivee = dateArrivee;
        this.estPresent = estPresent;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdEmploye() {
        return idEmploye;
    }

    public void setIdEmploye(Long idEmploye) {
        this.idEmploye = idEmploye;
    }

    public Date getDatePresence() {
        return datePresence;
    }

    public void setDatePresence(Date datePresence) {
        this.datePresence = datePresence;
    }

    public LocalDateTime getDateArrivee() {
        return dateArrivee;
    }

    public void setDateArrivee(LocalDateTime dateArrivee) {
        this.dateArrivee = dateArrivee;
    }

    public boolean isEstPresent() {
        return estPresent;
    }

    public void setEstPresent(boolean estPresent) {
        this.estPresent = estPresent;
    }
}
