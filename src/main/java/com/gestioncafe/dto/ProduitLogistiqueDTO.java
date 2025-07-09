package com.gestioncafe.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ProduitLogistiqueDTO {
    private String nom;
    private BigDecimal stock;
    private BigDecimal coutRevient;
    private BigDecimal prixVente;
    private BigDecimal benefice;
    private LocalDate dateProduction;
    private LocalDate datePeremption;
    private BigDecimal stockTotal;

    public ProduitLogistiqueDTO() {}

    public ProduitLogistiqueDTO(String nom, BigDecimal stock, BigDecimal coutRevient, BigDecimal prixVente, BigDecimal benefice, LocalDate dateProduction, LocalDate datePeremption, BigDecimal stockTotal) {
        this.nom = nom;
        this.stock = stock;
        this.coutRevient = coutRevient;
        this.prixVente = prixVente;
        this.benefice = benefice;
        this.dateProduction = dateProduction;
        this.datePeremption = datePeremption;
        this.stockTotal = stockTotal;
    }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public BigDecimal getStock() { return stock; }
    public void setStock(BigDecimal stock) { this.stock = stock; }
    public BigDecimal getCoutRevient() { return coutRevient; }
    public void setCoutRevient(BigDecimal coutRevient) { this.coutRevient = coutRevient; }
    public BigDecimal getPrixVente() { return prixVente; }
    public void setPrixVente(BigDecimal prixVente) { this.prixVente = prixVente; }
    public BigDecimal getBenefice() { return benefice; }
    public void setBenefice(BigDecimal benefice) { this.benefice = benefice; }
    public LocalDate getDateProduction() { return dateProduction; }
    public void setDateProduction(LocalDate dateProduction) { this.dateProduction = dateProduction; }
    public LocalDate getDatePeremption() { return datePeremption; }
    public void setDatePeremption(LocalDate datePeremption) { this.datePeremption = datePeremption; }
    public BigDecimal getStockTotal() { return stockTotal; }
    public void setStockTotal(BigDecimal stockTotal) { this.stockTotal = stockTotal; }
}
