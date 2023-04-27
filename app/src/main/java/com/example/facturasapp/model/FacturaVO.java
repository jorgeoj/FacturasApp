package com.example.facturasapp.model;

public class FacturaVO {

    private String descEstado;
    private Double importeOrdenacion;
    private String fecha;

    public FacturaVO(String descEstado, Double importeOrdenacion, String fecha){
        this.descEstado = descEstado;
        this.importeOrdenacion = importeOrdenacion;
        this.fecha = fecha;
    }

    public String getDescEstado(){
        return descEstado;
    }

    public Double getImporteOrdenacion(){
        return importeOrdenacion;
    }

    public String getFecha(){
        return fecha;
    }
}
