package com.gestioncafe.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gestioncafe.model.Fournisseur;

public interface FournisseurRepository extends JpaRepository<Fournisseur, Integer> {
    // CRUD standard
}
