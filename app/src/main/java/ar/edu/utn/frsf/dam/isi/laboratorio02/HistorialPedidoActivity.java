package ar.edu.utn.frsf.dam.isi.laboratorio02;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

import ar.edu.utn.frsf.dam.isi.laboratorio02.Adaptadores.PedidoAdapter;
import ar.edu.utn.frsf.dam.isi.laboratorio02.dao.PedidoRepository;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Pedido;

public class HistorialPedidoActivity extends AppCompatActivity {

    private ListView lstHistorialPedido;
    private Button btnHistorialNuevo;
    private Button btnHistorialMenu;

    private ArrayList<Pedido> listaPedidos;
    public static PedidoAdapter adaptadorHistorialPedido;
    private PedidoRepository pedidoRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial_pedido);

        inicializarAtributos();

        nuevoPedido();

        volverMenu();

        setearAdaptadorHistorial();

        clickLargoEnPedido();

        verDetallePedido();
    }

    private void inicializarAtributos(){
        lstHistorialPedido = (ListView) findViewById(R.id.lstHistorialPedido);
        btnHistorialNuevo = (Button) findViewById(R.id.btnHistorialNuevo);
        btnHistorialMenu = (Button) findViewById(R.id.btnHistorialMenu);

        pedidoRepository = new PedidoRepository(getApplicationContext());
        listaPedidos = new ArrayList<Pedido>();
        listaPedidos.addAll(pedidoRepository.getLista());
    }

    private void nuevoPedido(){
        btnHistorialNuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pedidoRepository = new Intent(HistorialPedidoActivity.this, PedidoRepositoryActivity.class);
                startActivity(pedidoRepository);
            }
        });
    }

    private void volverMenu(){
        btnHistorialMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent menuMainActivity = new Intent(HistorialPedidoActivity.this, MainActivity.class);
                startActivity(menuMainActivity);
            }
        });
    }

    private void setearAdaptadorHistorial(){
        adaptadorHistorialPedido = new PedidoAdapter(this, listaPedidos);
        lstHistorialPedido.setAdapter(adaptadorHistorialPedido);
    }

    private void clickLargoEnPedido(){
        lstHistorialPedido.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int pos, long id) {
                Intent mostrarPedido = new Intent(HistorialPedidoActivity.this, PedidoRepositoryActivity.class);

                int idPedido = pedidoRepository.getLista().get(pos).getId();

                mostrarPedido.putExtra("bandera", true);
                mostrarPedido.putExtra("idPedido", idPedido);
                startActivity(mostrarPedido);

                return true;
            }
        });
    }

    private void verDetallePedido(){
        lstHistorialPedido.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("APP_LAB02", "DETALLE PEDIDO");
            }
        });
    }
}
