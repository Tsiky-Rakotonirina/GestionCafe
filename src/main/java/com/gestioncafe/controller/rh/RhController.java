package com.gestioncafe.controller.rh;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;

import com.gestioncafe.model.Candidat;
import com.gestioncafe.model.DetailCandidat;
import com.gestioncafe.model.SerieBac;
import com.gestioncafe.model.Formation;
import com.gestioncafe.model.Langue;
import com.gestioncafe.model.Experience;
import com.gestioncafe.model.Genre;


import com.gestioncafe.service.CandidatService;
import com.gestioncafe.service.DetailCandidatService;
import com.gestioncafe.service.GenreService;
import com.gestioncafe.service.GradeService;
import com.gestioncafe.service.SerieBacService;
import com.gestioncafe.service.EmployeService;
import com.gestioncafe.service.PdfService;
import com.gestioncafe.service.StatutEmployeService;
import com.gestioncafe.service.GradeEmployeService;
import com.gestioncafe.service.StatutEmployeService;
import com.gestioncafe.service.LangueService;
import com.gestioncafe.service.ExperienceService;
import com.gestioncafe.service.FormationService;

import jakarta.servlet.http.HttpServletResponse;


import com.gestioncafe.model.Candidat;
import com.gestioncafe.model.DetailCandidat;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;


@Controller
@RequestMapping("/administratif/rh")
public class RhController {

    @Autowired
    private CandidatService candidatService;

    @Autowired
    private DetailCandidatService detailCandidatService;

    @Autowired
    private GenreService genreService;
    
    @Autowired
    private GradeService gradeService;

    @Autowired
    private SerieBacService serieBacService;

    @Autowired
    private com.gestioncafe.service.EmployeService employeService;

    @Autowired
    private PdfService pdfService;

    @Autowired
    private StatutEmployeService statutEmployeService;

    @Autowired
    private LangueService langueService;

    @Autowired
    private ExperienceService experienceService;

    @Autowired
    private FormationService formationService;
    
    @GetMapping
    public String index(Model model) {
        return "administratif/rh/index";
    }

@GetMapping("/recrutement")
public String recrutement(Model model) {
    List<Candidat> candidats = candidatService.getAllCandidats();

    Map<Long, List<DetailCandidat>> detailsMap = candidats.stream()
        .collect(Collectors.toMap(
            Candidat::getId,
            candidat -> detailCandidatService.getDetailsByCandidatId(candidat.getId())
        ));

    model.addAttribute("genres", genreService.getAllGenres());
    model.addAttribute("grades", gradeService.getAllGrades());
    model.addAttribute("seriesBac", serieBacService.getAllSerieBacs());

    // Ajout des nouvelles listes
    model.addAttribute("langues", langueService.getAllLangues());
    model.addAttribute("formations", formationService.getAllFormations());
    model.addAttribute("experiences", experienceService.getAllExperiences());

    model.addAttribute("candidats", candidats);
    model.addAttribute("detailsMap", detailsMap);

    return "administratif/rh/recrutement";
}

    @PostMapping("/recrutement")
public String filtreCandidats(
    @RequestParam(value = "genreId", required = false) Long genreId,
    @RequestParam(value = "minAge", required = false) Integer minAge,
    @RequestParam(value = "maxAge", required = false) Integer maxAge,
    @RequestParam(value = "gradeId", required = false) Long gradeId,
    @RequestParam(value = "serieBacIds", required = false) List<Long> serieBacIds,
    @RequestParam(value = "formationIds", required = false) List<Long> formationIds,
    @RequestParam(value = "langueIds", required = false) List<Long> langueIds,
    @RequestParam(value = "experienceId", required = false) Long experienceId,
    Model model
) {
    // 1. Récupération initiale
    List<Candidat> candidats = (genreId != null)
        ? candidatService.getCandidatsByGenreId(genreId)
        : candidatService.getAllCandidats();

    // 2. Filtrage par âge
    if (minAge != null || maxAge != null) {
        LocalDate today = LocalDate.now();
        candidats = candidats.stream().filter(c -> {
            if (c.getDateNaissance() == null) return false;
            int age = Period.between(c.getDateNaissance(), today).getYears();
            boolean afterMin = (minAge == null) || (age >= minAge);
            boolean beforeMax = (maxAge == null) || (age <= maxAge);
            return afterMin && beforeMax;
        }).collect(Collectors.toList());
    }

    // 3. Filtrage par grade
    if (gradeId != null) {
        candidats = candidats.stream()
            .filter(c -> c.getGrade() != null && c.getGrade().getId().equals(gradeId))
            .collect(Collectors.toList());
    }

    // 4. Filtrage par série Bac
    if (serieBacIds != null && !serieBacIds.isEmpty()) {
        candidats = candidats.stream().filter(c -> {
            List<DetailCandidat> details = detailCandidatService.getDetailsByCandidatId(c.getId());
            return details.stream()
                .anyMatch(d -> d.getSerieBac() != null && serieBacIds.contains(d.getSerieBac().getId()));
        }).collect(Collectors.toList());
    }

    // 5. Filtrage par formations
    if (formationIds != null && !formationIds.isEmpty()) {
        candidats = candidats.stream().filter(c -> {
            List<DetailCandidat> details = detailCandidatService.getDetailsByCandidatId(c.getId());
            return details.stream()
                .anyMatch(d -> d.getFormation() != null && formationIds.contains(d.getFormation().getId()));
        }).collect(Collectors.toList());
    }

    // 6. Filtrage par langues
    if (langueIds != null && !langueIds.isEmpty()) {
        candidats = candidats.stream().filter(c -> {
            List<DetailCandidat> details = detailCandidatService.getDetailsByCandidatId(c.getId());
            return details.stream()
                .anyMatch(d -> d.getLangue() != null && langueIds.contains(d.getLangue().getId()));
        }).collect(Collectors.toList());
    }

    // 7. Filtrage par expérience (radio)
    if (experienceId != null) {
        candidats = candidats.stream().filter(c -> {
            List<DetailCandidat> details = detailCandidatService.getDetailsByCandidatId(c.getId());
            return details.stream()
                .anyMatch(d -> d.getExperience() != null && d.getExperience().getId().equals(experienceId));
        }).collect(Collectors.toList());
    }

    // 8. Détails
    Map<Long, List<DetailCandidat>> detailsMap = candidats.stream()
        .collect(Collectors.toMap(
            Candidat::getId,
            c -> detailCandidatService.getDetailsByCandidatId(c.getId())
        ));

    // 9. Ajout au modèle
    model.addAttribute("genres", genreService.getAllGenres());
    model.addAttribute("grades", gradeService.getAllGrades());
    model.addAttribute("seriesBac", serieBacService.getAllSerieBacs());
    model.addAttribute("formations", formationService.getAllFormations());
    model.addAttribute("langues", langueService.getAllLangues());
    model.addAttribute("experiences", experienceService.getAllExperiences());

    model.addAttribute("candidats", candidats);
    model.addAttribute("detailsMap", detailsMap);

    // Pour garder les filtres sélectionnés après soumission
    model.addAttribute("selectedGenreId", genreId);
    model.addAttribute("minAge", minAge);
    model.addAttribute("maxAge", maxAge);
    model.addAttribute("selectedGradeId", gradeId);
    model.addAttribute("selectedSerieBacIds", serieBacIds);
    model.addAttribute("selectedFormationIds", formationIds);
    model.addAttribute("selectedLangueIds", langueIds);
    model.addAttribute("selectedExperienceId", experienceId);

    return "administratif/rh/recrutement";
}


    @PostMapping("/recruter")
    public String postRecruter(@RequestParam("candidatId") Long candidatId) {
        employeService.recruterCandidat(candidatId);
        return "redirect:/administratif/rh/recrutement";
    }

    
    
    @PostMapping("/pdf")
    public void exportPdf(@RequestParam("candidatId") Long candidatId, HttpServletResponse response) throws Exception {
        Candidat candidat = candidatService.getCandidatById(candidatId);
        if (candidat == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Candidat non trouvé");
            return;
        }

        List<DetailCandidat> details = detailCandidatService.getDetailsByCandidatId(candidatId);

        List<SerieBac> series = new ArrayList<>();
        List<Formation> formations = new ArrayList<>();
        List<Langue> langues = new ArrayList<>();
        List<Experience> experiences = new ArrayList<>();
        Genre genre = candidat.getGenre();

        for (DetailCandidat detail : details) {
            if (detail.getSerieBac() != null) series.add(detail.getSerieBac());
            if (detail.getFormation() != null) formations.add(detail.getFormation());
            if (detail.getLangue() != null) langues.add(detail.getLangue());
            if (detail.getExperience() != null) experiences.add(detail.getExperience());
        }

        pdfService.generateCandidatPdf(candidat, series, formations, langues, experiences, genre, response);
    }

    
}
