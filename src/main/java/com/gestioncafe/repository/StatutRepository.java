package com.gestioncafe.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gestioncafe.model.Statut;

public interface StatutRepository extends JpaRepository<Statut, Long> {
    Optional<Statut> findByValeur(String valeur);
}
