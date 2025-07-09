package com.gestioncafe.service;

import com.gestioncafe.model.Fournisseur;
import com.gestioncafe.repository.FournisseurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class FournisseurService {
    @Autowired
    private FournisseurRepository fournisseurRepository;

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
        return fournisseurRepository.findById(id).orElse(null);
    }
    
    public Fournisseur saveFournisseur(Fournisseur fournisseur) {
        return fournisseurRepository.save(fournisseur);
    }
    
    public void deleteFournisseur(Long id) {
        fournisseurRepository.deleteById(id);
    }
}
