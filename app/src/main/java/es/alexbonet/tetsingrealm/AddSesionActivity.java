package es.alexbonet.tetsingrealm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.List;

import es.alexbonet.tetsingrealm.db.Controller;
import es.alexbonet.tetsingrealm.db.DataBase;
import es.alexbonet.tetsingrealm.model.Film;
import es.alexbonet.tetsingrealm.model.Sala;
import es.alexbonet.tetsingrealm.model.Sesion;
import es.alexbonet.tetsingrealm.model.Usuario;
import es.alexbonet.tetsingrealm.model.enums.UserType;
import io.realm.Realm;

public class AddSesionActivity extends AppCompatActivity {

    private final Controller c = new Controller();
    private Realm connect;
    private Spinner spinnerPelis, spinnerSalas;
    private EditText inputHora;
    private Button btnAdd, btnVolver;
    private List<Film> filmList= new LinkedList<>();
    private List<Sala> salaList= new LinkedList<>();
    private ArrayAdapter<Film> filmAdapter;
    private ArrayAdapter<Sala> salaAdapter;
    private Sala sala;
    private Film film;
    private Sesion sesion;
    private String titulo, hora;
    private int num_sala, ocupacion;
    private String username;
    private Usuario u;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sesion);

        connect = DataBase.getInstance().conectar(this);
        username = getIntent().getExtras().getString("user");
        u = c.getUser(connect, username);


        btnVolver = findViewById(R.id.addSbtnVolver);
        btnAdd = findViewById(R.id.addSbtnAdd);
        spinnerPelis = findViewById(R.id.spinnerPelis);
        spinnerSalas = findViewById(R.id.spinnerSala);
        inputHora = findViewById(R.id.addSinputHora);

        filmList = c.getAllFilms(connect);
        salaList = c.getAllSalas(connect);

        filmAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, filmList);
        salaAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, salaList);
        salaAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        filmAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinnerPelis.setAdapter(filmAdapter);
        spinnerSalas.setAdapter(salaAdapter);

        spinnerSalas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                sala = salaList.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                sala = null;
            }
        });

        spinnerPelis.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                film = filmList.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                film = null;
            }
        });

        btnVolver.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(),AllSesionsActivity.class);
            intent.putExtra("user",username);
            startActivity(intent);
        });

        btnAdd.setOnClickListener(view -> {

            num_sala = sala.getNum_sala();
            titulo = film.getTitulo();
            hora = inputHora.getText().toString();
            ocupacion = film.getDuracion() + 10;

            if (hora.isEmpty()){
                Toast.makeText(this, "Tienes que pones una hora", Toast.LENGTH_SHORT).show();
            } else {
                sesion = new Sesion(num_sala, titulo, hora, ocupacion);

                c.createSesion(connect, sesion);
                Toast.makeText(this, "SESION AÑADIDA", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getApplicationContext(),AllSesionsActivity.class);
                intent.putExtra("user",username);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (u.getTipo().equals(UserType.ADMINISTRADOR.getString())){
            getMenuInflater().inflate(R.menu.menu_admin, menu);
            return super.onCreateOptionsMenu(menu);
        } else if (u.getTipo().equals(UserType.EMPLEADO.getString())){
            getMenuInflater().inflate(R.menu.menu_emple, menu);
            return super.onCreateOptionsMenu(menu);
        } else if (u.getTipo().equals(UserType.CLIENTE.getString())){
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
                Toast.makeText(this, "EL USUARIO " + u.getUserName() + " ES " + u.getTipo(), Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}