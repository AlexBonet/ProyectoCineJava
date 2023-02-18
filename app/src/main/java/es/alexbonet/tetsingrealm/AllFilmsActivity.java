package es.alexbonet.tetsingrealm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import es.alexbonet.tetsingrealm.RecyclerView.FilmsRVAdapter;
import es.alexbonet.tetsingrealm.db.Controller;
import es.alexbonet.tetsingrealm.db.DataBase;
import es.alexbonet.tetsingrealm.model.Film;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_films);

        connect = DataBase.getInstance().conectar(this);

        userName = getIntent().getExtras().getString("user");

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
        textView.setText("HAY " + filmsRvAdapter.getItemCount() + " PELICULAS EN TOTAL");
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void onClick(View view) {
        int posi = recyclerView.getChildAdapterPosition(view);
        Intent intent = new Intent(getApplicationContext(),DetFilmActivity.class);
        intent.putExtra("film",filmList.get(posi).getTitulo());
        intent.putExtra("user",userName);
        startActivity(intent);
    }
}