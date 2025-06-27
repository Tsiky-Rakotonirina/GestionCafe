package com.gestioncafe.controller.rh;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

import com.gestioncafe.service.rh.*;
import com.gestioncafe.repository.*;
import com.gestioncafe.model.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/administratif/rh")
public class RhController {

    @Autowired
    private RhService rhService;

    @GetMapping
    public String accueil() {
        return "redirect:/administratif/rh/gestion-employes";
    }

    @GetMapping("/gestion-employes")
    public String gestiontEmployes() {
        return "administratif/rh/gestion-employes";
    }

    @GetMapping("/gestion-salaires")
    public String gestionSalaires(Model model) {
        List<StatutEmploye> statutEmployes = rhService.getAllEmployesActifs();
        List<Employe> employes = statutEmployes.stream()
            .map(StatutEmploye::getEmploye)
            .collect(Collectors.toList());
        model.addAttribute("variationSalaireNet", rhService.variationSalaireNet());
        model.addAttribute("variationCommission", rhService.variationCommission());
        model.addAttribute("variationAvance", rhService.variationAvance());
        model.addAttribute("employes", employes);
        return "administratif/rh/gestion-salaires";
    }

    @GetMapping("/gestion-recrutements")
    public String gestionRecrutements() {
        return "administratif/rh/gestion-recrutements";
    }

    @GetMapping("/gestion-conges")
    public String gestionConges() {
        return "administratif/rh/gestion-conges";
    }

    @GetMapping("/parametre")
    public String parametre() {
        return "administratif/rh/parametre";
    }
}
