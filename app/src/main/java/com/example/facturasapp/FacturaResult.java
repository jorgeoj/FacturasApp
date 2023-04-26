package com.example.facturasapp;

import java.util.List;

public class FacturaResult {

    private String numFacturas;
    private List<FacturaVO> facturas;

    public FacturaResult(String numFacturas, List<FacturaVO> facturas) {
        this.numFacturas = numFacturas;
        this.facturas = facturas;
    }

    public String getNumFacturas() {
        return numFacturas;
    }

    public void setNumFacturas(String numFacturas) {
        this.numFacturas = numFacturas;
    }

    public List<FacturaVO> getFacturas() {
        return facturas;
    }

    public void setFacturas(List<FacturaVO> facturas) {
        this.facturas = facturas;
    }
}
