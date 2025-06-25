package com.gestioncafe.service.rh;

import java.math.BigDecimal;
import java.util.List;

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

        // Vérification de positivité
        if (minSal.compareTo(BigDecimal.ZERO) <= 0 || maxSal.compareTo(BigDecimal.ZERO) <= 0) {
            throw new Exception("Les salaires doivent être strictement positifs");
        }

        // Vérification logique : min < max
        if (minSal.compareTo(maxSal) >= 0) {
            throw new Exception("Le salaire min doit être strictement inférieur au salaire max");
        }

        // Vérification de disjonctivité
        for (Irsa existant : all) {
            BigDecimal minExist = existant.getSalaireMin();
            BigDecimal maxExist = existant.getSalaireMax();

            boolean chevauchement = !(maxSal.compareTo(minExist) < 0 || minSal.compareTo(maxExist) > 0);

            if (chevauchement) {
                throw new Exception("La tranche saisie chevauche une tranche existante");
            }
        }

        return irsaRepository.save(irsa);
    }

    public void delete(Irsa irsa) {
        irsaRepository.delete(irsa);
    }

    public void deleteById(Long id) {
        irsaRepository.deleteById(id);
    }
}
