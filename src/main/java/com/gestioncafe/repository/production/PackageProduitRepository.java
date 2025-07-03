package com.gestioncafe.repository.production;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gestioncafe.model.PackageProduit;

public interface PackageProduitRepository extends JpaRepository<PackageProduit, Integer> {
}
