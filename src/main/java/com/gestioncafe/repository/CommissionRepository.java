package com.gestioncafe.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.gestioncafe.model.*;

@Repository
public interface CommissionRepository extends JpaRepository<Commission, Long> {
    public List<Commission> findByEmployeIdOrderByDateCommssionDesc(Long employeId);
}
