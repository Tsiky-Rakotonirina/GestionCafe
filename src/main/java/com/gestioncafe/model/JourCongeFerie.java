package com.gestioncafe.model;

import java.time.LocalDate;

public class JourCongeFerie {
    private LocalDate jour;
    private JourFerie jourFerie; // peut être null
    private Employe employe;     // peut être null

    public JourCongeFerie(LocalDate jour, JourFerie jourFerie, Employe employe) {
        this.jour = jour;
        this.jourFerie = jourFerie;
        this.employe = employe;
    }

    public LocalDate getJour() {
        return jour;
    }

    public void setJour(LocalDate jour) {
        this.jour = jour;
    }

    public JourFerie getJourFerie() {
        return jourFerie;
    }

    public void setJourFerie(JourFerie jourFerie) {
        this.jourFerie = jourFerie;
    }

    public Employe getEmploye() {
        return employe;
    }

    public void setEmploye(Employe employe) {
        this.employe = employe;
    }
}

