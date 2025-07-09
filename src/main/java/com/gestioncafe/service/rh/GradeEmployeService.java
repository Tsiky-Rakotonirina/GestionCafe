package com.gestioncafe.service.rh;

import com.gestioncafe.model.GradeEmploye;
import com.gestioncafe.repository.GradeEmployeRepository;
import org.springframework.stereotype.Service;

@Service
public class GradeEmployeService {

    private final GradeEmployeRepository gradeEmployeRepository;

    public GradeEmployeService(GradeEmployeRepository gradeEmployeRepository) {
        this.gradeEmployeRepository = gradeEmployeRepository;
    }

    public GradeEmploye saveGradeEmploye(GradeEmploye gradeEmploye) {
        return gradeEmployeRepository.save(gradeEmploye);
    }
}
