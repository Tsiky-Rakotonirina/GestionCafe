// src/main/java/com/gestioncafe/model/Commande.java
package com.gestioncafe.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "commande")
public class Commande {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToOne
    @JoinColumn(name = "id_vente")
    private Vente vente;
    
    private LocalDateTime dateFin;
    private boolean estTerminee;
    
    // Getters et setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Vente getVente() { return vente; }
    public void setVente(Vente vente) { this.vente = vente; }
    public LocalDateTime getDateFin() { return dateFin; }
    public void setDateFin(LocalDateTime dateFin) { this.dateFin = dateFin; }
    public boolean isEstTerminee() { return estTerminee; }
    public void setEstTerminee(boolean estTerminee) { this.estTerminee = estTerminee; }
}