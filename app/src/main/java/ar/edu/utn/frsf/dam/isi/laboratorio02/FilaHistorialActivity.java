package ar.edu.utn.frsf.dam.isi.laboratorio02;


import android.content.Context;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import ar.edu.utn.frsf.dam.isi.laboratorio02.Adaptadores.PedidoHolder;

public class FilaHistorialActivity extends LinearLayout {
    public TextView tvMailPedido;
    public TextView tvHoraEntrega;
    public TextView tvCantidadItems;
    public TextView tvPrecio;
    public TextView tvEstado;
    public ImageView ivTipoEntrega;
    public Button btnCancelar;
    public Button btnVerDetalle;
    private PedidoHolder pedidoHolder;

    public FilaHistorialActivity(Context context){
        super(context);
        inflate(context, R.layout.fila_historial, this);
    }

    public void createViews(){
        //Instanciamos los elementos de la vista
        pedidoHolder = (PedidoHolder) this.getTag();
        tvMailPedido = pedidoHolder.tvMailPedido;
        tvHoraEntrega = pedidoHolder.tvHoraEntrega;
        tvCantidadItems = pedidoHolder.tvCantidadItems;
        tvPrecio = pedidoHolder.tvPrecio;
        tvEstado = pedidoHolder.tvEstado;
        ivTipoEntrega = pedidoHolder.ivTipoEntrega;
        btnCancelar = pedidoHolder.btnCancelar;
        btnVerDetalle = pedidoHolder.btnVerDetalle;
    }
}
