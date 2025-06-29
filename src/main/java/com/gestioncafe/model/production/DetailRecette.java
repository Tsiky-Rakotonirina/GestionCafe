package com.gestioncafe.model.production;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
public class DetailRecette {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_recette")
    private Recette recette;

    @ManyToOne
    @JoinColumn(name = "id_matiere_premiere")
    private MatierePremiere matierePremiere;

    @ManyToOne
    @JoinColumn(name = "id_unite")
    private Unite unite;

    private BigDecimal quantite;

    // Getters & Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Recette getRecette() {
        return recette;
    }

    public void setRecette(Recette recette) {
        this.recette = recette;
    }

    public MatierePremiere getMatierePremiere() {
        return matierePremiere;
    }

    public void setMatierePremiere(MatierePremiere matierePremiere) {
        this.matierePremiere = matierePremiere;
    }

    public Unite getUnite() {
        return unite;
    }

    public void setUnite(Unite unite) {
        this.unite = unite;
    }

    public BigDecimal getQuantite() {
        return quantite;
    }

    public void setQuantite(BigDecimal quantite) {
        this.quantite = quantite;
    }
}