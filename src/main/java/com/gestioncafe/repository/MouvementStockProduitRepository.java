package com.gestioncafe.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gestioncafe.model.MouvementStockProduit;

public interface MouvementStockProduitRepository extends JpaRepository<MouvementStockProduit, Long> {
}
