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
import com.example.facturasapp.model.FacturaVO;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private FacturasAdapter adapter;
    private RecyclerView rv1;
    private Toolbar toolbar;

    private ArrayList<FacturaVO> listaFacturas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rv1 = findViewById(R.id.rv1);

        //Toolbar en blanco
        toolbar = findViewById(R.id.toolbarPractica);
        MainActivity.this.setSupportActionBar(toolbar);

        //Cambiar toolbar titulo, el titulo está en los strings
        MainActivity.this.setTitle(R.string.activity_main_title_toolbar);

        //Boton para ir a la actividad de filtros
        MenuHost menu = this;
        menu.addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menuInflater.inflate(R.menu.ida_filtros_app, menu);
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.ida) {
                    cambiarActividad();
                    return true;
                } else {
                    return false;
                }
            }
        });

        peticionFacturas();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void cambiarActividad() {
        Intent intent = new Intent(MainActivity.this, FiltrosActivity.class);
        intent.putExtra("facturas", listaFacturas);
        startActivity(intent);
    }

    public void peticionFacturas() {
        Call<FacturaResult> call = APIAdapter.getService().getList();
        call.enqueue(new Callback<FacturaResult>() {
            @Override
            public void onResponse(Call<FacturaResult> call, Response<FacturaResult> response) {
                if (response.isSuccessful()) {
                    //El texto de abajo sale en el logcat
                    Log.d("facturas cargadas", response.body().getFacturas().toString());
                    // Obtener la lista de facturas
                    listaFacturas = response.body().getFacturas();
                    //Vinculamos el recyclerView con la lista
                    adapter = new FacturasAdapter(listaFacturas);
                    rv1.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<FacturaResult> call, Throwable t) {
                // No op. Método de la implementación de CallBack
                Log.d("onFailure", "Fallo callback de enqueue");
            }
        });
    }
}