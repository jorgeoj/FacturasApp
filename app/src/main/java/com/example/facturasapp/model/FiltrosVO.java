package com.example.facturasapp.model;

import java.util.Date;
import java.util.HashMap;

public class FiltrosVO {
    //Creamos estas variables para los botones de las fechas
    private String fechaInicio;
    private String fechaFin;
    //Esta variable para la seekbar
    private int maxImporte;
    //Este mapa para los valores de las checkbox
    private HashMap<String, Boolean> estadoCB = new HashMap<>();

    public FiltrosVO(String fechaInicio, String fechaFin, int maxImporte, HashMap<String, Boolean> estadoCB) {
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.maxImporte = maxImporte;
        this.estadoCB = estadoCB;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public int getMaxImporte() {
        return maxImporte;
    }

    public void setMaxImporte(int maxImporte) {
        this.maxImporte = maxImporte;
    }

    public HashMap<String, Boolean> getEstadoCB() {
        return estadoCB;
    }

    public void setEstadoCB(HashMap<String, Boolean> estadoCB) {
        this.estadoCB = estadoCB;
    }
}


