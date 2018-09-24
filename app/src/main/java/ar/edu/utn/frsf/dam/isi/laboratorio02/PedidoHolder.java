package ar.edu.utn.frsf.dam.isi.laboratorio02;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import ar.edu.utn.frsf.dam.isi.laboratorio02.Adaptadores.PedidoAdapter;
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
        //tvCantidadItems.setText(listaPedidos.get(position).getDetalle().size()); //TODO: item??
        tvPrecio.setText("A pagar: $" + listaPedidos.get(position).total().toString()); //TODO: ver

        switch (listaPedidos.get(position).getEstado()){
            case LISTO:
                this.tvEstado.setTextColor(Color.DKGRAY);
            break;
            case ENTREGADO:
                this.tvEstado.setTextColor(Color.BLUE);
                break;
            case CANCELADO:
            case RECHAZADO:
                this.tvEstado.setTextColor(Color.RED);
                break;
            case ACEPTADO:
                this.tvEstado.setTextColor(Color.GREEN);
                break;
            case EN_PREPARACION:
                this.tvEstado.setTextColor(Color.MAGENTA);
                break;
            case REALIZADO:
                this.tvEstado.setTextColor(Color.BLUE);
                break;
        }

        tvEstado.setText(listaPedidos.get(position).getEstado().toString());

        if(listaPedidos.get(position).getRetirar()){
            ivTipoEntrega.setImageResource(R.drawable.retira);
        }
        else{
            ivTipoEntrega.setImageResource(R.drawable.envio);
        }

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int indice = (int) view.getTag();
                Pedido pedidoSeleccionado = listaPedidos.get(indice);
                if (pedidoSeleccionado.getEstado().equals(Pedido.Estado.REALIZADO) ||
                        pedidoSeleccionado.getEstado().equals(Pedido.Estado.ACEPTADO) ||
                        pedidoSeleccionado.getEstado().equals(Pedido.Estado.EN_PREPARACION)) {
                    pedidoSeleccionado.setEstado(Pedido.Estado.CANCELADO);

                    //PedidoAdapter pedidoAdapter = new PedidoAdapter(contexto, listaPedidos);
                    Log.d("APP_LAB02", "ENTRA");
                    //PedidoAdapter.this.notifyDataSetChanged();
                    return;
                }
            }
        };

        return vista;
    }
}
