package com.example.facturasapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuHost;
import androidx.core.view.MenuProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

        //Toolbar en blanco
        toolbar = findViewById(R.id.toolbarPractica);
        MainActivity.this.setSupportActionBar(toolbar);

        //Cambiar toolbar titulo
        MainActivity.this.setTitle("Facturas");

        //Boton para ir a la actividad de filtros
        MenuHost menu = this;
        menu.addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menuInflater.inflate(R.menu.ida_filtros_app, menu);
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.ida:
                        Intent intent = new Intent(MainActivity.this, FiltrosActivity.class);
                        startActivity(intent);
                        return true;
                }
                return false;
            }
        });

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