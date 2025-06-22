package com.gestioncafe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.ui.Model;

import com.gestioncafe.service.*;
import com.gestioncafe.repository.*;
import com.gestioncafe.model.*;

@Controller
@RequestMapping("/")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @GetMapping
    public String login() {
        return "login";
    }

    // utiliser ModelAttribute pour lier foramulaire : nom des champs = nom des attributs de classe
    @PostMapping("/login-administratif")
    public String loginAdministratif(@RequestParam("nom") String nom, @RequestParam("motDePasse") String motDePasse, Model model) {
        boolean isAuthenticated = loginService.loginAdministratif(nom, motDePasse);
        if (isAuthenticated) {
            // retour vers URL
            return "redirect:/administratif";
        } else {
            model.addAttribute("erreurAdministratif", "Nom ou mot de passe incorrect");
            // retour vers PAGE
            return "login"; 
        }
        
    }

    // sinon recuperer un par un avec requestParam
    @PostMapping("/login-quotidien")
    public String loginQuotidien(@RequestParam("nom") String nom, @RequestParam("motDePasse") String motDePasse, Model model) {
        boolean isAuthenticated = loginService.loginQuotidien(nom, motDePasse);
        if (isAuthenticated) {
            return "redirect:/quotidien";
        } else {
            model.addAttribute("erreurQuotidien", "Nom ou mot de passe incorrect");
            return "login"; 
        }
        
    }

    @GetMapping("/deconnexion-administratif")
    public String deconnexionAdministratif() {
        // session.invalidate();  // Supprime la session actuelle
        return "login";
    }

    @GetMapping("/deconnexion-quotidien")
    public String deconnexionQuotidien() {
        return "login";
    }    
}