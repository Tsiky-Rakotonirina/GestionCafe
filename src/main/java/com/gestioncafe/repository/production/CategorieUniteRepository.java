package com.gestioncafe.repository.production;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gestioncafe.model.production.CategorieUnite;

public interface CategorieUniteRepository extends JpaRepository<CategorieUnite, Integer> {
    // méthodes personnalisées si besoin
}
