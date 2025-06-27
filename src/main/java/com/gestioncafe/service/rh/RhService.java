package com.gestioncafe.service.rh;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gestioncafe.model.*;
import com.gestioncafe.repository.*;

@Service
public class RhService {
    @Autowired
    private StatutEmployeRepository statutEmployeRepository;

    public List<StatutEmploye> getAllEmployesActifs() {
        return statutEmployeRepository.findDerniersStatutsParEmployeEtStatut(Long.parseLong("1"));
    }

}
