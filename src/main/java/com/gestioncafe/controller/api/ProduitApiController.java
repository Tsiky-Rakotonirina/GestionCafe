// src/main/java/com/gestioncafe/controller/api/ProduitApiController.java
package com.gestioncafe.controller.api;

import com.gestioncafe.model.Produit;
import com.gestioncafe.repository.ProduitRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/produits")
public class ProduitApiController {
    private final ProduitRepository produitRepository;
    
    public ProduitApiController(ProduitRepository produitRepository) {
        this.produitRepository = produitRepository;
    }
    
    @GetMapping
    public List<Produit> getAllProduits() {
        return produitRepository.findAll();
    }
}