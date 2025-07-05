package com.gestioncafe.service;

import com.gestioncafe.dto.ClientSearchDto;
import com.gestioncafe.model.VClientLib;
import com.gestioncafe.repository.VClientLibRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VClientLibService {
    
    @Autowired
    private VClientLibRepository vClientLibRepository;
    
    /**
     * Recherche des clients selon les critères fournis
     * @param searchDto DTO contenant les critères de recherche
     * @return Liste des clients correspondant aux critères
     */
    public List<VClientLib> searchClients(ClientSearchDto searchDto) {
        return vClientLibRepository.searchClients(
            searchDto.getNom(),
            searchDto.getPrenom(),
            searchDto.getAgeMin(),
            searchDto.getAgeMax(),
            searchDto.getDateAdhesion()
        );
    }
    
    /**
     * Récupère tous les clients
     * @return Liste de tous les clients
     */
    public List<VClientLib> getAllClients() {
        return vClientLibRepository.findAll();
    }
}