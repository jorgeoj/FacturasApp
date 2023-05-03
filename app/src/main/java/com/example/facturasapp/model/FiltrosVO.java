package com.example.facturasapp.model;

import java.util.Date;

public class FiltrosVO {
    //Creamos estas variables para los botones de las fechas
    private Date fechaInicio;
    private Date fechaFin;
    //Esta variable para la seekbar
    private double importe;
    //Botones
    private boolean pagadas;
    private boolean anuladas;
    private boolean cuotaFija;
    private boolean pendientesPago;
    private boolean planPago;

    public FiltrosVO(Date fechaInicio, Date fechaFin, double importe, boolean pagadas, boolean anuladas, boolean cuotaFija, boolean pendientesPago, boolean planPago) {
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.importe = importe;
        this.pagadas = pagadas;
        this.anuladas = anuladas;
        this.cuotaFija = cuotaFija;
        this.pendientesPago = pendientesPago;
        this.planPago = planPago;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public double getImporte() {
        return importe;
    }

    public void setImporte(double importe) {
        this.importe = importe;
    }

    public boolean isPagadas() {
        return pagadas;
    }

    public void setPagadas(boolean pagadas) {
        this.pagadas = pagadas;
    }

    public boolean isAnuladas() {
        return anuladas;
    }

    public void setAnuladas(boolean anuladas) {
        this.anuladas = anuladas;
    }

    public boolean isCuotaFija() {
        return cuotaFija;
    }

    public void setCuotaFija(boolean cuotaFija) {
        this.cuotaFija = cuotaFija;
    }

    public boolean isPendientesPago() {
        return pendientesPago;
    }

    public void setPendientesPago(boolean pendientesPago) {
        this.pendientesPago = pendientesPago;
    }

    public boolean isPlanPago() {
        return planPago;
    }

    public void setPlanPago(boolean planPago) {
        this.planPago = planPago;
    }
}
