package ar.edu.utn.frsf.dam.isi.laboratorio02;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import ar.edu.utn.frsf.dam.isi.laboratorio02.dao.PedidoRepository;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Pedido;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.PedidoDetalle;

public class HistorialPedidoActivity extends AppCompatActivity {

    private ListView lstHistorialPedido;
    private Button btnHistorialNuevo;
    private Button btnHistorialMenu;

    private ArrayList<String> listaPedido;
    private ArrayAdapter<Pedido>  adaptadorHistorialPedido;
    private PedidoRepository pedidoRepository;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial_pedido);

        lstHistorialPedido = (ListView) findViewById(R.id.lstHistorialPedido);
        btnHistorialNuevo = (Button) findViewById(R.id.btnHistorialNuevo);
        btnHistorialMenu = (Button) findViewById(R.id.btnHistorialMenu);

        botonNuevo();
        botonMenu();
        lista();

    }

    private void botonNuevo(){
        btnHistorialNuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pedidoRepository = new Intent(HistorialPedidoActivity.this, PedidoRepositoryActivity.class);
                startActivity(pedidoRepository);
            }
        });
    }

    private void botonMenu(){
        btnHistorialMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent menuMainActivity = new Intent(HistorialPedidoActivity.this, MainActivity.class);
                startActivity(menuMainActivity);
            }
        });
    }

    private void lista(){
        pedidoRepository= new PedidoRepository();
        lstHistorialPedido.setAdapter((ListAdapter) pedidoRepository.getLista());
    }

    /*private void setearAdaptador(){
        adaptadorHistorialPedido = new ArrayAdapter<Pedido>(HistorialPedidoActivity.this, android.R.layout.simple_list_item_single_choice, listaPedido);
        lstHistorialPedido.setAdapter(adaptadorHistorialPedido);
    }

    private void lista() {

        Intent intent = new Intent(HistorialPedidoActivity.this, PedidoRepositoryActivity.class);
        intent.putStringArrayListExtra("Pedido", listaPedido);
        startActivity(intent);
    }*/





}
