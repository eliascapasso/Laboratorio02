package ar.edu.utn.frsf.dam.isi.laboratorio02.dao;

import android.arch.persistence.room.Room;
import android.content.Context;

import java.util.List;

import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Categoria;

public class CategoriaRepository {
    private static CategoriaRepository _REPO= null;
    private CategoriaDAO categoriaDAO;
    private CategoriaRepository(Context ctx){
        AppDatabase db = Room.databaseBuilder(ctx,
                AppDatabase.class, "dam-pry-2018").fallbackToDestructiveMigration()
                .build();
        categoriaDAO = db.categoriaDAO();
    }

    public static CategoriaRepository getInstance(Context ctx){
        if(_REPO==null) _REPO = new CategoriaRepository(ctx);
        return _REPO;
    }

    public void crearCategoria(Categoria c){
        categoriaDAO.crearCategoria(c);
    }

    public void actualizarCategoria(Categoria c){
        categoriaDAO.actualizarCategoria(c);
    }

    public void eliminarCategoria(Categoria c){
        categoriaDAO.eliminarCategoria(c);
    }

    public Categoria obtenerCategoria(int idCategoria){
        return categoriaDAO.obtenerCategoria(idCategoria);
    }

    public Categoria obtenerCategoria(String nombreCategoria){
        return categoriaDAO.obtenerCategoria(nombreCategoria);
    }

    public List<Categoria> listarCategorias(){
        return categoriaDAO.listarCategorias();
    }
}
