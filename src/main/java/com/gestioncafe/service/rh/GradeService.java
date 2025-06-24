package com.gestioncafe.service.rh;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gestioncafe.model.rh.Grade;
import com.gestioncafe.repository.rh.GradeRepository;

@Service
public class GradeService {
    @Autowired
    private GradeRepository GradeRepository;

    public List<Grade> findAll() {
        return GradeRepository.findAll();
    }

    public Grade findById(Long id) {
        return GradeRepository.findById(id).orElseThrow();
    }

    public Grade save(Grade Grade) {
        return GradeRepository.save(Grade);
    }

    public void delete(Grade Grade) {
        GradeRepository.delete(Grade);
    }

    public void deleteById(Long id) {
        GradeRepository.deleteById(id);
    }
}
