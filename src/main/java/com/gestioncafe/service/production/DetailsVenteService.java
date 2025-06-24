package com.gestioncafe.service.production;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.gestioncafe.dto.BeneficePeriodeStatDTO;
import com.gestioncafe.dto.VentePeriodeStatDTO;
import com.gestioncafe.dto.VenteProduitStatDTO;
import com.gestioncafe.model.production.DetailsVente;
import com.gestioncafe.repository.production.DetailsVenteRepository;

@Service
public class DetailsVenteService {
    private final DetailsVenteRepository detailsVenteRepository;

    public DetailsVenteService(DetailsVenteRepository detailsVenteRepository) {
        this.detailsVenteRepository = detailsVenteRepository;
    }

    public List<DetailsVente> findAll() {
        return detailsVenteRepository.findAll();
    }

    public List<VenteProduitStatDTO> getVenteStatParProduit() {
        return detailsVenteRepository.getVenteStatParProduit();
    }

    public List<VentePeriodeStatDTO> getVenteStatParPeriode(String periode) {
        return detailsVenteRepository.getVenteStatParPeriode(periode);
    }

    public List<VentePeriodeStatDTO> getVenteStatParPeriodeEtProduit(String periode, Integer produitId) {
        return detailsVenteRepository.getVenteStatParPeriodeEtProduit(periode, produitId);
    }

    public List<BeneficePeriodeStatDTO> getBeneficeStatParPeriode(String periode) {
        return detailsVenteRepository.getBeneficeStatParPeriode(periode);
    }

    public Optional<DetailsVente> findById(Integer id) {
        return detailsVenteRepository.findById(id);
    }

    public DetailsVente save(DetailsVente detailsVente) {
        return detailsVenteRepository.save(detailsVente);
    }

    public void deleteById(Integer id) {
        detailsVenteRepository.deleteById(id);
    }

    public List<VenteProduitStatDTO> getVenteStatParProduitAvecDate(LocalDateTime dateDebut, LocalDateTime dateFin) {
        return detailsVenteRepository.getVenteStatParProduitAvecDate(dateDebut, dateFin);
    }

    public List<VenteProduitStatDTO> getVenteStatParProduitFiltre(
            java.time.LocalDate dateExacte,
            Integer annee,
            Integer mois,
            Integer jourMois,
            Integer jourSemaine,
            java.time.LocalDateTime dateDebut,
            java.time.LocalDateTime dateFin
    ) {
        return detailsVenteRepository.getVenteStatParProduitFiltre(
            dateExacte, annee, mois, jourMois, jourSemaine, dateDebut, dateFin
        );
    }
}
