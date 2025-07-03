package com.gestioncafe.controller.production;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gestioncafe.model.production.Unite;
import com.gestioncafe.service.production.CategorieUniteService;
import com.gestioncafe.service.production.UniteService;

@Controller
@RequestMapping("/administratif/production/unite")
public class UniteController {
    @Autowired
    private UniteService uniteService;
    @Autowired
    private CategorieUniteService categorieUniteService;

    @GetMapping("")
    public String list(Model model) {
        List<Unite> unites = uniteService.findAll();
        model.addAttribute("unites", unites);
        model.addAttribute("categories", categorieUniteService.findAll());
        return "administratif/production/unite/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("unite", new Unite());
        model.addAttribute("categories", categorieUniteService.findAll());
        return "administratif/production/unite/form";
    }

    @PostMapping("/new")
    public String create(@ModelAttribute Unite unite) {
        uniteService.save(unite);
        return "redirect:/administratif/production/unite";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Integer id, Model model) {
        Unite unite = uniteService.findById(id).orElseThrow();
        model.addAttribute("unite", unite);
        model.addAttribute("categories", categorieUniteService.findAll());
        return "administratif/production/unite/form";
    }

    @PostMapping("/edit/{id}")
    public String update(@PathVariable Integer id, @ModelAttribute Unite unite) {
        unite.setId(id);
        uniteService.save(unite);
        return "redirect:/administratif/production/unite";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        uniteService.deleteById(id);
        return "redirect:/administratif/production/unite";
    }
}
