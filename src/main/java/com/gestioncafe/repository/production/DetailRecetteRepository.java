package com.gestioncafe.repository.production;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gestioncafe.model.production.DetailRecette;

public interface DetailRecetteRepository extends JpaRepository<DetailRecette, Integer> {
    List<DetailRecette> findByRecetteId(Integer recetteId);
}
