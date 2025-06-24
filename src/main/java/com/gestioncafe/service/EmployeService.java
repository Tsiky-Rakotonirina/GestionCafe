package com.gestioncafe.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gestioncafe.model.Employe;
import com.gestioncafe.model.Candidat;
import com.gestioncafe.repository.CandidatRepository;
import com.gestioncafe.repository.EmployeRepository;
import java.time.LocalDateTime;
import java.util.List;
import com.gestioncafe.model.StatutEmploye;
import com.gestioncafe.model.Statut;
import com.gestioncafe.repository.StatutEmployeRepository;
import com.gestioncafe.service.CandidatService;
import com.gestioncafe.service.DetailCandidatService;
import com.gestioncafe.service.StatutEmployeService;

import com.gestioncafe.model.Grade;
import com.gestioncafe.model.GradeEmploye;
import com.gestioncafe.service.GradeEmployeService;


import java.time.LocalDate;

@Service
public class EmployeService {

    @Autowired
    private EmployeRepository employeRepository;

    @Autowired
    private CandidatRepository candidatRepository;

    @Autowired
    private StatutEmployeService statutEmployeService;

    @Autowired
    private DetailCandidatService detailCandidatService;

    @Autowired
    private CandidatService candidatService;

    @Autowired
    private GradeEmployeService gradeEmployeService;



    public void recruterCandidat(Long candidatId) {
    Candidat candidat = candidatRepository.findById(candidatId)
        .orElseThrow(() -> new RuntimeException("Candidat non trouvé"));

    Employe employe = new Employe();
    employe.setNom(candidat.getNom());
    employe.setDateNaissance(candidat.getDateNaissance());
    employe.setContact(candidat.getContact());
    employe.setDateRecrutement(LocalDate.now());

    // Liaison avec entités
    employe.setGenre(candidat.getGenre());
    employe.setCandidat(candidat);

    employe = employeRepository.save(employe);

    // Création statut employé avec statut id = 1
    StatutEmploye statutEmploye = new StatutEmploye();
    statutEmploye.setEmploye(employe);
    statutEmploye.setDateStatut(LocalDateTime.now());

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
