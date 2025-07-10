// src/main/java/com/gestioncafe/service/CommandeService.java
package com.gestioncafe.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gestioncafe.model.Commande;
import com.gestioncafe.model.DetailsVente;
import com.gestioncafe.model.Produit;
import com.gestioncafe.model.Vente;
import com.gestioncafe.model.MouvementStockProduit;
import com.gestioncafe.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service
public class CommandeService {
    private final CommandeRepository commandeRepository;
    private final VenteRepository venteRepository;
    private final ProduitRepository produitRepository;
    private final ClientRepository clientRepository;
    private final EmployeRepository employeRepository;
    private final DetailsVenteRepository detailsVenteRepository;
    private final PrixVenteProduitRepository prixVenteProduitRepository;
    private final MouvementStockProduitRepository mouvementStockProduitRepository;


    public CommandeService(CommandeRepository commandeRepository,
                           VenteRepository venteRepository,
                           ProduitRepository produitRepository,
                           ClientRepository clientRepository,
                           EmployeRepository employeRepository,
                           DetailsVenteRepository detailsVenteRepository,
                           PrixVenteProduitRepository prixVenteProduitRepository,
                           MouvementStockProduitRepository mouvementStockProduitRepository) {
        this.commandeRepository = commandeRepository;
        this.venteRepository = venteRepository;
        this.produitRepository = produitRepository;
        this.clientRepository = clientRepository;
        this.employeRepository = employeRepository;
        this.detailsVenteRepository = detailsVenteRepository;
        this.prixVenteProduitRepository = prixVenteProduitRepository;
        this.mouvementStockProduitRepository = mouvementStockProduitRepository;
    }

    public List<Commande> getCommandesDuJour() {
        LocalDate today = LocalDate.now();
        LocalDateTime start = today.atStartOfDay();
        LocalDateTime end = today.plusDays(1).atStartOfDay().minusNanos(1);
        return commandeRepository.findByDateFinBetween(start, end);
    }

    public BigDecimal getRecetteDuJour() {
        List<Vente> ventes = venteRepository.findByStatus("TERMINEE").stream()
            .filter(v -> v.getDateVente().toLocalDate().equals(LocalDate.now()))
            .toList();

        return ventes.stream()
            .flatMap(v -> v.getDetailsVentes().stream())
            .map(DetailsVente::getMontant)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Transactional
    public void marquerCommandeTerminee(Long commandeId) {
        // Marquer la commande comme terminée
        commandeRepository.marquerCommeTerminee(commandeId);
        // Mettre à jour le status de la vente associée
        Commande commande = commandeRepository.findById(commandeId)
            .orElseThrow(() -> new RuntimeException("Commande non trouvée"));
        Vente vente = commande.getVente();
        if (vente != null) {
            vente.setStatus("TERMINEE");
            venteRepository.save(vente);
            // Mouvement de stock pour chaque produit vendu
            if (vente.getDetailsVentes() != null) {
                for (DetailsVente dv : vente.getDetailsVentes()) {
                    Produit produit = dv.getProduit();
                    if (produit.getStock() == null || produit.getStock().compareTo(dv.getQuantite()) < 0) {
                        throw new RuntimeException("Stock insuffisant pour le produit : " + produit.getNom());
                    }
                    MouvementStockProduit mvt = new MouvementStockProduit();
                    mvt.setProduit(produit);
                    mvt.setVente(vente);
                    mvt.setDateMouvement(LocalDateTime.now());
                    mvt.setQuantite(dv.getQuantite().negate()); // négatif = sortie du stock
                    mouvementStockProduitRepository.save(mvt);
                    // Mettre à jour le stock du produit
                    produit.setStock(produit.getStock().subtract(dv.getQuantite()));
                    produitRepository.save(produit);
                }
            }
        }
    }

    public List<Produit> getAllProduits() {
        return produitRepository.findAll();
    }

    @Transactional
    public void creerVente(Long clientId, Long employeId, String produitsJson) {
        try {
            // Convertir le JSON en Map (produitId -> quantite)
            ObjectMapper mapper = new ObjectMapper();
            Map<Long, BigDecimal> produitsMap = mapper.readValue(
                produitsJson,
                new TypeReference<Map<Long, BigDecimal>>() {
                }
            );


            // Créer et sauvegarder la vente
            Vente vente = new Vente();
            vente.setDateVente(LocalDateTime.now());
            vente.setClient(clientRepository.findById((long) Math.toIntExact(clientId))
                .orElseThrow(() -> new RuntimeException("Client non trouvé")));
            vente.setEmploye(employeRepository.findById(employeId)
                .orElseThrow(() -> new RuntimeException("Employé non trouvé")));
            vente.setStatus("EN_COURS");

            Vente savedVente = venteRepository.save(vente);

            // Créer les détails de vente
            List<DetailsVente> details = new ArrayList<>();

            for (Map.Entry<Long, BigDecimal> entry : produitsMap.entrySet()) {
                Long produitId = entry.getKey();
                BigDecimal quantite = entry.getValue();

                Produit produit = produitRepository.findById((long) Math.toIntExact(produitId))
                    .orElseThrow(() -> new RuntimeException("Produit non trouvé: " + produitId));

                // Récupérer le prix actuel du produit
                BigDecimal prixUnitaire = prixVenteProduitRepository.findCurrentPriceByProduitId(produitId)
                    .orElseThrow(() -> new RuntimeException("Prix non trouvé pour le produit: " + produitId));

                DetailsVente dv = new DetailsVente();
                dv.setVente(savedVente); // Utiliser savedVente qui est final
                dv.setProduit(produit);
                dv.setQuantite(quantite);
                dv.setPrixUnitaire(prixUnitaire);
                dv.setMontant(prixUnitaire.multiply(quantite));

                details.add(dv);
            }

            detailsVenteRepository.saveAll(details);

            // Créer la commande associée
            Commande commande = new Commande();
            commande.setVente(savedVente);
            commande.setDateFin(LocalDateTime.now());
            commande.setEstTerminee(false);
            commandeRepository.save(commande);

        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la création de la vente", e);
        }
    }
}
