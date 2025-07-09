package com.gestioncafe.service.marketing;

import com.gestioncafe.model.Langue;
import com.gestioncafe.repository.LangueRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LangueService {

    private final LangueRepository langueRepository;

    public LangueService(LangueRepository langueRepository) {
        this.langueRepository = langueRepository;
    }

    public List<Langue> getAllLangues() {
        return langueRepository.findAll();
    }

    public Langue getById(Long id) {
        return langueRepository.findById(id).orElse(null);
    }
}
