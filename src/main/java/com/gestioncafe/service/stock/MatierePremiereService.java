package com.gestioncafe.service.stock;

import com.gestioncafe.model.stock.MatierePremiere;
import java.util.List;

public interface MatierePremiereService {
    List<MatierePremiere> getAllMatieres();
    MatierePremiere getMatiereById(Integer id);
    MatierePremiere saveMatiere(MatierePremiere matiere);
    void deleteMatiere(Integer id);
}