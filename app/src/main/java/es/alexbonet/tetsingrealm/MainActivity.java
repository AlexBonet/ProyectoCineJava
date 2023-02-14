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

import es.alexbonet.tetsingrealm.RecyclerView.RVAdapter;
import es.alexbonet.tetsingrealm.controller.FilmController;
import es.alexbonet.tetsingrealm.controller.UserController;
import es.alexbonet.tetsingrealm.db.DataBase;
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
    private RVAdapter rvAdapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        connect = DataBase.getInstance().conectar(this);

        textView = findViewById(R.id.textView);

        userName = getIntent().getExtras().getString("user");

        if (userName != null){
            textView.setText("HOLA " + userName);
            u = uc.getUser(connect, userName);
        }

        //RECYCLERVIEW
        recyclerView = findViewById(R.id.rvMainCartelera);

        rvAdapter = new RVAdapter(this, fc.getFilmsEnCartelera(connect));
        rvAdapter.setOnClickListener(this);
        recyclerView.setAdapter(rvAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_admin, menu);
        return super.onCreateOptionsMenu(menu);
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

    }
}