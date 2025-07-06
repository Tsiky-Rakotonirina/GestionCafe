package com.gestioncafe.service.rh;

import com.gestioncafe.dto.EmployeInfosDTO;
import com.gestioncafe.model.Employe;
import com.gestioncafe.repository.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeService {

    private final EmployeRepository employeRepository;

    private final CandidatRepository candidatRepository;

    private final VenteRepository venteRepository;

    private final PresenceRepository presenceRepository;

    private final StatutEmployeRepository statutEmployeRepository;


    public EmployeService(EmployeRepository employeRepository,
                          CandidatRepository candidatRepository,
                          VenteRepository venteRepository,
                          PresenceRepository presenceRepository,
                          StatutEmployeRepository statutEmployeRepository) {
        this.employeRepository = employeRepository;
        this.candidatRepository = candidatRepository;
        this.venteRepository = venteRepository;
        this.presenceRepository = presenceRepository;
        this.statutEmployeRepository = statutEmployeRepository;
    }

    /**
     * Retourne la liste des employés avec indicateurs RH avancés
     */
    public List<EmployeInfosDTO> getEmployeInfos() {
        List<Employe> employes = employeRepository.findAll();
        List<EmployeInfosDTO> infos = new ArrayList<>();
        for (Employe employe : employes) {
            int nombreClients = 0;
            try {
                nombreClients = venteRepository.countDistinctClientsByEmploye(employe);
            } catch (Exception e) {
                // log or ignore
            }
            int nombrePresences = 0;
            try {
                nombrePresences = presenceRepository.countByIdEmployeAndEstPresent(employe.getId(), true);
            } catch (Exception e) {
                // log or ignore
            }
            double efficacite = (nombrePresences > 0) ? ((double) nombreClients / nombrePresences) : 0.0;
            String statut = "-";
            try {
                statut = statutEmployeRepository.findTopByEmploye_IdOrderByDateStatutDesc(employe.getId())
                    .map(se -> se.getIdStatut() != null ? se.getIdStatut().toString() : "-")
                    .orElse("-");
            } catch (Exception e) {
                // log or ignore
            }
            infos.add(new EmployeInfosDTO(employe, nombreClients, nombrePresences, efficacite, statut));
        }
        return infos;
    }

    /**
     * Recrute un candidat en tant qu'employé (copie les infos principales du candidat)
     */
    public void recruterCandidat(Long candidatId) {
        var candidatOpt = candidatRepository.findById(candidatId);
        if (candidatOpt.isEmpty()) throw new RuntimeException("Candidat non trouvé");
        var candidat = candidatOpt.get();
        Employe employe = new Employe();
        employe.setNom(candidat.getNom());
        employe.setDateNaissance(candidat.getDateNaissance());
        employe.setContact(candidat.getContact());
        employe.setDateRecrutement(new java.sql.Date(System.currentTimeMillis()));
        employe.setGenre(candidat.getGenre());
        employe.setCandidat(candidat);
        employeRepository.save(employe);
        // Statut et grade peuvent être ajoutés ici si besoin
    }
}