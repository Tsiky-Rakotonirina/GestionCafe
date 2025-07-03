package com.gestioncafe.repository.production;

import com.gestioncafe.model.Machine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MachineRepository extends JpaRepository<Machine, Integer> {}
