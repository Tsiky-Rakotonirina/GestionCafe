package com.gestioncafe.model;

import jakarta.persistence.*;
import java.sql.*;

@Entity
@Table(name = "avance") 
public class Avance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "id_raison_avance")  
    private RaisonAvance raisonAvance;
    private Long idEmploye;
    private double montant;
    private Date dateAvance;
}