package com.gestioncafe.controller;

import com.gestioncafe.model.*;
import com.gestioncafe.repository.CategorieUniteRepository;
import com.gestioncafe.repository.MatierePremiereRepository;
import com.gestioncafe.repository.UniteRepository;
import com.gestioncafe.service.MatierePremiereService;
import com.gestioncafe.service.DetailFournisseurService;
import com.gestioncafe.service.production.HistoriqueEstimationService;
import com.gestioncafe.service.production.SeuilMatierePremiereService;
import com.gestioncafe.service.FournisseurService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Controller
public class MatierePremiereController {
    // Dépendances communes
    private final MatierePremiereRepository matierePremiereRepository;
    private final UniteRepository uniteRepository;
    private final MatierePremiereService matierePremiereService;
    // Pour production
    private final CategorieUniteRepository categorieUniteRepository;
    private final DetailFournisseurService detailFournisseurService;
    private final SeuilMatierePremiereService seuilMatierePremiereService;
    private final HistoriqueEstimationService historiqueEstimationService;
    private final FournisseurService fournisseurService;

    private static String UPLOAD_DIR = "src/main/resources/static/images/matiere-premiere/";

    public MatierePremiereController(MatierePremiereRepository matierePremiereRepository, UniteRepository uniteRepository, MatierePremiereService matierePremiereService, CategorieUniteRepository categorieUniteRepository, DetailFournisseurService detailFournisseurService, SeuilMatierePremiereService seuilMatierePremiereService, HistoriqueEstimationService historiqueEstimationService, FournisseurService fournisseurService) {
        this.matierePremiereRepository = matierePremiereRepository;
        this.uniteRepository = uniteRepository;
        this.matierePremiereService = matierePremiereService;
        this.categorieUniteRepository = categorieUniteRepository;
        this.detailFournisseurService = detailFournisseurService;
        this.seuilMatierePremiereService = seuilMatierePremiereService;
        this.historiqueEstimationService = historiqueEstimationService;
        this.fournisseurService = fournisseurService;
    }

    // --- STOCK ---
    @RequestMapping("/administratif/stock/matiere-premiere")
    public String listStock(Model model, @RequestParam(required = false) String search, @RequestParam(required = false) String sort) {
        List<MatierePremiere> matieresPremieres;
        if (search != null && !search.isEmpty()) {
            matieresPremieres = matierePremiereRepository.findByNomContainingIgnoreCase(search);
        } else {
            matieresPremieres = matierePremiereRepository.findAll();
        }
        if (sort != null) {
            switch (sort) {
                case "nom":
                    matieresPremieres.sort((a, b) -> a.getNom().compareToIgnoreCase(b.getNom()));
                    break;
                case "stock":
                    matieresPremieres.sort((a, b) -> a.getStock().compareTo(b.getStock()));
                    break;
            }
        }
        model.addAttribute("matieresPremieres", matieresPremieres);
        model.addAttribute("unites", uniteRepository.findAll());
        model.addAttribute("search", search);
        Map<String, Object> statistiques = matierePremiereService.getStatistiques();
        model.addAttribute("statistiques", statistiques);
        return "administratif/stock/matiere-premiere/list";
    }

    @GetMapping("/administratif/stock/matiere-premiere/add")
    public String showAddFormStock(Model model) {
        model.addAttribute("matierePremiere", new MatierePremiere());
        model.addAttribute("unites", uniteRepository.findAll());
        return "administratif/stock/matiere-premiere/add";
    }

    @PostMapping("/administratif/stock/matiere-premiere/save")
    public String saveMatierePremiereStock(
        @ModelAttribute MatierePremiere matierePremiere,
        @RequestParam("idUnite") Long idUnite,
        @RequestParam(value = "image", required = false) MultipartFile file,
        RedirectAttributes redirectAttributes) {
        try {
            Unite unite = uniteRepository.findById(idUnite)
                .orElseThrow(() -> new IllegalArgumentException("Unité invalide"));
            matierePremiere.setUnite(unite);
            if (file != null && !file.isEmpty()) {
                String fileName = storeFile(file);
                // matierePremiere.setImage(fileName);
            }
            matierePremiere.setStock(0.0);
            matierePremiereRepository.save(matierePremiere);
            redirectAttributes.addFlashAttribute("success", "Matière première ajoutée avec succès");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erreur lors de l'ajout: " + e.getMessage());
        }
        return "redirect:/administratif/stock/matiere-premiere";
    }

    @GetMapping("/administratif/stock/matiere-premiere/edit/{id}")
    public String showEditFormStock(@PathVariable Long id, Model model) {
        Optional<MatierePremiere> matierePremiere = matierePremiereRepository.findById(id);
        if (matierePremiere.isPresent()) {
            model.addAttribute("matierePremiere", matierePremiere.get());
            model.addAttribute("unites", uniteRepository.findAll());
            return "administratif/stock/matiere-premiere/edit";
        }
        return "redirect:/administratif/stock/matiere-premiere";
    }

    @PostMapping("/administratif/stock/matiere-premiere/update")
    public String updateMatierePremiereStock(@ModelAttribute MatierePremiere matierePremiere,
                                             @RequestParam("image") MultipartFile file,
                                             RedirectAttributes redirectAttributes) {
        MatierePremiere existing = matierePremiereRepository.findById(matierePremiere.getId()).orElse(null);
        if (existing != null) {
            existing.setNom(matierePremiere.getNom());
            existing.setUnite(matierePremiere.getUnite());
            matierePremiereRepository.save(existing);
            redirectAttributes.addFlashAttribute("success", "Matière première mise à jour avec succès");
        }
        return "redirect:/administratif/stock/matiere-premiere";
    }

    @GetMapping("/administratif/stock/matiere-premiere/delete/{id}")
    public String deleteMatierePremiereStock(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        matierePremiereRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("success", "Matière première supprimée avec succès");
        return "redirect:/administratif/stock/matiere-premiere";
    }

    // --- PRODUCTION ---

    @GetMapping("/administratif/production/matiere-premiere")
    public String listProduction(Model model) {
        model.addAttribute("matieres", matierePremiereRepository.findAll());
        return "administratif/production/matiere-premiere/list";
    }

    @GetMapping("/administratif/production/matiere-premiere/ajouter")
    public String ajouterFormProduction(Model model) {
        model.addAttribute("matierePremiere", new MatierePremiere());
        model.addAttribute("unites", uniteRepository.findAll());
        model.addAttribute("categoriesUnite", categorieUniteRepository.findAll());
        return "administratif/production/matiere-premiere/form";
    }

    @PostMapping("/administratif/production/matiere-premiere/ajouter")
    public String ajouterProduction(@ModelAttribute MatierePremiere matierePremiere,
                                    @RequestParam("imageFile") MultipartFile imageFile,
                                    Model model) throws IOException {
        if (imageFile != null && !imageFile.isEmpty()) {
            String uploadDir = "uploads/";
            File dir = new File(uploadDir);
            if (!dir.exists()) dir.mkdirs();
            String fileName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();
            File dest = new File(uploadDir + fileName);
            imageFile.transferTo(dest);
            matierePremiere.setImage("/" + uploadDir + fileName);
        }
        matierePremiereRepository.save(matierePremiere);
        return "redirect:/administratif/production/matiere-premiere";
    }

    @GetMapping("/administratif/production/matiere-premiere/modifier/{id}")
    public String modifierFormProduction(@PathVariable Long id, Model model) {
        model.addAttribute("matierePremiere", matierePremiereRepository.findById(id).orElseThrow());
        model.addAttribute("unites", uniteRepository.findAll());
        model.addAttribute("categoriesUnite", categorieUniteRepository.findAll());
        return "administratif/production/matiere-premiere/form";
    }

    @PostMapping("/administratif/production/matiere-premiere/modifier/{id}")
    public String modifierProduction(@PathVariable Long id,
                                     @ModelAttribute MatierePremiere matierePremiere,
                                     @RequestParam("imageFile") MultipartFile imageFile,
                                     Model model) throws IOException {
        MatierePremiere existant = matierePremiereRepository.findById(id).orElseThrow();
        existant.setNom(matierePremiere.getNom());
        existant.setUnite(matierePremiere.getUnite());
        if (imageFile != null && !imageFile.isEmpty()) {
            String uploadDir = "uploads/";
            File dir = new File(uploadDir);
            if (!dir.exists()) dir.mkdirs();
            String fileName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();
            File dest = new File(uploadDir + fileName);
            imageFile.transferTo(dest);
            existant.setImage("/" + uploadDir + fileName);
        }
        matierePremiereRepository.save(existant);
        return "redirect:/administratif/production/matiere-premiere";
    }

    @GetMapping("/administratif/production/matiere-premiere/supprimer/{id}")
    public String supprimerProduction(@PathVariable Long id) {
        matierePremiereRepository.deleteById(id);
        return "redirect:/administratif/production/matiere-premiere";
    }

    @GetMapping("/administratif/production/matiere-premiere/details/{id}")
    public String detailsProduction(@PathVariable Long id, Model model) {
        MatierePremiere mp = matierePremiereRepository.findById(id).orElseThrow();
        List<DetailFournisseur> prixFournisseurs = detailFournisseurService.findByMatierePremiere(mp);
        List<SeuilMatierePremiere> seuils = seuilMatierePremiereService.findByMatierePremiere(mp);
        List<HistoriqueEstimation> historiques = historiqueEstimationService.findByMatierePremiere(mp);
        model.addAttribute("matierePremiere", mp);
        model.addAttribute("prixFournisseurs", prixFournisseurs);
        model.addAttribute("seuils", seuils);
        model.addAttribute("historiques", historiques);
        model.addAttribute("fournisseurs", fournisseurService.getAllFournisseurs());
        return "administratif/production/matiere-premiere/details";
    }

    // --- Utilitaire pour upload ---
    private String storeFile(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        Path uploadPath = Paths.get(UPLOAD_DIR);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        Path filePath = uploadPath.resolve(fileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        return fileName;
    }
}
