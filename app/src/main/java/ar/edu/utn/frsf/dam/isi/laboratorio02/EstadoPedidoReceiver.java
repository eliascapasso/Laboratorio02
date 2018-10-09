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
    public static String ESTADO_EN_PREPARACION = "EN_PREPARACION";

    private PedidoRepository pedidoRepository = new PedidoRepository();
    private Pedido unPedido;


    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        Bundle extras = intent.getExtras();
        int idPedido = extras.getInt("idPedido");

        unPedido = pedidoRepository.buscarPorId(idPedido);

        if(unPedido.getEstado().equals(Pedido.Estado.ACEPTADO)){
            Toast.makeText(context, "Pedido para " + unPedido.getMailContacto() + " ha cambiado a estado ACEPTADO", Toast.LENGTH_LONG).show();

            Intent pedidoIntent = new Intent(context, PedidoRepositoryActivity.class);
            pedidoIntent.putExtra("idPedido", idPedido);
            pedidoIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, pedidoIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            NotificationManager nManager = (NotificationManager) context
                    .getSystemService(Context.NOTIFICATION_SERVICE);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "CANAL01")
                    .setSmallIcon(android.R.drawable.ic_dialog_info)
                    .setContentTitle("Tu pedido fue aceptado")
                    .setContentText("El costo será de $" + unPedido.total() +
                            "\nPrevisto el envío para " + unPedido.getFecha())
                    .setStyle(new NotificationCompat.BigTextStyle()
                            .bigText("El costo será de $" + unPedido.total() +
                                    "\nPrevisto el envío para " + unPedido.getFecha()))
                    .setWhen(System.currentTimeMillis())
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true);

            nManager.notify(123456, builder.build());
        }
        else if(unPedido.getEstado().equals(Pedido.Estado.EN_PREPARACION)){
            Toast.makeText(context, "Pedido para " + unPedido.getMailContacto() + " ha cambiado a estado EN_PREPARACION", Toast.LENGTH_LONG).show();

            Intent pedidoIntent = new Intent(context, PedidoRepositoryActivity.class);
            pedidoIntent.putExtra("idPedido", idPedido);
            pedidoIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, pedidoIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            NotificationManager nManager = (NotificationManager) context
                    .getSystemService(Context.NOTIFICATION_SERVICE);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "CANAL01")
                    .setSmallIcon(android.R.drawable.ic_dialog_info)
                    .setContentTitle("Tu pedido está en preparación")
                    .setContentText("El costo será de $" + unPedido.total() +
                            "\nPrevisto el envío para " + unPedido.getFecha())
                    .setStyle(new NotificationCompat.BigTextStyle()
                            .bigText("El costo será de $" + unPedido.total() +
                                    "\nPrevisto el envío para " + unPedido.getFecha()))
                    .setWhen(System.currentTimeMillis())
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true);

            nManager.notify(123456, builder.build());
        }


    }
}
