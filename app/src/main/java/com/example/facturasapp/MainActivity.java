package com.example.facturasapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements Callback<FacturaResult> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Call<FacturaResult> call = FacturaAdapter.getService().getList();
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<FacturaResult> call, Response<FacturaResult> response) {
        if (response.isSuccessful()) {
            Log.d("prueba", response.body().getFacturas().toString());
        }
    }

    @Override
    public void onFailure(Call<FacturaResult> call, Throwable t) {

    }
}