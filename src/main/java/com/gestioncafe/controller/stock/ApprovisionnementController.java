package com.gestioncafe.controller.stock;

import com.gestioncafe.model.*;
import com.gestioncafe.repository.*;
import com.gestioncafe.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/administratif/stock/approvisionnement")
public class ApprovisionnementController {

    private final MatierePremiereRepository matierePremiereRepository;
    private final FournisseurRepository fournisseurRepository;
    private final DetailFournisseurRepository detailFournisseurRepository;
    private final ApprovisionnementRepository approvisionnementRepository;
    private final ApprovisionnementService approvisionnementService;

    public ApprovisionnementController(MatierePremiereRepository matierePremiereRepository,
                                     FournisseurRepository fournisseurRepository,
                                     DetailFournisseurRepository detailFournisseurRepository,
                                     ApprovisionnementRepository approvisionnementRepository,
                                     ApprovisionnementService approvisionnementService) {
        this.matierePremiereRepository = matierePremiereRepository;
        this.fournisseurRepository = fournisseurRepository;
        this.detailFournisseurRepository = detailFournisseurRepository;
        this.approvisionnementRepository = approvisionnementRepository;
        this.approvisionnementService = approvisionnementService;
    }

    @GetMapping
    public String listApprovisionnements(@RequestParam(required = false) String search, Model model) {
        List<Approvisionnement> approvisionnements;
        
        if (search != null && !search.isEmpty()) {
            approvisionnements = approvisionnementRepository.findByMatierePremiereNomContainingIgnoreCase(search);
        } else {
            approvisionnements = approvisionnementRepository.findAll();
        }
        
        model.addAttribute("approvisionnements", approvisionnements);
        model.addAttribute("matieresPremieres", matierePremiereRepository.findAll());
        model.addAttribute("fournisseurs", fournisseurRepository.findAll());
        model.addAttribute("search", search);
        
        return "administratif/stock/approvisionnement/list";
    }

    @GetMapping("/new")
    public String showApprovisionnementForm(@RequestParam Long idMatierePremiere, Model model) {
        MatierePremiere matiere = matierePremiereRepository.findById(idMatierePremiere)
                .orElseThrow(() -> new IllegalArgumentException("Matière première invalide"));
        
        // Récupérer les fournisseurs avec leurs prix pour cette matière première
        List<FournisseurPrix> fournisseurs = detailFournisseurRepository
                .findByMatierePremiereId(idMatierePremiere)
                .stream()
                .map(df -> new FournisseurPrix(
                        df.getFournisseur().getId(),
                        df.getFournisseur().getNom(),
                        df.getPrixUnitaire(),
                        df.getFournisseur().getFrais()
                ))
                .toList();
        
        model.addAttribute("matiere", matiere);
        model.addAttribute("fournisseurs", fournisseurs);
        
        return "administratif/stock/approvisionnement/new";
    }

    @PostMapping("/save")
    public String saveApprovisionnement(@RequestParam Long idMatierePremiere,
                                      @RequestParam Long idFournisseur,
                                      @RequestParam String referenceFacture,
                                      @RequestParam double quantite,
                                      @RequestParam double total,
                                      @RequestParam String dateApprovisionnement,
                                      RedirectAttributes redirectAttributes) {
        
        try {
            approvisionnementService.createApprovisionnement(
                    idMatierePremiere,
                    idFournisseur,
                    referenceFacture,
                    quantite,
                    total,
                    LocalDate.parse(dateApprovisionnement)
            );
            
            redirectAttributes.addFlashAttribute("success", "Approvisionnement enregistré avec succès");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erreur lors de l'enregistrement: " + e.getMessage());
        }
        
        return "redirect:/administratif/stock/approvisionnement";
    }

    // Classe helper pour afficher les fournisseurs avec leurs prix
    public static class FournisseurPrix {
        private Long id;
        private String nom;
        private double prixUnitaire;
        private double frais;

        public FournisseurPrix(Long id, String nom, double prixUnitaire, double frais) {
            this.id = id;
            this.nom = nom;
            this.prixUnitaire = prixUnitaire;
            this.frais = frais;
        }

        // Getters
        public Long getId() { return id; }
        public String getNom() { return nom; }
        public double getPrixUnitaire() { return prixUnitaire; }
        public double getFrais() { return frais; }
    }
}