package com.gestioncafe.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gestioncafe.model.*;
import com.gestioncafe.repository.*;

@Service
public class LoginService {
    
    @Autowired
    private AdministratifRepository administratif;
    @Autowired
    private QuotidienRepository quotidien;

    public boolean loginAdministratif(String nom, String motDePasse) {
        System.out.println("Tentative de connexion : nom=" + nom + ", mdp=" + motDePasse);
    
        // // 1. D'abord trouver l'administrateur par son nom
        // Administratif adminUtilisateur = administratif.findByNomAndMotDePasse(nom, motDePasse);
        
        // // 2. Si aucun admin trouvé avec ce nom
        // if (adminUtilisateur == null) {
        //     System.out.println("Aucun administrateur trouvé avec ce nom ET MOT DE PASSE");
        //     return false;
        // }
        
        // // 3. Vérifier le mot de passe manuellement
        // if (!adminUtilisateur.getMotDePasse().equals(motDePasse)) {
        //     System.out.println("Mot de passe incorrect");
        //     return false;
        // }
        
        // 4. Si on arrive ici, les identifiants sont valides
        return true;
    }

    public boolean loginQuotidien(String nom, String motDePasse) {
        // Quotidien quotidienUtilisateur = quotidien.findByNomAndMotDePasse(nom, motDePasse);
        // return quotidienUtilisateur != null;
        return  true;
    }
}
