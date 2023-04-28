package com.example.facturasapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuHost;
import androidx.core.view.MenuProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.facturasapp.R;

public class FiltrosActivity extends AppCompatActivity {

    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtros);

        //Toolbar en blanco
        toolbar = findViewById(R.id.toolbarPracticaFiltros);
        FiltrosActivity.this.setSupportActionBar(toolbar);

        //Cambiar toolbar titulo
        FiltrosActivity.this.setTitle("Filtrar facturas");

        //Boton para salir a la actividad de filtros
        MenuHost menu = this;
        menu.addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menuInflater.inflate(R.menu.vuelta_main_app, menu);
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.vuelta:
                        Intent intent = new Intent(FiltrosActivity.this, MainActivity.class);
                        startActivity(intent);
                        return true;
                }
                return false;
            }
        });
    }
}