package com.gestioncafe.controller.stock;

import com.gestioncafe.model.MatierePremiere;
import com.gestioncafe.model.Unite;
import com.gestioncafe.repository.MatierePremiereRepository;
import com.gestioncafe.repository.UniteRepository;
import com.gestioncafe.service.MatierePremiereStockService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
@RequestMapping("/administratif/stock/matiere-premiere")
public class MatierePremiereStockController {

    private final MatierePremiereRepository matierePremiereRepository;

    private final UniteRepository uniteRepository;

    private final MatierePremiereStockService matierePremiereService;

    private static String UPLOAD_DIR = "src/main/resources/static/images/matiere-premiere/";

    public MatierePremiereStockController(MatierePremiereRepository matierePremiereRepository, UniteRepository uniteRepository, MatierePremiereStockService matierePremiereService) {
        this.matierePremiereRepository = matierePremiereRepository;
        this.uniteRepository = uniteRepository;
        this.matierePremiereService = matierePremiereService;
    }

    @GetMapping("")
    public String listMatierePremiere(Model model, @RequestParam(required = false) String search, @RequestParam(required = false) String sort) {
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

        model.addAttribute("matieresPremieres", matieresPremieres);
        model.addAttribute("unites", uniteRepository.findAll());
        model.addAttribute("search", search);
        model.addAttribute("statistiques", statistiques);
        return "administratif/stock/matiere-premiere/list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("matierePremiere", new MatierePremiere());
        model.addAttribute("unites", uniteRepository.findAll());
        return "administratif/stock/matiere-premiere/add";
    }

    @PostMapping("/save")
    public String saveMatierePremiere(
        @ModelAttribute MatierePremiere matierePremiere,
        @RequestParam("idUnite") Long idUnite, // Ajoutez ce paramètre
        @RequestParam(value = "image", required = false) MultipartFile file,
        RedirectAttributes redirectAttributes) {

        try {
            // Récupérer l'unité correspondante
            Unite unite = uniteRepository.findById(Math.toIntExact(idUnite))
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

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Optional<MatierePremiere> matierePremiere = matierePremiereRepository.findById((long) Math.toIntExact(id));
        if (matierePremiere.isPresent()) {
            model.addAttribute("matierePremiere", matierePremiere.get());
            model.addAttribute("unites", uniteRepository.findAll());
            return "administratif/stock/matiere-premiere/edit";
        }
        return "redirect:/administratif/stock/matiere-premiere";
    }

    @PostMapping("/update")
    public String updateMatierePremiere(@ModelAttribute MatierePremiere matierePremiere,
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

    @GetMapping("/delete/{id}")
    public String deleteMatierePremiere(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        matierePremiereRepository.deleteById((long) Math.toIntExact(id));
        redirectAttributes.addFlashAttribute("success", "Matière première supprimée avec succès");
        return "redirect:/administratif/stock/matiere-premiere";
    }
}