package com.gestioncafe.controller.finance;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

import java.time.LocalDate;
import java.time.Period;

import com.gestioncafe.service.rh.*;
import com.gestioncafe.service.*;
import com.gestioncafe.repository.*;
import com.gestioncafe.model.*;

@Controller
@RequestMapping("/administratif/finance")
public class FinanceController {
    
    @GetMapping
    public String accueil() {
        return "redirect:/administratif/finance/bilan";
    }

    @GetMapping("/bilan")
    public String bilan() {
        return "administratif/finance/bilan";
    }

    @GetMapping("/budget-previsionnel")
    public String budget() {
        return "administratif/finance/budget-previsionnel";
    }

    @GetMapping("/resultat")
    public String resultat() {
        return "administratif/finance/resultat";
    }
}