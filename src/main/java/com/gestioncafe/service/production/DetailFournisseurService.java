package com.gestioncafe.service.production;

import com.gestioncafe.model.DetailFournisseur;
import com.gestioncafe.model.Fournisseur;
import com.gestioncafe.model.MatierePremiere;
import com.gestioncafe.repository.DetailFournisseurRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DetailFournisseurService {
    private final DetailFournisseurRepository detailFournisseurRepository;

    public DetailFournisseurService(DetailFournisseurRepository detailFournisseurRepository) {
        this.detailFournisseurRepository = detailFournisseurRepository;
    }

    public List<DetailFournisseur> findByMatierePremiere(MatierePremiere mp) {
        return detailFournisseurRepository.findByMatierePremiere(mp);
    }

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
}
