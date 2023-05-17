package com.example.facturasapp.model;

import java.util.HashMap;
import java.util.Map;

public class FiltrosVO {
    //Creamos estas variables para los botones de las fechas
    private String fechaInicio;
    private String fechaFin;
    //Esta variable para la seekbar

    private int importeSeleccionado;
    private int maxImporte;
    //Este mapa para los valores de las checkbox
    private Map<String, Boolean> estadoCB = new HashMap<>();

    public FiltrosVO(String fechaInicio, String fechaFin, int importeSeleccionado, int maxImporte, Map<String, Boolean> estadoCB) {
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.importeSeleccionado = importeSeleccionado;
        this.maxImporte = maxImporte;
        this.estadoCB = estadoCB;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public int getImporteSeleccionado() {
        return importeSeleccionado;
    }

    public Map<String, Boolean> getEstadoCB() {
        return estadoCB;
    }
}


