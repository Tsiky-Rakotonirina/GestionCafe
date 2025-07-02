package com.gestioncafe.repository.rh;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gestioncafe.model.rh.Employe;

public interface EmployeRepository extends JpaRepository<Employe,Long>{
    
}
