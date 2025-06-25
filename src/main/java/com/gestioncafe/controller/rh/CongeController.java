package com.gestioncafe.controller.rh;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gestioncafe.model.rh.Conge;
import com.gestioncafe.model.rh.TypeConge;
import com.gestioncafe.service.rh.CongeService;
import com.gestioncafe.service.rh.EmployeService;

@Controller
@RequestMapping("/administratif/rh/conge")
public class CongeController {
    @Autowired
    private CongeService congeService;

    @Autowired
    private EmployeService employeService;

    @GetMapping
    public String index(Model model) {
        if (!model.containsAttribute("conge")) {
            model.addAttribute("conge", new Conge());
        }
        if (!model.containsAttribute("typeConge")) {
            model.addAttribute("typeConge", new TypeConge());
        }

        model.addAttribute("listeEmployes", employeService.findAll());
        model.addAttribute("listeConges", congeService.findAll());
        model.addAttribute("listeTypeConges", congeService.findAllTypeConge());

        return "administratif/rh/gestion-conges";
    }

    @PostMapping("/ajout-type-conge")
    public String ajoutTypeConge(@ModelAttribute("typeConge") TypeConge typeConge) {
        congeService.saveTypeConge(typeConge);
        return "redirect:/administratif/rh/conge";
    }

    @PostMapping("/ajout-conge")
    public String ajoutConge(@ModelAttribute("conge") Conge conge, RedirectAttributes redirectAttributes) {
        try {
            congeService.save(conge);
            return "redirect:/administratif/rh/conge";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erreur", e.getMessage());
            return "redirect:/administratif/rh/conge";
        }

    }
}
