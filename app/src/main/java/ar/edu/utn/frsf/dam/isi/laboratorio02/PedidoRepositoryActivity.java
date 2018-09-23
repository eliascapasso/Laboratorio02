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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import java.util.GregorianCalendar;
import java.util.Calendar;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import ar.edu.utn.frsf.dam.isi.laboratorio02.dao.PedidoRepository;
import ar.edu.utn.frsf.dam.isi.laboratorio02.dao.ProductoRepository;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Pedido;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.PedidoDetalle;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Producto;

public class PedidoRepositoryActivity extends AppCompatActivity {

    private EditText edtMail;
    private RadioGroup rgOptEntrega;
    private RadioButton optRetira;
    private RadioButton optEnviar;
    private EditText edtDireccion;
    private EditText edtHoraSolicitada;
    private ListView lstPedidos;
    private Button btnAddProducto;
    private Button btnQuitarProducto;
    private Button btnHacerPedido;
    private Button btnVolver;
    private TextView lblCostoTotalPedido;

    private Pedido unPedido;
    private PedidoRepository repositorioPedido;
    private ProductoRepository repositorioProducto;
    private ArrayAdapter<PedidoDetalle> adaptadorListaPedidos;
    private List<PedidoDetalle> listaPedidoDetalle;
    private int posicionProductoSeleccionado;
    private boolean bandera; //True si proviene del historial de pedidos, False si proviene del menu principal

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido_repository);

        inicializaAtributos();

        deshabilitaDireccionSiRetira();

        agregarProducto();

        quitarProducto();

        hacerPedido();

        volver();
    }

    private void inicializaAtributos(){
        edtMail = (EditText) findViewById(R.id.editPedidoCorreo);
        rgOptEntrega = (RadioGroup)findViewById(R.id.optPedidoModoEntrega);
        optRetira = (RadioButton) findViewById(R.id.optPedidoRetira);
        optEnviar = (RadioButton) findViewById(R.id.optPedidoEnviar);
        edtDireccion = (EditText) findViewById(R.id.edtPedidoDireccion);
        edtHoraSolicitada = (EditText) findViewById(R.id.edtPedidoHoraEntrega);
        lstPedidos = (ListView) findViewById(R.id.lstPedidoItems);
        btnAddProducto = (Button) findViewById(R.id.btnPedidoAddProducto);
        btnQuitarProducto = (Button) findViewById(R.id.btnPedidoQuitarProducto);
        btnHacerPedido = (Button) findViewById(R.id.btnPedidoHacerPedido);
        btnVolver = (Button) findViewById(R.id.btnPedidoVolver);
        lblCostoTotalPedido = (TextView)findViewById(R.id.lblCostoTotalPedido);

        unPedido = new Pedido();
        repositorioPedido = new PedidoRepository();
        repositorioProducto = new ProductoRepository();
        listaPedidoDetalle = new ArrayList<PedidoDetalle>();

        inicializaDatosPedidoDesdeHitorial();
    }

    private void inicializaDatosPedidoDesdeHitorial(){
        Bundle extras = getIntent().getExtras();
        bandera = extras.getBoolean("bandera");

        if(bandera){
            int idPedido = extras.getInt("idPedido");

            Log.d("APP_LAB02", "ID PEDIDO: " + idPedido);
            Pedido pedido = repositorioPedido.buscarPorId(idPedido);

            edtMail.setText(pedido.getMailContacto());
            if(pedido.getRetirar()){
                optRetira.setChecked(true);
            }
            else{
                optEnviar.setChecked(true);
                edtDireccion.setText(pedido.getDireccionEnvio());
            }
            //edtHoraSolicitada.setText(pedido.getFecha().getTime()); //TODO: ver
            listaPedidoDetalle = pedido.getDetalle();
            setearAdaptadorProductos();
            mostrarCostoTotalPedido();
        }

    }

    private void deshabilitaDireccionSiRetira(){
        edtDireccion.setEnabled(false);
        rgOptEntrega.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(optEnviar.isChecked()){
                    edtDireccion.setEnabled(true);
                }
                else{
                    edtDireccion.setEnabled(false);
                }
            }
        });
    }

    private void agregarProducto(){
        btnAddProducto.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                Intent productosActivity = new Intent(PedidoRepositoryActivity.this, ProductosRepositoryActivity.class);
                productosActivity.putExtra("bandera", true);
                startActivityForResult(productosActivity, 0);
            }
        });
    }

    private void quitarProducto(){

        lstPedidos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                posicionProductoSeleccionado = position;

                btnQuitarProducto.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v)
                    {
                        listaPedidoDetalle.remove(posicionProductoSeleccionado);
                        adaptadorListaPedidos.notifyDataSetChanged();

                        mostrarCostoTotalPedido();
                    }
                });
            }
        });
    }

    private void mostrarCostoTotalPedido(){
        double costoTotal = 0.0;

        for (PedidoDetalle pedidodetalle: listaPedidoDetalle) {
                  costoTotal += repositorioProducto.buscarPorId(pedidodetalle.getId()).getPrecio() * pedidodetalle.getCantidad();
        }

        lblCostoTotalPedido.setText("Total pedido: $" + costoTotal);
    }

    private void hacerPedido() {
        btnHacerPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] horaIngresada = edtHoraSolicitada.getText().toString().split(":");
                GregorianCalendar hora = new GregorianCalendar();

                if(validarDatosHora(horaIngresada, hora) && validarDatosEmailHacerPedido()){
                    setearDatosPedido(hora);

                    repositorioPedido.guardarPedido(unPedido);
                    Log.d("APP_LAB02", "Pedido realizado: " + unPedido.toString());
                    // lo seteamos a una nueva instancia para el proximo pedido
                    unPedido = new Pedido();
                    Log.d("APP_LAB02", "Pedido nuevo: " + unPedido.toString());

                    Intent historialPedidoActivity = new Intent(PedidoRepositoryActivity.this, HistorialPedidoActivity.class);
                    startActivity(historialPedidoActivity);
                }
                else{
                    Toast.makeText(PedidoRepositoryActivity.this, "Campos inválidos", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private boolean validarDatosHora(String[] horaIngresada, GregorianCalendar hora){
        if((edtHoraSolicitada.getText().toString().length() != 0) && validarDatosEmailHacerPedido()){
            int valorHora = Integer.valueOf(horaIngresada[0]);
            int valorMinuto = Integer.valueOf(horaIngresada[1]);
            if (valorHora < 0 || valorHora > 23) {
                return false;
            }else if (valorMinuto < 0 || valorMinuto > 59) {
                return false;
            }

            hora.set(Calendar.HOUR_OF_DAY, valorHora);
            hora.set(Calendar.MINUTE, valorMinuto);
            hora.set(Calendar.SECOND, Integer.valueOf(0));

            return true;
        }
        else{
            return false;
        }
    }

    private boolean validarDatosEmailHacerPedido() {
        boolean bandera = true;

        if ((edtMail.getText().toString().length() == 0) || (edtMail == null)) {
            bandera = false;
        }
        if (optEnviar.isChecked() && (edtDireccion.getText().toString().length() == 0 || edtDireccion == null)) {
            bandera = false;
        }
        return bandera;
    }

    private void volver(){
        btnVolver.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                Intent mainActivity = new Intent(PedidoRepositoryActivity.this, MainActivity.class);
                startActivity(mainActivity);
            }
        });
    }

    private void setearAdaptadorProductos(){
        adaptadorListaPedidos = new ArrayAdapter<PedidoDetalle>(PedidoRepositoryActivity.this, android.R.layout.simple_list_item_single_choice, listaPedidoDetalle);
        lstPedidos.setAdapter(adaptadorListaPedidos);
    }

    public void setearDatosPedido(GregorianCalendar hora){
        unPedido.setFecha(hora.getTime());
        unPedido.setEstado(Pedido.Estado.REALIZADO);
        unPedido.setDireccionEnvio(edtDireccion.getText().toString());
        unPedido.setMailContacto(edtMail.getText().toString());
        unPedido.setDetalle(listaPedidoDetalle);
        unPedido.setRetirar(optRetira.isChecked());
    }

    //Se llama a este método cuando finaliza la segunda actividad (productosRepositoryActivity)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // check that it is the SecondActivity with an OK result
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) { // Activity.RESULT_OK

                //Ejercicio h
                int cantidadProducto = data.getIntExtra("cantidad", 1);
                Producto producto = repositorioProducto.buscarPorId(data.getIntExtra("idProducto", 1));
                PedidoDetalle pedidoDetalle = new PedidoDetalle(cantidadProducto, producto);
                pedidoDetalle.setPedido(unPedido); //TODO: revisar
                listaPedidoDetalle.add(pedidoDetalle);

                setearAdaptadorProductos();

                mostrarCostoTotalPedido();
            }
        }
    }
}
