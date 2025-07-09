package com.gestioncafe.controller.marketing;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gestioncafe.dto.ClientSearchDto;
import com.gestioncafe.dto.FicheClient;
import com.gestioncafe.model.VClientLib;
import com.gestioncafe.service.FicheClientService;
import com.gestioncafe.service.VClientLibService;

@Controller
@RequestMapping("/marketing/clients")
public class ClientController {

    private final VClientLibService vClientLibService;

    private final FicheClientService ficheClientService;

    public ClientController(VClientLibService vClientLibService, FicheClientService ficheClientService) {
        this.vClientLibService = vClientLibService;
        this.ficheClientService = ficheClientService;
    }

    /**
     * Affiche la page principale des clients
     */
    @GetMapping
    public String listClients(Model model) {
        List<VClientLib> clients = vClientLibService.getAllClients();
        model.addAttribute("clients", clients);
        model.addAttribute("searchDto", new ClientSearchDto());

        return "administratif/marketing/clients/list";
    }

    /**
     * Recherche des clients selon les critères
     */
    @PostMapping("/search")
    public String searchClients(@ModelAttribute ClientSearchDto searchDto, Model model) {
        List<VClientLib> clients = vClientLibService.searchClients(searchDto);
        model.addAttribute("clients", clients);
        model.addAttribute("searchDto", searchDto);
        return "administratif/marketing/clients/list";
    }

    /**
     * Affiche la fiche détaillée d'un client
     */
    @GetMapping("/{id}/fiche")
    public String ficheClient(@PathVariable("id") Long clientId, Model model) {
        FicheClient ficheClient = ficheClientService.getFicheClient(clientId);
        model.addAttribute("ficheClient", ficheClient);

        return "administratif/marketing/clients/fiche";
    }

    /**
     * API REST pour récupérer tous les clients (JSON)
     */
    @GetMapping("/api")
    @ResponseBody
    public List<VClientLib> getAllClientsApi() {
        return vClientLibService.getAllClients();
    }

    /**
     * API REST pour rechercher des clients (JSON)
     */
    @PostMapping("/api/search")
    @ResponseBody
    public List<VClientLib> searchClientsApi(@RequestBody ClientSearchDto searchDto) {
        return vClientLibService.searchClients(searchDto);
    }

    /**
     * API REST pour récupérer la fiche d'un client (JSON)
     */
    @GetMapping("/api/{id}/fiche")
    @ResponseBody
    public FicheClient getFicheClientApi(@PathVariable("id") Long clientId) {
        return ficheClientService.getFicheClient(clientId);
    }
}