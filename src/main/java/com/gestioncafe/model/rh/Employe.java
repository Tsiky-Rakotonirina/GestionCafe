package com.gestioncafe.model.rh;

import jakarta.persistence.*;
import java.time.LocalDate;

import lombok.*;

@Entity
@Data // génère getters, setters, toString, equals, hashCode
@NoArgsConstructor // constructeur sans arguments
@AllArgsConstructor // constructeur avec tous les champs
public class Employe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;

    @ManyToOne
    @JoinColumn(name = "id_genre", nullable = false)
    private Genre genre;

    @Column(name = "date_naissance", nullable = false)
    private LocalDate dateNaissance;

    private String contact;

    @Column(name = "date_recrutement", nullable = false)
    private LocalDate dateRecrutement;

    @ManyToOne
    @JoinColumn(name = "id_candidat", nullable = false)
    private Candidat candidat;

    private String image;

    @Column(columnDefinition = "TEXT", name = "reference_cv", nullable = false)
    private String referenceCv;

    // Getters & setters
}
