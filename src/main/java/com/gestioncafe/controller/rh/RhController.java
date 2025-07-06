package com.gestioncafe.controller.rh;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gestioncafe.model.Candidat;
import com.gestioncafe.model.DetailCandidat;
import com.gestioncafe.model.Employe;
import com.gestioncafe.model.Grade;
import com.gestioncafe.model.Irsa;
import com.gestioncafe.model.IrsaWrapper;
import com.gestioncafe.model.JourFerie;
import com.gestioncafe.model.StatutEmploye;
import com.gestioncafe.service.rh.CandidatService;
import com.gestioncafe.service.rh.DetailCandidatService;
import com.gestioncafe.service.rh.EmployeService;
import com.gestioncafe.service.rh.ExperienceService;
import com.gestioncafe.service.rh.FormationService;
import com.gestioncafe.service.rh.GenreService;
import com.gestioncafe.service.rh.GradeService;
import com.gestioncafe.service.rh.LangueService;
import com.gestioncafe.service.rh.RhParametreService;
import com.gestioncafe.service.rh.RhService;
import com.gestioncafe.service.rh.SerieBacService;

@Controller
@RequestMapping("/administratif/rh")

public class RhController {

    private final RhParametreService rhParametreService;
    private final RhService rhService;
    private final CandidatService candidatService;
    private final DetailCandidatService detailCandidatService;
    private final GenreService genreService;
    private final GradeService gradeService;
    private final SerieBacService serieBacService;
    private final LangueService langueService;
    private final ExperienceService experienceService;
    private final FormationService formationService;


    public RhController(RhParametreService rhParametreService, RhService rhService, CandidatService candidatService, DetailCandidatService detailCandidatService, GenreService genreService, GradeService gradeService, SerieBacService serieBacService, LangueService langueService, ExperienceService experienceService, FormationService formationService, EmployeService employeService) {
        this.rhParametreService = rhParametreService;
        this.rhService = rhService;
        this.candidatService = candidatService;
        this.detailCandidatService = detailCandidatService;
        this.genreService = genreService;
        this.gradeService = gradeService;
        this.serieBacService = serieBacService;
        this.langueService = langueService;
        this.experienceService = experienceService;
        this.formationService = formationService;
        this.employeService = employeService;
    }


    @GetMapping
    public String accueil() {
        return "redirect:/administratif/rh/gestion-employes";
    }

    private final EmployeService employeService;

    @GetMapping("/gestion-employes")
    public String gestiontEmployes(Model model) {
        model.addAttribute("employesInfos", employeService.getEmployeInfos());
        return "administratif/rh/gestion-employes";
    }

    @GetMapping("/gestion-salaires")
    public String gestionSalaires(Model model) {
        List<StatutEmploye> statutEmployes = rhService.getAllEmployesActifs();
        List<Employe> employes = statutEmployes.stream()
            .map(StatutEmploye::getEmploye)
            .collect(Collectors.toList());
        model.addAttribute("variationSalaireNet", rhService.variationSalaireNet());
        model.addAttribute("variationCommission", rhService.variationCommission());
        model.addAttribute("variationAvance", rhService.variationAvance());
        model.addAttribute("employes", employes);

        return "administratif/rh/gestion-salaires";
    }

    @GetMapping("/gestion-recrutements")
    public String gestionRecrutements(Model model) {
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

        return "administratif/rh/gestion-recrutements";
    }

    @GetMapping("/gestion-conges")
    public String gestionConges(Model model) {
        List<StatutEmploye> statutEmployes = rhService.getAllEmployesActifs();
        List<Employe> employes = statutEmployes.stream()
            .map(StatutEmploye::getEmploye)
            .collect(Collectors.toList());
        model.addAttribute("employes", employes);
        model.addAttribute("nbjCongeUtilise", rhService.nbjCongeUtilise(employes));
        model.addAttribute("nbjCongeReserve", rhService.nbjCongeReserve(employes));
        model.addAttribute("nbjCongeNonUtilise", rhService.nbjCongeNonUtilise(employes));
        model.addAttribute("typeConges", rhService.getAllTypeConges());
        return "administratif/rh/gestion-conges";
    }

    @GetMapping("/parametre")
    public String parametre(Model model) {
        List<Irsa> irsas = rhParametreService.getIrsaService().findAll();
        if (!model.containsAttribute("jourFerie")) {
            model.addAttribute("jourFerie", new JourFerie());
        }
        if (!model.containsAttribute("grade")) {
            model.addAttribute("grade", new Grade());
        }
        if (!model.containsAttribute("irsa")) {
            model.addAttribute("irsa", new Irsa());
        }
        if (model.containsAttribute("irsaWrapper")) {
            IrsaWrapper irsaWrapper = (IrsaWrapper) model.getAttribute("irsaWrapper");

            // Fusionner ou synchroniser avec les données réelles
            for (Irsa dbIrsa : irsas) {
                boolean existe = irsaWrapper.getIrsas().stream()
                    .anyMatch(i -> i.getId() != null && i.getId().equals(dbIrsa.getId()));
                if (!existe) {
                    irsaWrapper.addIrsa(dbIrsa);
                }
            }

            model.addAttribute("irsaWrapper", irsaWrapper);
        } else {
            IrsaWrapper irsaWrapper = new IrsaWrapper();
            irsaWrapper.setIrsas(rhParametreService.getIrsaService().findAll());
            model.addAttribute("irsaWrapper", irsaWrapper);
        }

        model.addAttribute("listeJoursFeries", rhParametreService.getJourFerieService().findAll());
        model.addAttribute("listeGrades", rhParametreService.getGradeService().findAll());
        model.addAttribute("listeIrsas", irsas);

        return "administratif/rh/parametrage";
    }

}
