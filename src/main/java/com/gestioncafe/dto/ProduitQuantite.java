package com.gestioncafe.dto;

import java.math.BigDecimal;

public class ProduitQuantite {
    private Long produitId;
    private BigDecimal quantite;
    
    // Getters et setters
    public Long getProduitId() { return produitId; }
    public void setProduitId(Long produitId) { this.produitId = produitId; }
    public BigDecimal getQuantite() { return quantite; }
    public void setQuantite(BigDecimal quantite) { this.quantite = quantite; }
}