package com.gestioncafe.service.tiers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gestioncafe.model.Employe;
import com.gestioncafe.repository.tiers.EmployeRepository;

@Service
public class EmployeService {
    @Autowired
    private EmployeRepository employeRepository;

    public List<Employe> findAll() {
        return employeRepository.findAll();
    }

    public Optional<Employe> findById(Integer id) {
        return employeRepository.findById(id);
    }

    public Employe save(Employe employe) {
        return employeRepository.save(employe);
    }

    public void deleteById(Integer id) {
        employeRepository.deleteById(id);
    }
}
