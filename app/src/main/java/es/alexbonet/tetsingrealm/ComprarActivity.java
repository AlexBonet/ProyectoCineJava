package es.alexbonet.tetsingrealm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import es.alexbonet.tetsingrealm.RecyclerView.CompraRVAdapter;
import es.alexbonet.tetsingrealm.RecyclerView.SesionRVAdapter;
import es.alexbonet.tetsingrealm.db.Controller;
import es.alexbonet.tetsingrealm.db.DataBase;
import es.alexbonet.tetsingrealm.model.Butaca;
import es.alexbonet.tetsingrealm.model.RecuentoButacas;
import es.alexbonet.tetsingrealm.model.Sala;
import es.alexbonet.tetsingrealm.model.SalaType;
import es.alexbonet.tetsingrealm.model.Sesion;
import es.alexbonet.tetsingrealm.model.Usuario;
import io.realm.Realm;

public class ComprarActivity extends AppCompatActivity{

    private final Controller c = new Controller();
    private Realm connect;
    private CompraRVAdapter compraRVAdapter;
    private List<Butaca> butacas;
    private TextView precioTotal;
    private Button btnComprar, btnCancelar;
    private RecyclerView recyclerView;
    private String username, id_sesion;
    private Sesion sesion;
    private Usuario user;
    private Sala sala;
    private int preu, preuTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comprar);
        //Obtener base de datos
        connect = DataBase.getInstance().conectar(this);

        //Obtener datos de otras actividades
        username = getIntent().getExtras().getString("user");
        id_sesion = getIntent().getExtras().getString("sesion");
        butacas = ((RecuentoButacas) getIntent().getSerializableExtra("butacas")).getButacas();

        //Iniciar Objetoys
        sesion = c.getAllSesionFromID(connect, id_sesion);
        user = c.getUser(connect, username);
        sala = c.getSala(connect, sesion.getNum_sala());

        //Obtener lo que vale una entrada depende de la sala
        if (sala.getTipo_sala().equals(SalaType.NORMAL.getString())) {
            preu = SalaType.NORMAL.getPreu();
        } else if (sala.getTipo_sala().equals(SalaType.TRESD.getString())) {
            preu = SalaType.TRESD.getPreu();
        } else {
            preu = SalaType.CUATRODX.getPreu();
        }

        //Obtener vistas
        precioTotal = findViewById(R.id.cTotal);
        btnCancelar = findViewById(R.id.cbtnCancelar);
        btnComprar = findViewById(R.id.cbtnComprar);
        recyclerView = findViewById(R.id.RVCompra);

        //Calcular el precio toal
        for (Butaca b : butacas){
            preuTotal+=preu;
        }
        precioTotal.setText("PRECIO TOTAL: " + preuTotal +"€");

        //Botones
        btnCancelar.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(),SalaActivity.class);
            intent.putExtra("user", username);
            intent.putExtra("sesion",id_sesion);
            startActivity(intent);
        });

        btnComprar.setOnClickListener(view -> {
            //TODO
            // Alert de login si es cliente
            // Crear entrada y venta
            // Intent a un ticket que te dixe descargar la entrada y la factura
        });

        //Recycler view TODO ARREGLAR
        compraRVAdapter = new CompraRVAdapter(this, butacas, preu);
        recyclerView.setAdapter(compraRVAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

    }
}