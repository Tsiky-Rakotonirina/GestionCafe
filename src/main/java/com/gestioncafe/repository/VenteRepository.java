// src/main/java/com/gestioncafe/repository/VenteRepository.java
package com.gestioncafe.repository;

import com.gestioncafe.model.Vente;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface VenteRepository extends JpaRepository<Vente, Long> {
    List<Vente> findByStatus(String status);

    public List<Vente> findByClientIdOrderByDateVenteDesc(Long id);
}