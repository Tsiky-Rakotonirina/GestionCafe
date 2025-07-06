package com.gestioncafe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gestioncafe.model.Employe;
import com.gestioncafe.model.Vente;

public interface VenteRepository extends JpaRepository<Vente, Integer> {
    @Query("SELECT COUNT(DISTINCT v.client.id) FROM Vente v WHERE v.employe = :employe")
    int countDistinctClientsByEmploye(@Param("employe") Employe employe);
    int countByEmploye(Employe employe);
}
