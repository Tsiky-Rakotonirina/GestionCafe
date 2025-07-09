package com.gestioncafe.controller.stock;

import com.gestioncafe.model.DetailFournisseur;
import com.gestioncafe.model.Fournisseur;
import com.gestioncafe.service.FournisseurService;
import com.gestioncafe.service.production.DetailFournisseurService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/administratif/stock/fournisseurs")
public class FournisseurController {
    private final FournisseurService fournisseurService;

    private final DetailFournisseurService detailFournisseurService;

    public FournisseurController(FournisseurService fournisseurService, DetailFournisseurService detailFournisseurService) {
        this.fournisseurService = fournisseurService;
        this.detailFournisseurService = detailFournisseurService;
    }

    @GetMapping
    public String listFournisseurs(@RequestParam(required = false) String search,
                                   @RequestParam(required = false) String sort,
                                   Model model) {
        List<Fournisseur> fournisseurs;

        if (search != null && !search.isEmpty()) {
            fournisseurs = fournisseurService.searchFournisseurs(search);
        } else if ("nom".equals(sort)) {
            fournisseurs = fournisseurService.getAllFournisseursOrderByNom();
        } else if ("frais".equals(sort)) {
            fournisseurs = fournisseurService.getAllFournisseursOrderByFrais();
        } else {
            fournisseurs = fournisseurService.getAllFournisseurs();
        }

        model.addAttribute("fournisseurs", fournisseurs);
        return "administratif/stock/fournisseurs/list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("fournisseur", new Fournisseur());
        return "administratif/stock/fournisseurs/add";
    }

    @PostMapping("/save")
    public String saveFournisseur(@ModelAttribute Fournisseur fournisseur, RedirectAttributes redirectAttributes) {
        fournisseurService.saveFournisseur(fournisseur);
        redirectAttributes.addFlashAttribute("success", "Fournisseur enregistré avec succès!");
        return "redirect:/administratif/stock/fournisseurs";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Fournisseur fournisseur = fournisseurService.getFournisseurById(id);
        model.addAttribute("fournisseur", fournisseur);
        return "administratif/stock/fournisseurs/edit";
    }

    @GetMapping("/delete/{id}")
    public String deleteFournisseur(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        fournisseurService.deleteFournisseur(id);
        redirectAttributes.addFlashAttribute("success", "Fournisseur supprimé avec succès!");
        return "redirect:/administratif/stock/fournisseurs";
    }

    @GetMapping("/detail/{id}")
    public String showDetail(@PathVariable Long id,
                             @RequestParam(required = false) String sort,
                             Model model) {
        Fournisseur fournisseur = fournisseurService.getFournisseurById(id);
        List<DetailFournisseur> details;

        if ("nom".equals(sort)) {
            details = detailFournisseurService.getDetailsByFournisseurOrderByNom(fournisseur);
        } else if ("prix".equals(sort)) {
            details = detailFournisseurService.getDetailsByFournisseurOrderByPrix(fournisseur);
        } else if ("date".equals(sort)) {
            details = detailFournisseurService.getDetailsByFournisseurOrderByDate(fournisseur);
        } else {
            details = detailFournisseurService.getDetailsByFournisseur(fournisseur);
        }

        model.addAttribute("fournisseur", fournisseur);
        model.addAttribute("details", details);
        return "administratif/stock/fournisseurs/detail";
    }
}