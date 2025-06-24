package com.gestioncafe.model.production;

import com.gestioncafe.model.tiers.Client;
import com.gestioncafe.model.tiers.Employe;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Vente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "date_vente", nullable = false)
    private LocalDateTime dateVente;

    @ManyToOne
    @JoinColumn(name = "id_client")
    private Client client;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_employe", nullable = false)
    private Employe employe;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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
}
