package es.alexbonet.tetsingrealm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import es.alexbonet.tetsingrealm.R;
import es.alexbonet.tetsingrealm.RecyclerView.FilmsRVAdapter;
import es.alexbonet.tetsingrealm.RecyclerView.SesionRVAdapter;
import es.alexbonet.tetsingrealm.controller.FilmController;
import es.alexbonet.tetsingrealm.controller.SesionController;
import es.alexbonet.tetsingrealm.controller.UserController;
import es.alexbonet.tetsingrealm.db.DataBase;
import es.alexbonet.tetsingrealm.model.Film;
import es.alexbonet.tetsingrealm.model.Sesion;
import io.realm.Realm;

public class SesionsDispoActivity extends AppCompatActivity implements View.OnClickListener {

    private final SesionController sc = new SesionController();
    private SesionRVAdapter sesionRVAdapter;
    private List<Sesion> sesionList;
    private Realm connect;

    private TextView tvtitulo, tvdia;
    private RecyclerView recyclerView;
    private String fecha, peli;
    private int dia,mes,any;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sesions_dispo);

        connect = DataBase.getInstance().conectar(this);

        tvdia = findViewById(R.id.sdDia);
        tvtitulo = findViewById(R.id.sdTitulo);

        peli = getIntent().getExtras().getString("film");
        fecha = getIntent().getExtras().getString("fecha");
        dia = getIntent().getExtras().getInt("dia");
        mes = getIntent().getExtras().getInt("mes");
        any = getIntent().getExtras().getInt("any");

        tvtitulo.setText(peli);
        tvdia.setText(fecha);

        //RECYCLERVIEW
        sesionList = sc.getAllSesionFromAFilm(connect, peli);
        recyclerView = findViewById(R.id.sdRecyclerview);

        sesionRVAdapter = new SesionRVAdapter(this, sesionList);
        sesionRVAdapter.setOnClickListener(this);
        recyclerView.setAdapter(sesionRVAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void onClick(View v) {
        int posi = recyclerView.getChildAdapterPosition(v);
        //username = getIntent().getExtras().getString("user");
        //Intent intent = new Intent(getApplicationContext(),DetFilmActivity.class); //TODO IR AL SALA
        //intent.putExtra("titulo",sesionList.get(posi).getTitulo());
        //intent.putExtra("user", username);
        //startActivity(intent);
    }
}