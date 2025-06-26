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
                    // prochain fiche de paie
                    // list des payements
                    model.addAttribute("employe", employe);
                    // return "administratif/rh/fiche-de-paie";
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
            try{
                Employe employe = rhSalaireService.getEmployeById(id);
                if(employe != null){
                    model.addAttribute("prochainSalaire", rhSalaireService.prochainSalaire(id));
                    model.addAttribute("retenuPourAvance", rhSalaireService.retenuPourAvance(id));
                    model.addAttribute("employAvances", rhSalaireService.getAllRaisonAvances());
                    model.addAttribute("avances", rhSalaireService.getAvancesByEmployeId(id));
                    return "administratif/rh/avance";
                }
            } catch (RuntimeException e) {
                erreur = "Employé non trouvé avec l'ID: " + idEmploye;
            } 
        }catch (NumberFormatException e) {
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
            try{
                Employe employe = rhSalaireService.getEmployeById(id);
                if(employe != null){
                    model.addAttribute("employe", employe);
                    model.addAttribute("raisonCommissions", rhSalaireService.getAllRaisonCommissions());
                    model.addAttribute("commissions", rhSalaireService.getCommissionsByEmployeId(id));
                    return "administratif/rh/commission";
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

    @PostMapping("/ajout-commission")
    public String ajoutCommission(@RequestParam("idEmploye") String idEmploye, @RequestParam("montant") String montant, @RequestParam("idRaison") String idRaison, Model model) {
        String erreur = "";
        try {
            double montantDouble = Double.parseDouble(montant);    
            try{
                Long raison = Long.parseLong(idRaison);
                Long id = Long.parseLong(idEmploye); 
                try{
                    rhSalaireService.ajoutCommission(id, raison, montantDouble);
                    model.addAttribute("succesAjoutCommission", "Commission de "+montant+"descerne a l'employe");
                } catch(Exception e) {
                    erreur = "Erreur dans l'ajout : "+ e.getMessage();
                }
            } catch(NumberFormatException e) {
                erreur = "Erreur dans l'ajout : L'employe et la raison doivent être valide.";
            }
        } catch(NumberFormatException e) {
            erreur = "Erreur dans l'ajout :  Le montant doit être un nombre valide.";
        }
        System.out.println(erreur);
        model.addAttribute("erreurAjoutCommission", erreur);
        return "redirect:/administratif/rh/salaire/commission?idEmploye="+idEmploye;
    }

    @PostMapping("/ajout-avance")
    public String ajoutAvance(@RequestParam("idEmploye") String idEmploye, @RequestParam("montant") String montant, @RequestParam("idRaison") String idRaison, Model model) {
        String erreur = "";
        try {
            double montantDouble = Double.parseDouble(montant);    
            try{
                Long raison = Long.parseLong(idRaison);
                Long id = Long.parseLong(idEmploye); 
                try{
                    rhSalaireService.ajoutAvance(id, raison, montantDouble);
                    model.addAttribute("succesAjoutAvance", "Avance de "+montant+"descerne a l'employe");
                } catch(Exception e) {
                    erreur = "Erreur dans l'ajout : "+ e.getMessage();
                }
            } catch(NumberFormatException e) {
                erreur = "Erreur dans l'ajout : L'employe et la raison doivent être valide.";
            }
        } catch(NumberFormatException e) {
            erreur = "Erreur dans l'ajout :  Le montant doit être un nombre valide.";
        }
        System.out.println(erreur);
        model.addAttribute("erreurAjoutAvance", erreur);
        return "redirect:/administratif/rh/salaire/avance?idEmploye="+idEmploye;
    }

    @PostMapping("/payer")
    public String payer(@RequestParam("idEmploye") String idEmploye, Model model) {
        return "/administratif/rh/salaire/fiche-de-paie(idEmploye="+idEmploye+"})";
    }
}
