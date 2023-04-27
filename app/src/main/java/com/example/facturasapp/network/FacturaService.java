package com.example.facturasapp.network;

import com.example.facturasapp.model.FacturaResult;

import retrofit2.Call;
import retrofit2.http.GET;

// Esta clase se encarga de llamar la lista de facturas de internet
public interface FacturaService {

    @GET("facturas")
    Call<FacturaResult> getList();
}
