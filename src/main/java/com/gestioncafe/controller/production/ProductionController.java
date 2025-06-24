package com.gestioncafe.controller.production;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequestMapping("/administratif/production")
public class ProductionController {

    @GetMapping
    public String index() {
        return "administratif/production/index";
    }
}
