package ar.edu.utn.frsf.dam.isi.laboratorio02.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Categoria;

@Dao
public interface CategoriaDAO{
    @Insert
    public void crearCategoria(Categoria categoria);
    @Update
    public void actualizarCategoria (Categoria categoria);
    @Delete
    public void eliminarCategoria(Categoria categoria);
    @Query("SELECT * FROM Categoria WHERE idCategoria")
    public Categoria obtenerCategoria(int idCategoria);
    @Query("SELECT * FROM Categoria WHERE nombreCategoria")
    public Categoria obtenerCategoria(String nombreCategoria);
    @Query("SELECT * FROM Categoria")
    public List<Categoria> listarCategorias();
}
