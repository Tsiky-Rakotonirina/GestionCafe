// src/main/java/com/gestioncafe/repository/VenteRepository.java
package com.gestioncafe.repository;

import java.math.BigDecimal;
import java.util.Optional;

import com.gestioncafe.model.*;

import org.springframework.data.jpa.repository.JpaRepository;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PrixVenteProduitRepository extends JpaRepository<PrixVenteProduit, Long> {
    @Query("SELECT p.prixVente FROM PrixVenteProduit p " +
        "WHERE p.produit.id = :produitId " +
        "ORDER BY p.dateApplication DESC LIMIT 1")
    Optional<BigDecimal> findCurrentPriceByProduitId(@Param("produitId") Long produitId);
}