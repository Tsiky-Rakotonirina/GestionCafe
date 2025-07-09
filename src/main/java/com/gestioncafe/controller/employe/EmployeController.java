package com.gestioncafe.controller.employe;

import com.gestioncafe.model.Produit;
import com.gestioncafe.repository.ClientRepository;
import com.gestioncafe.repository.EmployeRepository;
import com.gestioncafe.service.CommandeService;
import com.gestioncafe.service.PresenceService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

@Controller
@RequestMapping("/administratif/employe")
public class EmployeController {
    private final PresenceService presenceService;
    private final CommandeService commandeService;
    private final ClientRepository clientRepository;
    private final EmployeRepository employeRepository;


    public EmployeController(PresenceService presenceService,
                             CommandeService commandeService,
                             ClientRepository clientRepository,
                             EmployeRepository employeRepository) {
        this.presenceService = presenceService;
        this.commandeService = commandeService;
        this.clientRepository = clientRepository;
        this.employeRepository = employeRepository;
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