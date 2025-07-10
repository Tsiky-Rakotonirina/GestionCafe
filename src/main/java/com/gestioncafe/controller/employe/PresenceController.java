package com.gestioncafe.controller.employe;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gestioncafe.model.Presence;
import com.gestioncafe.repository.EmployeRepository;
import com.gestioncafe.service.PresenceService;

@Controller
@RequestMapping("/administratif/employe/presence")
public class PresenceController {
    @Autowired
    private EmployeRepository employeRepository;
    @Autowired
    private PresenceService presenceService;

    @GetMapping
    public String presencePage(Model model) {
        model.addAttribute("titre", "Présence");
        model.addAttribute("dateAujourdhui", java.time.LocalDate.now());
        model.addAttribute("employes", employeRepository.findAll());
        // Construction d'une Map<Long, Presence> pour le template
        Map<Long, Presence> presencesMap = new HashMap<>();
        for (Presence p : presenceService.getPresencesDuJour()) {
            if (p.getEmploye() != null && p.getEmploye().getId() != null) {
                presencesMap.put(p.getEmploye().getId(), p);
            }
        }
        model.addAttribute("presences", presencesMap);
        return "administratif/employe/presence";
    }
}
