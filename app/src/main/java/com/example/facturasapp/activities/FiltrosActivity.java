package com.example.facturasapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuHost;
import androidx.core.view.MenuProvider;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.Toast;

import com.example.facturasapp.R;

import java.util.Calendar;

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

        //Boton para fecha desde, inicializar y al hacer click salga el calendario
        Button botonFechaDesde = findViewById(R.id.fechaDesde);
        botonFechaDesde.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH)+1;
                int day = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dpd = new DatePickerDialog(FiltrosActivity.this, (view1, year1, monthofyear, dayofmonth) ->
                        botonFechaDesde.setText(dayofmonth + "/" + (monthofyear+1) + "/" + year1), year, month, day);
                dpd.show();
            }
        });

        //Boton fecha hasta, inicializar y al hacer click salga el calendario
        Button botonFechaHasta = findViewById(R.id.fechaHasta);
        botonFechaHasta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH)+1;
                int day = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dpd = new DatePickerDialog(FiltrosActivity.this, (view1, year1, monthofyear, dayofmonth) ->
                        botonFechaHasta.setText(dayofmonth + "/" + (monthofyear+1) + "/" + year1), year, month, day);
                dpd.show();
            }
        });

        //Boton para aplicar los filtros
        Button botonAplicar = findViewById(R.id.buttonAplicar);
        botonAplicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(this, "Funciona el aplicar", Toast.LENGTH_SHORT).show();
            }
        });

        //Boton para eliminar los filtros
        Button botonEliminar = findViewById(R.id.buttonEliminarFiltros);
        botonEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SeekBar seekBar = findViewById(R.id.seekBar);
                CheckBox chBoxPagadas = findViewById(R.id.cbPagadas);
                CheckBox chBoxAnuladas = findViewById(R.id.cbAnuladas);
                CheckBox chBoxCuotaFija = findViewById(R.id.cbCuotaFija);
                CheckBox chBoxPendientesPago = findViewById(R.id.cbPendientesPago);
                CheckBox chBoxPlanPago = findViewById(R.id.cbPlanPago);

                //Poner la seekbar a 0
                seekBar.setProgress(0);
                //Quitar el check a las checkbox
                chBoxPagadas.setChecked(false);
                chBoxAnuladas.setChecked(false);
                chBoxCuotaFija.setChecked(false);
                chBoxPendientesPago.setChecked(false);
                chBoxPlanPago.setChecked(false);
            }
        });

    }

}
