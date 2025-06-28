package com.gestioncafe.service;

import com.gestioncafe.model.*;
import com.gestioncafe.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class ApprovisionnementService {

    private final MatierePremiereRepository matierePremiereRepository;
    private final DetailFournisseurRepository detailFournisseurRepository;
    private final ApprovisionnementRepository approvisionnementRepository;

    public ApprovisionnementService(MatierePremiereRepository matierePremiereRepository,
                                  DetailFournisseurRepository detailFournisseurRepository,
                                  ApprovisionnementRepository approvisionnementRepository) {
        this.matierePremiereRepository = matierePremiereRepository;
        this.detailFournisseurRepository = detailFournisseurRepository;
        this.approvisionnementRepository = approvisionnementRepository;
    }

    @Transactional
    public void createApprovisionnement(Long idMatierePremiere,
                                      Long idFournisseur,
                                      String referenceFacture,
                                      double quantite,
                                      double total,
                                      LocalDate dateApprovisionnement) {
        // Récupérer la matière première et mettre à jour le stock
        MatierePremiere matiere = matierePremiereRepository.findById(idMatierePremiere)
                .orElseThrow(() -> new IllegalArgumentException("Matière première non trouvée"));
        
        // Récupérer le détail fournisseur
        DetailFournisseur detailFournisseur = detailFournisseurRepository
                .findByFournisseurIdAndMatierePremiereId(idFournisseur, idMatierePremiere)
                .orElseThrow(() -> new IllegalArgumentException("Fournisseur ne propose pas cette matière première"));
        
        // Créer l'approvisionnement
        Approvisionnement approvisionnement = new Approvisionnement();
        approvisionnement.setDetailFournisseur(detailFournisseur);
        approvisionnement.setMatierePremiere(matiere);
        approvisionnement.setQuantite(quantite);
        approvisionnement.setTotal(total);
        approvisionnement.setDateApprovisionnement(dateApprovisionnement);
        approvisionnement.setReferenceFacture(referenceFacture);
        
        // Mettre à jour le stock
        matiere.setStock(matiere.getStock() + quantite);
        matierePremiereRepository.save(matiere);
        
        // Enregistrer l'approvisionnement
        approvisionnementRepository.save(approvisionnement);
    }
}