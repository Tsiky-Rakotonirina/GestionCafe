package com.gestioncafe.controller.stock;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequestMapping("/administratif/stock")
public class StockController {

    @GetMapping
    public String index() {
        return "administratif/stock/logistique";
    }

    
}
