package com.gestioncafe.controller.stock;

import com.gestioncafe.model.stock.MatierePremiere;
import com.gestioncafe.repository.stock.UniteRepository;
import com.gestioncafe.service.stock.MatierePremiereService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/matieres")
public class MatierePremiereController {
    private final MatierePremiereService matierePremiereService;
    private final UniteRepository uniteRepository;

    public MatierePremiereController(MatierePremiereService matierePremiereService, UniteRepository uniteRepository) {
        this.matierePremiereService = matierePremiereService;
        this.uniteRepository = uniteRepository;
    }

    @GetMapping
    public String listMatieres(Model model) {
        model.addAttribute("matieres", matierePremiereService.getAllMatieres());
        return "matiere-premiere/list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("matiere", new MatierePremiere());
        model.addAttribute("unites", uniteRepository.findAll());
        return "matiere-premiere/add";
    }

    @PostMapping("/add")
    public String addMatiere(@ModelAttribute MatierePremiere matiere) {
        matierePremiereService.saveMatiere(matiere);
        return "redirect:/matieres";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Integer id, Model model) {
        model.addAttribute("matiere", matierePremiereService.getMatiereById(id));
        model.addAttribute("unites", uniteRepository.findAll());
        return "matiere-premiere/edit";
    }

    @PostMapping("/edit/{id}")
    public String editMatiere(@PathVariable Integer id, @ModelAttribute MatierePremiere matiere) {
        matiere.setId(id);
        matierePremiereService.saveMatiere(matiere);
        return "redirect:/matieres";
    }

    @GetMapping("/delete/{id}")
    public String deleteMatiere(@PathVariable Integer id) {
        matierePremiereService.deleteMatiere(id);
        return "redirect:/matieres";
    }
}