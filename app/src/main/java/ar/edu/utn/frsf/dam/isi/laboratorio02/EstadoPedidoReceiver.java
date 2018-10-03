package ar.edu.utn.frsf.dam.isi.laboratorio02;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import ar.edu.utn.frsf.dam.isi.laboratorio02.dao.PedidoRepository;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Pedido;

public class EstadoPedidoReceiver extends BroadcastReceiver {

    public static String ESTADO_ACEPTADO = "ACEPTADO";

    private PedidoRepository pedidoRepository = new PedidoRepository();
    private Pedido unPedido;


    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        Bundle extras = intent.getExtras();
        int idPedido = extras.getInt("idPedido");

        unPedido = pedidoRepository.buscarPorId(idPedido);

        Toast.makeText(context, "Pedido para " + pedidoRepository.buscarPorId(idPedido).getMailContacto() + " ha cambiado a estado ACEPTADO", Toast.LENGTH_LONG).show();
    }
}
