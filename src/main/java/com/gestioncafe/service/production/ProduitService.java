package com.gestioncafe.service.production;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.gestioncafe.model.production.Produit;
import com.gestioncafe.repository.production.ProduitRepository;

@Service
public class ProduitService {
    private final ProduitRepository produitRepository;

    public ProduitService(ProduitRepository produitRepository) {
        this.produitRepository = produitRepository;
    }

    public List<Produit> findAll() {
        return produitRepository.findAll();
    }

    public Optional<Produit> findById(Integer id) {
        return produitRepository.findById(id);
    }

    public Produit save(Produit produit) {
        return produitRepository.save(produit);
    }

    public void deleteById(Integer id) {
        produitRepository.deleteById(id);
    }
}
