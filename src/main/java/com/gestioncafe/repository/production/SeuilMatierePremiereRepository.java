package com.gestioncafe.repository.production;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gestioncafe.model.MatierePremiere;
import com.gestioncafe.model.SeuilMatierePremiere;

public interface SeuilMatierePremiereRepository extends JpaRepository<SeuilMatierePremiere, Integer> {
    List<SeuilMatierePremiere> findByMatierePremiere(MatierePremiere matierePremiere);
}
