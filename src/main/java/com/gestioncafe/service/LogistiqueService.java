// LogistiqueService.java
package com.gestioncafe.service;

import com.gestioncafe.model.MatierePremiere;
import com.gestioncafe.model.Produit;
import com.gestioncafe.repository.MatierePremiereRepository;
import com.gestioncafe.repository.ProduitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class LogistiqueService {
    private final MatierePremiereRepository matierePremiereRepository;
    private final ProduitRepository produitRepository;

    @Autowired
    public LogistiqueService(MatierePremiereRepository matierePremiereRepository, ProduitRepository produitRepository) {
        this.matierePremiereRepository = matierePremiereRepository;
        this.produitRepository = produitRepository;
    }

    
    public List<Produit> getAllProduits() {
        return produitRepository.findAllByOrderByNomAsc();
    }

    public List<MatierePremiere> getAllMatierePremiere() {
        return matierePremiereRepository.findAllByOrderByNomAsc();
    }

    public List<MatierePremiere> searchMatierePremiere(String nom) {
        return matierePremiereRepository.findByNomContainingIgnoreCase(nom);
    }

    public List<Produit> searchProduit(String nom) {
        return produitRepository.findByNomContainingIgnoreCase(nom);
    }

    public int getTotalValeurMatierePremiere() {
        return matierePremiereRepository.getTotalValeurStockMatierePremiere();
    }

    public BigDecimal getTauxRuptureStockMatierePremiere() {
        long total = matierePremiereRepository.count();
        long enRupture = matierePremiereRepository.countMatierePremiereEnRupture();
        return BigDecimal.valueOf(enRupture).divide(BigDecimal.valueOf(total), 2, BigDecimal.ROUND_HALF_UP);
    }

    public BigDecimal getTotalValeurProduits() {
        return produitRepository.getTotalValeurProduits();
    }
}