package com.gestioncafe.service.production;

import java.util.List;

import org.springframework.stereotype.Service;

import com.gestioncafe.model.HistoriqueEstimation;
import com.gestioncafe.model.MatierePremiere;
import com.gestioncafe.repository.production.HistoriqueEstimationRepository;

@Service
public class HistoriqueEstimationService {
    private final HistoriqueEstimationRepository repository;

    public HistoriqueEstimationService(HistoriqueEstimationRepository repository) {
        this.repository = repository;
    }

    public List<HistoriqueEstimation> findByMatierePremiere(MatierePremiere mp) {
        return repository.findByMatierePremiere(mp);
    }
}
