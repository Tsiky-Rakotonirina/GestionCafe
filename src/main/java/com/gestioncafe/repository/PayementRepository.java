package com.gestioncafe.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.gestioncafe.model.*;

@Repository
public interface PayementRepository extends JpaRepository<Payement, Long>{
    public List<Payement> findByIdEmployeOrderByMoisReferenceDesc(Long idEmploye);
    @Query("SELECT p FROM Payement p WHERE p.idEmploye = :idEmploye ORDER BY p.moisReference DESC")
    Payement findLatestPayementByEmployeId(Long idEmploye);
}
