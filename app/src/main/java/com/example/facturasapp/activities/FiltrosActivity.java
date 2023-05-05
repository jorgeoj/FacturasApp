package com.example.facturasapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuHost;
import androidx.core.view.MenuProvider;

import android.app.DatePickerDialog;
import android.content.Intent;
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
import com.example.facturasapp.model.FacturaVO;

import java.util.ArrayList;
import java.util.Calendar;

public class FiltrosActivity extends AppCompatActivity {

    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtros);

        //Al pasar de una actividad a otra cargamos la lista aqui y la metemos en una variable
        ArrayList<FacturaVO> listaFactura = cargarListaFacturas();

        //Metodo para cargar toolbar en blanco
        establecerToolbar();

        //Funcionalidades del menu de la toolbar
        establecerMenuToolbar();


        //Botones para la fecha -->
        establecerFechas();


        //Seekbar -->
        //Calculamos el valor máximo de las facturas
        int maxImporte = calcularMaximoImporte(listaFactura);

        //Usamos el maximo para los textview asociados a la seekbar
        //Y controlamos el movimiento en este metodo
        pintarMaxSeekbar(maxImporte);

        //Checkboxes -->
        // TODO checkbox tienen que devolver algo para filtrar o como?



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
                //botones de fecha por defecto
                restablecerFechas();

                //SeekBar por defecto
                restablecerSeekbar(maxImporte);

                //Todas las checkbox por defecto
                restablecerCheckBox();
            }
        });

        // TODO devolverFacturasFiltradas() o devolverFiltroAplicado();

    }


    //Métodos a los que llamamos arriba -->

    //Al pasar de una actividad a otra cargamos la lista aqui y la metemos en una variable
    private ArrayList<FacturaVO> cargarListaFacturas() {
        ArrayList<FacturaVO> listaFactura = getIntent().getParcelableArrayListExtra("facturas");
        Log.d("tamaño facturas", "" + listaFactura.size());

        return listaFactura;
    }

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

    private void establecerFechas() {
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
    }

    private int calcularMaximoImporte(ArrayList<FacturaVO> listaFactura) {
        int maxImporte = 0;

        for (FacturaVO factura : listaFactura) {
            double maxFactura = factura.getImporteOrdenacion();
            if (maxImporte < maxFactura) {
                maxImporte = (int) Math.ceil(maxFactura);
            }
        }
        return maxImporte;
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

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

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
