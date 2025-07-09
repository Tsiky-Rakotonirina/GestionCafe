package com.gestioncafe.controller.rh;

import com.gestioncafe.model.*;
import com.gestioncafe.service.rh.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/administratif/rh")
public class RhController {

    private final RhParametreService rhParametreService;
    private final RhService rhService;
    private final RhSalaireService rhSalaireService;
    private final RhCongeService rhCongeService;
    private final CandidatService candidatService;
    private final DetailCandidatService detailCandidatService;
    private final GenreService genreService;
    private final GradeService gradeService;
    private final SerieBacService serieBacService;
    private final LangueService langueService;
    private final ExperienceService experienceService;
    private final FormationService formationService;
    private final EmployeService employeService;

    public RhController(RhParametreService rhParametreService, RhService rhService, RhSalaireService rhSalaireService,
                        RhCongeService rhCongeService, CandidatService candidatService,
                        DetailCandidatService detailCandidatService, GenreService genreService, GradeService gradeService,
                        SerieBacService serieBacService, LangueService langueService, ExperienceService experienceService,
                        FormationService formationService, EmployeService employeService) {
        this.rhParametreService = rhParametreService;
        this.rhService = rhService;
        this.rhSalaireService = rhSalaireService;
        this.rhCongeService = rhCongeService;
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

    @GetMapping("/gestion-employes")
    public String gestiontEmployes(Model model) {
        model.addAttribute("employesInfos", employeService.getEmployeInfos());
        return "administratif/rh/gestion-employes";
    }

    @GetMapping("/gestion-employes/{id}")
    public String detailsEmploye(@org.springframework.web.bind.annotation.PathVariable Long id, Model model) {
        model.addAttribute("employesInfos", employeService.getEmployeInfos());
        // Récupérer les détails de l'employé (à adapter selon ta structure de service)
        var detail = employeService.getEmployeDetail(id);
        model.addAttribute("employeDetail", detail);
        return "administratif/rh/gestion-employes";
    }

    @GetMapping("/gestion-salaires")
    public String gestionSalaires(Model model) {
        List<StatutEmploye> statutEmployes = rhService.getAllEmployesActifs();
        List<Employe> employes = statutEmployes.stream()
            .map(StatutEmploye::getEmploye)
            .collect(Collectors.toList());

        List<Map<String, Object>> varSN = rhService.variationSalaireNet();
        List<Map<String, Object>> varCOMM = rhService.variationCommission();
        List<Map<String, Object>> varAVC = rhService.variationAvance();

        System.out.println("== Variation Salaire Net ==");
        varSN.forEach(map -> System.out.println(map));

        System.out.println("== Variation Commission ==");
        varCOMM.forEach(map -> System.out.println(map));

        System.out.println("== Variation Avance ==");
        varAVC.forEach(map -> System.out.println(map));

        model.addAttribute("variationSalaireNet", varSN);
        model.addAttribute("variationCommission", varCOMM);
        model.addAttribute("variationAvance", varAVC);
        model.addAttribute("employes", employes);

        return "administratif/rh/gestion-salaires";
    }

    @GetMapping("/gestion-recrutements")
    public String gestionRecrutements(Model model) {
        List<Candidat> candidats = candidatService.getAllCandidats();

        Map<Long, List<DetailCandidat>> detailsMap = candidats.stream()
            .collect(Collectors.toMap(
                Candidat::getId,
                candidat -> detailCandidatService.getDetailsByCandidatId(candidat.getId())));

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
            if (irsaWrapper != null && irsaWrapper.getIrsas() != null) {
                for (Irsa dbIrsa : irsas) {
                    boolean existe = irsaWrapper.getIrsas().stream()
                        .anyMatch(i -> i.getId() != null && i.getId().equals(dbIrsa.getId()));
                    if (!existe) {
                        irsaWrapper.addIrsa(dbIrsa);
                    }
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

    @GetMapping("/commission/{id}")
    public String voirCommission(@org.springframework.web.bind.annotation.PathVariable Long id, Model model) {
        try {
            // Charger les informations de l'employé
            var employe = rhSalaireService.getEmployeById(id);
            model.addAttribute("employe", employe);

            // Charger les commissions de l'employé
            var commissions = rhSalaireService.getCommissionsByEmployeId(id);
            model.addAttribute("commissions", commissions);

            // Charger les raisons de commission pour le formulaire d'ajout
            var raisonCommissions = rhSalaireService.getAllRaisonCommissions();
            model.addAttribute("raisonCommissions", raisonCommissions);

            return "administratif/rh/commission";
        } catch (RuntimeException e) {
            model.addAttribute("erreur", "Employé non trouvé avec l'ID: " + id);
            return "redirect:/administratif/rh/gestion-employes";
        }
    }

    @GetMapping("/fiche-de-paie/{id}")
    public String voirFicheDePaie(@org.springframework.web.bind.annotation.PathVariable Long id, Model model) {
        try {
            // Charger les informations de l'employé
            var employe = rhSalaireService.getEmployeById(id);
            model.addAttribute("employe", employe);

            // Charger les fiches de paie de l'employé
            var ficheDePaies = rhSalaireService.getFicheDePaiesByEmployeId(id);
            model.addAttribute("ficheDePaies", ficheDePaies);

            // Charger les paiements effectués pour l'employé
            var payements = rhSalaireService.getPayementsByEmployeId(id);
            model.addAttribute("payements", payements);

            return "administratif/rh/fiche-de-paie";
        } catch (RuntimeException e) {
            model.addAttribute("erreur", "Employé non trouvé avec l'ID: " + id);
            return "redirect:/administratif/rh/gestion-employes";
        }
    }

    @GetMapping("/avance/{id}")
    public String voirAvance(@org.springframework.web.bind.annotation.PathVariable Long id, Model model) {
        try {
            // Charger les informations de l'employé
            var employe = rhSalaireService.getEmployeById(id);
            model.addAttribute("employe", employe);

            // Charger les avances de l'employé
            var avances = rhSalaireService.getAvancesByEmployeId(id);
            model.addAttribute("avances", avances);

            // Charger le prochain salaire disponible
            double prochainSalaire = rhSalaireService.prochainSalaire(id);
            model.addAttribute("prochainSalaire", prochainSalaire);

            // Charger le montant retenu pour avances
            double retenuPourAvance = rhSalaireService.retenuPourAvance(id);
            model.addAttribute("retenuPourAvance", retenuPourAvance);

            // Charger les raisons d'avance pour le formulaire d'ajout
            var raisonAvances = rhSalaireService.getAllRaisonAvances();
            model.addAttribute("raisonAvances", raisonAvances);

            return "administratif/rh/avance";
        } catch (RuntimeException e) {
            model.addAttribute("erreur", "Employé non trouvé avec l'ID: " + id);
            return "redirect:/administratif/rh/gestion-employes";
        }
    }

    @GetMapping("/conge/{id}")
    public String voirConge(@org.springframework.web.bind.annotation.PathVariable Long id, Model model) {
        try {
            // Charger l'employé sélectionné
            var selectedEmploye = rhSalaireService.getEmployeById(id);
            model.addAttribute("employe", selectedEmploye);
            model.addAttribute("selectedEmploye", selectedEmploye);

            // Charger tous les employés pour la sidebar
            List<StatutEmploye> statutEmployes = rhService.getAllEmployesActifs();
            List<Employe> tousLesEmployes = statutEmployes.stream()
                .map(StatutEmploye::getEmploye)
                .collect(Collectors.toList());
            model.addAttribute("employes", tousLesEmployes);

            // Charger les congés de l'employé sélectionné
            var conges = rhCongeService.getCongesByEmployeId(id);
            model.addAttribute("conges", conges);

            // Charger les types de congé pour le formulaire d'ajout
            var typeConges = rhService.getAllTypeConges();
            model.addAttribute("typeConges", typeConges);

            // Charger les statistiques pour tous les employés (pour l'affichage dans la sidebar et le détail)
            var nbjCongeUtilise = rhService.nbjCongeUtilise(tousLesEmployes);
            var nbjCongeReserve = rhService.nbjCongeReserve(tousLesEmployes);
            var nbjCongeNonUtilise = rhService.nbjCongeNonUtilise(tousLesEmployes);

            model.addAttribute("nbjCongeUtilise", nbjCongeUtilise);
            model.addAttribute("nbjCongeReserve", nbjCongeReserve);
            model.addAttribute("nbjCongeNonUtilise", nbjCongeNonUtilise);

            return "administratif/rh/gestion-conges";
        } catch (RuntimeException e) {
            model.addAttribute("erreur", "Employé non trouvé avec l'ID: " + id);
            return "redirect:/administratif/rh/gestion-employes";
        }
    }

    @GetMapping("/fiche-employe/{id}")
    public String ficheEmploye(@org.springframework.web.bind.annotation.PathVariable Long id, Model model) {
        try {
            // Charger les informations de l'employé
            var employe = rhSalaireService.getEmployeById(id);
            model.addAttribute("employe", employe);

            // Charger les informations détaillées (infos, commissions, avances, etc.)
            var employeInfos = employeService.getEmployeInfos().stream()
                .filter(info -> info.getEmploye().getId().equals(id))
                .findFirst()
                .orElse(null);
            model.addAttribute("employeInfos", employeInfos);

            // Charger les commissions récentes
            var commissions = rhSalaireService.getCommissionsByEmployeId(id);
            model.addAttribute("commissions", commissions.size() > 5 ? commissions.subList(0, 5) : commissions);

            // Charger les avances récentes
            var avances = rhSalaireService.getAvancesByEmployeId(id);
            model.addAttribute("avances", avances.size() > 5 ? avances.subList(0, 5) : avances);

            // Charger les fiches de paie récentes
            var ficheDePaies = rhSalaireService.getFicheDePaiesByEmployeId(id);
            model.addAttribute("ficheDePaies", ficheDePaies.size() > 3 ? ficheDePaies.subList(0, 3) : ficheDePaies);

            return "administratif/rh/fiche-employe";
        } catch (RuntimeException e) {
            model.addAttribute("erreur", "Employé non trouvé avec l'ID: " + id);
            return "redirect:/administratif/rh/gestion-employes";
        }
    }
}
