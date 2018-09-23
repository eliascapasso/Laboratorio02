package ar.edu.utn.frsf.dam.isi.laboratorio02;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import ar.edu.utn.frsf.dam.isi.laboratorio02.dao.ProductoRepository;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Categoria;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.PedidoDetalle;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Producto;

public class ProductosRepositoryActivity extends AppCompatActivity {

    private Spinner cmbProductosCategoria;
    private ListView lstProductos;
    private EditText edtProdCantidad;
    private Button btnProdAddPedido;
    private ProductoRepository repositorioProductos;
    private ArrayAdapter<Categoria> adaptadorSpinnerCategorias;
    private ArrayAdapter<Producto> adaptadorListaProductos;
    private Categoria catSeleccionada;
    private int idProductoSeleccionado;
    private boolean bandera; //false si viene desde el menu principal, true si viene desde la pantalla de un nuevo pedido
    public List<PedidoDetalle> listaPedidoDetalle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productos_repository);

        inicializaAtributos();

        setearAdaptadorProductos();

        seleccionCategoria();

        setearIDProducto();

        recibirDatos();

        agregarPedido();
    }

    private void inicializaAtributos(){
        cmbProductosCategoria = (Spinner) findViewById(R.id.cmbProductosCategoria);
        lstProductos = (ListView) findViewById(R.id.lstProductos);
        edtProdCantidad = (EditText) findViewById(R.id.edtProdCantidad);
        btnProdAddPedido = (Button) findViewById(R.id.btnProdAddPedido);

        repositorioProductos = new ProductoRepository();
        listaPedidoDetalle = new ArrayList<PedidoDetalle>();
    }

    private void setearAdaptadorProductos(){
        adaptadorSpinnerCategorias = new ArrayAdapter<Categoria>(this, android.R.layout.simple_spinner_item, repositorioProductos.getCategorias());
        cmbProductosCategoria.setAdapter(adaptadorSpinnerCategorias);
    }

    private void seleccionCategoria(){
        cmbProductosCategoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id)
            {
                catSeleccionada = adaptadorSpinnerCategorias.getItem(position);

                adaptadorListaProductos = new ArrayAdapter<Producto>(ProductosRepositoryActivity.this, android.R.layout.simple_list_item_single_choice, repositorioProductos.buscarPorCategoria(catSeleccionada));
                lstProductos.setAdapter(adaptadorListaProductos);

                //Cada vez que se cambia de categoria, se deshabilita el boton y el editText
                if(lstProductos.getCheckedItemPosition() == -1){
                    edtProdCantidad.setEnabled(false);
                    btnProdAddPedido.setEnabled(false);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView)
            {
                catSeleccionada = repositorioProductos.getCategorias().get(0);
            }
        });
    }

    private void setearIDProducto(){
        lstProductos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                idProductoSeleccionado = repositorioProductos.buscarPorCategoria(catSeleccionada).get(position).getId();

                if(bandera){
                    edtProdCantidad.setEnabled(true);
                    btnProdAddPedido.setEnabled(true);
                }
            }
        });
    }

    private void recibirDatos(){
        Bundle extras = getIntent().getExtras();
        bandera = extras.getBoolean("bandera");

        //false si viene desde el menu principal, true si viene desde la pantalla de un nuevo pedido
        if(bandera){
            edtProdCantidad.setEnabled(true);
            btnProdAddPedido.setEnabled(true);
        }
        else{
            edtProdCantidad.setEnabled(false);
            btnProdAddPedido.setEnabled(false);
        }
    }

    private void agregarPedido(){
        btnProdAddPedido.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                int cantidad;

                //Si no setea ninguna cantidad
                if(edtProdCantidad == null){
                    cantidad = 1;
                }
                else{
                    cantidad = Integer.parseInt(edtProdCantidad.getText().toString());
                }

                int idProducto = idProductoSeleccionado;

                Intent pedidoActivity = new Intent();
                pedidoActivity.putExtra("cantidad", cantidad);
                pedidoActivity.putExtra("idProducto", idProducto);
                pedidoActivity.putExtra("bandera", true);
                setResult(ProductosRepositoryActivity.RESULT_OK, pedidoActivity);
                finish();
            }
        });
    }
}
