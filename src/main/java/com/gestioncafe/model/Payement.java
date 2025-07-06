package com.gestioncafe.model;

import jakarta.persistence.*;
import java.sql.*;

@Entity
@Table(name = "payement")
public class Payement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long idEmploye;
    private double montant;
    private Date moisReference;
    private Date datePayement;
    private String referencePayement;
    private Double irsa;
    private Double cotisationSociale;

    public Payement() {
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

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public Date getMoisReference() {
        return moisReference;
    }

    public void setMoisReference(Date moisReference) {
        this.moisReference = moisReference;
    }

    public Date getDatePayement() {
        return datePayement;
    }

    public void setDatePayement(Date datePayement) {
        this.datePayement = datePayement;
    }

    public String getReferencePayement() {
        return referencePayement;
    }

    public void setReferencePayement(String referencePayement) {
        this.referencePayement = referencePayement;
    }

    public Double getIrsa() {
        return irsa;
    }

    public void setIrsa(Double irsa) {
        this.irsa = irsa;
    }

    public Double getCotisationSociale() {
        return cotisationSociale;
    }

    public void setCotisationSociale(Double cotisationSociale) {
        this.cotisationSociale = cotisationSociale;
    }

    public Payement(Long idEmploye, double montant, Date moisReference, Date datePayement, String referencePayement,
            Double irsa, Double cotisationSociale) {
        this.idEmploye = idEmploye;
        this.montant = montant;
        this.moisReference = moisReference;
        this.datePayement = datePayement;
        this.referencePayement = referencePayement;
        this.irsa = irsa;
        this.cotisationSociale = cotisationSociale;
    }

    public Payement(Long id, Long idEmploye, double montant, Date moisReference, Date datePayement,
            String referencePayement, Double irsa, Double cotisationSociale) {
        this.id = id;
        this.idEmploye = idEmploye;
        this.montant = montant;
        this.moisReference = moisReference;
        this.datePayement = datePayement;
        this.referencePayement = referencePayement;
        this.irsa = irsa;
        this.cotisationSociale = cotisationSociale;
    }

}
