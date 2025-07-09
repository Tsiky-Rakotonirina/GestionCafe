package com.gestioncafe.repository;

import com.gestioncafe.model.PrixVenteProduit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface PrixVenteProduitRepository extends JpaRepository<PrixVenteProduit, Long> {
    List<PrixVenteProduit> findByProduitId(Integer produitId);

    List<PrixVenteProduit> findByProduitIdOrderByDateApplicationDesc(Integer produitId);

    @Query("SELECT p.prixVente FROM PrixVenteProduit p " +
        "WHERE p.produit.id = :produitId " +
        "ORDER BY p.dateApplication DESC LIMIT 1")
    Optional<BigDecimal> findCurrentPriceByProduitId(@Param("produitId") Long produitId);
}