package ar.edu.utn.frsf.dam.isi.laboratorio02;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

public class HistorialPedidoActivity extends AppCompatActivity {

    private ListView lstHistorialPedido;
    private Button btnHistorialNuevo;
    private Button btnHistorialMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial_pedido);

        lstHistorialPedido = (ListView) findViewById(R.id.lstHistorialPedido);
        btnHistorialNuevo = (Button) findViewById(R.id.btnHistorialNuevo);
        btnHistorialMenu = (Button) findViewById(R.id.btnHistorialMenu);

    }
}
