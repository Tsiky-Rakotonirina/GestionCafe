package com.gestioncafe.controller.employe;

import com.gestioncafe.service.*;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/administratif/employe")
public class EmployeController {
    @Autowired 
    private PresenceService presenceService;
    @Autowired
    private CommandeService commandeService;


    public EmployeController(PresenceService presenceService) {
        this.presenceService = presenceService;
    }
    
    @GetMapping("/presence")
    public String presence(Model model) {
        // Formatage de la date
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, dd MMMM yyyy", Locale.FRENCH);
        String dateFormatted = LocalDate.now().format(formatter);
        
        model.addAttribute("titre", "Présence");
        model.addAttribute("dateAujourdhui", dateFormatted);
        model.addAttribute("employes", presenceService.getAllEmployes());
        model.addAttribute("presences", presenceService.getPresencesForToday());
        
        return "administratif/employe/presence";
    }
    
    @PostMapping("/presence/valider")
    public String validerPresence(@RequestParam Long employeId, @RequestParam String password) {
        presenceService.validerPresence(employeId, password);
        return "redirect:/administratif/employe/presence";
    }

    @GetMapping("/commande")
    public String commande(Model model) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, dd MMMM yyyy", Locale.FRENCH);
        String dateFormatted = LocalDate.now().format(formatter);
        
        model.addAttribute("titre", "Commande");
        model.addAttribute("dateAujourdhui", dateFormatted);
        model.addAttribute("recetteDuJour", commandeService.getRecetteDuJour());
        model.addAttribute("commandes", commandeService.getCommandesDuJour());
        
        return "administratif/employe/commande";
    }
    
    @PostMapping("/commande/terminer/{id}")
    public String terminerCommande(@PathVariable Long id) {
        commandeService.marquerCommandeTerminee(id);
        return "redirect:/administratif/employe/commande";
    }

    @GetMapping("/vente")
    public String vente(
        @RequestParam(required = false) Long clientId,
        Model model) {
        
        model.addAttribute("titre", "Nouvelle Commande");
        if (clientId != null) {
            model.addAttribute("clientId", clientId);
        }
        return "administratif/employe/vente";
    }

    @PostMapping("/vente")
    public String validerVente(
        @RequestParam Long clientId,
        @RequestParam Long employeId,
        @RequestParam String produitsJson,
        HttpSession session) {
        
        // Cette méthode sera implémentée dans le service
        commandeService.creerVente(clientId, employeId, produitsJson);
        return "redirect:/administratif/employe/commande";
    }
}