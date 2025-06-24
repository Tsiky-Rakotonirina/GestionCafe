package com.gestioncafe.repository.production;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gestioncafe.dto.BeneficePeriodeStatDTO;
import com.gestioncafe.dto.VentePeriodeStatDTO;
import com.gestioncafe.dto.VenteProduitStatDTO;
import com.gestioncafe.model.production.DetailsVente;

public interface DetailsVenteRepository extends JpaRepository<DetailsVente, Integer> {
    @Query("SELECT new com.gestioncafe.dto.VenteProduitStatDTO(d.produit.id, d.produit.nom, SUM(d.quantite), SUM(d.montant)) " +
           "FROM DetailsVente d GROUP BY d.produit.id, d.produit.nom")
    List<VenteProduitStatDTO> getVenteStatParProduit();

    @Query("""
        SELECT new com.gestioncafe.dto.VentePeriodeStatDTO(
            CASE WHEN :periode = 'jour' THEN FUNCTION('to_char', v.dateVente, 'YYYY-MM-DD')
                 WHEN :periode = 'semaine' THEN FUNCTION('to_char', v.dateVente, 'IYYY-IW')
                 ELSE FUNCTION('to_char', v.dateVente, 'YYYY-MM') END,
            SUM(d.quantite)
        )
        FROM DetailsVente d JOIN d.vente v
        GROUP BY
            CASE WHEN :periode = 'jour' THEN FUNCTION('to_char', v.dateVente, 'YYYY-MM-DD')
                 WHEN :periode = 'semaine' THEN FUNCTION('to_char', v.dateVente, 'IYYY-IW')
                 ELSE FUNCTION('to_char', v.dateVente, 'YYYY-MM') END
        ORDER BY 1
    """)
    List<VentePeriodeStatDTO> getVenteStatParPeriode(@Param("periode") String periode);

    @Query("""
        SELECT new com.gestioncafe.dto.VentePeriodeStatDTO(
            CASE WHEN :periode = 'jour' THEN FUNCTION('to_char', v.dateVente, 'YYYY-MM-DD')
                 WHEN :periode = 'semaine' THEN FUNCTION('to_char', v.dateVente, 'IYYY-IW')
                 ELSE FUNCTION('to_char', v.dateVente, 'YYYY-MM') END,
            SUM(d.quantite)
        )
        FROM DetailsVente d JOIN d.vente v
        WHERE d.produit.id = :produitId
        GROUP BY
            CASE WHEN :periode = 'jour' THEN FUNCTION('to_char', v.dateVente, 'YYYY-MM-DD')
                 WHEN :periode = 'semaine' THEN FUNCTION('to_char', v.dateVente, 'IYYY-IW')
                 ELSE FUNCTION('to_char', v.dateVente, 'YYYY-MM') END
        ORDER BY 1
    """)
    List<VentePeriodeStatDTO> getVenteStatParPeriodeEtProduit(@Param("periode") String periode, @Param("produitId") Integer produitId);

    @Query("""
        SELECT new com.gestioncafe.dto.BeneficePeriodeStatDTO(
            CASE WHEN :periode = 'jour' THEN FUNCTION('to_char', v.dateVente, 'YYYY-MM-DD')
                 WHEN :periode = 'semaine' THEN FUNCTION('to_char', v.dateVente, 'IYYY-IW')
                 ELSE FUNCTION('to_char', v.dateVente, 'YYYY-MM') END,
            AVG(d.quantite),
            SUM(d.montant)
        )
        FROM DetailsVente d JOIN d.vente v
        GROUP BY
            CASE WHEN :periode = 'jour' THEN FUNCTION('to_char', v.dateVente, 'YYYY-MM-DD')
                 WHEN :periode = 'semaine' THEN FUNCTION('to_char', v.dateVente, 'IYYY-IW')
                 ELSE FUNCTION('to_char', v.dateVente, 'YYYY-MM') END
        ORDER BY 1
    """)
    List<BeneficePeriodeStatDTO> getBeneficeStatParPeriode(@Param("periode") String periode);

    @Query("""
        SELECT new com.gestioncafe.dto.VenteProduitStatDTO(d.produit.id, d.produit.nom, SUM(d.quantite), SUM(d.montant))
        FROM DetailsVente d
        WHERE (:dateDebut IS NULL OR d.vente.dateVente >= :dateDebut)
          AND (:dateFin IS NULL OR d.vente.dateVente <= :dateFin)
        GROUP BY d.produit.id, d.produit.nom
    """)
    List<VenteProduitStatDTO> getVenteStatParProduitAvecDate(@Param("dateDebut") LocalDateTime dateDebut, @Param("dateFin") LocalDateTime dateFin);

    @Query("""
        SELECT new com.gestioncafe.dto.VenteProduitStatDTO(d.produit.id, d.produit.nom, SUM(d.quantite), SUM(d.montant))
        FROM DetailsVente d
        WHERE (:dateExacte IS NULL OR FUNCTION('date', d.vente.dateVente) = :dateExacte)
          AND (:annee IS NULL OR FUNCTION('year', d.vente.dateVente) = :annee)
          AND (:mois IS NULL OR FUNCTION('month', d.vente.dateVente) = :mois)
          AND (:jourMois IS NULL OR FUNCTION('day', d.vente.dateVente) = :jourMois)
          AND (:jourSemaine IS NULL OR FUNCTION('day_of_week', d.vente.dateVente) = :jourSemaine)
          AND (:dateDebut IS NULL OR d.vente.dateVente >= :dateDebut)
          AND (:dateFin IS NULL OR d.vente.dateVente <= :dateFin)
        GROUP BY d.produit.id, d.produit.nom
    """)
    List<VenteProduitStatDTO> getVenteStatParProduitFiltre(
        @Param("dateExacte") LocalDate dateExacte,
        @Param("annee") Integer annee,
        @Param("mois") Integer mois,
        @Param("jourMois") Integer jourMois,
        @Param("jourSemaine") Integer jourSemaine,
        @Param("dateDebut") LocalDateTime dateDebut,
        @Param("dateFin") LocalDateTime dateFin
    );
}
