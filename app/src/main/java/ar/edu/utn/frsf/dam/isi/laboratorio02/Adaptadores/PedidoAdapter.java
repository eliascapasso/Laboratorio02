package ar.edu.utn.frsf.dam.isi.laboratorio02.Adaptadores;

import android.content.Context;
import java.util.List;
import ar.edu.utn.frsf.dam.isi.laboratorio02.PedidoHolder;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Pedido;

public class PedidoAdapter extends PedidoHolder{
    private Context contexto;
    private List<Pedido> Pedidos;

    public PedidoAdapter(Context context, List<Pedido> objects) {
        super(context, 0, objects); //TODO: para que es el 2do argumento?
        this.contexto = context;
        this.Pedidos = objects;
    }
}
