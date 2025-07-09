package com.gestioncafe.controller.marketing;

import com.gestioncafe.model.Marketing;
import com.gestioncafe.service.MarketingService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/administratif/marketing")
public class MarketingController {

    private final MarketingService marketingService;

    public MarketingController(MarketingService marketingService) {
        this.marketingService = marketingService;
    }

    @GetMapping
    public String index(Model model) {
        return "administratif/marketing/index";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        List<Marketing> marketingList = marketingService.getAllMarketing();
        model.addAttribute("marketingList", marketingList);

        // Moyenne âge
        double averageAge = marketingService.getAverageAge();
        model.addAttribute("averageAge", averageAge);

        // Pourcentage hommes / femmes
        double pourcentageHommes = marketingService.getPourcentageGenre("homme");
        double pourcentageFemmes = marketingService.getPourcentageGenre("femme");
        model.addAttribute("pourcentageHommes", pourcentageHommes);
        model.addAttribute("pourcentageFemmes", pourcentageFemmes);

        // Moyenne budget moyen
        model.addAttribute("averageBudgetMoyen", marketingService.getAverageBudgetMoyen());

        // Moyenne estimation prix produit
        model.addAttribute("averageEstimationPrixProduit", marketingService.getAverageEstimationPrixProduit());

        // Lieux et leur nombre d’occurrences (Map)
        model.addAttribute("lieuxCounts", marketingService.getLieuxWithCounts());

        // Produits et leur nombre d’occurrences (Map)
        model.addAttribute("produitsCounts", marketingService.getProduitsWithCounts());

        return "administratif/marketing/dashboard";
    }

}
