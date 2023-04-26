package com.example.facturasapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements Callback<List<FacturaVO>> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Call<List<FacturaVO>> call = FacturaAdapter.getService().getList();
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<List<FacturaVO>> call, Response<List<FacturaVO>> response) {
        if (response.isSuccessful()) {
            List<FacturaVO> facturas = response.body();
        }
    }

    @Override
    public void onFailure(Call<List<FacturaVO>> call, Throwable t) {

    }
}