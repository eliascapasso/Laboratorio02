package ar.edu.utn.frsf.dam.isi.laboratorio02.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.PedidoDetalle;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Producto;

@Dao
public interface PedidoDetalleDAO {
    @Insert
    public void crearDetalle(PedidoDetalle detalle);
    @Update
    public void actualizarDetalle(PedidoDetalle detalle);
    @Delete
    public void eliminarDetalle(PedidoDetalle detalle);
    @Query("SELECT * FROM Producto WHERE idProducto = :id")
    public PedidoDetalle buscarPorId(Integer id);
    @Query("SELECT * FROM Producto")
    public List<PedidoDetalle> getLista();
}
