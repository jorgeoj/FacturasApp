package com.example.facturasapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "factura")
public class FacturaVO implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String descEstado;
    private Double importeOrdenacion;
    private String fecha;

    public FacturaVO(String descEstado, Double importeOrdenacion, String fecha) {
        this.descEstado = descEstado;
        this.importeOrdenacion = importeOrdenacion;
        this.fecha = fecha;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescEstado() {
        return descEstado;
    }

    public Double getImporteOrdenacion() {
        return importeOrdenacion;
    }

    public String getFecha() {
        return fecha;
    }

    // Implementa los métodos Parcelable
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.descEstado);
        dest.writeDouble(this.importeOrdenacion);
        dest.writeString(this.fecha);
    }

    protected FacturaVO(Parcel in) {
        this.descEstado = in.readString();
        this.importeOrdenacion = in.readDouble();
        this.fecha = in.readString();
    }

    public static final Creator<FacturaVO> CREATOR = new Creator<FacturaVO>() {
        @Override
        public FacturaVO createFromParcel(Parcel source) {
            return new FacturaVO(source);
        }

        @Override
        public FacturaVO[] newArray(int size) {
            return new FacturaVO[size];
        }
    };
}

