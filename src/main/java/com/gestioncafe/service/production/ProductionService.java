

package com.gestioncafe.service.production;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gestioncafe.model.DetailRecette;
import com.gestioncafe.model.MatierePremiere;
import com.gestioncafe.model.MouvementStock;
import com.gestioncafe.model.Production;
import com.gestioncafe.model.Produit;
import com.gestioncafe.model.Recette;
import com.gestioncafe.repository.DetailRecetteRepository;
import com.gestioncafe.repository.EmployeRepository;
import com.gestioncafe.repository.MatierePremiereRepository;
import com.gestioncafe.repository.MouvementStockRepository;
import com.gestioncafe.repository.ProductionRepository;
import com.gestioncafe.repository.ProduitRepository;
import com.gestioncafe.repository.RecetteRepository;

@Service
public class ProductionService {

    @Transactional
    public void produireSelonRecette(Long produitId, Long employeId) {
        Produit produit = produitRepository.findById(produitId)
                .orElseThrow(() -> new RuntimeException("Produit non trouvé"));
        List<Recette> recettes = recetteRepository.findByProduitId(produitId.intValue());
        if (recettes.isEmpty()) {
            throw new RuntimeException("Recette non trouvée pour ce produit" );
        }
        Recette recette = recettes.get(0);
        List<DetailRecette> details = detailRecetteRepository.findByRecetteId(recette.getId());

        // Vérification des stocks de matières premières
        for (DetailRecette dr : details) {
            MatierePremiere mp = dr.getMatierePremiere();
            double quantiteNecessaire = dr.getQuantite().doubleValue();
            if (mp.getStock() == null || mp.getStock() < quantiteNecessaire) {
                throw new RuntimeException("Stock insuffisant pour la matière première : " + mp.getNom());
            }
        }
        // Décrémenter les stocks de matières premières et enregistrer les mouvements
        for (DetailRecette dr : details) {
            MatierePremiere mp = dr.getMatierePremiere();
            double quantiteNecessaire = dr.getQuantite().doubleValue();
            mp.setStock(mp.getStock() - quantiteNecessaire);
            matierePremiereRepository.save(mp);
            MouvementStock mvt = new MouvementStock();
            mvt.setMatierePremiere(mp);
            mvt.setDateMouvement(LocalDateTime.now());
            mvt.setQuantite(-quantiteNecessaire);
            mouvementStockRepository.save(mvt);
        }
        // Incrémenter le stock du produit selon la recette
        produit.setStock(produit.getStock().add(recette.getQuantiteProduite()));
        produitRepository.save(produit);
        // Enregistrer la production
        Production production = new Production();
        production.setProduit(produit);
        production.setEmploye(employeRepository.findById(employeId).orElse(null));
        production.setQuantite(recette.getQuantiteProduite());
        production.setDateProduction(LocalDateTime.now());
        productionRepository.save(production);
    }

    public List<Production> getAllProductions() {
        return productionRepository.findAll();
    }
    private final ProduitRepository produitRepository;
    private final RecetteRepository recetteRepository;
    private final DetailRecetteRepository detailRecetteRepository;
    private final MatierePremiereRepository matierePremiereRepository;
    private final MouvementStockRepository mouvementStockRepository;
    private final ProductionRepository productionRepository;
    private final EmployeRepository employeRepository;

    public ProductionService(ProduitRepository produitRepository,
                            RecetteRepository recetteRepository,
                            DetailRecetteRepository detailRecetteRepository,
                            MatierePremiereRepository matierePremiereRepository,
                            MouvementStockRepository mouvementStockRepository,
                            ProductionRepository productionRepository,
                            EmployeRepository employeRepository) {
        this.produitRepository = produitRepository;
        this.recetteRepository = recetteRepository;
        this.detailRecetteRepository = detailRecetteRepository;
        this.matierePremiereRepository = matierePremiereRepository;
        this.mouvementStockRepository = mouvementStockRepository;
        this.productionRepository = productionRepository;
        this.employeRepository = employeRepository;
    }

    @Transactional
    public void produire(Long produitId, Long employeId, BigDecimal quantiteProduite) {
        Produit produit = produitRepository.findById(produitId)
                .orElseThrow(() -> new RuntimeException("Produit non trouvé"));

        // Conversion Long -> Integer pour compatibilité avec RecetteRepository
        List<Recette> recettes = recetteRepository.findByProduitId(produitId.intValue());
        if (recettes.isEmpty()) {
            throw new RuntimeException("Recette non trouvée pour ce produit");
        }
        Recette recette = recettes.get(0);
        List<DetailRecette> details = detailRecetteRepository.findByRecetteId(recette.getId());

        // Vérification des stocks de matières premières
        for (DetailRecette dr : details) {
            MatierePremiere mp = dr.getMatierePremiere();
            double quantiteNecessaire = dr.getQuantite().doubleValue() * quantiteProduite.doubleValue() / recette.getQuantiteProduite().doubleValue();
            if (mp.getStock() == null || mp.getStock() < quantiteNecessaire) {
                throw new RuntimeException("Stock insuffisant pour la matière première : " + mp.getNom());
            }
        }
        // Décrémenter les stocks de matières premières et enregistrer les mouvements
        for (DetailRecette dr : details) {
            MatierePremiere mp = dr.getMatierePremiere();
            double quantiteNecessaire = dr.getQuantite().doubleValue() * quantiteProduite.doubleValue() / recette.getQuantiteProduite().doubleValue();
            mp.setStock(mp.getStock() - quantiteNecessaire);
            matierePremiereRepository.save(mp);
            MouvementStock mvt = new MouvementStock();
            mvt.setMatierePremiere(mp);
            mvt.setDateMouvement(LocalDateTime.now());
            mvt.setQuantite(-quantiteNecessaire);
            mouvementStockRepository.save(mvt);
        }
        // Incrémenter le stock du produit
        produit.setStock(produit.getStock().add(quantiteProduite));
        produitRepository.save(produit);
        // Enregistrer la production
        Production production = new Production();
        production.setProduit(produit);
        production.setEmploye(employeRepository.findById(employeId).orElse(null));
        production.setQuantite(quantiteProduite);
        production.setDateProduction(LocalDateTime.now());
        productionRepository.save(production);
    }
}
