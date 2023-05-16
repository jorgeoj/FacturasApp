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
import java.util.Map;

public class FiltrosActivity extends AppCompatActivity {

    private int maxImporte;
    private Toolbar toolbar;
    private SeekBar seekBar;
    private TextView tvValorImporte;
    private CheckBox cbPagadas;
    private CheckBox cbAnuladas;
    private CheckBox cbCuotaFija;
    private CheckBox cbPendientesPago;
    private CheckBox cbPlanPago;
    private Button botonAplicar;
    private Button botonFechaDesde;
    private Button botonFechaHasta;
    private Button botonEliminar;
    private TextView tvMaxSeekBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtros);

        instanciarVariables();

        maxImporte = getIntent().getIntExtra("maxImporte", 0);

        String datosFiltro = getIntent().getStringExtra(Constantes.FILTRO_DATOS);
        if(datosFiltro != null){
            rellenarDatos(datosFiltro);
        } else {
            seekBar.setProgress(maxImporte);
            tvValorImporte.setText(String.valueOf(maxImporte));
        }

        //Metodo para cargar toolbar en blanco
        establecerToolbar();

        //Funcionalidades del menu de la toolbar
        establecerMenuToolbar();

        //Obtenemos las fechas
        obtenerFechaInicio();
        obtenerFechaFinal();

        //Usamos el maximo para los textview asociados a la seekbar
        //Y controlamos el movimiento en este metodo
        pintarMaxSeekbar();

        //Inicializamos las checkbox
        botonAplicar.setOnClickListener(view -> {
            Gson gson = new Gson();
            Intent intent = new Intent(FiltrosActivity.this, MainActivity.class);
            HashMap<String, Boolean> estadosCB = new HashMap<>();
            estadosCB.put(Constantes.PAGADAS, cbPagadas.isChecked());
            estadosCB.put(Constantes.ANULADAS, cbAnuladas.isChecked());
            estadosCB.put(Constantes.CUOTA_FIJA, cbCuotaFija.isChecked());
            estadosCB.put(Constantes.PENDIENTES_PAGO, cbPendientesPago.isChecked());
            estadosCB.put(Constantes.PLAN_PAGO, cbPlanPago.isChecked());
            //Creamos un objeto filtro con los parametros obtenidos y lo enviamos
            FiltrosVO filtroEnviado = new FiltrosVO(botonFechaDesde.getText().toString(), botonFechaHasta.getText().toString(), Integer.parseInt(tvValorImporte.getText().toString()), maxImporte, estadosCB);
            //Para llevar el filtro a la otra clase
            intent.putExtra(Constantes.FILTRO_DATOS, gson.toJson(filtroEnviado));
            startActivity(intent);
        });

        //Boton para eliminar los filtros
        botonEliminar.setOnClickListener(view -> {
            //botones de fecha por defecto
            restablecerFechas();

            //SeekBar por defecto
            restablecerSeekbar(maxImporte);

            //Todas las checkbox por defecto
            restablecerCheckBox();
        });

    }

    //Inicio aqui todas las variables y asi no repito codigo
    private void instanciarVariables() {
        seekBar = findViewById(R.id.seekBar);
        tvValorImporte = findViewById(R.id.tvValorImporte);
        tvMaxSeekBar = findViewById(R.id.tvMaxSeekbar);
        botonFechaDesde = findViewById(R.id.fechaDesde);
        botonFechaHasta = findViewById(R.id.fechaHasta);
        cbPagadas = findViewById(R.id.cbPagadas);
        cbAnuladas = findViewById(R.id.cbAnuladas);
        cbCuotaFija = findViewById(R.id.cbCuotaFija);
        cbPendientesPago = findViewById(R.id.cbPendientesPago);
        cbPlanPago = findViewById(R.id.cbPlanPago);
        botonAplicar = findViewById(R.id.buttonAplicar);
        botonEliminar = findViewById(R.id.buttonEliminarFiltros);
        toolbar = findViewById(R.id.toolbarPracticaFiltros);
    }

    //Rellenamos los datos de los filtros
    private void rellenarDatos(String datosFiltro) {
        FiltrosVO filtros = new Gson().fromJson(datosFiltro, FiltrosVO.class);

        botonFechaDesde.setText(filtros.getFechaInicio());

        botonFechaHasta.setText(filtros.getFechaFin());

        seekBar.setProgress(filtros.getImporteSeleccionado());

        tvValorImporte.setText(String.valueOf(filtros.getImporteSeleccionado()));

        for (Map.Entry<String, Boolean> entry : filtros.getEstadoCB().entrySet()) {
            String clave = entry.getKey();
            Boolean valor = entry.getValue();

            // Establecer el estado del checkbox correspondiente
            switch (clave) {
                case Constantes.PAGADAS:
                    cbPagadas.setChecked(valor);
                    break;
                case Constantes.ANULADAS:
                    cbAnuladas.setChecked(valor);
                    break;
                case Constantes.CUOTA_FIJA:
                    cbCuotaFija.setChecked(valor);
                    break;
                case Constantes.PENDIENTES_PAGO:
                    cbPendientesPago.setChecked(valor);
                    break;
                case Constantes.PLAN_PAGO:
                    cbPlanPago.setChecked(valor);
                    break;
                default:
                    //No hacer nada
                    break;
            }
        }

    }

    //Cambiamos el color y el titulo de la toolbar
    private void establecerToolbar() {
        //Toolbar en blanco
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

    private void pintarMaxSeekbar() {

        //Seekbar y textos de la seekbar, inicializar y onClick
        seekBar.setMax(maxImporte);
        tvMaxSeekBar.setText(String.valueOf(maxImporte));

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
        botonFechaDesde.setText(R.string.activity_filtros_button_date);
        botonFechaHasta.setText(R.string.activity_filtros_button_date);
    }

    private void restablecerSeekbar(int maxImporte) {
        //Poner la seekbar al valor maximo de las facturas como predeterminado
        seekBar.setProgress(maxImporte);
    }

    private void restablecerCheckBox() {
        //Quitar el check a las checkbox
        cbPagadas.setChecked(false);
        cbAnuladas.setChecked(false);
        cbCuotaFija.setChecked(false);
        cbPendientesPago.setChecked(false);
        cbPlanPago.setChecked(false);
    }

}
