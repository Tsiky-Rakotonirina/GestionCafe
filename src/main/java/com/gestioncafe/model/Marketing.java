package com.gestioncafe.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "marketing")
public class Marketing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int age;

    @Column(name = "budget_moyen")
    private BigDecimal budgetMoyen;

    @Column(name = "estimation_prix_produit")
    private BigDecimal estimationPrixProduit;

    @ManyToOne
    @JoinColumn(name = "id_genre")
    private Genre genre;

    @ManyToOne
    @JoinColumn(name = "id_lieu")
    private Lieux lieu;

    @ManyToOne
    @JoinColumn(name = "id_produit")
    private Produit produit;

    public Marketing() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public BigDecimal getBudgetMoyen() {
        return budgetMoyen;
    }

    public void setBudgetMoyen(BigDecimal budgetMoyen) {
        this.budgetMoyen = budgetMoyen;
    }

    public BigDecimal getEstimationPrixProduit() {
        return estimationPrixProduit;
    }

    public void setEstimationPrixProduit(BigDecimal estimationPrixProduit) {
        this.estimationPrixProduit = estimationPrixProduit;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public Lieux getLieu() {
        return lieu;
    }

    public void setLieu(Lieux lieu) {
        this.lieu = lieu;
    }

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }
}
