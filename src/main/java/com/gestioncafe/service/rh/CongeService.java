package com.gestioncafe.service.rh;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gestioncafe.model.rh.Conge;
import com.gestioncafe.model.rh.TypeConge;
import com.gestioncafe.repository.rh.CongeRepository;
import com.gestioncafe.repository.rh.TypeCongeRepository;

@Service
public class CongeService {
    @Autowired
    private TypeCongeRepository typeCongeRepository;

    @Autowired
    private CongeRepository congeRepository;

    public List<Conge> findAll() {
        return congeRepository.findAll();
    }

    public Conge findById(Long id) {
        return congeRepository.findById(id).orElseThrow();
    }

    public Conge save(Conge conge) throws Exception {
        LocalDate debut = conge.getDateDebut();
        LocalDate fin = conge.getDateFin();

        if (debut == null || fin == null) {
            throw new Exception("Les dates de début et de fin sont obligatoires.");
        }

        if (!fin.isAfter(debut)) {
            throw new Exception("La date de fin doit être postérieure à la date de début.");
        }

        // Calcul automatique de la durée
        int duree = (int) java.time.temporal.ChronoUnit.DAYS.between(debut, fin) + 1;
        conge.setDuree(duree);

        return congeRepository.save(conge);
    }

    public void delete(Conge conge) {
        congeRepository.delete(conge);
    }

    public void deleteById(Long id) {
        congeRepository.deleteById(id);
    }

    public List<TypeConge> findAllTypeConge() {
        return typeCongeRepository.findAll();
    }

    public TypeConge findByIdTypeConge(Long id) {
        return typeCongeRepository.findById(id).orElseThrow();
    }

    public TypeConge saveTypeConge(TypeConge typeConge) {
        return typeCongeRepository.save(typeConge);
    }

    public void deleteTypetypeConge(TypeConge typeConge) {
        typeCongeRepository.delete(typeConge);
    }

    public void deleteByIdTypeConge(Long id) {
        typeCongeRepository.deleteById(id);
    }
}
