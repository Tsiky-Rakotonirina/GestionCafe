package com.gestioncafe.service.marketing;

import com.gestioncafe.model.DetailCandidat;
import com.gestioncafe.repository.DetailCandidatRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DetailCandidatService {

    private final DetailCandidatRepository detailCandidatRepository;

    public DetailCandidatService(DetailCandidatRepository detailCandidatRepository) {
        this.detailCandidatRepository = detailCandidatRepository;
    }

    public List<DetailCandidat> getDetailsByCandidatId(Long candidatId) {
        return detailCandidatRepository.findByCandidatId(candidatId);
    }
}
