package com.gestioncafe.controller.production;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gestioncafe.dto.VentePeriodeStatDTO;
import com.gestioncafe.dto.VenteProduitStatDTO;
import com.gestioncafe.service.production.DetailsVenteService;

@Controller
@RequestMapping("/administratif/production")
public class ProductionController {
    private final DetailsVenteService detailsVenteService;

    public ProductionController(DetailsVenteService detailsVenteService) {
        this.detailsVenteService = detailsVenteService;
    }

    @GetMapping
    public String index() {
        return "administratif/production/stats";
    }

    @GetMapping("/produit")
    public String produit() {
        return "administratif/production/produit";
    }

    @GetMapping("/machine")
    public String machine() {
        return "administratif/production/machine";
    }

    @GetMapping("/mat-premiere")
    public String metPremiere() {
        return "administratif/production/mat-premiere";
    }

    @GetMapping("/stats")
    public String statistiques(Model model) {
        List<VenteProduitStatDTO> stats = detailsVenteService.getVenteStatParProduit();
        model.addAttribute("stats", stats);

        return "administratif/production/stats";
    }

    @GetMapping("/courbe")
    public String courbeStatistique(@RequestParam(defaultValue = "jour") String periode, Model model) {
        List<VentePeriodeStatDTO> courbe = detailsVenteService.getVenteStatParPeriode(periode);
        model.addAttribute("courbe", courbe);
        model.addAttribute("periode", periode);

        return "administratif/production/courbe";
    }

    @GetMapping("/courbe-produit/{produitId}")
    public String courbeProduit(@PathVariable Integer produitId, @RequestParam(defaultValue = "jour") String periode, Model model) {
        List<VentePeriodeStatDTO> courbe = detailsVenteService.getVenteStatParPeriodeEtProduit(periode, produitId);
        model.addAttribute("courbe", courbe);
        model.addAttribute("periode", periode);
        model.addAttribute("produitId", produitId);

        return "administratif/production/courbe-produit";
    }

    @GetMapping("/stats-filtre")
    public String statistiquesFiltre(
        @RequestParam(required = false) LocalDate dateExacte,
        @RequestParam(required = false) Integer annee,
        @RequestParam(required = false) Integer mois,
        @RequestParam(required = false) Integer jourMois,
        @RequestParam(required = false) Integer jourSemaine,
        @RequestParam(required = false) LocalDateTime dateDebut,
        @RequestParam(required = false) LocalDateTime dateFin,
        Model model
    ) {
        List<VenteProduitStatDTO> stats = detailsVenteService.getVenteStatParProduitFiltre(
            dateExacte, annee, mois, jourMois, jourSemaine, dateDebut, dateFin
        );
        model.addAttribute("stats", stats);
        model.addAttribute("annee", annee);
        model.addAttribute("mois", mois);
        model.addAttribute("jourMois", jourMois);
        model.addAttribute("jourSemaine", jourSemaine);
        model.addAttribute("dateDebut", dateDebut);
        model.addAttribute("dateFin", dateFin);

        return "administratif/production/stats";
    }
}
