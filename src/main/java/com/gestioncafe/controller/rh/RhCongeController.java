package com.gestioncafe.controller.rh;

import com.gestioncafe.service.rh.RhCongeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Date;

@Controller
@RequestMapping("/administratif/rh/conge")
public class RhCongeController {

    private final RhCongeService rhCongeService;

    public RhCongeController(RhCongeService rhCongeService) {
        this.rhCongeService = rhCongeService;
    }

    @PostMapping("/ajout-conge")
    public String ajoutConge(@RequestParam("typeConge") String typeConge,
                             @RequestParam("dateDebut") String dateDebut,
                             @RequestParam("dateFin") String dateFin,
                             @RequestParam("idEmploye") String idEmploye, Model model) {
        String erreur = "";
        try {
            Long idTypeConge = Long.parseLong(typeConge);
            Date debut = Date.valueOf(dateDebut);
            Date fin = Date.valueOf(dateFin);
            Long id = Long.parseLong(idEmploye);
            rhCongeService.ajoutConge(id, idTypeConge, debut, fin);
        } catch (NumberFormatException e) {
            erreur = "Erreur : les Id doivent etre des valeurs numeriques";
        } catch (IllegalArgumentException e) {
            erreur = "Erreur : date en format invalide";
        } catch (Exception e) {
            erreur = "Erreur dans l ajout conge : " + e.getMessage();
        }
        model.addAttribute("erreurAjoutConge", erreur);

        return "redirect:/administratif/rh/gestion-conges";
    }

    @GetMapping("/calendrier")
    public String calendrier(Model model) {
        model.addAttribute("jours", rhCongeService.jours());
        return "administratif/rh/calendrier";
    }

}
