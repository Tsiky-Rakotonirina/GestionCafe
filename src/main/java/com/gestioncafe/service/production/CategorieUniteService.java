package com.gestioncafe.service.production;

import com.gestioncafe.model.CategorieUnite;
import com.gestioncafe.repository.CategorieUniteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategorieUniteService {
    private final CategorieUniteRepository categorieUniteRepository;

    public CategorieUniteService(CategorieUniteRepository categorieUniteRepository) {
        this.categorieUniteRepository = categorieUniteRepository;
    }

    public List<CategorieUnite> findAll() {
        return categorieUniteRepository.findAll();
    }
}
