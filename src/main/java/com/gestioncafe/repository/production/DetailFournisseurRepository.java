package com.gestioncafe.repository.production;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gestioncafe.model.production.DetailFournisseur;
import com.gestioncafe.model.production.MatierePremiere;

public interface DetailFournisseurRepository extends JpaRepository<DetailFournisseur, Integer> {
    List<DetailFournisseur> findByMatierePremiere(MatierePremiere matierePremiere);
}
