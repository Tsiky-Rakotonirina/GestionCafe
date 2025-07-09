package com.gestioncafe.service.production;

import com.gestioncafe.model.MatierePremiere;
import com.gestioncafe.model.SeuilMatierePremiere;
import com.gestioncafe.repository.SeuilMatierePremiereRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeuilMatierePremiereService {
    private final SeuilMatierePremiereRepository repository;

    public SeuilMatierePremiereService(SeuilMatierePremiereRepository repository) {
        this.repository = repository;
    }

    public List<SeuilMatierePremiere> findByMatierePremiere(MatierePremiere mp) {
        return repository.findByMatierePremiere(mp);
    }
}
