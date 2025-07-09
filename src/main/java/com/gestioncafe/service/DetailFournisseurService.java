package com.gestioncafe.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gestioncafe.model.DetailFournisseur;
import com.gestioncafe.model.Fournisseur;
import com.gestioncafe.model.MatierePremiere;
import com.gestioncafe.repository.DetailFournisseurRepository;

@Service
public class DetailFournisseurService {
    @Autowired
    private DetailFournisseurRepository detailFournisseurRepository;

    // Pour Fournisseur
    public List<DetailFournisseur> getDetailsByFournisseur(Fournisseur fournisseur) {
        return detailFournisseurRepository.findByFournisseur(fournisseur);
    }
    public List<DetailFournisseur> getDetailsByFournisseurOrderByNom(Fournisseur fournisseur) {
        return detailFournisseurRepository.findByFournisseurOrderByMatierePremiereNomAsc(fournisseur);
    }
    public List<DetailFournisseur> getDetailsByFournisseurOrderByPrix(Fournisseur fournisseur) {
        return detailFournisseurRepository.findByFournisseurOrderByPrixAsc(fournisseur);
    }
    public List<DetailFournisseur> getDetailsByFournisseurOrderByDate(Fournisseur fournisseur) {
        return detailFournisseurRepository.findByFournisseurOrderByDateModificationDesc(fournisseur);
    }
    public DetailFournisseur saveDetail(DetailFournisseur detail) {
        return detailFournisseurRepository.save(detail);
    }

    // Pour MatierePremiere (production)
    public List<DetailFournisseur> findByMatierePremiere(MatierePremiere mp) {
        return detailFournisseurRepository.findByMatierePremiere(mp);
    }
}
