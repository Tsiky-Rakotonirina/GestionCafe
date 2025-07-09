package com.gestioncafe.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gestioncafe.model.MatierePremiere;
import com.gestioncafe.model.MouvementStock;
import com.gestioncafe.repository.MatierePremiereRepository;
import com.gestioncafe.repository.MouvementStockRepository;

@Service
public class MatierePremiereService {
    @Autowired
    private MatierePremiereRepository matierePremiereRepository;
    @Autowired(required = false)
    private MouvementStockRepository mouvementStockRepository;

    // --- Méthodes statistiques (stock) ---
    public Map<String, Object> getStatistiques() {
        Map<String, Object> stats = new HashMap<>();
        LocalDate now = LocalDate.now();
        List<String> periodes = new ArrayList<>();
        for (int i = 5; i >= 0; i--) {
            LocalDate date = now.minusMonths(i);
            periodes.add(date.getMonth().toString() + " " + date.getYear());
        }
        List<MatierePremiere> matieres = matierePremiereRepository.findAll();
        Map<String, Map<String, Double>> quantiteParMatiereParMois = new LinkedHashMap<>();
        Map<String, Map<String, Double>> coutParMatiereParMois = new LinkedHashMap<>();
        Map<String, Map<String, Double>> perteQuantiteParMois = new LinkedHashMap<>();
        Map<String, Map<String, Double>> perteCoutParMois = new LinkedHashMap<>();
        for (int i = 0; i < 6; i++) {
            LocalDate startDate = now.minusMonths(i).with(TemporalAdjusters.firstDayOfMonth());
            LocalDate endDate = now.minusMonths(i).with(TemporalAdjusters.lastDayOfMonth());
            LocalDateTime startDateTime = startDate.atStartOfDay();
            LocalDateTime endDateTime = endDate.atTime(23, 59, 59);
            String periode = periodes.get(i);
            Map<String, Double> quantiteParMatiere = new HashMap<>();
            Map<String, Double> coutParMatiere = new HashMap<>();
            Map<String, Double> perteQuantite = new HashMap<>();
            Map<String, Double> perteCout = new HashMap<>();
            for (MatierePremiere mp : matieres) {
                if (mouvementStockRepository == null) continue;
                List<MouvementStock> mouvements = mouvementStockRepository.findByMatiereAndDates(
                    mp.getId(), startDateTime, endDateTime);
                double quantite = mouvements.stream()
                    .mapToDouble(MouvementStock::getQuantite)
                    .sum();
                double cout = quantite * 10; // À adapter selon votre modèle
                quantiteParMatiere.put(mp.getNom(), quantite);
                coutParMatiere.put(mp.getNom(), cout);
                double perte = quantite * 0.05;
                perteQuantite.put(mp.getNom(), perte);
                perteCout.put(mp.getNom(), perte * 10);
            }
            quantiteParMatiereParMois.put(periode, quantiteParMatiere);
            coutParMatiereParMois.put(periode, coutParMatiere);
            perteQuantiteParMois.put(periode, perteQuantite);
            perteCoutParMois.put(periode, perteCout);
        }
        Map<String, Double> dureeMoyenneStock = matieres.stream()
            .collect(Collectors.toMap(
                MatierePremiere::getNom,
                mp -> calculerDureeMoyenneStock(mp.getId())
            ));
        stats.put("periodes", periodes);
        stats.put("matieres", matieres.stream().map(MatierePremiere::getNom).collect(Collectors.toList()));
        stats.put("quantiteParMatiereParMois", quantiteParMatiereParMois);
        stats.put("coutParMatiereParMois", coutParMatiereParMois);
        stats.put("perteQuantiteParMois", perteQuantiteParMois);
        stats.put("perteCoutParMois", perteCoutParMois);
        stats.put("dureeMoyenneStock", dureeMoyenneStock);
        return stats;
    }

    public double calculerDureeMoyenneStock(Long matiereId) {
        if (mouvementStockRepository == null) return 0;
        List<MouvementStock> mouvements = mouvementStockRepository.findByMatierePremiereId(matiereId);
        if (mouvements.isEmpty()) {
            return 0;
        }
        mouvements.sort(Comparator.comparing(MouvementStock::getDateMouvement));
        long totalDays = 0;
        int count = 0;
        for (int i = 1; i < mouvements.size(); i++) {
            LocalDateTime date1 = mouvements.get(i-1).getDateMouvement();
            LocalDateTime date2 = mouvements.get(i).getDateMouvement();
            long daysBetween = java.time.Duration.between(date1, date2).toDays();
            totalDays += Math.abs(daysBetween);
            count++;
        }
        return count > 0 ? (double) totalDays / count : 0;
    }

    // --- CRUD et utilitaires (production) ---
    public List<MatierePremiere> findAll() {
        return matierePremiereRepository.findAll();
    }
    public Optional<MatierePremiere> findById(Long id) {
        return matierePremiereRepository.findById(id);
    }
    public MatierePremiere save(MatierePremiere mp) {
        return matierePremiereRepository.save(mp);
    }
    public void deleteById(Long id) {
        matierePremiereRepository.deleteById(id);
    }
    public BigDecimal getPrixUnitaire(Long idMatierePremiere) {
        return matierePremiereRepository.findById(idMatierePremiere)
            .map(mp -> {
                var historiques = mp.getHistoriquesEstimation();
                if (historiques != null && !historiques.isEmpty()) {
                    return historiques.stream()
                        .filter(h -> h.getPrix() != null)
                        .max((h1, h2) -> {
                            if (h1.getDateApplication() == null) return -1;
                            if (h2.getDateApplication() == null) return 1;
                            return h1.getDateApplication().compareTo(h2.getDateApplication());
                        })
                        .map(h -> {
                            Number prix = h.getPrix();
                            return new BigDecimal(prix.toString());
                        })
                        .orElse(BigDecimal.ZERO);
                }
                return BigDecimal.ZERO;
            })
            .orElse(BigDecimal.ZERO);
    }
}
