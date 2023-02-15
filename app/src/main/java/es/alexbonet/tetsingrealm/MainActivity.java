package es.alexbonet.tetsingrealm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import es.alexbonet.tetsingrealm.RecyclerView.FilmsRVAdapter;
import es.alexbonet.tetsingrealm.controller.FilmController;
import es.alexbonet.tetsingrealm.controller.UserController;
import es.alexbonet.tetsingrealm.db.DataBase;
import es.alexbonet.tetsingrealm.model.Film;
import es.alexbonet.tetsingrealm.model.UserType;
import es.alexbonet.tetsingrealm.model.Usuario;
import io.realm.Realm;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    //TODO: tinc la idea vaig aanar fent pelis y probar el crud

    private final UserController uc = new UserController();
    private final FilmController fc = new FilmController();
    private TextView textView;
    private String userName;
    private Usuario u;
    private Realm connect;
    private RecyclerView recyclerView;
    private FilmsRVAdapter filmsRvAdapter;
    private List<Film> filmList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        connect = DataBase.getInstance().conectar(this);

        textView = findViewById(R.id.textView);
        userName = getIntent().getExtras().getString("user");

        //RECYCLERVIEW
        filmList = fc.getFilmsEnCartelera(connect);
        recyclerView = findViewById(R.id.rvMainCartelera);

        filmsRvAdapter = new FilmsRVAdapter(this, filmList);
        filmsRvAdapter.setOnClickListener(this);
        recyclerView.setAdapter(filmsRvAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        //OBTENER USUARIO
        if (userName != null){
            u = uc.getUser(connect, userName);
            textView.setText("HOLA " + userName + " -- " + filmList.size());
        }
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
                Toast.makeText(this, "Bye " + userName, Toast.LENGTH_SHORT).show();
                startActivity(intent);
                return true;
            case (R.id.am_addEmple):
                intent = new Intent(this, AddUserActivity.class);
                startActivity(intent);
                return true;
            case (R.id.am_addFilm):
                intent = new Intent(this, AddFilmActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View view) {
        int posi = recyclerView.getChildAdapterPosition(view);
        //username = getIntent().getExtras().getString("user");
        Intent intent = new Intent(getApplicationContext(),DetFilmActivity.class);
        intent.putExtra("titulo",filmList.get(posi).getTitulo());
        //intent.putExtra("user", username);
        startActivity(intent);
    }
}