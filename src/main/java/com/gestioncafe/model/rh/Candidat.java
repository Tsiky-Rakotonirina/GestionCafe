package com.gestioncafe.model.rh;

import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.*;


@Data // génère getters, setters, toString, equals, hashCode
@NoArgsConstructor // constructeur sans arguments
@AllArgsConstructor // constructeur avec tous les champs
@Entity
public class Candidat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;

    @ManyToOne
    @JoinColumn(name = "id_genre", nullable = false)
    private Genre genre;

    @Column(name = "date_naissance", nullable = false)
    private LocalDate dateNaissance;

    @Column(name = "date_candidature", nullable = false)
    private LocalDate dateCandidature;

    private String contact;

    private String image;

    @Column(columnDefinition = "TEXT", name = "reference_cv", nullable = false)
    private String referenceCv;

    private Long idGrade; // à transformer en relation si tu as une table Grade

}
