package com.gestioncafe.repository.production;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.gestioncafe.dto.VenteProduitStatDTO;
import com.gestioncafe.model.production.DetailsVente;

public interface DetailsVenteRepository extends JpaRepository<DetailsVente, Integer> {
    @Query("SELECT new com.gestioncafe.dto.VenteProduitStatDTO(d.produit.id, d.produit.nom, SUM(d.quantite), SUM(d.montant)) " +
           "FROM DetailsVente d GROUP BY d.produit.id, d.produit.nom")
    List<VenteProduitStatDTO> getVenteStatParProduit();
}
