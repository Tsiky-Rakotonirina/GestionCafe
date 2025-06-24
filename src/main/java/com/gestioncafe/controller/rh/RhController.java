package com.gestioncafe.controller.rh;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/administratif/rh")
public class RhController {

    @GetMapping
    public String index() {
        return "administratif/rh/index";
    }
}
