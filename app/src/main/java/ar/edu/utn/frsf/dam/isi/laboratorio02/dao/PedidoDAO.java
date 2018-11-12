package ar.edu.utn.frsf.dam.isi.laboratorio02.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Pedido;

@Dao
public interface PedidoDAO {
    @Insert
    public void guardarPedido(Pedido p);
    @Update
    public void actualizarPedido(Pedido p);
    @Delete
    public void eliminarPedido(Pedido p);
    @Query("SELECT * FROM Pedido WHERE idPedido = :id")
    public Pedido buscarPorId(int id);
    @Query("SELECT * FROM Pedido")
    public List<Pedido> getLista();
}
