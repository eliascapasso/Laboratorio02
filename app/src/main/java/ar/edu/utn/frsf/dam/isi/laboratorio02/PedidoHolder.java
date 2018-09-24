package ar.edu.utn.frsf.dam.isi.laboratorio02;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Pedido;

public class PedidoHolder extends BaseAdapter{
    private static LayoutInflater inflater = null;

    public TextView tvMailPedido;
    public TextView tvHoraEntrega;
    public TextView tvCantidadItems;
    public TextView tvPrecio;
    public TextView tvEstado;
    public ImageView ivTipoEntrega;
    public Button btnCancelar;

    private Context contexto;
    private List<Pedido> listaPedidos;

    public PedidoHolder(Context contexto, int i, List<Pedido> objects) {
        this.contexto = contexto;
        this.listaPedidos = objects;

        inflater = (LayoutInflater)contexto.getSystemService(contexto.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return listaPedidos.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final View vista = inflater.inflate(R.layout.fila_historial, null);
        tvMailPedido = (TextView)vista.findViewById(R.id.tvMailPedido);
        tvHoraEntrega = (TextView)vista.findViewById(R.id.tvHoraEntrega);
        tvCantidadItems = (TextView)vista.findViewById(R.id.tvCantidadItems);
        tvPrecio = (TextView)vista.findViewById(R.id.tvPrecio);
        tvEstado = (TextView)vista.findViewById(R.id.tvEstado);
        ivTipoEntrega = (ImageView) vista.findViewById(R.id.ivTipoEntrega);
        btnCancelar = (Button) vista.findViewById(R.id.btnCancelar);

        tvMailPedido.setText(listaPedidos.get(position).getMailContacto());
        tvHoraEntrega.setText(listaPedidos.get(position).getFecha().toString());
        //tvCantidadItems.setText(listaPedidos.get(position).getDetalle().get(position).getCantidad()); //TODO: ver
        //tvPrecio.setText(listaPedidos.get(position).getDetalle().get(position).getProducto().getPrecio().toString()); //TODO: ver
        tvEstado.setText(listaPedidos.get(position).getEstado().toString());

        if(listaPedidos.get(position).getRetirar()){
            ivTipoEntrega.setImageResource(R.drawable.retira);
        }
        else{
            ivTipoEntrega.setImageResource(R.drawable.envio);
        }

        return vista;
    }
}
