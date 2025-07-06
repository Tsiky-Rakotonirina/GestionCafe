// src/main/java/com/gestioncafe/repository/CommandeRepository.java
package com.gestioncafe.repository;

import com.gestioncafe.model.Commande;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;

public interface CommandeRepository extends JpaRepository<Commande, Long> {
    List<Commande> findByDateFinBetween(LocalDate start, LocalDate end);
    
    @Transactional
    @Modifying
    @Query("UPDATE Commande c SET c.estTerminee = true WHERE c.id = :id AND c.estTerminee = false")
    int marquerCommeTerminee(Long id);
}