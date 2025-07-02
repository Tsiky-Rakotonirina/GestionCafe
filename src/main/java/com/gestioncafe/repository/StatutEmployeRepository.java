package com.gestioncafe.repository;

import com.gestioncafe.model.StatutEmploye;
import com.gestioncafe.model.Employe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StatutEmployeRepository extends JpaRepository<StatutEmploye, Long> {
    List<StatutEmploye> findByEmployeOrderByDateStatutDesc(Employe employe);
    List<StatutEmploye> findByStatut_Id(Long statutId);

}
