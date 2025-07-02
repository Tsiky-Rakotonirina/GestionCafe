package com.gestioncafe.repository.rh;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gestioncafe.model.rh.Grade;

public interface GradeRepository extends JpaRepository<Grade, Long> {

}
