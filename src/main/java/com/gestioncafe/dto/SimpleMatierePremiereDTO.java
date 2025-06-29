package com.gestioncafe.dto;

public class SimpleMatierePremiereDTO {
    private Integer id;
    private String nom;

    public SimpleMatierePremiereDTO(Integer id, String nom) {
        this.id = id;
        this.nom = nom;
    }
    public Integer getId() { return id; }
    public String getNom() { return nom; }
}
