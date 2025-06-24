package com.gestioncafe.model.tiers;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;

import java.time.LocalDate;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class Employe extends Tiers {
    @Column(name = "date_naissance", nullable = false)
    private LocalDate dateNaissance;

    @Column(name = "date_recrutement", nullable = false)
    private LocalDate dateRecrutement;

    @Column(name = "date_demission")
    private LocalDate dateDemission;

    @Column(name = "reference_cv")
    private String referenceCv;


    public LocalDate getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public LocalDate getDateRecrutement() {
        return dateRecrutement;
    }

    public void setDateRecrutement(LocalDate dateRecrutement) {
        this.dateRecrutement = dateRecrutement;
    }

    public LocalDate getDateDemission() {
        return dateDemission;
    }

    public void setDateDemission(LocalDate dateDemission) {
        this.dateDemission = dateDemission;
    }

    public String getReferenceCv() {
        return referenceCv;
    }

    public void setReferenceCv(String referenceCv) {
        this.referenceCv = referenceCv;
    }
}
