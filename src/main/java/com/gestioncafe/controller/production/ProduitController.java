package com.gestioncafe.controller.production;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gestioncafe.model.production.Produit;
import com.gestioncafe.service.production.ProduitService;

@Controller
@RequestMapping("/produits")
public class ProduitController {
    private final ProduitService produitService;

    public ProduitController(ProduitService produitService) {
        this.produitService = produitService;
    }

    @GetMapping
    public String listProduits(Model model) {
        model.addAttribute("produits", produitService.findAll());
        return "administratif/production/produit/list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("produit", new Produit());
        // ...ajoute ici les listes nécessaires pour le formulaire si besoin...
        return "administratif/production/produit/form";
    }

    @PostMapping("/save")
    public String saveProduit(
            @ModelAttribute Produit produit,
            @RequestParam String modePrix,
            @RequestParam(required = false) BigDecimal coefficient,
            @RequestParam(required = false) BigDecimal prixVenteManuel
    ) {
        if ("coefficient".equals(modePrix)) {
            BigDecimal cout = BigDecimal.ZERO;
            if (produit.getId() != null) {
                cout = produitService.calculerCoutFabrication(produit.getId());
            }
            if (coefficient != null) {
                produit.setPrixVente(cout.multiply(coefficient));
            }
        } else {
            if (prixVenteManuel != null) {
                produit.setPrixVente(prixVenteManuel);
            }
        }
        produitService.save(produit);
        return "redirect:/produits";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Integer id, Model model) {
        Optional<Produit> produitOpt = produitService.findById(id);
        if (produitOpt.isPresent()) {
            model.addAttribute("produit", produitOpt.get());
            // ...ajoute ici les listes nécessaires pour le formulaire si besoin...
            return "administratif/production/produit/form";
        } else {
            return "redirect:/produits";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteProduit(@PathVariable Integer id) {
        produitService.deleteById(id);
        return "redirect:/produits";
    }

    @GetMapping("/fiche/{id}")
    public String ficheTechnique(@PathVariable Integer id, Model model) {
        Optional<Produit> produitOpt = produitService.findById(id);
        if (produitOpt.isPresent()) {
            Produit produit = produitOpt.get();
            model.addAttribute("produit", produit);
            // Ajoute d'autres attributs nécessaires à la fiche technique si besoin
            // Exemple : coût de fabrication
            BigDecimal coutFabrication = produitService.calculerCoutFabrication(id);
            model.addAttribute("coutFabrication", coutFabrication);
            // Ajoute d'autres informations selon le besoin métier
            return "produit/fiche";
        } else {
            // Redirige vers la liste si le produit n'existe pas
            return "redirect:/produits";
        }
    }
}