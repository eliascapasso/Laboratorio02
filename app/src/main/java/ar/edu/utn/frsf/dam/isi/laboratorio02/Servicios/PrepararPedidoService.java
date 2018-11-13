package ar.edu.utn.frsf.dam.isi.laboratorio02.Servicios;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;
import java.util.List;

import ar.edu.utn.frsf.dam.isi.laboratorio02.EstadoPedidoReceiver;
import ar.edu.utn.frsf.dam.isi.laboratorio02.dao.PedidoDetalleRepository;
import ar.edu.utn.frsf.dam.isi.laboratorio02.dao.PedidoRepository;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Pedido;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.PedidoDetalle;

public class PrepararPedidoService extends IntentService {
    private PedidoRepository pedidoRepository;
    private PedidoDetalleRepository pedidoDetalleRepository;
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
                    Thread.currentThread().sleep(20000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Log.d("APP_LAB02", "Se despierta el hilo");

                pedidoRepository = new PedidoRepository(getApplicationContext());
                pedidoDetalleRepository = new PedidoDetalleRepository(getApplicationContext());
                listaPedidos = pedidoRepository.getLista();

                //envia el broadcastreciver
                Intent intent = new Intent(PrepararPedidoService.this, EstadoPedidoReceiver.class);

                verificarEstadoPedidos(intent);

                intent.setAction(EstadoPedidoReceiver.ESTADO_EN_PREPARACION);
                sendBroadcast(intent);
            }
        };
        Thread unHilo = new Thread(r);
        unHilo.start();
    }

    private void verificarEstadoPedidos(Intent i){
        for(Pedido p: listaPedidos){
            if(p.getEstado().equals(Pedido.Estado.ACEPTADO)){
                p.setEstado(Pedido.Estado.EN_PREPARACION);

                pedidoRepository.actualizarPedido(p);
                for(PedidoDetalle detalle: p.getDetalle()){
                    pedidoDetalleRepository.guardarDetalle(detalle);
                }

                i.putExtra("idPedido", p.getId());
            }
        }
    }
}
