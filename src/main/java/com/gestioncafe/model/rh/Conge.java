package com.gestioncafe.model.rh;

import jakarta.persistence.*;
import java.time.LocalDate;

import lombok.*;

@Entity
@Data // génère getters, setters, toString, equals, hashCode
@NoArgsConstructor // constructeur sans arguments
@AllArgsConstructor // constructeur avec tous les champs
public class Conge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_type_conge", nullable = false)
    private TypeConge typeConge;

    @Column(name = "date_debut", nullable = false)
    private LocalDate dateDebut;

    @Column(name = "date_Fin", nullable = false)
    private LocalDate dateFin;

    private int duree;

    @ManyToOne
    @JoinColumn(name = "id_employe", nullable = false)
    private Employe employe; // À remplacer par @ManyToOne si tu as une entité Employe

}
