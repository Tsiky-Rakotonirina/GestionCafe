package com.gestioncafe.dto;

import com.gestioncafe.model.Employe;

public class EmployeInfosDTO {
    private Employe employe;
    private int nombreClients;
    private int nombrePresences;
    private double efficacite;
    private String statut;
    // Ajout d'un champ gradeActuel pour l'affichage
    private String gradeActuel;

    public EmployeInfosDTO(Employe employe, int nombreClients, int nombrePresences, double efficacite, String statut) {
        this.employe = employe;
        this.nombreClients = nombreClients;
        this.nombrePresences = nombrePresences;
        this.efficacite = efficacite;
        this.statut = statut;
        // Si Employe a un grade actuel, décommente la ligne suivante et adapte
        // this.gradeActuel = employe.getGradeActuel() != null ? employe.getGradeActuel().getNom() : "-";
        this.gradeActuel = ""; // à adapter selon ta structure
    }

    public Employe getEmploye() { return employe; }
    public int getNombreClients() { return nombreClients; }
    public int getNombrePresences() { return nombrePresences; }
    public double getEfficacite() { return efficacite; }
    public String getStatut() { return statut; }
    public String getGradeActuel() { return gradeActuel; }

    public void setEmploye(Employe employe) { this.employe = employe; }
    public void setNombreClients(int nombreClients) { this.nombreClients = nombreClients; }
    public void setNombrePresences(int nombrePresences) { this.nombrePresences = nombrePresences; }
    public void setEfficacite(double efficacite) { this.efficacite = efficacite; }
    public void setStatut(String statut) { this.statut = statut; }
    public void setGradeActuel(String gradeActuel) { this.gradeActuel = gradeActuel; }
}
