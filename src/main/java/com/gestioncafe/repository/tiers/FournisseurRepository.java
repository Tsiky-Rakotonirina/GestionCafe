package com.gestioncafe.repository.tiers;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gestioncafe.model.tiers.Fournisseur;

public interface FournisseurRepository extends JpaRepository<Fournisseur, Integer> {
    // CRUD standard
}
