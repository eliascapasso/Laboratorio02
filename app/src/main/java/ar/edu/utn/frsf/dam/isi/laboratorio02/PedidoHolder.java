package ar.edu.utn.frsf.dam.isi.laboratorio02;

import android.content.Context;
import android.graphics.Color;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        final View vista = inflater.inflate(R.layout.fila_historial, null);

        inicializaAtributos(vista);

        seteaAtributos(position);

        cancelarPedido(position);

        return vista;
    }

    private void inicializaAtributos(View vista){
        tvMailPedido = (TextView)vista.findViewById(R.id.tvMailPedido);
        tvHoraEntrega = (TextView)vista.findViewById(R.id.tvHoraEntrega);
        tvCantidadItems = (TextView)vista.findViewById(R.id.tvCantidadItems);
        tvPrecio = (TextView)vista.findViewById(R.id.tvPrecio);
        tvEstado = (TextView)vista.findViewById(R.id.tvEstado);
        ivTipoEntrega = (ImageView) vista.findViewById(R.id.ivTipoEntrega);
        btnCancelar = (Button) vista.findViewById(R.id.btnCancelar);
    }

    private void seteaAtributos(int position){
        tvMailPedido.setText(listaPedidos.get(position).getMailContacto());
        tvHoraEntrega.setText(listaPedidos.get(position).getFecha().toString());
        //tvCantidadItems.setText(listaPedidos.get(position).getDetalle().size()); //TODO: item??
        tvPrecio.setText("A pagar: $" + listaPedidos.get(position).total().toString());
        tvEstado.setText(listaPedidos.get(position).getEstado().toString());

        //Setea el color del TextView "tvEstado" de acuerdo al estado actual del pedido
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

        //Setea imagen de acuerdo al tipo de entrega seleccionado en el pedido
        if(listaPedidos.get(position).getRetirar()){
            ivTipoEntrega.setImageResource(R.drawable.retira);
        }
        else{
            ivTipoEntrega.setImageResource(R.drawable.envio);
        }
    }

    private void cancelarPedido(final int position){
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Pedido pedidoSeleccionado = listaPedidos.get(position);
                if( pedidoSeleccionado.getEstado().equals(Pedido.Estado.REALIZADO)||
                        pedidoSeleccionado.getEstado().equals(Pedido.Estado.ACEPTADO)||
                        pedidoSeleccionado.getEstado().equals(Pedido.Estado.EN_PREPARACION)){

                    pedidoSeleccionado.setEstado(Pedido.Estado.CANCELADO);

                    HistorialPedidoActivity.adaptadorHistorialPedido.notifyDataSetChanged();
                    return;
                }
            }
        });
    }
}
