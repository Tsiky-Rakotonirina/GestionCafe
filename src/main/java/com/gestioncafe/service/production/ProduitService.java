package com.gestioncafe.service.production;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gestioncafe.model.production.Produit;
import com.gestioncafe.repository.production.ProduitRepository;
import com.gestioncafe.repository.production.RecetteRepository;
import com.gestioncafe.repository.production.DetailsVenteRepository;

@Service
public class ProduitService {
    private final ProduitRepository produitRepository;

    @Autowired
    private RecetteRepository recetteRepository;
    @Autowired
    private DetailsVenteRepository detailsVenteRepository;

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

    // Ajout de la méthode manquante pour le calcul du coût de fabrication
    public BigDecimal calculerCoutFabrication(Integer idProduit) {
        // Exemple de logique fictive, à adapter selon votre modèle métier
        Optional<Produit> produitOpt = findById(idProduit);
        if (produitOpt.isPresent()) {
            Produit produit = produitOpt.get();
            // Remplacer par la vraie logique de calcul du coût
            return produit.getStock() != null ? produit.getStock() : BigDecimal.ZERO;
        }
        return BigDecimal.ZERO;
    }

    // Vérifie si le produit est utilisé dans d'autres tables (recette, vente, etc.)
    public boolean isProduitUtilise(Integer idProduit) {
        // Vérifie dans Recette
        boolean utiliseDansRecette = recetteRepository.existsByProduitId(idProduit);
        // Vérifie dans DetailsVente
        boolean utiliseDansVente = detailsVenteRepository.existsByProduitId(idProduit);
        // Ajoutez d'autres vérifications si besoin
        return utiliseDansRecette || utiliseDansVente;
    }
}
