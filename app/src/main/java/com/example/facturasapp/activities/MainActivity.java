package com.example.facturasapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuHost;
import androidx.core.view.MenuProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.facturasapp.data.adapters.APIAdapter;
import com.example.facturasapp.data.constantes.Constantes;
import com.example.facturasapp.model.FacturaResult;
import com.example.facturasapp.data.adapters.FacturasAdapter;
import com.example.facturasapp.R;
import com.example.facturasapp.model.FacturaVO;
import com.example.facturasapp.model.FiltrosVO;
import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private FacturasAdapter adapter;
    private RecyclerView rv1;
    private Toolbar toolbar;
    public int maxImporte;

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
        intent.putExtra("maxImporte", maxImporte);
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

    private ArrayList<FacturaVO> llenarDatos(String datosFiltro) {
        FiltrosVO filtros = new Gson().fromJson(datosFiltro, FiltrosVO.class);
        ArrayList<FacturaVO> filtroLista;

        filtroLista =  comprobarSeekBar(filtros.getMaxImporte());
        if(!Objects.equals(filtros.getFechaInicio(), getBaseContext().getResources().getString(R.string.activity_filtros_button_date)) ||
                !Objects.equals(filtros.getFechaFin(), getBaseContext().getResources().getString(R.string.activity_filtros_button_date))) {
            filtroLista = comprobarFecha(filtros.getFechaInicio(), filtros.getFechaFin(), filtroLista);
        }
        comprobarCheckBox();
        return filtroLista;
    }



    //Metodo para filtrar las fechas
    private ArrayList<FacturaVO> comprobarFecha(String fechaInicio, String fechaFin, ArrayList<FacturaVO> filtroLista) {
        //Creamos lista auxiliar para despues devolverla
        ArrayList<FacturaVO> listaAux = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyyy");
        Date fechaDesde = new Date();
        Date fechaHasta = new Date();

        {
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
        }

        return listaAux;
    }

    private ArrayList<FacturaVO> comprobarSeekBar(int maxImporte) {

        ArrayList<FacturaVO> listaAux = new ArrayList<>();

        for (FacturaVO facturaSeekBar : listaFacturas) {
            if (Double.parseDouble(String.valueOf(facturaSeekBar.getImporteOrdenacion())) < maxImporte) {
                listaAux.add(facturaSeekBar);
            }
        }

        return listaAux;
    }

    //Metodo filtro de las checkBox
    private void comprobarCheckBox() {
        ArrayList<FacturaVO> listaAux = new ArrayList<>();
        for (FacturaVO factura: listaFacturas) {
            if (factura.getDescEstado().equals(Constantes.PAGADAS)){

            }
        }
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
}