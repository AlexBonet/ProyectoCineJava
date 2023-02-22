package es.alexbonet.tetsingrealm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;
import java.util.List;

import es.alexbonet.tetsingrealm.RecyclerView.FilmsRVAdapter;
import es.alexbonet.tetsingrealm.db.Controller;
import es.alexbonet.tetsingrealm.db.DataBase;
import es.alexbonet.tetsingrealm.model.Butaca;
import es.alexbonet.tetsingrealm.model.Entrada;
import es.alexbonet.tetsingrealm.model.Film;
import es.alexbonet.tetsingrealm.model.Usuario;
import es.alexbonet.tetsingrealm.model.Venta;
import es.alexbonet.tetsingrealm.model.enums.UserType;
import io.realm.Realm;

public class AllFilmsActivity extends AppCompatActivity implements View.OnClickListener {
    private final Controller c = new Controller();
    private TextView textView;
    private Realm connect;
    private RecyclerView recyclerView;
    private FilmsRVAdapter filmsRvAdapter;
    private List<Film> filmList;
    private String userName;
    private Button btnvolver, btnAdd;
    private Usuario u;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_films);

        connect = DataBase.getInstance().conectar(this);

        userName = getIntent().getExtras().getString("user");
        u = c.getUser(connect, userName);

        textView = findViewById(R.id.allFTextView);
        btnvolver = findViewById(R.id.allFbtnVolver);
        btnAdd = findViewById(R.id.allFbtnAddF);

        btnvolver.setOnClickListener(view -> {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("user",userName);
            Toast.makeText(this, "Volviendo a la cartelera", Toast.LENGTH_SHORT).show();
            startActivity(intent);
        });

        btnAdd.setOnClickListener(view -> {
            Intent intent = new Intent(this, AddFilmActivity.class);
            intent.putExtra("user",userName);
            Toast.makeText(this, "Volviendo a la cartelera", Toast.LENGTH_SHORT).show();
            startActivity(intent);
        });

        //RECYCLERVIEW
        filmList = c.getAllFilms(connect);
        recyclerView = findViewById(R.id.allFRV);

        filmsRvAdapter = new FilmsRVAdapter(this, filmList,connect,userName);
        filmsRvAdapter.setOnClickListener(this);
        recyclerView.setAdapter(filmsRvAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        textView.setText("HAY " + filmsRvAdapter.getItemCount() + " PELICULAS EN TOTAL \n DESLIZA HACIA LA IZQUIERDA PARA ELIMINAR" );
        recyclerView.setLayoutManager(linearLayoutManager);

        //DESLIZAR PARA ELIMINAR
        ItemTouchHelper ith = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false; // true if moved, false otherwise
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int posi = viewHolder.getAdapterPosition();
                Film f = filmList.get(posi);
                filmsRvAdapter.notifyItemRemoved(posi);
                c.deleteFilm(connect, f);
                Toast.makeText(AllFilmsActivity.this, "Película eliminada correctamente", Toast.LENGTH_SHORT).show();
            }
        });
        ith.attachToRecyclerView(recyclerView);
    }

    @Override
    public void onClick(View view) {
        int posi = recyclerView.getChildAdapterPosition(view);
        Intent intent = new Intent(getApplicationContext(),DetFilmActivity.class);
        intent.putExtra("film",filmList.get(posi).getTitulo());
        intent.putExtra("user",userName);
        startActivity(intent);
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
                intent.putExtra("user",userName);
                startActivity(intent);
                return true;
            case (R.id.am_gestion_film):
                intent = new Intent(this, AllFilmsActivity.class);
                intent.putExtra("user",userName);
                startActivity(intent);
                return true;
            case (R.id.am_gestion_sesion):
                intent = new Intent(this, AllSesionsActivity.class);
                intent.putExtra("user",userName);
                startActivity(intent);
                return true;
            case (R.id.am_allVentas):
                intent = new Intent(this, VerVentasActivity.class);
                intent.putExtra("user",userName);
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