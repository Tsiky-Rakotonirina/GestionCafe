package com.gestioncafe.service.production;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.gestioncafe.model.production.MatierePremiere;
import com.gestioncafe.repository.production.MatierePremiereRepository;

@Service
public class MatierePremiereService {
    private final MatierePremiereRepository repository;

    public MatierePremiereService(MatierePremiereRepository repository) {
        this.repository = repository;
    }

    public List<MatierePremiere> findAll() {
        return repository.findAll();
    }

    public Optional<MatierePremiere> findById(Integer id) {
        return repository.findById(id);
    }

    public MatierePremiere save(MatierePremiere mp) {
        return repository.save(mp);
    }

    public void deleteById(Integer id) {
        repository.deleteById(id);
    }
}
