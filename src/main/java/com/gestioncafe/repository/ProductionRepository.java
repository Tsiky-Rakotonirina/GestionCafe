package com.gestioncafe.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gestioncafe.model.Production;

public interface ProductionRepository extends JpaRepository<Production, Long> {
    Optional<Production> findByProduitId(Long produitId);
}
