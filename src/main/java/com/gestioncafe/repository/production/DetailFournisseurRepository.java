package com.gestioncafe.repository.production;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gestioncafe.model.DetailFournisseur;
import com.gestioncafe.model.MatierePremiere;

public interface DetailFournisseurRepository extends JpaRepository<DetailFournisseur, Integer> {
    List<DetailFournisseur> findByMatierePremiere(MatierePremiere matierePremiere);
}
