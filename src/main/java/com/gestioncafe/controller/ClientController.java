package com.gestioncafe.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gestioncafe.dto.ClientSearchDto;
import com.gestioncafe.dto.FicheClient;
import com.gestioncafe.model.Client;
import com.gestioncafe.model.VClientLib;
import com.gestioncafe.model.Vente;
import com.gestioncafe.repository.ClientRepository;
import com.gestioncafe.repository.VenteRepository;
import com.gestioncafe.service.FicheClientService;
import com.gestioncafe.service.VClientLibService;

@Controller
public class ClientController {
    // Employé (administratif)
    private final ClientRepository clientRepository;
    private final VenteRepository venteRepository;
    // Marketing
    private final VClientLibService vClientLibService;
    private final FicheClientService ficheClientService;

    public ClientController(ClientRepository clientRepository,
                           VenteRepository venteRepository,
                           VClientLibService vClientLibService,
                           FicheClientService ficheClientService) {
        this.clientRepository = clientRepository;
        this.venteRepository = venteRepository;
        this.vClientLibService = vClientLibService;
        this.ficheClientService = ficheClientService;
    }

    // --- Employé (administratif) ---
    @GetMapping("/administratif/client")
    public String clientPage(Model model) {
        model.addAttribute("titre", "Gestion Clients");
        model.addAttribute("clientRepository", clientRepository);
        return "administratif/employe/client";
    }

    @GetMapping("/administratif/client/search")
    @ResponseBody
    public List<Client> searchClients(@RequestParam String term) {
        return clientRepository.findByNomContainingIgnoreCaseOrPrenomContainingIgnoreCase(term, term);
    }

    @GetMapping("/administratif/client/fiche/{id}")
    public String ficheClient(@PathVariable Long id, Model model) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client non trouvé"));
        List<Vente> ventes = venteRepository.findByClientIdOrderByDateVenteDesc(id);
        model.addAttribute("client", client);
        model.addAttribute("ventes", ventes);
        return "administratif/employe/ficheClient";
    }

    // --- Marketing ---
    @GetMapping("/marketing/clients")
    public String listClients(Model model) {
        List<VClientLib> clients = vClientLibService.getAllClients();
        model.addAttribute("clients", clients);
        model.addAttribute("searchDto", new ClientSearchDto());
        return "administratif/marketing/clients/list";
    }

    @PostMapping("/marketing/clients/search")
    public String searchClients(@ModelAttribute ClientSearchDto searchDto, Model model) {
        List<VClientLib> clients = vClientLibService.searchClients(searchDto);
        model.addAttribute("clients", clients);
        model.addAttribute("searchDto", searchDto);
        return "administratif/marketing/clients/list";
    }

    @GetMapping("/marketing/clients/{id}/fiche")
    public String ficheClientMarketing(@PathVariable("id") Long clientId, Model model) {
        FicheClient ficheClient = ficheClientService.getFicheClient(clientId);
        model.addAttribute("ficheClient", ficheClient);
        return "administratif/marketing/clients/fiche";
    }

    // --- API REST Marketing ---
    @GetMapping("/marketing/clients/api")
    @ResponseBody
    public List<VClientLib> getAllClientsApi() {
        return vClientLibService.getAllClients();
    }

    @PostMapping("/marketing/clients/api/search")
    @ResponseBody
    public List<VClientLib> searchClientsApi(@RequestBody ClientSearchDto searchDto) {
        return vClientLibService.searchClients(searchDto);
    }

    @GetMapping("/marketing/clients/api/{id}/fiche")
    @ResponseBody
    public FicheClient getFicheClientApi(@PathVariable("id") Long clientId) {
        return ficheClientService.getFicheClient(clientId);
    }
}
