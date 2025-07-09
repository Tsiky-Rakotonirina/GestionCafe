package com.gestioncafe.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.gestioncafe.dto.FicheClient;

@Service
public class FicheClientService {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    public FicheClient getFicheClient(Long clientId) {
        FicheClient fiche = new FicheClient();
        
        // 1. Informations de base du client + genre (plus de table tiers)
        String sqlBase = """
            SELECT c.id, c.nom, c.prenom, c.email, c.contact,
                   CASE 
                       WHEN c.date_naissance IS NOT NULL THEN 
                           EXTRACT(YEAR FROM AGE(CURRENT_DATE, c.date_naissance))
                       ELSE 
                           EXTRACT(YEAR FROM AGE(CURRENT_DATE, c.date_adhesion))
                   END as age,
                   g.nom as nom_genre,
                   c.date_adhesion as date_adhesion
                   ,(
                       SELECT MAX(v.date_vente)
                       FROM vente v
                       WHERE v.id_client = c.id
                   ) as derniere_date_vente
            FROM client c
            LEFT JOIN genre g ON c.id_genre = g.id
            WHERE c.id = ?
            """;

        jdbcTemplate.queryForObject(sqlBase, (rs, rowNum) -> {
            fiche.setId(rs.getLong("id"));
            fiche.setNom(rs.getString("nom"));
            fiche.setPrenom(rs.getString("prenom"));
            fiche.setEmail(rs.getString("email"));
            fiche.setContact(rs.getString("contact"));
            fiche.setAge(rs.getInt("age"));
            fiche.setNomGenre(rs.getString("nom_genre"));
            fiche.setDateAdhesion(rs.getObject("date_adhesion", java.time.LocalDate.class));
            fiche.setDerniereDateVente(rs.getObject("derniere_date_vente", java.time.LocalDate.class));
            return null;
        }, clientId);
        
        // 2. Statistiques de vente (CA total et nombre de ventes)
        String sqlStats = """
            SELECT COUNT(DISTINCT v.id) as nb_vente,
                   COALESCE(SUM(dv.montant), 0) as ca_total
            FROM client c
            LEFT JOIN vente v ON c.id = v.id_client
            LEFT JOIN details_vente dv ON v.id = dv.id_vente
            WHERE c.id = ?
            """;
        
        jdbcTemplate.queryForObject(sqlStats, (rs, rowNum) -> {
            fiche.setNbVenteTotal(rs.getLong("nb_vente"));
            fiche.setCaTotal(rs.getBigDecimal("ca_total"));
            return null;
        }, clientId);
        
        // 3. Produit le plus acheté
        String sqlProduit = """
            SELECT p.nom, SUM(dv.quantite) as total_quantite
            FROM client c
            INNER JOIN vente v ON c.id = v.id_client
            INNER JOIN details_vente dv ON v.id = dv.id_vente
            INNER JOIN produit p ON dv.id_produit = p.id
            WHERE c.id = ?
            GROUP BY p.id, p.nom
            ORDER BY total_quantite DESC
            LIMIT 1
            """;
        
        List<Map<String, Object>> produitResults = jdbcTemplate.queryForList(sqlProduit, clientId);
        if (!produitResults.isEmpty()) {
            Map<String, Object> produit = produitResults.get(0);
            fiche.setProduitPlusAchete((String) produit.get("nom"));
            fiche.setQuantiteProduitPlusAchete(((Number) produit.get("total_quantite")).intValue());
        }
        
        // 4. Dépenses par commande (min, max, moyenne)
        String sqlDepenses = """
            SELECT MIN(total_vente) as depense_min,
                   MAX(total_vente) as depense_max,
                   AVG(total_vente) as depense_moyenne
            FROM (
                SELECT v.id, SUM(dv.montant) as total_vente
                FROM client c
                INNER JOIN vente v ON c.id = v.id_client
                INNER JOIN details_vente dv ON v.id = dv.id_vente
                WHERE c.id = ?
                GROUP BY v.id
            ) as ventes_totales
            """;
        
        List<Map<String, Object>> depenseResults = jdbcTemplate.queryForList(sqlDepenses, clientId);
        if (!depenseResults.isEmpty()) {
            Map<String, Object> depenses = depenseResults.get(0);
            fiche.setDepenseMin((BigDecimal) depenses.get("depense_min"));
            fiche.setDepenseMax((BigDecimal) depenses.get("depense_max"));
            BigDecimal moyenne = (BigDecimal) depenses.get("depense_moyenne");
            if (moyenne != null) {
                fiche.setDepenseMoyenneParCommande(moyenne.setScale(2, RoundingMode.HALF_UP));
            }
        }
        
        // 5. Analyse temporelle - Intervalle entre les ventes
        String sqlIntervalle = """
            SELECT AVG(EXTRACT(DAY FROM (lead_date - date_vente))) as intervalle_moyen
            FROM (
                SELECT v.date_vente,
                       LEAD(v.date_vente) OVER (ORDER BY v.date_vente) as lead_date
                FROM client c
                INNER JOIN vente v ON c.id = v.id_client
                WHERE c.id = ?
                ORDER BY v.date_vente
            ) as ventes_avec_suivante
            WHERE lead_date IS NOT NULL
            """;
        
        List<Map<String, Object>> intervalleResults = jdbcTemplate.queryForList(sqlIntervalle, clientId);
        if (!intervalleResults.isEmpty()) {
            Object intervalle = intervalleResults.get(0).get("intervalle_moyen");
            if (intervalle != null) {
                fiche.setIntervalleJourVente(((Number) intervalle).intValue());
            }
        }
        
        // 6. Jour préféré de la semaine
        String sqlJourPrefere = """
            SELECT 
                CASE EXTRACT(DOW FROM v.date_vente)
                    WHEN 0 THEN 'Dimanche'
                    WHEN 1 THEN 'Lundi'
                    WHEN 2 THEN 'Mardi'
                    WHEN 3 THEN 'Mercredi'
                    WHEN 4 THEN 'Jeudi'
                    WHEN 5 THEN 'Vendredi'
                    WHEN 6 THEN 'Samedi'
                END as jour_semaine,
                COUNT(*) as nb_ventes
            FROM client c
            INNER JOIN vente v ON c.id = v.id_client
            WHERE c.id = ?
            GROUP BY EXTRACT(DOW FROM v.date_vente)
            ORDER BY nb_ventes DESC
            LIMIT 1
            """;
        
        List<Map<String, Object>> jourResults = jdbcTemplate.queryForList(sqlJourPrefere, clientId);
        if (!jourResults.isEmpty()) {
            fiche.setJourPrefere((String) jourResults.get(0).get("jour_semaine"));
        }
        
        // 7. Tranche horaire préférée
        String sqlTrancheHoraire = """
            SELECT 
                CASE 
                    WHEN EXTRACT(HOUR FROM v.date_vente) BETWEEN 6 AND 11 THEN 'Matin (6h-12h)'
                    WHEN EXTRACT(HOUR FROM v.date_vente) BETWEEN 12 AND 17 THEN 'Après-midi (12h-18h)'
                    WHEN EXTRACT(HOUR FROM v.date_vente) BETWEEN 18 AND 23 THEN 'Soir (18h-24h)'
                    ELSE 'Nuit (0h-6h)'
                END as tranche_horaire,
                COUNT(*) as nb_ventes
            FROM client c
            INNER JOIN vente v ON c.id = v.id_client
            WHERE c.id = ?
            GROUP BY 
                CASE 
                    WHEN EXTRACT(HOUR FROM v.date_vente) BETWEEN 6 AND 11 THEN 'Matin (6h-12h)'
                    WHEN EXTRACT(HOUR FROM v.date_vente) BETWEEN 12 AND 17 THEN 'Après-midi (12h-18h)'
                    WHEN EXTRACT(HOUR FROM v.date_vente) BETWEEN 18 AND 23 THEN 'Soir (18h-24h)'
                    ELSE 'Nuit (0h-6h)'
                END
            ORDER BY nb_ventes DESC
            LIMIT 1
            """;
        
        List<Map<String, Object>> trancheResults = jdbcTemplate.queryForList(sqlTrancheHoraire, clientId);
        if (!trancheResults.isEmpty()) {
            fiche.setTrancheHorairePrefere((String) trancheResults.get(0).get("tranche_horaire"));
        }
        
        return fiche;
    }
    
    public List<FicheClient> getAllFichesClients() {
        String sql = "SELECT id FROM client";
        List<Long> clientIds = jdbcTemplate.queryForList(sql, Long.class);
        
        return clientIds.stream()
                .map(this::getFicheClient)
                .toList();
    }
}