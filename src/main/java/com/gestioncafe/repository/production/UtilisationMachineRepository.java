package com.gestioncafe.repository.production;

import com.gestioncafe.model.production.UtilisationMachine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UtilisationMachineRepository extends JpaRepository<UtilisationMachine, Integer> {
    List<UtilisationMachine> findByMachineId(Integer machineId);
}
