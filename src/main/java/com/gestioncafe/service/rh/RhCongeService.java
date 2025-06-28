package com.gestioncafe.service.rh;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gestioncafe.model.*;
import com.gestioncafe.repository.*;

@Service
public class RhCongeService {
    @Autowired
    private CongeRepository congeRepository;
}
