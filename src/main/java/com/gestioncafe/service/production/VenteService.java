package com.gestioncafe.service.production;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gestioncafe.model.production.Vente;
import com.gestioncafe.repository.production.VenteRepository;

@Service
public class VenteService {
    @Autowired
    private VenteRepository venteRepository;

    public List<Vente> findAll() {
        return venteRepository.findAll();
    }

    public Optional<Vente> findById(Integer id) {
        return venteRepository.findById(id);
    }

    public Vente save(Vente vente) {
        return venteRepository.save(vente);
    }

    public void deleteById(Integer id) {
        venteRepository.deleteById(id);
    }
}
