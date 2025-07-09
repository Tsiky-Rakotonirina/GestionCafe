package com.gestioncafe.controller.marketing;

import com.gestioncafe.model.Marketing;
import com.gestioncafe.service.MarketingService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MarketingController {
    @GetMapping("/administratif/marketing")
    public String accueilMarketing() {
        // Redirige vers la liste des clients marketing
        return "redirect:/marketing/clients";
    }
}
