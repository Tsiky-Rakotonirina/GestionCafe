package com.gestioncafe.controller.marketing;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;


@Controller
@RequestMapping("/administratif/marketing")
public class MarketingController {
    @GetMapping
    public String index(Model model) {
        return "administratif/marketing/index";
    }
    @GetMapping("/dashboard")
    public String dashboard(Model model) {

        return "administratif/marketing/dashboard";
    }
}