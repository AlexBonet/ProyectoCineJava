package es.alexbonet.tetsingrealm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import es.alexbonet.tetsingrealm.RecyclerView.FilmsRVAdapter;
import es.alexbonet.tetsingrealm.controller.FilmController;
import es.alexbonet.tetsingrealm.db.DataBase;
import es.alexbonet.tetsingrealm.model.Film;
import io.realm.Realm;

public class AllFilmsActivity extends AppCompatActivity implements View.OnClickListener {
    private final FilmController fc = new FilmController();
    private TextView textView;
    private Realm connect;
    private RecyclerView recyclerView;
    private FilmsRVAdapter filmsRvAdapter;
    private List<Film> filmList;
    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_films);

        connect = DataBase.getInstance().conectar(this);

        userName = getIntent().getExtras().getString("user");

        textView = findViewById(R.id.allFTextView);

        //RECYCLERVIEW
        filmList = fc.getAllFilms(connect);
        recyclerView = findViewById(R.id.allFRV);

        filmsRvAdapter = new FilmsRVAdapter(this, filmList,connect,userName);
        filmsRvAdapter.setOnClickListener(this);
        recyclerView.setAdapter(filmsRvAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        textView.setText("HAY " + filmsRvAdapter.getItemCount() + " PELICULAS EN TOTAL");
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void onClick(View view) { //TODO PASAR USERS???
        int posi = recyclerView.getChildAdapterPosition(view);
        Intent intent = new Intent(getApplicationContext(),DetFilmActivity.class);
        intent.putExtra("titulo",filmList.get(posi).getTitulo());
        intent.putExtra("user",userName);
        startActivity(intent);
    }
}