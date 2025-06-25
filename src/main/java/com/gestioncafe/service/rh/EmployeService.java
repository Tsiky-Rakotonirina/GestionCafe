package com.gestioncafe.service.rh;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gestioncafe.model.rh.Employe;
import com.gestioncafe.repository.rh.EmployeRepository;

@Service
public class EmployeService {

    @Autowired
    private EmployeRepository employeRepository;

    public List<Employe> findAll() {
        return employeRepository.findAll();
    }

    public Employe findById(Long id) {
        return employeRepository.findById(id).orElseThrow();
    }

    public Employe save(Employe Employe) {
        return employeRepository.save(Employe);
    }

    public void delete(Employe Employe) {
        employeRepository.delete(Employe);
    }

    public void deleteById(Long id) {
        employeRepository.deleteById(id);
    }

}
