package com.gestioncafe.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "prix_vente_produit")
public class PrixVenteProduit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_produit", nullable = false)
    private Produit produit;

    @Column(name = "prix_vente")
    private BigDecimal prixVente;

    @Column(name = "date_application")
    private LocalDate dateApplication;

    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Produit getProduit() { return produit; }
    public void setProduit(Produit produit) { this.produit = produit; }
    public BigDecimal getPrixVente() { return prixVente; }
    public void setPrixVente(BigDecimal prixVente) { this.prixVente = prixVente; }
    public LocalDate getDateApplication() { return dateApplication; }
    public void setDateApplication(LocalDate dateApplication) { this.dateApplication = dateApplication; }
}