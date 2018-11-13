package ar.edu.utn.frsf.dam.isi.laboratorio02.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Producto;

@Dao
public interface ProductoDAO {
    @Insert
    public void crearProducto(Producto producto);
    @Update
    public void actualizarProducto(Producto producto);
    @Delete
    public void eliminarProducto(Producto producto);
    @Query("SELECT * FROM Producto WHERE idProducto = :id")
    public Producto buscarPorId(Integer id);
    @Query("SELECT * FROM Producto")
    public List<Producto> getLista();
    @Query("SELECT * FROM Producto WHERE cat_nombreCategoria = :categoria")
    public List<Producto> buscarPorCategoria(String categoria);
}
