package com.gestioncafe.service;

import com.gestioncafe.model.DetailFournisseur;
import com.gestioncafe.model.Fournisseur;
import com.gestioncafe.repository.DetailFournisseurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DetailFournisseurService {
    @Autowired
    private DetailFournisseurRepository detailFournisseurRepository;

    public List<DetailFournisseur> getDetailsByFournisseur(Fournisseur fournisseur) {
        return detailFournisseurRepository.findByFournisseur(fournisseur);
    }
    
    public List<DetailFournisseur> getDetailsByFournisseurOrderByNom(Fournisseur fournisseur) {
        return detailFournisseurRepository.findByFournisseurOrderByMatierePremiereNomAsc(fournisseur);
    }
    
    public List<DetailFournisseur> getDetailsByFournisseurOrderByPrix(Fournisseur fournisseur) {
        return detailFournisseurRepository.findByFournisseurOrderByPrixUnitaireAsc(fournisseur);
    }
    
    public List<DetailFournisseur> getDetailsByFournisseurOrderByDate(Fournisseur fournisseur) {
        return detailFournisseurRepository.findByFournisseurOrderByDateModificationDesc(fournisseur);
    }
    
    public DetailFournisseur saveDetail(DetailFournisseur detail) {
        return detailFournisseurRepository.save(detail);
    }
}