package com.example.facturasapp.model;

import java.util.List;

public class FacturaResult {

    private List<FacturaVO> facturas;

    public FacturaResult(List<FacturaVO> facturas) {
        //Declaracion de la lista de facturas (JSON)
        this.facturas = facturas;
    }

    public List<FacturaVO> getFacturas() {
        return facturas;
    }

}
