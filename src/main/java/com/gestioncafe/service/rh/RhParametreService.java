package com.gestioncafe.service.rh;

import org.springframework.stereotype.Service;

@Service
public class RhParametreService {
    private JourFerieService jourFerieService;
    private GradeService gradeService;

    private IrsaService irsaService;

    public RhParametreService(JourFerieService jourFerieService, GradeService gradeService, IrsaService irsaService) {
        this.jourFerieService = jourFerieService;
        this.gradeService = gradeService;
        this.irsaService = irsaService;
    }

    public IrsaService getIrsaService() {
        return irsaService;
    }

    public void setIrsaService(IrsaService irsaService) {
        this.irsaService = irsaService;
    }

    public JourFerieService getJourFerieService() {
        return jourFerieService;
    }

    public void setJourFerieService(JourFerieService jourFerieService) {
        this.jourFerieService = jourFerieService;
    }

    public GradeService getGradeService() {
        return gradeService;
    }

    public void setGradeService(GradeService gradeService) {
        this.gradeService = gradeService;
    }

}
