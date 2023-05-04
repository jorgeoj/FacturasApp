package com.example.facturasapp.data.adapters;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.facturasapp.R;
import com.example.facturasapp.data.constantes.Constantes;
import com.example.facturasapp.model.FacturaVO;

import java.util.List;


//Esta clase añade la lista de la factura al RecyclerView
public class FacturasAdapter extends RecyclerView.Adapter<FacturasAdapter.ViewHolder> {

    //Inicializamos lista para las facturas
    private List<FacturaVO> listaFacturas;

    //Creamos objeto dialog para el popup
    Dialog mDialog;

    public FacturasAdapter(List<FacturaVO> listaFacturas) {
        this.listaFacturas = listaFacturas;
    }

    //Inflar: para que se muestre y poder acceder la lista
    //Inflar la vista de la factura
    @NonNull
    @Override
    public FacturasAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_factura, parent, false));
    }

    //Poner el texto adecuado de las facturas
    @Override
    public void onBindViewHolder(@NonNull FacturasAdapter.ViewHolder holder, int position) {
        holder.tvImporteOrdenacion.setText(String.valueOf(listaFacturas.get(position).getImporteOrdenacion()) + Constantes.MONEDA);
        holder.tvFecha.setText((listaFacturas.get(position).getFecha()));
        holder.tvDescEstado.setText(listaFacturas.get(position).getDescEstado());
        //Cambiar el color del textview según el estado
        //si el estado no es pendiente o pagada, se pondra el texto en negro
        if(listaFacturas.get(position).getDescEstado().equals(Constantes.ESTADO_PENDIENTE)){
            holder.tvDescEstado.setTextColor(Color.RED);
        }else if(listaFacturas.get(position).getDescEstado().equals(Constantes.ESTADO_PAGADO)){
            //El oxFF es como la almohadilla y el resto es el codigo del color
            //Ell color es es verde
            holder.tvDescEstado.setTextColor(0xFF8BC34A);
        }else{
            holder.tvDescEstado.setTextColor(Color.BLACK);
        }
    }

    //Llamamos al tamaño de la lista
    @Override
    public int getItemCount() {
        return listaFacturas.size();
    }

    //Encuentra los textView del layout de la factura (de la clase layout_factura)
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvFecha;
        TextView tvDescEstado;
        TextView tvImporteOrdenacion;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvFecha = itemView.findViewById(R.id.tvFecha);
            tvDescEstado = itemView.findViewById(R.id.tvDescEstado);
            tvImporteOrdenacion = itemView.findViewById(R.id.tvImporteOrdenacion);
            mDialog = new Dialog(itemView.getContext());

            //Implementacion para  que aparezca el popup
            itemView.setOnClickListener(view -> {
                    mDialog.setContentView(R.layout.layout_popup);
                    mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    TextView mensajePopup = mDialog.findViewById(R.id.mensajePopup);
                    mensajePopup.setText(R.string.layout_popup_text);
                    mDialog.show();

                    //cierra el popup al darle al boton
                    Button cerrarButton = mDialog.findViewById(R.id.buttonCerrarPopup);
                    cerrarButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mDialog.dismiss();
                        }
                    });
            });
        }
    }


}
