package com.gestioncafe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gestioncafe.model.Employe;
import com.gestioncafe.model.Vente;

import java.util.List;

public interface VenteRepository extends JpaRepository<Vente, Long> {
    @Query("SELECT COUNT(DISTINCT v.client.id) FROM Vente v WHERE v.employe = :employe")
    int countDistinctClientsByEmploye(@Param("employe") Employe employe);
    int countByEmploye(Employe employe);
    List<Vente> findByStatus(String status);

    public List<Vente> findByClientIdOrderByDateVenteDesc(Long id);
}
