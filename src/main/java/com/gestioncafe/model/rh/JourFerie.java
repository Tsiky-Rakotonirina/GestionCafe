package com.gestioncafe.model.rh;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.*;

@Entity
@Data // génère getters, setters, toString, equals, hashCode
@NoArgsConstructor // constructeur sans arguments
@AllArgsConstructor // constructeur avec tous les champs
@Table(name = "jour_ferie")
public class JourFerie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    @Column(name = "date_ferie", nullable = false)
    private LocalDate dateFerie;
 

}
