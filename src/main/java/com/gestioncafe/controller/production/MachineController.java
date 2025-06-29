package com.gestioncafe.controller.production;

import com.gestioncafe.model.production.Machine;
import com.gestioncafe.service.production.*;
import com.gestioncafe.repository.production.UniteRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/machines")
public class MachineController {
    private final MachineService machineService;
    private final ProduitService produitService;
    private final UniteRepository uniteRepository;

    public MachineController(MachineService machineService, ProduitService produitService, UniteRepository uniteRepository) {
        this.machineService = machineService;
        this.produitService = produitService;
        this.uniteRepository = uniteRepository;
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
    public String save(@ModelAttribute Machine machine) {
        machineService.save(machine);
        return "redirect:/machines";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Integer id, Model model) {
        machineService.findById(id).ifPresent(m -> model.addAttribute("machine", m));
        return "administratif/production/machine/form";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        machineService.deleteById(id);
        return "redirect:/machines";
    }
}
