package com.gestioncafe.repository;

import com.gestioncafe.model.GradeEmploye;
import com.gestioncafe.model.Employe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GradeEmployeRepository extends JpaRepository<GradeEmploye, Long> {
    List<GradeEmploye> findByEmployeOrderByDateGradeDesc(Employe employe);
    
}
