// src/main/java/com/gestioncafe/repository/VenteRepository.java
package com.gestioncafe.repository;

import com.gestioncafe.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List; 

public interface DetailsVenteRepository extends JpaRepository<DetailsVente, Long> {
}