package com.gestioncafe.repository;

import java.math.BigDecimal;

import com.gestioncafe.model.Produit;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProduitRepository extends JpaRepository<Produit, Long> {
    List<Produit> findByNomContainingIgnoreCase(String nom);

    @Query("SELECT SUM(m.stock) FROM MatierePremiere m")
    BigDecimal getTotalStockMatierePremiere();

    @Query("SELECT COUNT(m) FROM MatierePremiere m ")
    Long countMatierePremiereEnRupture();

    @Query("SELECT SUM(p.stock * ppv.prixVente) " +
           "FROM Produit p " +
           "JOIN p.prixVenteProduits ppv " +
           "WHERE ppv.dateApplication = (SELECT MAX(ppv2.dateApplication) FROM PrixVenteProduit ppv2 WHERE ppv2.produit = p)")
    BigDecimal getTotalValeurProduits();

    // Nouvelle méthode pour récupérer tous les produits
    List<Produit> findAllByOrderByNomAsc();
}