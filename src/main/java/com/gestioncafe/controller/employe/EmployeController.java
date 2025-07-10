package com.gestioncafe.controller.employe;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gestioncafe.model.Employe;
import com.gestioncafe.model.Production;
import com.gestioncafe.model.Produit;
import com.gestioncafe.repository.ClientRepository;
import com.gestioncafe.repository.EmployeRepository;
import com.gestioncafe.service.CommandeService;
import com.gestioncafe.service.PresenceService;
import com.gestioncafe.service.production.ProductionService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/administratif/employe")
public class EmployeController {
    private final PresenceService presenceService;
    private final CommandeService commandeService;
    private final ClientRepository clientRepository;
    private final EmployeRepository employeRepository;
    private final ProductionService productionService;


    public EmployeController(PresenceService presenceService,
                             CommandeService commandeService,
                             ClientRepository clientRepository,
                             EmployeRepository employeRepository,
                             ProductionService productionService) {
        this.presenceService = presenceService;
        this.commandeService = commandeService;
        this.clientRepository = clientRepository;
        this.employeRepository = employeRepository;
        this.productionService = productionService;
    }
    @GetMapping("/production")
    public String production(Model model, @RequestParam(value = "success", required = false) String success, @RequestParam(value = "error", required = false) String error) {
        List<Produit> produits = commandeService.getAllProduits();
        List<Employe> employes = employeRepository.findAll();
        List<Production> productions = productionService.getAllProductions();
        model.addAttribute("produits", produits);
        model.addAttribute("employes", employes);
        model.addAttribute("productions", productions);
        if (success != null) model.addAttribute("success", success);
        if (error != null) model.addAttribute("error", error);
        return "administratif/employe/production";
    }

    @PostMapping("/production")
    public String produire(@RequestParam Long produitId, @RequestParam Long employeId, @RequestParam BigDecimal quantiteProduite, Model model) {
        try {
            productionService.produire(produitId, employeId, quantiteProduite);
            return "redirect:/administratif/employe/production?success=Production enregistrée";
        } catch (RuntimeException e) {
            return "redirect:/administratif/employe/production?error=" + e.getMessage();
        }
    }

    // @GetMapping("/presence")
    // public String presence(Model model) {
    //     // Ancienne gestion de la présence, à désactiver car doublon avec PresenceController
    //     return "administratif/employe/presence";
    // }

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
    public String terminerCommande(@PathVariable Long id, Model model) {
        try {
            commandeService.marquerCommandeTerminee(id);
            return "redirect:/administratif/employe/commande";
        } catch (RuntimeException e) {
            // On recharge la page commande avec un message d'erreur
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, dd MMMM yyyy", Locale.FRENCH);
            String dateFormatted = LocalDate.now().format(formatter);
            model.addAttribute("titre", "Commande");
            model.addAttribute("dateAujourdhui", dateFormatted);
            model.addAttribute("recetteDuJour", commandeService.getRecetteDuJour());
            model.addAttribute("commandes", commandeService.getCommandesDuJour());
            model.addAttribute("erreurStock", e.getMessage());
            return "administratif/employe/commande";
        }
    }

    @GetMapping("/vente")
    public String vente(
        @RequestParam(required = false) Long clientId,
        Model model) {

        List<Produit> produits = commandeService.getAllProduits();
        // Ajouter la liste au modèle
        model.addAttribute("produits", produits);
        model.addAttribute("commandeService", commandeService);
        model.addAttribute("clientRepository", clientRepository);
        model.addAttribute("employeRepository", employeRepository);
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