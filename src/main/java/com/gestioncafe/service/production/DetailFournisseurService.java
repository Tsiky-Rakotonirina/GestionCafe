package com.gestioncafe.service.production;

import com.gestioncafe.model.DetailFournisseur;
import com.gestioncafe.model.MatierePremiere;
import com.gestioncafe.repository.DetailFournisseurRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DetailFournisseurService {
    private final DetailFournisseurRepository repository;

    public DetailFournisseurService(DetailFournisseurRepository repository) {
        this.repository = repository;
    }

    public List<DetailFournisseur> findByMatierePremiere(MatierePremiere mp) {
        return repository.findByMatierePremiere(mp);
    }
}
