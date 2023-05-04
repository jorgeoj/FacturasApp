package com.example.facturasapp.model;

import com.example.facturasapp.model.FacturaVO;

import java.util.ArrayList;

public class FacturaResult {

    //Declaracion de la lista de facturas (JSON)
    private String numFacturas;
    private ArrayList<FacturaVO> facturas;

    public FacturaResult(String numFacturas, ArrayList<FacturaVO> facturas) {
        this.numFacturas = numFacturas;
        this.facturas = facturas;
    }

    public String getNumFacturas() {
        return numFacturas;
    }

    public void setNumFacturas(String numFacturas) {
        this.numFacturas = numFacturas;
    }

    public ArrayList<FacturaVO> getFacturas() {
        return facturas;
    }

    public void setFacturas(ArrayList<FacturaVO> facturas) {
        this.facturas = facturas;
    }
}
