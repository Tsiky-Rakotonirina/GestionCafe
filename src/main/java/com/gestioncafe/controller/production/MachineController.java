package com.gestioncafe.controller.production;

import com.gestioncafe.model.production.Machine;
import com.gestioncafe.model.production.*;
import com.gestioncafe.service.production.*;
import com.gestioncafe.repository.production.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/machines")
public class MachineController {
    private MachineService machineService;
    private ProduitService produitService;
    private UniteRepository uniteRepository;
    private UtilisationMachineRepository utilisationMachineRepository;

    public MachineController(MachineService machineService, ProduitService produitService, UniteRepository uniteRepository, UtilisationMachineRepository utilisationMachineRepository) {
        this.machineService = machineService;
        this.produitService = produitService;
        this.uniteRepository = uniteRepository;
        this.utilisationMachineRepository = utilisationMachineRepository;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("machines", machineService.findAll());
        model.addAttribute("produits", produitService.findAll());

        return "administratif/production/machine/list";

    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("machine", new Machine());
        model.addAttribute("produits", produitService.findAll());
        model.addAttribute("unites", uniteRepository.findAll());
        return "administratif/production/machine/form";
    }

    @PostMapping("/save")
    public String save(
        @ModelAttribute Machine machine,
        @RequestParam(value = "utilisations.idProduit", required = false) List<Integer> idProduits,
        @RequestParam(value = "utilisations.duree", required = false) List<Double> durees,
        @RequestParam(value = "utilisations.idUnite", required = false) List<Integer> idUnites,
        @RequestParam(value = "deleteUtilisationId", required = false) Integer deleteUtilisationId
    ) {
        Machine savedMachine = machineService.save(machine);

        System.out.println("idProduits = " + idProduits);
        System.out.println("durees = " + durees);
        System.out.println("idUnites = " + idUnites);

        if (idProduits != null && durees != null && idUnites != null) {
            for (int i = 0; i < idProduits.size(); i++) {
                UtilisationMachine utilisation = new UtilisationMachine();
                utilisation.setMachine(savedMachine);

                Produit produit = new Produit();
                produit.setId(idProduits.get(i));
                utilisation.setProduit(produit);

                utilisation.setDuree(durees.get(i));

                Unite unite = new Unite();
                unite.setId(idUnites.get(i));
                utilisation.setUnite(unite);

                utilisationMachineRepository.save(utilisation);
            }
        }

        if (deleteUtilisationId != null) {
            utilisationMachineRepository.deleteById(deleteUtilisationId);
            // Recharge la page d'édition
            return "redirect:/machines/edit/" + machine.getId();
        }

        return "redirect:/machines";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Integer id, Model model) {
        Machine machine = machineService.findById(id).orElseThrow();
        model.addAttribute("machine", machine);
        model.addAttribute("produits", produitService.findAll());
        model.addAttribute("unites", uniteRepository.findAll());
        // Charge les utilisations existantes de la machine
        List<UtilisationMachine> utilisations = utilisationMachineRepository.findByMachineId(id);
        model.addAttribute("utilisationsExistantes", utilisations);
        return "administratif/production/machine/form";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        machineService.deleteById(id);
        return "redirect:/machines";
    }
}
