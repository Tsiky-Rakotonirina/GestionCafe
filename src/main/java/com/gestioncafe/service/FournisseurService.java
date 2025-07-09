package com.gestioncafe.service;

import com.gestioncafe.model.Fournisseur;
import com.gestioncafe.repository.FournisseurRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FournisseurService {
    private final FournisseurRepository fournisseurRepository;

    public FournisseurService(FournisseurRepository fournisseurRepository) {
        this.fournisseurRepository = fournisseurRepository;
    }

    public List<Fournisseur> getAllFournisseurs() {
        return fournisseurRepository.findAll();
    }

    public List<Fournisseur> searchFournisseurs(String searchTerm) {
        return fournisseurRepository.search(searchTerm);
    }

    public List<Fournisseur> getAllFournisseursOrderByNom() {
        return fournisseurRepository.findAllByOrderByNomAsc();
    }

    public List<Fournisseur> getAllFournisseursOrderByFrais() {
        return fournisseurRepository.findAllByOrderByFraisAsc();
    }

    public Fournisseur getFournisseurById(Long id) {
        return fournisseurRepository.findById(Math.toIntExact(id)).orElse(null);
    }

    public void saveFournisseur(Fournisseur fournisseur) {
        fournisseurRepository.save(fournisseur);
    }

    public void deleteFournisseur(Long id) {
        fournisseurRepository.deleteById(Math.toIntExact(id));
    }
}
