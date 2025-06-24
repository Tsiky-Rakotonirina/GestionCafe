package com.gestioncafe.controller.rh;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.ui.Model;

import com.gestioncafe.service.rh.*;
import com.gestioncafe.repository.*;
import com.gestioncafe.model.*;

@Controller
@RequestMapping("/administratif/rh/salaire")
public class RhSalaireController {
    
    @Autowired
    private RhSalaireService rhSalaireService;

    @GetMapping("/fiche-de-paie")
    public String ficheDePaie(@RequestParam("idEmploye") String idEmploye, Model model) {
        String erreur="";
        try{
            Long id = Long.parseLong(idEmploye);
            if (id <= 0) {
                erreur = "L'ID de l'employé doit être positif.";
            }
            try{
                Employe employe = rhSalaireService.getEmployeById(id);
                if(employe != null){
                    // logique pour recuperer les informations de la fiche de paie
                    // list des fiches de paie
                    // list des pdf de fiche de paie possible a importer
                    model.addAttribute("employe", employe);
                    return "administratif/rh/fiche-de-paie";
                }
            } catch (RuntimeException e) {
                erreur = "Employé non trouvé avec l'ID: " + idEmploye;
            }
            
        } catch (NumberFormatException e) {
            erreur = "L'ID de l'employé doit être un nombre valide.";
        }
        model.addAttribute("erreurFicheDePaie", erreur);
        return "redirect:/administratif/rh/gestion-salaires";
    }

    @GetMapping("/avance")
    public String avance(@RequestParam("idEmploye") String idEmploye, Model model) {
        String erreur="";
        try{
            Long id = Long.parseLong(idEmploye);
            if (id <= 0) {
                erreur = "L'ID de l'employé doit être positif.";
            }
            try{
                Employe employe = rhSalaireService.getEmployeById(id);
                if(employe != null){
                    model.addAttribute("employe", employe);
                    model.addAttribute("avances", rhSalaireService.getAvancesByEmployeId(id));
                    return "administratif/rh/fiche-de-paie";
                }
            } catch (RuntimeException e) {
                erreur = "Employé non trouvé avec l'ID: " + idEmploye;
            }
            
        } catch (NumberFormatException e) {
            erreur = "L'ID de l'employé doit être un nombre valide.";
        }
        model.addAttribute("erreurAvance", erreur);
        return "redirect:/administratif/rh/gestion-salaires";
    }

    @GetMapping("/commission")
    public String commission(@RequestParam("idEmploye") String idEmploye, Model model) {
        String erreur="";
        try{
            Long id = Long.parseLong(idEmploye);
            if (id <= 0) {
                erreur = "L'ID de l'employé doit être positif.";
            }
            try{
                Employe employe = rhSalaireService.getEmployeById(id);
                if(employe != null){
                    model.addAttribute("employe", employe);
                    model.addAttribute("commissions", rhSalaireService.getCommissionsByEmployeId(id));
                    return "administratif/rh/fiche-de-paie";
                }
            } catch (RuntimeException e) {
                erreur = "Employé non trouvé avec l'ID: " + idEmploye;
            }
            
        } catch (NumberFormatException e) {
            erreur = "L'ID de l'employé doit être un nombre valide.";
        }
        model.addAttribute("erreurCommission", erreur);
        return "redirect:/administratif/rh/gestion-salaires";
    }
    
}
