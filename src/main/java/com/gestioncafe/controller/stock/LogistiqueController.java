// LogistiqueController.java
package com.gestioncafe.controller.stock;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gestioncafe.dto.ProduitLogistiqueDTO;
import com.gestioncafe.model.DetailRecette;
import com.gestioncafe.model.HistoriqueEstimation;
import com.gestioncafe.model.MatierePremiere;
import com.gestioncafe.model.PrixVenteProduit;
import com.gestioncafe.model.Produit;
import com.gestioncafe.model.Recette;
import com.gestioncafe.service.LogistiqueService;
import com.gestioncafe.service.production.DetailRecetteService;
import com.gestioncafe.service.production.HistoriqueEstimationService;
import com.gestioncafe.service.production.PrixVenteProduitService;
import com.gestioncafe.service.production.RecetteService;


@Controller
@RequestMapping("/administratif/logistique")
public class LogistiqueController {
    private final LogistiqueService logistiqueService;
    private final RecetteService recetteService;
    private final DetailRecetteService detailRecetteService;
    private final HistoriqueEstimationService historiqueEstimationService;
    private final PrixVenteProduitService prixVenteProduitService;

    @Autowired
    public LogistiqueController(LogistiqueService logistiqueService,
                                RecetteService recetteService,
                                DetailRecetteService detailRecetteService,
                                HistoriqueEstimationService historiqueEstimationService,
                                PrixVenteProduitService prixVenteProduitService) {
        this.logistiqueService = logistiqueService;
        this.recetteService = recetteService;
        this.detailRecetteService = detailRecetteService;
        this.historiqueEstimationService = historiqueEstimationService;
        this.prixVenteProduitService = prixVenteProduitService;
    }

    @GetMapping
    public String showLogistiquePage(
            @RequestParam(value = "searchMatiere", required = false) String searchMatiere,
            @RequestParam(value = "searchProduit", required = false) String searchProduit,
            Model model) {
        
        // Inventaire Matière Première
        List<MatierePremiere> matieresPremieres = searchMatiere != null ? 
            logistiqueService.searchMatierePremiere(searchMatiere) : 
            logistiqueService.getAllMatierePremiere();
            
        
        int totalValeurMatiere = logistiqueService.getTotalValeurMatierePremiere();
        BigDecimal tauxRupture = logistiqueService.getTauxRuptureStockMatierePremiere();
        
        // Inventaire Produit Fini
        List<Produit> produits = searchProduit != null ?
            logistiqueService.searchProduit(searchProduit) :
            logistiqueService.getAllProduits();

        // Construction de la liste des DTO pour l'affichage logistique
        List<ProduitLogistiqueDTO> produitsLogistique = new ArrayList<>();
        for (Produit produit : produits) {
            // Calcul du coût de revient
            BigDecimal coutRevient = BigDecimal.ZERO;
            List<Recette> recettes = recetteService.findByProduitId(produit.getId().intValue());
            if (recettes != null && !recettes.isEmpty()) {
                Recette recette = recettes.get(0);
                List<DetailRecette> details = detailRecetteService.findByRecetteId(recette.getId());
                for (DetailRecette detail : details) {
                    List<HistoriqueEstimation> historiques = historiqueEstimationService.findByMatierePremiere(detail.getMatierePremiere());
                    HistoriqueEstimation estimationRecente = historiques.stream()
                        .filter(h -> h.getPrix() != null)
                        .max(Comparator.comparing(HistoriqueEstimation::getDateApplication))
                        .orElse(null);
                    if (estimationRecente != null && estimationRecente.getPrix() != null) {
                        BigDecimal quantiteNorme = detail.getQuantite();
                        if (detail.getUnite() != null && detail.getUnite().getValeurParNorme() != null) {
                            quantiteNorme = quantiteNorme.multiply(detail.getUnite().getValeurParNorme());
                        }
                        BigDecimal prixEstime = BigDecimal.valueOf(estimationRecente.getPrix());
                        BigDecimal valeurParNormeEstimation = estimationRecente.getUnite() != null && estimationRecente.getUnite().getValeurParNorme() != null ? estimationRecente.getUnite().getValeurParNorme() : BigDecimal.ONE;
                        BigDecimal prixParNorme = prixEstime.divide(valeurParNormeEstimation, 6, java.math.RoundingMode.HALF_UP);
                        coutRevient = coutRevient.add(quantiteNorme.multiply(prixParNorme));
                    }
                }
            }

            // Récupérer le dernier prix de vente
            PrixVenteProduit prixVente = prixVenteProduitService.findLastByProduitId(produit.getId().intValue());
            BigDecimal prixVenteValue = prixVente != null && prixVente.getPrixVente() != null ? prixVente.getPrixVente() : BigDecimal.ZERO;

            // Calcul du bénéfice
            BigDecimal benefice = prixVenteValue.subtract(coutRevient);

            ProduitLogistiqueDTO dto = new ProduitLogistiqueDTO();
            dto.setNom(produit.getNom());
            dto.setStock(produit.getStock());
            dto.setCoutRevient(coutRevient);
            dto.setPrixVente(prixVenteValue);
            dto.setBenefice(benefice);
            // Les champs suivants sont mis à null car absents du modèle Produit
            dto.setDateProduction(null);
            dto.setDatePeremption(null);
            dto.setStockTotal(null);
            produitsLogistique.add(dto);
        }

        BigDecimal totalValeurProduits = logistiqueService.getTotalValeurProduits();

        model.addAttribute("matieresPremieres", matieresPremieres);
        model.addAttribute("totalValeurMatiere", totalValeurMatiere);
        model.addAttribute("tauxRupture", tauxRupture);
        model.addAttribute("produitsLogistique", produitsLogistique);
        model.addAttribute("totalValeurProduits", totalValeurProduits);
        model.addAttribute("searchMatiere", searchMatiere);
        model.addAttribute("searchProduit", searchProduit);

        return "administratif/stock/logistique";
    }
}