package es.alexbonet.tetsingrealm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.time.LocalDate;
import java.util.List;

import es.alexbonet.tetsingrealm.R;
import es.alexbonet.tetsingrealm.RecyclerView.FilmsRVAdapter;
import es.alexbonet.tetsingrealm.RecyclerView.SesionRVAdapter;
import es.alexbonet.tetsingrealm.db.Controller;
import es.alexbonet.tetsingrealm.db.DataBase;
import es.alexbonet.tetsingrealm.model.Film;
import es.alexbonet.tetsingrealm.model.Sesion;
import io.realm.Realm;

public class SesionsDispoActivity extends AppCompatActivity implements View.OnClickListener {

    private final Controller c = new Controller();
    private SesionRVAdapter sesionRVAdapter;
    private List<Sesion> sesionList;
    private Realm connect;

    private TextView tvtitulo, tvdia;
    private RecyclerView recyclerView;
    private String peli, username;
    private Button btnVolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sesions_dispo);

        connect = DataBase.getInstance().conectar(this);

        tvdia = findViewById(R.id.sdDia);
        tvtitulo = findViewById(R.id.sdTitulo);
        btnVolver = findViewById(R.id.sdbtnVolver);

        username = getIntent().getExtras().getString("user");
        peli = getIntent().getExtras().getString("film");
        tvtitulo.setText(peli);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            tvdia.setText("SESIONSE DE HOY " + LocalDate.now());
        } else {
            tvdia.setText("SESIONSE DE HOY");

        }

        //RECYCLERVIEW
        sesionList = c.getAllSesionFromAFilm(connect, peli);
        recyclerView = findViewById(R.id.sdRecyclerview);

        sesionRVAdapter = new SesionRVAdapter(this, sesionList);
        sesionRVAdapter.setOnClickListener(this);
        recyclerView.setAdapter(sesionRVAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        btnVolver.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(),DetFilmActivity.class);
            intent.putExtra("film",peli);
            intent.putExtra("user",username);
            startActivity(intent);
        });
    }

    @Override
    public void onClick(View v) {
        int posi = recyclerView.getChildAdapterPosition(v);
        Intent intent = new Intent(getApplicationContext(),SalaActivity.class);
        intent.putExtra("user", username);
        intent.putExtra("sesion",sesionList.get(posi).getId_sesion()); //EN ESTO PASE LA SALA Y LA PELI
        startActivity(intent);
    }
}