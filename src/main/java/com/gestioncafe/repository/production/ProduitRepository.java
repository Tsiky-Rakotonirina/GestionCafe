package com.gestioncafe.repository.production;

import com.gestioncafe.model.production.Produit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProduitRepository extends JpaRepository<Produit, Integer> {
}