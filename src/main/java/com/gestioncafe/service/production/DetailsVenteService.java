package com.gestioncafe.service.production;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gestioncafe.dto.VenteProduitStatDTO;
import com.gestioncafe.model.production.DetailsVente;
import com.gestioncafe.repository.production.DetailsVenteRepository;

@Service
public class DetailsVenteService {
    private final DetailsVenteRepository detailsVenteRepository;

    public DetailsVenteService(DetailsVenteRepository detailsVenteRepository) {
        this.detailsVenteRepository = detailsVenteRepository;
    }

    public List<DetailsVente> findAll() {
        return detailsVenteRepository.findAll();
    }

    public List<VenteProduitStatDTO> getVenteStatParProduit() {
        return detailsVenteRepository.getVenteStatParProduit();
    }

    public Optional<DetailsVente> findById(Integer id) {
        return detailsVenteRepository.findById(id);
    }

    public DetailsVente save(DetailsVente detailsVente) {
        return detailsVenteRepository.save(detailsVente);
    }

    public void deleteById(Integer id) {
        detailsVenteRepository.deleteById(id);
    }
}
