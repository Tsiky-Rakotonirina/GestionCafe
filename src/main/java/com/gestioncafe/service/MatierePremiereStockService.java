package com.gestioncafe.service;

import com.gestioncafe.model.MatierePremiere;
import com.gestioncafe.model.MouvementStock;
import com.gestioncafe.repository.MatierePremiereRepository;
import com.gestioncafe.repository.MouvementStockRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MatierePremiereStockService {

    private final MatierePremiereRepository matierePremiereRepository;

    private final MouvementStockRepository mouvementStockRepository;

    public MatierePremiereStockService(MatierePremiereRepository matierePremiereRepository, MouvementStockRepository mouvementStockRepository) {
        this.matierePremiereRepository = matierePremiereRepository;
        this.mouvementStockRepository = mouvementStockRepository;
    }

    public Map<String, Object> getStatistiques() {
        Map<String, Object> stats = new HashMap<>();

        // Périodes pour les statistiques (6 derniers mois)
        LocalDate now = LocalDate.now();
        List<String> periodes = new ArrayList<>();
        for (int i = 5; i >= 0; i--) {
            LocalDate date = now.minusMonths(i);
            periodes.add(date.getMonth().toString() + " " + date.getYear());
        }

        // Récupérer toutes les matières premières
        List<MatierePremiere> matieres = matierePremiereRepository.findAll();

        // Statistiques par période
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
                List<MouvementStock> mouvements = mouvementStockRepository.findByMatiereAndDates(
                    mp.getId(), startDateTime, endDateTime);

                double quantite = mouvements.stream()
                    .mapToDouble(MouvementStock::getQuantite)
                    .sum();

                // Ici vous devriez avoir un prix unitaire pour calculer le coût
                double cout = quantite * 10; // Exemple: 10€ par unité

                quantiteParMatiere.put(mp.getNom(), quantite);
                coutParMatiere.put(mp.getNom(), cout);

                // Calcul des pertes (exemple simplifié)
                double perte = quantite * 0.05; // 5% de perte
                perteQuantite.put(mp.getNom(), perte);
                perteCout.put(mp.getNom(), perte * 10);
            }

            quantiteParMatiereParMois.put(periode, quantiteParMatiere);
            coutParMatiereParMois.put(periode, coutParMatiere);
            perteQuantiteParMois.put(periode, perteQuantite);
            perteCoutParMois.put(periode, perteCout);
        }

        // Durée moyenne en stock
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
        List<MouvementStock> mouvements = mouvementStockRepository.findByMatierePremiereId(matiereId);

        if (mouvements.isEmpty()) {
            return 0;
        }

        // Trier les mouvements par date croissante
        mouvements.sort(Comparator.comparing(MouvementStock::getDateMouvement));

        long totalDays = 0;
        int count = 0;

        for (int i = 1; i < mouvements.size(); i++) {
            LocalDateTime date1 = mouvements.get(i - 1).getDateMouvement();
            LocalDateTime date2 = mouvements.get(i).getDateMouvement();

            // Calculer la durée entre deux mouvements consécutifs
            long daysBetween = java.time.Duration.between(date1, date2).toDays();
            totalDays += Math.abs(daysBetween);
            count++;
        }

        return count > 0 ? (double) totalDays / count : 0;
    }
}