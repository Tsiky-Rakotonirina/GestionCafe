package com.gestioncafe.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.gestioncafe.model.*;

@Repository
public interface CongeRepository extends JpaRepository<Conge, Long>{
    @Query("SELECT c.idEmploye, SUM(c.duree) " +
        "FROM Conge c " +
        "WHERE YEAR(c.dateDebut) = :annee AND c.dateDebut <= CURRENT_DATE " +
        "GROUP BY c.idEmploye")
    public List<Object[]> getDureeCongeParEmploye(@Param("annee") int annee);
    @Query("SELECT c.idEmploye, SUM(c.duree) " +
        "FROM Conge c " +
        "WHERE YEAR(c.dateDebut) = :annee " +
        "AND c.dateDebut > CURRENT_DATE " +
        "GROUP BY c.idEmploye")
    public List<Object[]> getDureeCongeReserveParEmploye(@Param("annee") int annee);

}
