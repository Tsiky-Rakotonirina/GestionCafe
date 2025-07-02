package com.gestioncafe.controller.rh;

import com.gestioncafe.model.rh.JourFerie;
import com.gestioncafe.service.rh.RhParametreService;
import com.gestioncafe.model.rh.Grade;
import com.gestioncafe.model.rh.Irsa;
import com.gestioncafe.model.rh.IrsaWrapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        List<Irsa> irsas = rhParametreService.getIrsaService().findAll();
        if (!model.containsAttribute("jourFerie")) {
            model.addAttribute("jourFerie", new JourFerie());
        }
        if (!model.containsAttribute("grade")) {
            model.addAttribute("grade", new Grade());
        }
        if (!model.containsAttribute("irsa")) {
            model.addAttribute("irsa", new Irsa());
        }
        if (model.containsAttribute("irsaWrapper")) {
            IrsaWrapper irsaWrapper = (IrsaWrapper) model.getAttribute("irsaWrapper");

            // Fusionner ou synchroniser avec les données réelles
            for (Irsa dbIrsa : irsas) {
                boolean existe = irsaWrapper.getIrsas().stream()
                        .anyMatch(i -> i.getId() != null && i.getId().equals(dbIrsa.getId()));
                if (!existe) {
                    irsaWrapper.addIrsa(dbIrsa);
                }
            }

            model.addAttribute("irsaWrapper", irsaWrapper);
        } else {
            IrsaWrapper irsaWrapper = new IrsaWrapper();
            irsaWrapper.setIrsas(rhParametreService.getIrsaService().findAll());
            model.addAttribute("irsaWrapper", irsaWrapper);
        }

        model.addAttribute("listeJoursFeries", rhParametreService.getJourFerieService().findAll());
        model.addAttribute("listeGrades", rhParametreService.getGradeService().findAll());
        model.addAttribute("listeIrsas", irsas);

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

    @PostMapping("/supprimer-irsa")
    @ResponseBody
    public ResponseEntity<?> supprimerIrsa(@RequestParam Long id) {
        try {
            rhParametreService.getIrsaService().deleteById(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de la suppression");
        }
    }

    @PostMapping("/enregistrer-irsa")
    public String enregistrerIrsas(@ModelAttribute("irsaWrapper") IrsaWrapper irsaWrapper,
            RedirectAttributes redirectAttributes) {
        try {
            List<Irsa> irsas = irsaWrapper.getIrsas();
            for (Irsa irsa : irsas) {
                rhParametreService.getIrsaService().save(irsa);
            }
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
