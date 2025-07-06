// src/main/java/com/gestioncafe/service/CommandeService.java
package com.gestioncafe.service;

import com.gestioncafe.model.*;
import com.gestioncafe.repository.*;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gestioncafe.dto.ProduitQuantite;
import com.gestioncafe.model.DetailsVente;


@Service
public class CommandeService {
    @Autowired
    private CommandeRepository commandeRepository;
    @Autowired
    private VenteRepository venteRepository;
    @Autowired
    private ProduitRepository produitRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private EmployeRepository employeRepository;
    @Autowired
    private DetailsVenteRepository detailsVenteRepository;
    @Autowired
    private PrixVenteProduitRepository prixVenteProduitRepository;
    
    

    public CommandeService(CommandeRepository commandeRepository, VenteRepository venteRepository) {
        this.commandeRepository = commandeRepository;
        this.venteRepository = venteRepository;
    }
    
    public List<Commande> getCommandesDuJour() {
        LocalDate aujourdhui = LocalDate.now();
        return commandeRepository.findByDateFinBetween(aujourdhui.minusDays(1), aujourdhui);
    }
    
    public BigDecimal getRecetteDuJour() {
        List<Vente> ventes = venteRepository.findByStatus("TERMINEE").stream()
                .filter(v -> v.getDateVente().toLocalDate().equals(LocalDate.now()))
                .collect(Collectors.toList());
        
        return ventes.stream()
                .flatMap(v -> v.getDetailsVentes().stream())
                .map(DetailsVente::getMontant)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    
    public void marquerCommandeTerminee(Long commandeId) {
        commandeRepository.marquerCommeTerminee(commandeId);
    }

    // src/main/java/com/gestioncafe/service/CommandeService.java
    // Ajoutez ces méthodes à votre service existant

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
                new TypeReference<Map<Long, BigDecimal>>(){}
            ); 


            // Créer et sauvegarder la vente
            Vente vente = new Vente();
            vente.setDateVente(LocalDateTime.now());
            vente.setClient(clientRepository.findById(clientId)
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
                
                Produit produit = produitRepository.findById(produitId)
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
            commande.setDateFin(LocalDate.now().plusDays(1));
            commande.setEstTerminee(false);
            commandeRepository.save(commande);

        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la création de la vente", e);
        }
    }
}