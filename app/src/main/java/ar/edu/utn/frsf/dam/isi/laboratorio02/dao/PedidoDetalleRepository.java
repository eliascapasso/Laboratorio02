package ar.edu.utn.frsf.dam.isi.laboratorio02.dao;

import android.arch.persistence.room.Room;
import android.content.Context;

import java.util.List;

import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.PedidoDetalle;

public class PedidoDetalleRepository {
    private static PedidoDetalleRepository _REPO= null;
    private PedidoDetalleDAO pedidoDetalleDAO;

    public PedidoDetalleRepository(Context ctx){
        AppDatabase db = Room.databaseBuilder(ctx,
                AppDatabase.class, "bd_lab")
                .allowMainThreadQueries()
                .build();
        pedidoDetalleDAO = db.pedidoDetalleDAO();
    }

    public static PedidoDetalleRepository getInstance(Context ctx){
        if(_REPO==null) _REPO = new PedidoDetalleRepository(ctx);
        return _REPO;
    }

    public void crearDetalle(PedidoDetalle detalle){
        pedidoDetalleDAO.crearDetalle(detalle);
    }

    public void actualizarDetalle(PedidoDetalle detalle){
        pedidoDetalleDAO.actualizarDetalle(detalle);
    }

    public void eliminarDetalle(PedidoDetalle detalle){
        pedidoDetalleDAO.eliminarDetalle(detalle);
    }

    public PedidoDetalle buscarPorId(Integer id){
        return pedidoDetalleDAO.buscarPorId(id);
    }

    public List<PedidoDetalle> getLista(){
        return pedidoDetalleDAO.getLista();
    }
}
