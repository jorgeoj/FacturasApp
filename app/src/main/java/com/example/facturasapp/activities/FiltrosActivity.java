package com.example.facturasapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuHost;
import androidx.core.view.MenuProvider;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.facturasapp.R;
import com.example.facturasapp.data.constantes.Constantes;
import com.example.facturasapp.model.FacturaVO;
import com.example.facturasapp.model.FiltrosVO;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class FiltrosActivity extends AppCompatActivity {

    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtros);

        //Metodo para cargar toolbar en blanco
        establecerToolbar();

        //Funcionalidades del menu de la toolbar
        establecerMenuToolbar();

        obtenerFechaInicio();
        obtenerFechaFinal();


        //Seekbar -->
        TextView tvValorImporte = findViewById(R.id.tvValorImporte);
        //Calculamos el valor máximo de las facturas
        int maxImporte = getIntent().getIntExtra("maxImporte", 0);

        //Usamos el maximo para los textview asociados a la seekbar
        //Y controlamos el movimiento en este metodo
        pintarMaxSeekbar(maxImporte);

        //Checkbox -->
        //Inicializamos las checkbox
        CheckBox cbPagadas = findViewById(R.id.cbPagadas);
        CheckBox cbAnuladas = findViewById(R.id.cbAnuladas);
        CheckBox cbCuotaFija = findViewById(R.id.cbCuotaFija);
        CheckBox cbPendientesPago = findViewById(R.id.cbPendientesPago);
        CheckBox cbPlanPago = findViewById(R.id.cbPlanPago);

        //Boton para aplicar los filtros
        Button botonAplicar = findViewById(R.id.buttonAplicar);
        botonAplicar.setOnClickListener(new View.OnClickListener() {
            Button botonFechaDesde = findViewById(R.id.fechaDesde);
            Button botonFechaHasta = findViewById(R.id.fechaHasta);
            @Override
            public void onClick(View view) {
                Gson gson = new Gson();
                Intent intent = new Intent(FiltrosActivity.this, MainActivity.class);
                HashMap<String, Boolean> estadosCB = new HashMap<>();
                estadosCB.put(Constantes.PAGADAS, cbPagadas.isChecked());
                estadosCB.put(Constantes.ANULADAS, cbAnuladas.isChecked());
                estadosCB.put(Constantes.CUOTA_FIJA, cbCuotaFija.isChecked());
                estadosCB.put(Constantes.PENDIENTES_PAGO, cbPendientesPago.isChecked());
                estadosCB.put(Constantes.PLAN_PAGO, cbPlanPago.isChecked());
                //Creamos un objeto filtro con los parametros obtenidos y lo enviamos
                FiltrosVO filtroEnviado = new FiltrosVO(botonFechaDesde.getText().toString(), botonFechaHasta.getText().toString(), Integer.parseInt(tvValorImporte.getText().toString()), estadosCB);
                //Para llevar el filtro a la otra clase
                intent.putExtra(Constantes.FILTRO_DATOS, gson.toJson(filtroEnviado));
                startActivity(intent);
            }
        });

        //Boton para eliminar los filtros
        Button botonEliminar = findViewById(R.id.buttonEliminarFiltros);
        botonEliminar.setOnClickListener(view -> {
            //botones de fecha por defecto
            restablecerFechas();

            //SeekBar por defecto
            restablecerSeekbar(maxImporte);

            //Todas las checkbox por defecto
            restablecerCheckBox();
        });

    }




    //Métodos a los que llamamos arriba -->

    //Cambiamos el color y el titulo de la toolbar
    private void establecerToolbar() {
        //Toolbar en blanco
        toolbar = findViewById(R.id.toolbarPracticaFiltros);
        FiltrosActivity.this.setSupportActionBar(toolbar);

        //Cambiar toolbar titulo
        FiltrosActivity.this.setTitle(R.string.activity_filtros_main_title_toolbar);
    }

    //Hacemos el menú de la toolbar y la vuelta a la otra actividad
    private void establecerMenuToolbar() {
        //Boton para salir a la actividad de filtros
        MenuHost menu = this;

        menu.addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menuInflater.inflate(R.menu.vuelta_main_app, menu);
            }
            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                if(menuItem.getItemId() == R.id.vuelta){
                    Intent intent = new Intent(FiltrosActivity.this, MainActivity.class);
                    startActivity(intent);
                    return true;
                }else{
                    return false;
                }
            }
        });
    }

    private void obtenerFechaInicio() {
        //Boton para fecha desde, inicializar y al hacer click salga el calendario
        Button botonFechaDesde = findViewById(R.id.fechaDesde);
        botonFechaDesde.setOnClickListener(view -> {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH)+1;
            int day = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog dpd = new DatePickerDialog(FiltrosActivity.this, (view1, year1, monthofyear, dayofmonth) ->
                    botonFechaDesde.setText(dayofmonth + "/" + (monthofyear+1) + "/" + year1), year, month, day);
            dpd.getDatePicker().setMaxDate(new Date().getTime());
            dpd.show();
        });
    }

    private String obtenerFechaFinal() {
        //Boton fecha hasta, inicializar y al hacer click salga el calendario
        Button botonFechaHasta = findViewById(R.id.fechaHasta);
        botonFechaHasta.setOnClickListener(view -> {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH)+1;
            int day = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog dpd = new DatePickerDialog(FiltrosActivity.this, (view1, year1, monthofyear, dayofmonth) ->
                    botonFechaHasta.setText(dayofmonth + "/" + (monthofyear+1) + "/" + year1), year, month, day);
            dpd.show();
        });

        return botonFechaHasta.getText().toString();
    }

    private void pintarMaxSeekbar(int maxImporte) {
        //Seekbar y textos de la seekbar, inicializar y onClick
        SeekBar seekBar = findViewById(R.id.seekBar);
        TextView tvMaxSeekBar = (TextView) findViewById(R.id.tvMaxSeekbar);
        TextView tvValorImporte = (TextView) findViewById(R.id.tvValorImporte);

        seekBar.setMax(maxImporte);
        seekBar.setProgress(maxImporte);
        tvMaxSeekBar.setText(String.valueOf(maxImporte));
        tvValorImporte.setText(String.valueOf(maxImporte));

        //Acciones a realizar en caso de mover la barra
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                tvValorImporte.setText(String.valueOf(i));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //No hace nada
                Log.d("onStartTrackingTouch", "onStartTrackingTouch: ha fallado");
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //No hace nada
                Log.d("onStopTrackingTouch", "onStopTrackingTouch: ha fallado");
            }
        });
    }

    private void restablecerFechas() {
        // Restablecer valores de fecha
        Button fechaDesde = findViewById(R.id.fechaDesde);
        fechaDesde.setText(R.string.activity_filtros_button_date);
        Button fechaHasta = findViewById(R.id.fechaHasta);
        fechaHasta.setText(R.string.activity_filtros_button_date);
    }

    private void restablecerSeekbar(int maxImporte) {
        SeekBar seekBar = findViewById(R.id.seekBar);

        //Poner la seekbar al valor maximo de las facturas como predeterminado
        seekBar.setProgress(maxImporte);
    }

    private void restablecerCheckBox() {
        CheckBox chBoxPagadas = findViewById(R.id.cbPagadas);
        CheckBox chBoxAnuladas = findViewById(R.id.cbAnuladas);
        CheckBox chBoxCuotaFija = findViewById(R.id.cbCuotaFija);
        CheckBox chBoxPendientesPago = findViewById(R.id.cbPendientesPago);
        CheckBox chBoxPlanPago = findViewById(R.id.cbPlanPago);

        //Quitar el check a las checkbox
        chBoxPagadas.setChecked(false);
        chBoxAnuladas.setChecked(false);
        chBoxCuotaFija.setChecked(false);
        chBoxPendientesPago.setChecked(false);
        chBoxPlanPago.setChecked(false);
    }

}
