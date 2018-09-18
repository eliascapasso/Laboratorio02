package ar.edu.utn.frsf.dam.isi.laboratorio02;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

import ar.edu.utn.frsf.dam.isi.laboratorio02.dao.ProductoRepository;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Categoria;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Producto;

public class ProductosRepositoryActivity extends AppCompatActivity {

    private Spinner cmbProductosCategoria;
    private ListView lstProductos;
    private EditText edtProdCantidad;
    private Button btnProdAddPedido;
    private ProductoRepository productoDAO; //prueba

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productos_repository);

        cmbProductosCategoria = (Spinner) findViewById(R.id.cmbProductosCategoria);
        lstProductos = (ListView) findViewById(R.id.lstProductos);
        edtProdCantidad = (EditText) findViewById(R.id.edtProdCantidad);
        btnProdAddPedido = (Button) findViewById(R.id.btnProdAddPedido);

        productoDAO = new ProductoRepository();

        ArrayAdapter<Categoria> adaptadorSpinnerCategorias = new ArrayAdapter<Categoria>(this, android.R.layout.simple_spinner_item, productoDAO.getCategorias());
        cmbProductosCategoria.setAdapter(adaptadorSpinnerCategorias);

        //ArrayAdapter<Producto> adaptadorListaProductos = new ArrayAdapter<Producto>(this, android.R.layout.simple_list_item_1, cmbProductosCategoria.setOnItemClickListener(););
        //lstProductos.setAdapter(adaptadorListaProductos);
        //TODO: llegamos hasta acá
    }
}
