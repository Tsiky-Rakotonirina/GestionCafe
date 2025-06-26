package com.gestioncafe.model;

import jakarta.persistence.*;
import java.sql.*;

@Entity
@Table(name = "commission") 
public class Commission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "id_raison_commission") 
    private RaisonCommission raisonCommission;
    private Long idEmploye;
    private double montant;
    private Date dateCommission;
}
