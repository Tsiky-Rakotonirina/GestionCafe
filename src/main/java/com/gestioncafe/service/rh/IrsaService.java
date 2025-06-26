package com.gestioncafe.service.rh;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gestioncafe.model.rh.Irsa;
import com.gestioncafe.repository.rh.IrsaRepository;

@Service
public class IrsaService {
    @Autowired
    private IrsaRepository irsaRepository;

    public List<Irsa> findAll() {
        return irsaRepository.findAll();
    }

    public Irsa findById(Long id) {
        return irsaRepository.findById(id).orElseThrow();
    }

    public Irsa save(Irsa irsa) throws Exception {
        List<Irsa> all = findAll();

        BigDecimal minSal = irsa.getSalaireMin();
        BigDecimal maxSal = irsa.getSalaireMax();

        // 1. Ignorer les lignes totalement vides
        if (minSal == null && maxSal == null && irsa.getTaux() == null) {
            return null;
        }

        // 2. S'assurer que la première tranche commence à 0
        boolean aucuneCommenceAZero = all.stream()
                .noneMatch(i -> i.getSalaireMin() != null && i.getSalaireMin().compareTo(BigDecimal.ZERO) == 0);
        if (aucuneCommenceAZero) {
            irsa.setSalaireMin(BigDecimal.ZERO);
            minSal = BigDecimal.ZERO;
        }

        // 3. Vérification des champs obligatoires et cohérence
        if (minSal == null)
            throw new Exception("Le salaire minimum est obligatoire.");
        if (minSal.compareTo(BigDecimal.ZERO) < 0)
            throw new Exception("Le salaire minimum doit être positif.");
        if (maxSal != null && maxSal.compareTo(BigDecimal.ZERO) <= 0)
            throw new Exception("Le salaire maximum doit être strictement positif.");
        if (maxSal != null && minSal.compareTo(maxSal) >= 0)
            throw new Exception("Le salaire min doit être strictement inférieur au salaire max.");

        // 4. Vérifier le chevauchement avec les tranches existantes
        for (Irsa existant : all) {
            if (irsa.getId() != null && irsa.getId().equals(existant.getId()))
                continue;

            BigDecimal minExist = existant.getSalaireMin();
            BigDecimal maxExist = existant.getSalaireMax();

            boolean chevauchement;

            // Refuser doublon de taux
            if (existant.getTaux() != null && existant.getTaux().compareTo(irsa.getTaux()) == 0) {
                throw new Exception("Une tranche avec le taux " + irsa.getTaux() + "% existe déjà.");
            }
            if (maxSal == null) {
                chevauchement = maxExist == null || minSal.compareTo(maxExist) <= 0;
            } else if (maxExist == null) {
                chevauchement = maxSal.compareTo(minExist) > 0;
            } else {
                chevauchement = !(maxSal.compareTo(minExist) <= 0 || minSal.compareTo(maxExist) >= 0);
            }

            if (chevauchement) {
                throw new Exception("La tranche saisie chevauche une tranche existante.");
            }
        }

        // 5. Vérifier la continuité de toutes les tranches
        List<Irsa> toutes = new ArrayList<>(all);
        if (irsa.getId() != null) {
            toutes.removeIf(i -> i.getId().equals(irsa.getId()));
        }
        toutes.add(irsa);

        // Tri par salaireMin
        toutes.sort(Comparator.comparing(Irsa::getSalaireMin));

        for (int i = 0; i < toutes.size() - 1; i++) {
            Irsa current = toutes.get(i);
            Irsa next = toutes.get(i + 1);

            if (current.getSalaireMax() == null) {
                throw new Exception("Une tranche ouverte (≥ " + current.getSalaireMin() + ") doit être la dernière.");
            }

            if (next.getSalaireMin() == null) {
                throw new Exception("Tranche invalide : salaire min manquant.");
            }

            if (current.getSalaireMax().add(new BigDecimal(1)).compareTo(next.getSalaireMin()) != 0) {
                throw new Exception("Les tranches IRSA ne sont pas continues entre " +
                        current.getSalaireMax() + " et " + next.getSalaireMin());
            }
        }

        return irsaRepository.save(irsa);
    }

    @Transactional
    public void delete(Irsa irsa) throws Exception {
        deleteById(irsa.getId());
    }

    @Transactional
    public void deleteById(Long id) throws Exception {
        // Charger toutes les tranches actuelles
        List<Irsa> tranches = findAll();

        // Rechercher la tranche à supprimer
        Irsa aSupprimer = tranches.stream()
                .filter(i -> i.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new Exception("Tranche introuvable pour suppression"));

        // Simuler la suppression
        tranches.remove(aSupprimer);

        // Vérifier la continuité
        tranches.sort(Comparator.comparing(Irsa::getSalaireMin));
        for (int i = 0; i < tranches.size() - 1; i++) {
            Irsa actuelle = tranches.get(i);
            Irsa suivante = tranches.get(i + 1);

            if (actuelle.getSalaireMax() == null) {
                throw new Exception("La tranche ouverte ≥ " + actuelle.getSalaireMin() + " doit être la dernière.");
            }

            if (!actuelle.getSalaireMax().add(new BigDecimal(1)).equals(suivante.getSalaireMin())) {
                throw new Exception("Suppression invalide : discontinuité entre "
                        + actuelle.getSalaireMax() + " et " + suivante.getSalaireMin());
            }
        }

        // Si tout est valide, procéder à la suppression réelle
        irsaRepository.deleteById(id);
    }

}
