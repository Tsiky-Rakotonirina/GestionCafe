package com.gestioncafe.controller.rh;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.ui.Model;

import com.gestioncafe.service.*;
import com.gestioncafe.service.rh.JourFerieService;
import com.gestioncafe.repository.*;
import com.gestioncafe.model.*;
import com.gestioncafe.model.rh.JourFerie;

@Controller
@RequestMapping("/administratif/rh")
public class RhController {
    @Autowired
    private JourFerieService jourFerieService;

    @GetMapping
    public String index(Model model) {
        if (!model.containsAttribute("jourFerie")) {
            model.addAttribute("jourFerie", new JourFerie());
        }
        model.addAttribute("listeJoursFeries", jourFerieService.findAll());
        return "administratif/rh/parametre";
    }

    @PostMapping("/ajout-jour-ferie")
    public String ajoutJourFerie(@ModelAttribute("jourFerie") JourFerie jourFerie) {
        jourFerieService.save(jourFerie);
        return "redirect:/administratif/rh";
    }

}
