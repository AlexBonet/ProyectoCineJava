package es.alexbonet.tetsingrealm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStreamWriter;
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
                    progressBar.setVisibility(View.VISIBLE);
                    new Handler().postDelayed(() -> {
                        //Crea totes les entrades
                        int num_entrada = c.getAllEntradas(connect).size();
                        for (Butaca b : butacas){
                            Entrada e = new Entrada(c.getAllEntradas(connect).size(), b.getFila(), b.getColunna(), id_sesion);
                            c.createEntrada(connect, e);
                            String str = "*** ENTRADA " + e.getNum_entrada() + " ***\n" +
                                    "SESION: " + sesion.getTitulo_peli() + " a las " + sesion.getHora_empieza() + " en la sala " + sesion.getNum_sala() + "\n" +
                                    "\n - FILA: " + e.getNum_fila() + "\n - BUTACA: " + e.getNum_butaca();
                            saveFile("entrada", e.getNum_entrada(), str);
                        }
                        if (!user.getTipo().equals(UserType.CLIENTE.getString())){
                            Venta v =new Venta(c.getAllVentas(connect).size(),num_entrada,preuTotal, username, new Date(System.currentTimeMillis()));
                            String str = "+++ VENTA " + v.getNum_venta() + " +++\n" +
                                    "\n + IMPORTE TOTAL: " + v.getImporte() + "\n" +
                                    " - VENDIDO POR: " + v.getNombre_empleado() + "\n · FECHA DE VENTA: " + v.getHora();

                            c.createVenta(connect, v);
                            saveFile("ticket", v.getNum_venta(), str);
                        }else{
                            Venta v = new Venta(c.getAllVentas(connect).size(),num_entrada,preuTotal, "AUTOSERVICIO", new Date(System.currentTimeMillis()));
                            String str = "+++ VENTA " + v.getNum_venta() + " +++\n" +
                                    "Entradas: " + num_entrada + "\nIMPORTE TOTAL: " + v.getImporte() + "\n" +
                                    "VENDIDO POR: " + v.getNombre_empleado() + "\n FECHA DE VENTA: " + v.getHora();
                            c.createVenta(connect, v);
                            saveFile("ticket", v.getNum_venta(), str);
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

        //Recycler view
        compraRVAdapter = new CompraRVAdapter(this, butacas, preu);
        recyclerView.setAdapter(compraRVAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (user.getTipo().equals(UserType.ADMINISTRADOR.getString())){
            getMenuInflater().inflate(R.menu.menu_admin, menu);
            return super.onCreateOptionsMenu(menu);
        } else if (user.getTipo().equals(UserType.EMPLEADO.getString())){
            getMenuInflater().inflate(R.menu.menu_emple, menu);
            return super.onCreateOptionsMenu(menu);
        } else if (user.getTipo().equals(UserType.CLIENTE.getString())){
            getMenuInflater().inflate(R.menu.menu_client, menu);
            return super.onCreateOptionsMenu(menu);
        } else {
            return super.onCreateOptionsMenu(menu);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent;
        switch (item.getItemId()){
            case (R.id.am_cerrarSesion): // Si clicamos aqui vamos cierra sesion
                intent = new Intent(this, LogInActivity.class);
                Toast.makeText(this, "Bye " + username, Toast.LENGTH_SHORT).show();
                startActivity(intent);
                return true;
            case (R.id.am_addEmple):
                intent = new Intent(this, AddUserActivity.class);
                intent.putExtra("user",username);
                startActivity(intent);
                return true;
            case (R.id.am_gestion_film):
                intent = new Intent(this, AllFilmsActivity.class);
                intent.putExtra("user",username);
                startActivity(intent);
                return true;
            case (R.id.am_gestion_sesion):
                intent = new Intent(this, AllSesionsActivity.class);
                intent.putExtra("user",username);
                startActivity(intent);
                return true;
            case (R.id.am_allVentas):
                intent = new Intent(this, VerVentasActivity.class);
                intent.putExtra("user",username);
                startActivity(intent);
                return true;
            case (R.id.perfil):
                Toast.makeText(this, "EL USUARIO " + user.getUserName() + " ES " + user.getTipo(), Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void saveFile(String entrada, int numEntrada, String contenido){
        try {
            OutputStreamWriter archivo = new OutputStreamWriter(openFileOutput(entrada + numEntrada +".txt", Activity.MODE_PRIVATE));
            archivo.write(contenido);
            archivo.flush();
            archivo.close();
        } catch (IOException e) {
            Toast.makeText(this,"ERROR COMPRANDO", Toast.LENGTH_SHORT).show();
        }
        Toast.makeText(this, "COMPRA CORRECTA", Toast.LENGTH_SHORT).show();
    }
}