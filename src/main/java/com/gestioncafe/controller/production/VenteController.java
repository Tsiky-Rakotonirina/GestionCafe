package com.gestioncafe.controller.production;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gestioncafe.dto.VenteProduitStatDTO;
import com.gestioncafe.service.production.DetailsVenteService;

@Controller
@RequestMapping("/administratif/vente")
public class VenteController {
    private final DetailsVenteService detailsVenteService;

    public VenteController(DetailsVenteService detailsVenteService) {
        this.detailsVenteService = detailsVenteService;
    }

    @GetMapping
    public String index() {
        return "administratif/vente/index";
    }

    @GetMapping("/statistiques")
    public String statistiques(Model model) {
        List<VenteProduitStatDTO> stats = detailsVenteService.getVenteStatParProduit();
        model.addAttribute("stats", stats);

        return "administratif/vente/statistiques";
    }
}
