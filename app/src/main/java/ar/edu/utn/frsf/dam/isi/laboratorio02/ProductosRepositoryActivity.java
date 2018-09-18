package ar.edu.utn.frsf.dam.isi.laboratorio02;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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
    private ProductoRepository productoDAO;
    private ArrayAdapter<Categoria> adaptadorSpinnerCategorias;
    private ArrayAdapter<Producto> adaptadorListaProductos;
    private Categoria catSeleccionada;
    private int idProductoSeleccionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productos_repository);

        cmbProductosCategoria = (Spinner) findViewById(R.id.cmbProductosCategoria);
        lstProductos = (ListView) findViewById(R.id.lstProductos);
        edtProdCantidad = (EditText) findViewById(R.id.edtProdCantidad);
        btnProdAddPedido = (Button) findViewById(R.id.btnProdAddPedido);

        productoDAO = new ProductoRepository();

        adaptadorSpinnerCategorias = new ArrayAdapter<Categoria>(this, android.R.layout.simple_spinner_item, productoDAO.getCategorias());
        cmbProductosCategoria.setAdapter(adaptadorSpinnerCategorias);

        seleccionCategoria();

        /*setearIDProducto();

        if(lstProductos.getCheckedItemCount() == 0){
            edtProdCantidad.setEnabled(false);
            btnProdAddPedido.setEnabled(false);
        }
        else{
            edtProdCantidad.setEnabled(true);
            btnProdAddPedido.setEnabled(true);
        }*/
        //TODO: REVISAR
    }

    private void setearIDProducto(){
        btnProdAddPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                idProductoSeleccionado = lstProductos.getCheckedItemPosition();
            }
        });
    }

    private void seleccionCategoria(){

        cmbProductosCategoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id)
            {
                catSeleccionada = adaptadorSpinnerCategorias.getItem(position);

                adaptadorListaProductos = new ArrayAdapter<Producto>(ProductosRepositoryActivity.this, android.R.layout.simple_list_item_single_choice, productoDAO.buscarPorCategoria(catSeleccionada));
                lstProductos.setAdapter(adaptadorListaProductos);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView)
            {
                catSeleccionada = productoDAO.getCategorias().get(0);
            }
        });
    }
}
