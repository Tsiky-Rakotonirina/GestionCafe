package com.gestioncafe.controller.rh;

import com.gestioncafe.model.rh.JourFerie;
import com.gestioncafe.service.rh.RhParametreService;
import com.gestioncafe.model.rh.Grade;
import com.gestioncafe.model.rh.Irsa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/administratif/rh/parametre")
public class RhParametreController {

    @Autowired
    private RhParametreService rhParametreService;

    @GetMapping
    public String index(Model model) {
        if (!model.containsAttribute("jourFerie")) {
            model.addAttribute("jourFerie", new JourFerie());
        }
        if (!model.containsAttribute("grade")) {
            model.addAttribute("grade", new Grade());
        }
        if (!model.containsAttribute("irsa")) {
            model.addAttribute("irsa", new Irsa());
        }

        model.addAttribute("listeJoursFeries", rhParametreService.getJourFerieService().findAll());
        model.addAttribute("listeGrades", rhParametreService.getGradeService().findAll());
        model.addAttribute("listeIrsas", rhParametreService.getIrsaService().findAll());

        return "administratif/rh/parametrage";
    }

    @PostMapping("/ajout-jour-ferie")
    public String ajoutJourFerie(@ModelAttribute("jourFerie") JourFerie jourFerie) {
        rhParametreService.getJourFerieService().save(jourFerie);
        return "redirect:/administratif/rh/parametre";
    }

    @PostMapping("/ajout-grade")
    public String ajoutGrade(@ModelAttribute("grade") Grade grade) {
        rhParametreService.getGradeService().save(grade);
        return "redirect:/administratif/rh/parametre";
    }

    @PostMapping("/ajout-irsa")
    public String ajoutIrsa(@ModelAttribute("irsa") Irsa irsa, RedirectAttributes redirectAttributes) {
        try {
            rhParametreService.getIrsaService().save(irsa);
            return "redirect:/administratif/rh/parametre";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erreur", e.getMessage());
            return "redirect:/administratif/rh/parametre";
        }
    }

    @PostMapping("/modification-irsa")
    public String modificationIrsa(@ModelAttribute("irsa") Irsa irsa, Model model,
            RedirectAttributes redirectAttributes) {
        try {
            rhParametreService.getIrsaService().save(irsa);
            return "redirect:/administratif/rh/parametre";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erreur", e.getMessage());
            return "redirect:/administratif/rh/parametre";
        }
    }

}
