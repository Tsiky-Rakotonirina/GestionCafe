// src/main/java/com/gestioncafe/repository/PresenceRepository.java
package com.gestioncafe.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gestioncafe.model.Presence;

public interface PresenceRepository extends JpaRepository<Presence, Long> {
    List<Presence> findByDatePresence(Date datePresence);
    Presence findByEmploye_IdAndDatePresence(Long employeId, Date datePresence);

    // Compte le nombre de présences d'un employé selon le statut de présence
    int countByEmploye_IdAndEstPresent(Long employeId, Boolean estPresent);

    List<Presence> findByEmploye_IdAndDatePresenceBetweenAndEstPresentFalse(Long employeId, Date dateDebut, Date dateFin);
}