package com.gestioncafe.repository;

import com.gestioncafe.model.Employe;
import com.gestioncafe.model.Vente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VenteRepository extends JpaRepository<Vente, Long> {
    List<Vente> findByStatus(String status);

    @Query("SELECT COUNT(DISTINCT v.client.id) FROM Vente v WHERE v.employe = :employe")
    int countDistinctClientsByEmploye(@Param("employe") Employe employe);

    int countByEmploye(Employe employe);

    public List<Vente> findByClientIdOrderByDateVenteDesc(Long id);
}