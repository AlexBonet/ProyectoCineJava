package es.alexbonet.tetsingrealm;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import es.alexbonet.tetsingrealm.RecyclerView.CompraRVAdapter;
import es.alexbonet.tetsingrealm.db.Controller;
import es.alexbonet.tetsingrealm.db.DataBase;
import es.alexbonet.tetsingrealm.model.Butaca;
import es.alexbonet.tetsingrealm.model.Entrada;
import es.alexbonet.tetsingrealm.model.RecuentoButacas;
import es.alexbonet.tetsingrealm.model.Sala;
import es.alexbonet.tetsingrealm.model.Venta;
import es.alexbonet.tetsingrealm.model.enums.SalaType;
import es.alexbonet.tetsingrealm.model.Sesion;
import es.alexbonet.tetsingrealm.model.Usuario;
import es.alexbonet.tetsingrealm.model.enums.UserType;
import io.realm.Realm;

public class ComprarActivity extends AppCompatActivity{

    private final Controller c = new Controller();
    private Realm connect;
    private CompraRVAdapter compraRVAdapter;
    private List<Butaca> butacas;
    private TextView precioTotal;
    private ProgressBar progressBar;
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
        progressBar = findViewById(R.id.progressBar);

        progressBar.setVisibility(View.INVISIBLE);

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
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("SEGURO QUE QUIERES CONTAR ESTAS ENTRADAS");
            builder.setPositiveButton("CONFIRMAR", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    Toast.makeText(ComprarActivity.this, "REALIZANDO COMPRA", Toast.LENGTH_SHORT).show();
                    new Handler().postDelayed(() -> {
                        //TODO PROGRES BAR
                        progressBar.setVisibility(View.VISIBLE);
                        //Crea totes les entrades
                        for (Butaca b : butacas){
                            int num_entrada = c.getAllEntradas(connect).size();
                            c.createEntrada(connect, new Entrada(num_entrada, b.getFila(), b.getColunna(), sala.getNum_sala()));
                            if (!user.getTipo().equals(UserType.CLIENTE.getString())){
                                c.createVenta(connect, new Venta(c.getAllVentas(connect).size(),num_entrada,preuTotal, username, new Date(System.currentTimeMillis())));
                            }else{
                                c.createVenta(connect, new Venta(c.getAllVentas(connect).size(),num_entrada,preuTotal, "AUTOSERVICIO", new Date(System.currentTimeMillis())));
                            }
                            //TODO FILE ENTRADA  descargar la entrada y la factura
                        }
                        //Intent
                        Intent intent = new Intent(ComprarActivity.this, MainActivity.class);
                        intent.putExtra("user", username);
                        startActivity(intent);
                    }, 2000);
                }
            });
            builder.setNegativeButton("CANCELAR", null);
            AlertDialog dialog = builder.create();
            dialog.show();
        });

        //Recycler view TODO ARREGLAR
        compraRVAdapter = new CompraRVAdapter(this, butacas, preu);
        recyclerView.setAdapter(compraRVAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

    }
}