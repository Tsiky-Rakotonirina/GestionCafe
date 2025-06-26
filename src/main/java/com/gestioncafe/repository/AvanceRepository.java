package com.gestioncafe.repository;

import java.util.List;
import java.sql.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.gestioncafe.model.*;

@Repository
public interface AvanceRepository extends JpaRepository<Avance, Long> {
    public List<Avance> findByIdEmployeOrderByDateAvanceDesc(Long employeId);
    List<Avance> findByIdEmployeAndDateAvanceAfter(Long employeId, Date dateRepere);
}