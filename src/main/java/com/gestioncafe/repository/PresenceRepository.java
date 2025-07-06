package com.gestioncafe.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gestioncafe.model.Presence;

@Repository
public interface PresenceRepository  extends JpaRepository<Presence, Long> {
    public List<Presence> findByIdEmployeAndDatePresenceBetweenAndEstPresentFalse(Long idEmploye, Date dateDebut, Date dateFin);
    int countByIdEmployeAndEstPresent(Long idEmploye, boolean estPresent);
}
