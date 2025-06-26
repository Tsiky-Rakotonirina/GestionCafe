package com.gestioncafe.model;

import jakarta.persistence.*;

@Entity
@Table(name = "raison_avance") 
public class RaisonAvance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String valeur;
    private String description; 
}
