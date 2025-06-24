package com.gestioncafe.service.rh;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gestioncafe.model.*;
import com.gestioncafe.repository.*;

@Service
public class RhSalaireService {

    @Autowired
    private EmployeRepository employeRepository;
    @Autowired
    private CommissionRepository commissionRepository;
    @Autowired
    private AvanceRepository avanceRepository;

    public Employe getEmployeById(Long id) {
        return employeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employé non trouvé avec l'ID: " + id));
    }

    public List<Commission> getCommissionsByEmployeId(Long idEmploye) {
        return commissionRepository.findByEmployeIdOrderByDateCommssionDesc(idEmploye);
    }

    public List<Avance> getAvancesByEmployeId(Long idEmploye) {
        return avanceRepository.findByEmployeIdOrderByDateAvanceDesc(idEmploye);
    }
    
}
