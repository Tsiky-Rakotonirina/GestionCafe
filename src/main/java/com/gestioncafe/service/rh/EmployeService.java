package com.gestioncafe.service.rh;

import com.gestioncafe.dto.EmployeDetailDTO;
import com.gestioncafe.dto.EmployeInfosDTO;
import com.gestioncafe.model.Employe;
import com.gestioncafe.model.Statut;
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
    private final StatutRepository statutRepository;

    public EmployeService(EmployeRepository employeRepository,
                          CandidatRepository candidatRepository,
                          VenteRepository venteRepository,
                          PresenceRepository presenceRepository,
                          StatutEmployeRepository statutEmployeRepository,
                          StatutRepository statutRepository) {
        this.employeRepository = employeRepository;
        this.candidatRepository = candidatRepository;
        this.venteRepository = venteRepository;
        this.presenceRepository = presenceRepository;
        this.statutEmployeRepository = statutEmployeRepository;
        this.statutRepository = statutRepository;
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
                nombrePresences = presenceRepository.countByEmploye_IdAndEstPresent(employe.getId(), true);
            } catch (Exception e) {
                // log or ignore
            }
            double efficacite = (nombrePresences > 0) ? ((double) nombreClients / nombrePresences) : 0.0;
            String statut = "-";
            try {
                statut = statutEmployeRepository.findTopByEmploye_IdOrderByDateStatutDesc(employe.getId())
                    .map(se -> {
                        if (se.getStatut().getId() != null) {
                            Statut statutObj = statutRepository.findById(se.getStatut().getId()).orElse(null);
                            return (statutObj != null && statutObj.getValeur() != null) ? statutObj.getValeur() : "-";
                        }
                        return "-";
                    })
                    .orElse("-");
            } catch (Exception e) {
                // log or ignore
            }
            infos.add(new EmployeInfosDTO(employe, nombreClients, nombrePresences, efficacite, statut));
        }
        return infos;
    }

    /**
     * Retourne les détails d'un employé pour l'affichage RH
     */
    public EmployeDetailDTO getEmployeDetail(Long id) {
        Employe employe = employeRepository.findById(id).orElse(null);
        if (employe == null) return null;
        EmployeDetailDTO dto = new EmployeDetailDTO();
        dto.id = employe.getId();
        dto.nom = employe.getNom();
        // dto.prenom = employe.getPrenom(); // décommente si tu ajoutes ce champ
        dto.prenom = ""; // à adapter si tu ajoutes le champ
        // dto.genre = employe.getGenre() != null ? employe.getGenre().getNom() : "-";
        dto.genre = ""; // à adapter si tu ajoutes le champ
        dto.dateNaissance = employe.getDateNaissance();
        dto.dateRecrutement = employe.getDateRecrutement();
        // dto.gradeActuel = employe.getGradeActuel() != null ? employe.getGradeActuel().getNom() : "-";
        dto.gradeActuel = ""; // à adapter si tu ajoutes le champ
        dto.contact = employe.getContact();
        // dto.email = employe.getEmail();
        dto.email = ""; // à adapter si tu ajoutes le champ
        // Statut
        dto.statut = statutEmployeRepository.findTopByEmploye_IdOrderByDateStatutDesc(id)
            .map(se -> {
                if (se.getStatut().getId() != null) {
                    Statut statutObj = statutRepository.findById(se.getStatut().getId()).orElse(null);
                    return (statutObj != null && statutObj.getValeur() != null) ? statutObj.getValeur() : "-";
                }
                return "-";
            })
            .orElse("-");
        // Statistiques
        dto.nombrePresences = presenceRepository.countByEmploye_IdAndEstPresent(id, true);
        int nombreClients = 0;
        try {
            nombreClients = venteRepository.countDistinctClientsByEmploye(employe);
        } catch (Exception ignored) {
        }
        dto.nombreClients = nombreClients;
        dto.efficacite = (dto.nombrePresences > 0) ? ((double) dto.nombreClients / dto.nombrePresences) : 0.0;
        return dto;
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

        // Mettre à jour le statut de l'employé en 'Actif'
        Statut statutActif = statutRepository.findByValeur("Actif").orElse(null);
        if (statutActif != null) {
            var statutEmploye = new com.gestioncafe.model.StatutEmploye();
            statutEmploye.setEmploye(employe);
            statutEmploye.setStatut(statutActif);
            statutEmploye.setDateStatut(java.time.LocalDateTime.now());
            statutEmployeRepository.save(statutEmploye);
        }
    }
}