package com.example.facturasapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.RelativeLayout;

import com.example.facturasapp.data.adapters.APIAdapter;
import com.example.facturasapp.model.FacturaResult;
import com.example.facturasapp.data.adapters.FacturasAdapter;
import com.example.facturasapp.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements Callback<FacturaResult> {

    private FacturasAdapter adapter;
    private RecyclerView rv1;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rv1 = findViewById(R.id.rv1);
        toolbar = findViewById(R.id.toolbarPractica);

        MainActivity.this.setSupportActionBar(toolbar);

        //Cambiar toolbar titulo
        MainActivity.this.setTitle("Facturas");

        Call<FacturaResult> call = APIAdapter.getService().getList();
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<FacturaResult> call, Response<FacturaResult> response) {
        if (response.isSuccessful()) {
            Log.d("prueba", response.body().getFacturas().toString());
            //Vinculamos el recyclerView con la lista
            adapter = new FacturasAdapter(response.body().getFacturas());
            rv1.setAdapter(adapter);
        }
    }

    @Override
    public void onFailure(Call<FacturaResult> call, Throwable t) {

    }


}