package com.gestioncafe.repository.tiers;

import com.gestioncafe.model.tiers.Employe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeRepository extends JpaRepository<Employe, Integer> {
}
