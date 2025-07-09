package com.gestioncafe.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.gestioncafe.model.GradeEmploye;

@Repository
public interface GradeEmployeRepository extends JpaRepository<GradeEmploye, Long> {
    @Query("SELECT COALESCE(ge.grade.salaire, 0.0) FROM GradeEmploye ge WHERE ge.employe.id = :idEmploye AND ge.dateGrade <= :date ORDER BY ge.dateGrade DESC")
    public Double findSalaireByEmployeAndDate(Long idEmploye, LocalDate date);
    // public List<GradeEmploye> findByEmployeOrderByDateGradeDesc(Employe employe);

}
