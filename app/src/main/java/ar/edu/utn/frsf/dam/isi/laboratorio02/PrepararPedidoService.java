package ar.edu.utn.frsf.dam.isi.laboratorio02;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;
import java.util.List;

import ar.edu.utn.frsf.dam.isi.laboratorio02.dao.PedidoRepository;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Pedido;

public class PrepararPedidoService extends IntentService {
    private PedidoRepository pedidoRepository = new PedidoRepository();
    private List<Pedido> listaPedidos;

    public PrepararPedidoService() {
        super("PrepararPedidoService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.currentThread().sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Log.d("APP_LAB02", "Se despierta el hilo");

                listaPedidos = pedidoRepository.getLista();



                //envia el broadcastreciver
                Intent intent = new Intent(PrepararPedidoService.this, EstadoPedidoReceiver.class);

                verificarEstadoPedidos(intent);

                intent.setAction(EstadoPedidoReceiver.ESTADO_EN_PREPARACION);
                sendBroadcast(intent);

                /*runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(PedidoRepositoryActivity.this,
                                "Se despierta el hilo",
                                Toast.LENGTH_LONG).show();
                    }
                });*/
            }
        };
        Thread unHilo = new Thread(r);
        unHilo.start();
    }

    private void verificarEstadoPedidos(Intent i){
        for(Pedido p: listaPedidos){
            if(p.getEstado().equals(Pedido.Estado.ACEPTADO)){
                p.setEstado(Pedido.Estado.EN_PREPARACION);

                i.putExtra("idPedido", p.getId());
            }
        }
    }
}
