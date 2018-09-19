package ar.edu.utn.frsf.dam.isi.laboratorio02;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import ar.edu.utn.frsf.dam.isi.laboratorio02.dao.PedidoRepository;
import ar.edu.utn.frsf.dam.isi.laboratorio02.dao.ProductoRepository;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Pedido;

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

    private Pedido unPedido;
    private PedidoRepository repositorioPedido;
    private ProductoRepository repositorioProducto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido_repository);

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
}
