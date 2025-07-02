package com.gestioncafe.model.rh;

import java.util.ArrayList;
import java.util.List;

public class IrsaWrapper {
    private List<Irsa> irsas = new ArrayList<>();

    public List<Irsa> getIrsas() {
        return irsas;
    }

    public void setIrsas(List<Irsa> irsas) {
        this.irsas = irsas;
    }

    public void addIrsa(Irsa irsa) {
        irsas.add(irsa);
    }
}
