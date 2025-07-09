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

    @Column(name = "date_vente", nullable = false)
    private LocalDateTime dateVente;

    private String status;

    @ManyToOne
    @JoinColumn(name = "id_client")
    private Client client;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_employe", nullable = false)
    private Employe employe;

    @OneToMany(mappedBy = "vente", cascade = CascadeType.ALL)
    private List<DetailsVente> detailsVentes;

    @OneToOne(mappedBy = "vente", cascade = CascadeType.ALL)
    private Commande commande;


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

    public Commande getCommande() {
        return commande;
    }

    public Vente setCommande(Commande commande) {
        this.commande = commande;
        return this;
    }

    public List<DetailsVente> getDetailsVentes() {
        return detailsVentes;
    }

    public Vente setDetailsVentes(List<DetailsVente> detailsVentes) {
        this.detailsVentes = detailsVentes;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
