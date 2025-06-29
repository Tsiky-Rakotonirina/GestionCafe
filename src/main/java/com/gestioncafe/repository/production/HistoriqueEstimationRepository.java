package com.gestioncafe.repository.production;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gestioncafe.model.production.HistoriqueEstimation;
import com.gestioncafe.model.production.MatierePremiere;

public interface HistoriqueEstimationRepository extends JpaRepository<HistoriqueEstimation, Integer> {
    List<HistoriqueEstimation> findByMatierePremiere(MatierePremiere matierePremiere);
}
