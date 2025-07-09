// src/main/java/com/gestioncafe/service/PresenceService.java
package com.gestioncafe.service;

import com.gestioncafe.model.Employe;
import com.gestioncafe.model.Presence;
import com.gestioncafe.repository.EmployeRepository;
import com.gestioncafe.repository.PresenceRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

@Service
public class PresenceService {
    @Autowired
    private EmployeRepository employeRepository;
    
    @Autowired
    private PresenceRepository presenceRepository;
    
    public PresenceService(EmployeRepository employeRepository, PresenceRepository presenceRepository) {
        this.employeRepository = employeRepository;
        this.presenceRepository = presenceRepository;
    }
    
    public List<Employe> getAllEmployes() {
        return employeRepository.findAll();
    }
    
    public List<Presence> getPresencesForToday() {
        return presenceRepository.findByDatePresence(LocalDate.now());
    }
    
    public void validerPresence(Long employeId, String password) {
        // Ici, vous devriez vérifier le mot de passe (logique simplifiée)
        Presence presence = presenceRepository.findByEmployeIdAndDatePresence(employeId, LocalDate.now());
        
        if (presence == null) {
            presence = new Presence();
            presence.setEmploye(employeRepository.findById(employeId).orElseThrow());
            presence.setDatePresence(LocalDate.now());
        }
        
        presence.setDateArrivee(LocalDateTime.now());
        presence.setEstPresent(true);
        presenceRepository.save(presence);
    }
}