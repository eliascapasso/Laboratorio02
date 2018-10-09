package ar.edu.utn.frsf.dam.isi.laboratorio02;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
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

        Toast.makeText(context, "Pedido para " + unPedido.getMailContacto() + " ha cambiado a estado ACEPTADO", Toast.LENGTH_LONG).show();

        NotificationManager nManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "CANAL01")
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle("Tu pedido fue aceptado")
                .setContentText("El costo será de " + unPedido.total() +
                                "\nPrevisto el envío para " + unPedido.getFecha())
                .setWhen(System.currentTimeMillis());

        Intent targetIntent = new Intent(context, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
                targetIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        builder.setAutoCancel(true);

        nManager.notify(123456, builder.build());
    }
}
