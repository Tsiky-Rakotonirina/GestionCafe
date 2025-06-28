// LogistiqueController.java
package com.gestioncafe.controller.stock;

import com.gestioncafe.model.MatierePremiere;
import com.gestioncafe.model.Produit;
import com.gestioncafe.service.LogistiqueService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;


@Controller
@RequestMapping("/administratif/logistique")
public class LogistiqueController {
    private final LogistiqueService logistiqueService;

    @Autowired
    public LogistiqueController(LogistiqueService logistiqueService) {
        this.logistiqueService = logistiqueService;
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
            
        BigDecimal totalValeurProduits = logistiqueService.getTotalValeurProduits();
        
        model.addAttribute("matieresPremieres", matieresPremieres);
        model.addAttribute("totalValeurMatiere", totalValeurMatiere);
        model.addAttribute("tauxRupture", tauxRupture);
        model.addAttribute("produits", produits);
        model.addAttribute("totalValeurProduits", totalValeurProduits);
        model.addAttribute("searchMatiere", searchMatiere);
        model.addAttribute("searchProduit", searchProduit);
        
        return "administratif/stock/logistique";
    }
}