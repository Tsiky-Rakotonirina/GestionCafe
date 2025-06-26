package com.gestioncafe.model;

import jakarta.persistence.*;
import java.sql.*;

@Entity
@Table(name = "presence") 
public class Presence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long idEmploye;
    private Date datePresence;
    private Timestamp dateArrivee;
    private boolean estpresent;
    public Presence() {
    }
    public Presence(Long idEmploye, Date datePresence, Timestamp dateArrivee, boolean estpresent) {
        this.idEmploye = idEmploye;
        this.datePresence = datePresence;
        this.dateArrivee = dateArrivee;
        this.estpresent = estpresent;
    }
    public Presence(Long id, Long idEmploye, Date datePresence, Timestamp dateArrivee, boolean estpresent) {
        this.id = id;
        this.idEmploye = idEmploye;
        this.datePresence = datePresence;
        this.dateArrivee = dateArrivee;
        this.estpresent = estpresent;
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
    public Timestamp getDateArrivee() {
        return dateArrivee;
    }
    public void setDateArrivee(Timestamp dateArrivee) {
        this.dateArrivee = dateArrivee;
    }
    public boolean isEstpresent() {
        return estpresent;
    }
    public void setEstpresent(boolean estpresent) {
        this.estpresent = estpresent;
    }
}
