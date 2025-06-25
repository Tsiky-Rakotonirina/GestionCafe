package com.gestioncafe.service.rh;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gestioncafe.model.rh.Grade;
import com.gestioncafe.repository.rh.GradeRepository;

@Service
public class GradeService {
    @Autowired
    private GradeRepository gradeRepository;

    public List<Grade> findAll() {
        return gradeRepository.findAll();
    }

    public Grade findById(Long id) {
        return gradeRepository.findById(id).orElseThrow();
    }

    public Grade save(Grade grade) {
        return gradeRepository.save(grade);
    }

    public void delete(Grade grade) {
        gradeRepository.delete(grade);
    }

    public void deleteById(Long id) {
        gradeRepository.deleteById(id);
    }
}
