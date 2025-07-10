// src/main/java/com/gestioncafe/repository/CommandeRepository.java
package com.gestioncafe.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.gestioncafe.model.Commande;

public interface CommandeRepository extends JpaRepository<Commande, Long> {
    List<Commande> findByDateFinBetween(LocalDateTime start, LocalDateTime end);
    
    @Transactional
    @Modifying
    @Query("UPDATE Commande c SET c.estTerminee = true WHERE c.id = :id AND c.estTerminee = false")
    int marquerCommeTerminee(Long id);
}