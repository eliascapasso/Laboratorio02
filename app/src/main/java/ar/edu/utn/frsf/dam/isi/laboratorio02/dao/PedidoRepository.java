package ar.edu.utn.frsf.dam.isi.laboratorio02.dao;

import android.arch.persistence.room.Room;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Pedido;

public class PedidoRepository {

    //private static List<Pedido> LISTA_PEDIDOS = new ArrayList<>();
    private static int GENERADOR_ID_PEDIDO = 0;

    private static PedidoRepository _REPO= null;
    private PedidoDAO pedidoDAO;

    public PedidoRepository(Context ctx){
        AppDatabase db = Room.databaseBuilder(ctx,
                AppDatabase.class, "bd_lab")
                .allowMainThreadQueries()
                .build();
        pedidoDAO = db.pedidoDAO();
    }

    public static PedidoRepository getInstance(Context ctx){
        if(_REPO==null) _REPO = new PedidoRepository(ctx);
        return _REPO;
    }

    public void guardarPedido(Pedido p){
        if(p.getId()!=null && p.getId()>0) {
            this.eliminarPedido(p);
        }else{
            p.setId(GENERADOR_ID_PEDIDO ++);
        }
        pedidoDAO.guardarPedido(p);
    }
    public void actualizarPedido(Pedido p){
        pedidoDAO.actualizarPedido(p);
    }
    public void eliminarPedido(Pedido p){
        pedidoDAO.eliminarPedido(p);
    }
    public Pedido buscarPorId(int id){
        return pedidoDAO.buscarPorId(id);
    }
    public List<Pedido> getLista(){
        return pedidoDAO.getLista();
    }

    /*public List<Pedido> getLista(){
        return LISTA_PEDIDOS;
    }

    public void guardarPedido(Pedido p){
        if(p.getId()!=null && p.getId()>0) {
            LISTA_PEDIDOS.remove(p);
        }else{
            p.setId(GENERADOR_ID_PEDIDO ++);
        }
        LISTA_PEDIDOS.add(p);
    }

    public Pedido buscarPorId(Integer id){
        for(Pedido p: LISTA_PEDIDOS){
            if(p.getId().equals(id)) return p;
        }
        return null;
    }*/


}
