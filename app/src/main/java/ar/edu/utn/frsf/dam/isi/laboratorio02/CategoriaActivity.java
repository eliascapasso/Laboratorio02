package ar.edu.utn.frsf.dam.isi.laboratorio02;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ar.edu.utn.frsf.dam.isi.laboratorio02.dao.CategoriaRepository;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Categoria;

public class CategoriaActivity extends AppCompatActivity {

    private EditText textoCat;
    private Button btnCrear;
    private Button btnMenu;
    private CategoriaRepository categoriaRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categoria);

        categoriaRepository = new CategoriaRepository(getApplicationContext());

        textoCat = (EditText) findViewById(R.id.txtNombreCategoria);
        btnCrear = (Button) findViewById(R.id.btnCrearCategoria);
        btnCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // completer el codigo en el paso “f”
                final Categoria categoria = new Categoria(textoCat.getText().toString());
                //final CategoriaRest categoriaRest = new CategoriaRest();

                Runnable r = new Runnable() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //categoriaRest.crearCategoria(categoria);
                                categoriaRepository.crearCategoria(categoria);
                            }
                        });
                    }
                };
                Thread unHilo = new Thread(r);
                unHilo.start();

                Toast.makeText(CategoriaActivity.this, "La categoría " + textoCat.getText().toString() + " ha sido creada", Toast.LENGTH_SHORT).show();

                textoCat.setText("");
            } });

        btnMenu= (Button) findViewById(R.id.btnCategoriaVolver);

            btnMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(CategoriaActivity.this, MainActivity.class);
                    startActivity(i);
                }
            });
    }
}
