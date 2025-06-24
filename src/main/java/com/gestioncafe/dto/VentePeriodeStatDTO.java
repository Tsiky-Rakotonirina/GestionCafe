package com.gestioncafe.dto;

import java.math.BigDecimal;

public class VentePeriodeStatDTO {
    private final String periode;
    private final BigDecimal quantiteTotale;

    public VentePeriodeStatDTO(String periode, BigDecimal quantiteTotale) {
        this.periode = periode;
        this.quantiteTotale = quantiteTotale;
    }

    public String getPeriode() {
        return periode;
    }

    public BigDecimal getQuantiteTotale() {
        return quantiteTotale;
    }
}
