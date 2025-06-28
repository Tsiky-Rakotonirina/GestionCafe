package com.gestioncafe.controller.rh;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

import com.gestioncafe.service.rh.*;
import com.gestioncafe.repository.*;
import com.gestioncafe.model.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/administratif/rh/conge")
public class RhCongeController {

    @Autowired
    private RhCongeService rhCongeService;

    @GetMapping("/calendrier")
    public String calendrier() {
        return "redirect:/administratif/rh/gestion-conges";
    }

    @PostMapping("/ajout-conge")
    public String ajoutConge() {
        return "redirect:/administratif/rh/gestion-conges";
    }
}
