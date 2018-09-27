package ar.edu.utn.frsf.dam.isi.laboratorio02;


import android.content.Context;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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
        tvCantidadItems = tvCantidadItems;
        tvPrecio = tvPrecio;
        tvEstado = tvEstado;
        ivTipoEntrega = ivTipoEntrega;
        btnCancelar = btnCancelar;
        btnVerDetalle = btnVerDetalle;
    }
    // Resto de métodos de la clase
}
