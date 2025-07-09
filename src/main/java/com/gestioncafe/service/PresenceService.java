// src/main/java/com/gestioncafe/service/PresenceService.java
package com.gestioncafe.service;

import com.gestioncafe.model.Employe;
import com.gestioncafe.model.Presence;
import com.gestioncafe.repository.EmployeRepository;
import com.gestioncafe.repository.PresenceRepository;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PresenceService {
    private final EmployeRepository employeRepository;

    private final PresenceRepository presenceRepository;

    public PresenceService(EmployeRepository employeRepository, PresenceRepository presenceRepository) {
        this.employeRepository = employeRepository;
        this.presenceRepository = presenceRepository;
    }

    public List<Employe> getAllEmployes() {
        return employeRepository.findAll();
    }

    public List<Presence> getPresencesForToday() {
        return presenceRepository.findByDatePresence(Date.valueOf(LocalDate.now()));
    }

    public void validerPresence(Long employeId, String password) {
        // Ici, vous devriez vérifier le mot de passe (logique simplifiée)
        Presence presence = presenceRepository.findByEmploye_IdAndDatePresence(employeId, Date.valueOf(LocalDate.now()));

        if (presence == null) {
            presence = new Presence();
            presence.setEmploye(employeRepository.findById(employeId).orElseThrow());
            presence.setDatePresence(Date.valueOf(LocalDate.now()));
        }

        presence.setDateArrivee(LocalDateTime.now());
        presence.setEstPresent(true);
        presenceRepository.save(presence);
    }
}