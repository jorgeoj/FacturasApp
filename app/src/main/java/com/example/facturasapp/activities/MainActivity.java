package com.example.facturasapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuHost;
import androidx.core.view.MenuProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.facturasapp.Application;
import com.example.facturasapp.R;
import com.example.facturasapp.data.adapters.APIAdapter;
import com.example.facturasapp.data.adapters.FacturasAdapter;
import com.example.facturasapp.data.constantes.Constantes;
import com.example.facturasapp.database.AppDatabase;
import com.example.facturasapp.model.FacturaResult;
import com.example.facturasapp.model.FacturaVO;
import com.example.facturasapp.model.FiltrosVO;
import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private FacturasAdapter adapter;
    private RecyclerView rv1;
    private TextView tvNoDatos;
    private int maxImporte;

    private List<FacturaVO> listaFacturas;

    private FiltrosVO filtros;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rv1 = findViewById(R.id.rv1);
        tvNoDatos = findViewById(R.id.tvNoDatos);

        //Toolbar en blanco
        Toolbar toolbar;
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

    //Cambiamos entre actividades
    public void cambiarActividad() {
        Intent intent = new Intent(MainActivity.this, FiltrosActivity.class);
        intent.putExtra("maxImporte", maxImporte);

        if (filtros != null) {
            Gson gson = new Gson();
            intent.putExtra(Constantes.FILTRO_DATOS, gson.toJson(filtros));
        }

        startActivity(intent);
    }

    //Aqui hacemos la peticion de las facturas
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

                    AppDatabase database = AppDatabase.getDbInstance(Application.context);
                    database.facturaDao().deleteAll();
                    database.facturaDao().insert(listaFacturas);

                    maxImporte = calcularMaximoImporte(listaFacturas);
                    //Vinculamos el recyclerView con la lista


                    //Recibir los datos de la otra actividad
                    String datosFiltro = getIntent().getStringExtra(Constantes.FILTRO_DATOS);
                    if(datosFiltro != null){
                        listaFacturas = llenarDatos(datosFiltro);
                    }
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

    private List<FacturaVO> llenarDatos(String datosFiltro) {
        filtros = new Gson().fromJson(datosFiltro, FiltrosVO.class);
        List<FacturaVO> filtroLista;

        //Comprobamos si hay que filtrar la seekbar y hacemos los cambios
        filtroLista =  comprobarSeekBar(filtros.getImporteSeleccionado());
        //Vemos si hay que filtrar las fechas y las filtramos
        if(!filtros.getFechaInicio().equals(getBaseContext().getResources().getString(R.string.activity_filtros_button_date))  ||
                !filtros.getFechaFin().equals(getBaseContext().getResources().getString(R.string.activity_filtros_button_date))) {
            filtroLista = comprobarFecha(filtros.getFechaInicio(), filtros.getFechaFin(), filtroLista);
        }

        //Comprobamos si hay que filtrar las checkbox
        boolean hayBooleanoTrue = false;
        for (Map.Entry<String, Boolean> entry : filtros.getEstadoCB().entrySet()) {
            if (Boolean.TRUE.equals(entry.getValue())) {
                hayBooleanoTrue = true;
                break;
            }
        }

        //Si entra por aquí es porque hay alguna checkbox marcada
        if (hayBooleanoTrue) {
            filtroLista = comprobarCheckBox(filtros.getEstadoCB(), filtroLista);
        }

        //En caso de que no haya facturas por el filtro, mostraremos mensaje de que no hay facturas
        if(filtroLista.isEmpty()){
            tvNoDatos.setVisibility(View.VISIBLE);
        }

        return filtroLista;
    }




    //Metodo para filtrar las fechas
    private ArrayList<FacturaVO> comprobarFecha(String fechaInicio, String fechaFin, List<FacturaVO> filtroLista) {
        //Creamos lista auxiliar para despues devolverla
        ArrayList<FacturaVO> listaAux = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyyy");
        Date fechaDesde = new Date();
        Date fechaHasta = new Date();

            //Parseamos las fechas para cambiarlas de tipo String a tipo Date
            try {
                fechaDesde = sdf.parse(fechaInicio);
                fechaHasta = sdf.parse(fechaFin);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            //Recorremos la lista de facturas y la que coincidan la añadimos a la lista auxiliar para luego devolverla
            for (FacturaVO facturaFecha: filtroLista) {
                Date fechaFactura;
                try {
                    fechaFactura = sdf.parse(facturaFecha.getFecha());
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                if (fechaFactura.after(fechaDesde) && fechaFactura.before(fechaHasta)) {
                    listaAux.add(facturaFecha);
                }
            }

        return listaAux;
    }

    //Miramos como ha movido la barra el usuario en los filtros y devolvemos la lista filtrada por la barra
    private List<FacturaVO> comprobarSeekBar(int maxImporte) {

        List<FacturaVO> listaAux = new ArrayList<>();

        for (FacturaVO facturaSeekBar : listaFacturas) {
            if (Double.parseDouble(String.valueOf(facturaSeekBar.getImporteOrdenacion())) < maxImporte) {
                listaAux.add(facturaSeekBar);
            }
        }

        return listaAux;
    }

    //Metodo filtro de las checkBox
    private ArrayList<FacturaVO> comprobarCheckBox(Map<String, Boolean> estadoCB, List<FacturaVO> filtroLista) {
        ArrayList<FacturaVO> listaAux = new ArrayList<>();

        for (FacturaVO factura: filtroLista) {
            String descEstado = factura.getDescEstado();
            if (estadoCB.containsKey(descEstado) && Boolean.TRUE.equals(estadoCB.get(descEstado))) {
                listaAux.add(factura);
            }
        }

        return listaAux;
    }

    //Calculamos el máximo importe de las facturas
    private int calcularMaximoImporte(List<FacturaVO> listaFactura) {
        int importeMax = 0;

        for (FacturaVO factura : listaFactura) {
            double maxFactura = factura.getImporteOrdenacion();
            if (importeMax < maxFactura) {
                importeMax = (int) Math.ceil(maxFactura);
            }
        }
        return importeMax;
    }

}