// src/main/java/com/gestioncafe/controller/employe/ClientController.java
package com.gestioncafe.controller.employe;

import com.gestioncafe.model.Client;
import com.gestioncafe.model.Vente;
import com.gestioncafe.repository.ClientRepository;
import com.gestioncafe.repository.VenteRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List; 

@Controller
@RequestMapping("/administratif/client")
public class ClientController {
    private final ClientRepository clientRepository;
    private final VenteRepository venteRepository;

    public ClientController(ClientRepository clientRepository, VenteRepository venteRepository) {
        this.clientRepository = clientRepository;
        this.venteRepository = venteRepository;
    }

    @GetMapping
    public String clientPage(Model model) {
        model.addAttribute("titre", "Gestion Clients");
        return "employe/client";
    }

    @GetMapping("/search")
    @ResponseBody
    public List<Client> searchClients(@RequestParam String term) {
        return clientRepository.findByNomContainingIgnoreCaseOrPrenomContainingIgnoreCase(term, term);
    }

    @GetMapping("/fiche/{id}")
    public String ficheClient(@PathVariable Long id, Model model) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client non trouvé"));
        List<Vente> ventes = venteRepository.findByClientIdOrderByDateVenteDesc(id);
        
        model.addAttribute("client", client);
        model.addAttribute("ventes", ventes);
        return "employe/ficheClient";
    }
}