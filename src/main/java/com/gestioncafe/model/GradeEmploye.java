package com.gestioncafe.model;

import jakarta.persistence.*;

import java.time.LocalDate;


@Entity
@Table(name = "grade_employe")
public class GradeEmploye {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_employe", referencedColumnName = "id")
    private Employe employe;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_grade", referencedColumnName = "id")
    private Grade grade;

    @Column(name = "date_grade", nullable = false)
    private LocalDate dateGrade;

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

    public Grade getGrade() {
        return this.grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    public LocalDate getDateGrade() {
        return this.dateGrade;
    }

    public void setDateGrade(LocalDate dateGrade) {
        this.dateGrade = dateGrade;
    }
}
