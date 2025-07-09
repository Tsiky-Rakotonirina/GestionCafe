// src/main/java/com/gestioncafe/repository/PresenceRepository.java
package com.gestioncafe.repository;

import com.gestioncafe.model.Presence;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

public interface PresenceRepository extends JpaRepository<Presence, Long> {
    List<Presence> findByDatePresence(LocalDate date);
    Presence findByEmployeIdAndDatePresence(Long employeId, LocalDate date);
}