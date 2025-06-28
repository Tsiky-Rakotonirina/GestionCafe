package com.gestioncafe.repository;

import com.gestioncafe.model.MouvementStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MouvementStockRepository extends JpaRepository<MouvementStock, Long> {
    List<MouvementStock> findByMatierePremiereId(Long matierePremiereId);
    
    @Query("SELECT m FROM MouvementStock m WHERE m.dateMouvement BETWEEN :startDate AND :endDate")
    List<MouvementStock> findBetweenDates(LocalDateTime startDate, LocalDateTime endDate);
    
    @Query("SELECT m FROM MouvementStock m WHERE m.matierePremiere.id = :matiereId AND m.dateMouvement BETWEEN :startDate AND :endDate")
    List<MouvementStock> findByMatiereAndDates(Long matiereId, LocalDateTime startDate, LocalDateTime endDate);
}