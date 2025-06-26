package com.gestioncafe.serviceImpl.stock;

import com.gestioncafe.model.stock.MatierePremiere;
import com.gestioncafe.repository.stock.MatierePremiereRepository;
import com.gestioncafe.service.stock.MatierePremiereService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MatierePremiereServiceImpl implements MatierePremiereService {
    private final MatierePremiereRepository matierePremiereRepository;

    public MatierePremiereServiceImpl(MatierePremiereRepository matierePremiereRepository) {
        this.matierePremiereRepository = matierePremiereRepository;
    }

    @Override
    public List<MatierePremiere> getAllMatieres() {
        return matierePremiereRepository.findAll();
    }

    @Override
    public MatierePremiere getMatiereById(Integer id) {
        return matierePremiereRepository.findById(id).orElse(null);
    }

    @Override
    public MatierePremiere saveMatiere(MatierePremiere matiere) {
        return matierePremiereRepository.save(matiere);
    }

    @Override
    public void deleteMatiere(Integer id) {
        matierePremiereRepository.deleteById(id);
    }
}