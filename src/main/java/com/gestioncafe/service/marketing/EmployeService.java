package com.gestioncafe.service.marketing;

import com.gestioncafe.model.*;
import com.gestioncafe.repository.CandidatRepository;
import com.gestioncafe.repository.EmployeRepository;
import com.gestioncafe.service.GradeEmployeService;
import com.gestioncafe.service.StatutEmployeService;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;

@Service
public class EmployeService {

    private final EmployeRepository employeRepository;

    private final CandidatRepository candidatRepository;

    private final StatutEmployeService statutEmployeService;


    private final GradeEmployeService gradeEmployeService;

    public EmployeService(EmployeRepository employeRepository,
                          CandidatRepository candidatRepository,
                          StatutEmployeService statutEmployeService,
                          GradeEmployeService gradeEmployeService) {
        this.employeRepository = employeRepository;
        this.candidatRepository = candidatRepository;
        this.statutEmployeService = statutEmployeService;
        this.gradeEmployeService = gradeEmployeService;
    }


    public void recruterCandidat(Long candidatId) {
        Candidat candidat = candidatRepository.findById(candidatId)
            .orElseThrow(() -> new RuntimeException("Candidat non trouvé"));

        Employe employe = new Employe();
        employe.setNom(candidat.getNom());
        employe.setDateNaissance(candidat.getDateNaissance());
        employe.setContact(candidat.getContact());
        employe.setDateRecrutement(Date.valueOf(LocalDate.now()));

        // Liaison avec entités
        employe.setGenre(candidat.getGenre());
        employe.setCandidat(candidat);

        employe = employeRepository.save(employe);

        // Création statut employé avec statut id = 1
        StatutEmploye statutEmploye = new StatutEmploye();
        statutEmploye.setEmploye(employe);
        statutEmploye.setDateStatut(LocalDate.now().atStartOfDay());

        Statut statut = new Statut();
        statut.setId(1L);
        statutEmploye.setStatut(statut);

        statutEmployeService.saveStatutEmploye(statutEmploye);

        // Récupérer idGrade du candidat directement
        Long idGrade = null;

        if (candidat.getGrade() != null) {
            idGrade = candidat.getGrade().getId();
        }

        if (idGrade != null) {
            GradeEmploye gradeEmploye = new GradeEmploye();
            gradeEmploye.setEmploye(employe);
            gradeEmploye.setDateGrade(LocalDate.now());

            Grade grade = new Grade();
            grade.setId(idGrade);
            gradeEmploye.setGrade(grade);

            gradeEmployeService.saveGradeEmploye(gradeEmploye);
        } else {
            throw new RuntimeException("Grade du candidat non trouvé");
        }
    }


}
