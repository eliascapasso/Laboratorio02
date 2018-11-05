package ar.edu.utn.frsf.dam.isi.laboratorio02;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ar.edu.utn.frsf.dam.isi.laboratorio02.dao.ProductoRetrofit;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Categoria;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Producto;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GestionProductoActivity extends AppCompatActivity {
    private Button btnMenu;
    private Button btnGuardar;
    private Spinner comboCategorias;
    private EditText nombreProducto;
    private EditText descProducto;
    private EditText precioProducto;
    private ToggleButton opcionNuevoBusqueda;
    private EditText idProductoBuscar;
    private Button btnBuscar;
    private Button btnBorrar;
    private Boolean flagActualizacion;
    private ArrayAdapter<Categoria> comboAdapter;

    private Call<Producto> call;
    private ProductoRetrofit clienteRest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_producto);
        flagActualizacion = false;
        opcionNuevoBusqueda = (ToggleButton) findViewById(R.id.abmProductoAltaNuevo);
        idProductoBuscar = (EditText) findViewById(R.id.abmProductoIdBuscar);
        nombreProducto = (EditText) findViewById(R.id.abmProductoNombre);
        descProducto = (EditText) findViewById(R.id.abmProductoDescripcion);
        precioProducto = (EditText) findViewById(R.id.abmProductoPrecio);
        comboCategorias = (Spinner) findViewById(R.id.abmProductoCategoria);
        btnMenu = (Button) findViewById(R.id.btnAbmProductoVolver);
        btnGuardar = (Button) findViewById(R.id.btnAbmProductoCrear);
        btnBuscar = (Button) findViewById(R.id.btnAbmProductoBuscar);
        btnBorrar= (Button) findViewById(R.id.btnAbmProductoBorrar);
        opcionNuevoBusqueda.setChecked(false);
        btnBuscar.setEnabled(false);
        btnBorrar.setEnabled(false);
        idProductoBuscar.setEnabled(false);

        setearCategorias();

        clienteRest = RestClient.getInstance().getRetrofit().create(ProductoRetrofit.class);

        opcionNuevoBusqueda.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                flagActualizacion =isChecked;
                btnBuscar.setEnabled(isChecked);
                btnBorrar.setEnabled(isChecked);
                idProductoBuscar.setEnabled(isChecked);
        }
        });

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                call = null;

                //Creacion del producto con los datos seteados
                final Producto p = new Producto();
                p.setNombre(nombreProducto.getText().toString());
                p.setDescripcion(descProducto.getText().toString());
                p.setPrecio(Double.parseDouble(precioProducto.getText().toString()));
                comboCategorias.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        p.setCategoria(new Categoria(comboCategorias.getItemAtPosition(position).toString()));
                    }
                    @Override public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                if(flagActualizacion){
                    call = clienteRest.actualizarProducto(Integer.parseInt(idProductoBuscar.getText().toString()), p);

                    Toast.makeText(GestionProductoActivity.this, "El producto ha sido actualizado", Toast.LENGTH_SHORT).show();
                }
                else{

                    call= clienteRest.crearProducto(p);

                    Toast.makeText(GestionProductoActivity.this, "El nuevo producto ha sido guardado", Toast.LENGTH_SHORT).show();
                }

                call.enqueue(new Callback<Producto>() {
                    @Override
                    public void onResponse(Call<Producto> call, Response<Producto> resp) {
                        // procesar la respuesta
                        System.out.println("Respuesta:\n");
                        System.out.println("Id: " + resp.body().getId() + "\n");
                        System.out.println("Nombre: " + resp.body().getNombre() + "\n");
                        System.out.println("Descripcion: " + resp.body().getDescripcion() + "\n");
                        System.out.println("Precio: " + resp.body().getPrecio() + "\n");
                    }

                    @Override
                    public void onFailure(Call<Producto> call, Throwable t) {
                        System.out.println(t.getCause().toString());
                    }
                });

                nombreProducto.setText("");
                descProducto.setText("");
                precioProducto.setText("");
                comboCategorias.setSelection(0);
            }
        });

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                call = clienteRest.buscarProductoPorId(Integer.parseInt(idProductoBuscar.getText().toString()));

                call.enqueue(new Callback<Producto>() {
                    @Override
                    public void onResponse(Call<Producto> call, Response<Producto> response) {
                        nombreProducto.setText(response.body().getNombre());
                        descProducto.setText(response.body().getDescripcion());
                        precioProducto.setText(response.body().getPrecio().toString());
                        //comboCategorias.setSelection(response.body().getCategoria().getId());
                    }

                    @Override
                    public void onFailure(Call<Producto> call, Throwable t) {
                        System.out.println(t.getCause().toString());
                    }
                });

                nombreProducto.setText("");
                descProducto.setText("");
                precioProducto.setText("");
                comboCategorias.setSelection(0);
            }
        });

        btnBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                call = clienteRest.borrar(Integer.parseInt(idProductoBuscar.getText().toString()));

                Toast.makeText(GestionProductoActivity.this, "El producto con id " + idProductoBuscar.getText().toString() + " ha sido borrado", Toast.LENGTH_SHORT).show();

                call.enqueue(new Callback<Producto>() {
                    @Override
                    public void onResponse(Call<Producto> call, Response<Producto> response) {
                        System.out.println("El producto con id " + idProductoBuscar.getText().toString() + " ha sido borrado");
                    }

                    @Override
                    public void onFailure(Call<Producto> call, Throwable t) {
                        System.out.println(t.getCause().toString());
                    }
                });

                nombreProducto.setText("");
                descProducto.setText("");
                precioProducto.setText("");
                comboCategorias.setSelection(0);
            }
        });
    }

    public void setearCategorias(){
        Runnable r = new Runnable() {
            @Override public void run() {
                CategoriaRest catRest = new CategoriaRest();
                Categoria[] cats = catRest.listarTodas().toArray(new Categoria[0]);
                final List<Categoria> listaCategoria = new ArrayList<Categoria>();
                for(int i = 0; i < cats.length ; i++){
                    listaCategoria.add(cats[i]);
                }

                runOnUiThread(new Runnable() {
                    @Override public void run() {
                        comboAdapter = new ArrayAdapter<Categoria>(GestionProductoActivity.this, android.R.layout.simple_spinner_dropdown_item, listaCategoria);
                        comboCategorias.setAdapter(comboAdapter);
                        comboCategorias.setSelection(0);
                    }
                });
            }
        };
        Thread hiloCargarCombo = new Thread(r);
        hiloCargarCombo.start();
    }
}