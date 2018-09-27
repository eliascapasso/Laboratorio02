package ar.edu.utn.frsf.dam.isi.laboratorio02.Adaptadores;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ar.edu.utn.frsf.dam.isi.laboratorio02.FilaHistorialActivity;
import ar.edu.utn.frsf.dam.isi.laboratorio02.PedidoHolder;
import ar.edu.utn.frsf.dam.isi.laboratorio02.PedidoRepositoryActivity;
import ar.edu.utn.frsf.dam.isi.laboratorio02.R;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Pedido;

public class PedidoAdapter extends ArrayAdapter{
    private Context contexto;
    private List<Pedido> listaPedidos;

    public TextView tvMailPedido;
    public TextView tvHoraEntrega;
    public TextView tvCantidadItems;
    public TextView tvPrecio;
    public TextView tvEstado;
    public ImageView ivTipoEntrega;
    public Button btnCancelar;
    public Button btnVerDetalle;

    public PedidoAdapter(Context context, List<Pedido> objects) {
        super(context, 0, objects);
        this.contexto = context;
        this.listaPedidos = objects;
    }

    @Override
    public int getCount() {
        return listaPedidos.size();
    }

    @Override
    public Object getItem(int position) {
        return listaPedidos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return listaPedidos.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View vista = obtenerVista(convertView);

        seteaAtributos(position);

        cancelarPedido(position);

        verDetallePedido(position);

        return vista;
    }

    private View obtenerVista(View convertView){
        PedidoHolder pedidoHolder;
        //Convertimos la vista por defecto en el tipo de nuestra vista personalizada
        FilaHistorialActivity view = (FilaHistorialActivity) convertView;
        if(view == null){
            //Instanciamos la vista y el PedidoHolder
            pedidoHolder = new PedidoHolder();
            view = new FilaHistorialActivity(contexto);
            //Instanciamos los recursos
            tvMailPedido = (TextView)view.findViewById(R.id.tvMailPedido);
            tvHoraEntrega = (TextView)view.findViewById(R.id.tvHoraEntrega);
            tvCantidadItems = (TextView)view.findViewById(R.id.tvCantidadItems);
            tvPrecio = (TextView)view.findViewById(R.id.tvPrecio);
            tvEstado = (TextView)view.findViewById(R.id.tvEstado);
            ivTipoEntrega = (ImageView) view.findViewById(R.id.ivTipoEntrega);
            btnCancelar = (Button) view.findViewById(R.id.btnCancelar);
            btnVerDetalle = (Button) view.findViewById(R.id.btnVerDetalle);
            //asignamos el viewHolder a la vista
            view.setTag(pedidoHolder);
            //Al cambiar el codigo, debemos llamar nosotros al metodo createViews() de la vista
            view.createViews();
        }else{
            //Si la vista ya existe, recuperamos el viewHolder asociado
            pedidoHolder = (PedidoHolder) view.getTag();
        }

        return view;
    }

    private void seteaAtributos(int position){
        tvMailPedido.setText("Contacto: " + listaPedidos.get(position).getMailContacto());
        tvHoraEntrega.setText("Entrega: " + listaPedidos.get(position).getFecha().toString());
        //tvCantidadItems.setText("Items: " + listaPedidos.get(position).getDetalle().size()); //TODO: item??
        tvPrecio.setText("A pagar: $" + listaPedidos.get(position).total().toString());
        tvEstado.setText("Estado: " + listaPedidos.get(position).getEstado().toString());

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

                    Log.d("APP_LAB02", "Cancela el pedido");

                    pedidoSeleccionado.setEstado(Pedido.Estado.CANCELADO);

                    PedidoAdapter.this.notifyDataSetChanged();
                }
            }
        });
    }

    private void verDetallePedido(final int position){
        btnVerDetalle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(contexto, PedidoRepositoryActivity.class);

                Log.d("APP_LAB02", "ID PEDIDO que mando: " + listaPedidos.get(position).getId());

                i.putExtra("idPedido", listaPedidos.get(position).getId());
                contexto.startActivity(i);
            }
        });
    }
}
