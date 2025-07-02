package com.gestioncafe.controller.production;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gestioncafe.dto.IngredientFormDTO;
import com.gestioncafe.dto.IngredientsFormWrapper;
import com.gestioncafe.model.production.DetailRecette;
import com.gestioncafe.model.production.MatierePremiere;
import com.gestioncafe.model.production.PrixVenteProduit;
import com.gestioncafe.model.production.Produit;
import com.gestioncafe.model.production.Recette;
import com.gestioncafe.model.production.Unite;
import com.gestioncafe.service.production.DetailRecetteService;
import com.gestioncafe.service.production.MatierePremiereService;
import com.gestioncafe.service.production.PackageProduitService;
import com.gestioncafe.service.production.PrixVenteProduitService;
import com.gestioncafe.service.production.ProduitService;
import com.gestioncafe.service.production.RecetteService;
import com.gestioncafe.service.production.UniteService;

@Controller
@RequestMapping("/produits")
public class ProduitController {
    private final ProduitService produitService;
    private final UniteService uniteService;
    private final PackageProduitService packageProduitService;
    private final RecetteService recetteService;
    private final DetailRecetteService detailRecetteService;
    private final MatierePremiereService matierePremiereService;
    private final PrixVenteProduitService prixVenteProduitService;

    public ProduitController(ProduitService produitService, UniteService uniteService, PackageProduitService packageProduitService, RecetteService recetteService, DetailRecetteService detailRecetteService, MatierePremiereService matierePremiereService, PrixVenteProduitService prixVenteProduitService) {
        this.produitService = produitService;
        this.uniteService = uniteService;
        this.packageProduitService = packageProduitService;
        this.recetteService = recetteService;
        this.detailRecetteService = detailRecetteService;
        this.matierePremiereService = matierePremiereService;
        this.prixVenteProduitService = prixVenteProduitService;
    }

    @GetMapping
    public String listProduits(Model model) {
        var produits = produitService.findAll();
        Map<Integer, BigDecimal> prixVenteMap = new HashMap<>();
        for (Produit produit : produits) {
            PrixVenteProduit prix = prixVenteProduitService.findLastByProduitId(produit.getId());
            prixVenteMap.put(produit.getId(), prix != null ? prix.getPrixVente() : BigDecimal.ZERO);
        }
        model.addAttribute("produits", produits);
        model.addAttribute("prixVenteMap", prixVenteMap);
        return "administratif/production/produit/list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("produit", new Produit());
        model.addAttribute("unites", uniteService.findAll());
        model.addAttribute("packages", packageProduitService.findAll());
        model.addAttribute("matieresPremieres", matierePremiereService.findAll());
        model.addAttribute("ingredientsWrapper", new IngredientsFormWrapper());

        return "administratif/production/produit/form";
    }

    @PostMapping("/save")
    public String saveProduit(
        @ModelAttribute Produit produit,
        @RequestParam String modePrix,
        @RequestParam(required = false) BigDecimal coefficient,
        @RequestParam(required = false) BigDecimal prixVenteManuel,
        @ModelAttribute("ingredientsWrapper") IngredientsFormWrapper ingredientsWrapper,
        @RequestParam BigDecimal quantiteProduiteRecette,
        @RequestParam BigDecimal tempsFabricationRecette
    ) {
        // Debug amélioré : log des paramètres reçus pour les ingrédients
        System.out.println("=== DEBUG INGREDIENTS ===");
        if (ingredientsWrapper != null) {
            System.out.println("Wrapper exists");
            if (ingredientsWrapper.getIngredients() != null) {
                System.out.println("Ingredients size: " + ingredientsWrapper.getIngredients().size());
                int idx = 0;
                for (IngredientFormDTO ing : ingredientsWrapper.getIngredients()) {
                    System.out.println("Ingredient [" + idx + "] : idMatierePremiere=" + ing.getIdMatierePremiere()
                        + ", quantite=" + ing.getQuantite()
                        + ", idUnite=" + ing.getIdUnite());
                    idx++;
                }
            } else {
                System.out.println("Ingredients list is null");
            }
        } else {
            System.out.println("Wrapper is null");
        }
        // Log supplémentaire : dump des paramètres bruts reçus (optionnel)
        // Peut être utile si le binding ne fonctionne pas
        // javax.servlet.http.HttpServletRequest req
        // Enumeration<String> paramNames = req.getParameterNames();
        // while (paramNames.hasMoreElements()) {
        //     String param = paramNames.nextElement();
        //     System.out.println("Param: " + param + " = " + req.getParameter(param));
        // }
        // 1. Sauvegarde du produit
        Produit savedProduit = produitService.save(produit);

        // 2. Création et sauvegarde de la recette associée avec les données du formulaire
        Recette recette = new Recette();
        recette.setProduit(savedProduit);
        recette.setQuantiteProduite(quantiteProduiteRecette);
        recette.setTempsFabrication(tempsFabricationRecette);
        Recette savedRecette = recetteService.save(recette);

        // 3. Ajout des ingrédients (detail_recette) avec la bonne recette
        if (ingredientsWrapper != null && ingredientsWrapper.getIngredients() != null) {
            for (IngredientFormDTO ing : ingredientsWrapper.getIngredients()) {
                try {
                    if (ing.getIdMatierePremiere() != null && ing.getIdUnite() != null && ing.getQuantite() != null) {
                        MatierePremiere mp = matierePremiereService.findById(ing.getIdMatierePremiere()).orElse(null);
                        Unite unite = uniteService.findById(ing.getIdUnite()).orElse(null);
                        if (mp != null && unite != null) {
                            DetailRecette detail = new DetailRecette();
                            detail.setRecette(savedRecette);
                            detail.setMatierePremiere(mp);
                            detail.setUnite(unite);
                            detail.setQuantite(BigDecimal.valueOf(ing.getQuantite()));
                            detailRecetteService.save(detail);
                        }
                    }
                } catch (Exception e) {
                    // Log ou gestion d'erreur si besoin
                }
            }
        }

        // 4. Calcul et insertion du prix de vente dans prix_vente_produit
        BigDecimal prixVente = produitService.calculerPrixVente(
            savedProduit.getId(), modePrix, coefficient, prixVenteManuel
        );
        PrixVenteProduit pvp = new PrixVenteProduit();
        pvp.setProduit(savedProduit);
        pvp.setPrixVente(prixVente);
        pvp.setDateApplication(LocalDate.now());
        prixVenteProduitService.save(pvp);

        return "redirect:/produits";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Integer id, Model model) {
        Optional<Produit> produitOpt = produitService.findById(id);
        if (produitOpt.isPresent()) {
            model.addAttribute("produit", produitOpt.get());
            model.addAttribute("unites", uniteService.findAll());
            model.addAttribute("packages", packageProduitService.findAll());
            model.addAttribute("matieresPremieres", matierePremiereService.findAll());

            return "administratif/production/produit/form";
        } else {
            return "redirect:/produits";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteProduit(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        // Vérifie si le produit est utilisé ailleurs
        if (produitService.isProduitUtilise(id)) {
            redirectAttributes.addFlashAttribute("deleteError", "Impossible de supprimer ce produit car il est utilisé dans d'autres opérations (recettes, ventes, etc.).");
            return "redirect:/produits";
        }
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