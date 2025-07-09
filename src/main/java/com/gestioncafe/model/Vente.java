package com.gestioncafe.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "vente")
public class Vente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dateVente;
    private String status; // "EN_COURS", "TERMINEE"

    @ManyToOne
    @JoinColumn(name = "id_client")
    private Client client;

    @ManyToOne
    @JoinColumn(name = "id_employe")
    private Employe employe;

    @OneToMany(mappedBy = "vente", cascade = CascadeType.ALL)
    private List<DetailsVente> detailsVentes;

    @OneToOne(mappedBy = "vente", cascade = CascadeType.ALL)
    private Commande commande;

    // Getters et setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDateVente() {
        return dateVente;
    }

    public void setDateVente(LocalDateTime dateVente) {
        this.dateVente = dateVente;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Employe getEmploye() {
        return employe;
    }

    public void setEmploye(Employe employe) {
        this.employe = employe;
    }

    public List<DetailsVente> getDetailsVentes() {
        return detailsVentes;
    }

    public void setDetailsVentes(List<DetailsVente> detailsVentes) {
        this.detailsVentes = detailsVentes;
    }

    public Commande getCommande() {
        return commande;
    }

    public void setCommande(Commande commande) {
        this.commande = commande;
    }
}