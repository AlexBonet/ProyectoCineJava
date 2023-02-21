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
import es.alexbonet.tetsingrealm.model.Venta;
import es.alexbonet.tetsingrealm.model.enums.UserType;
import io.realm.Realm;

public class AllFilmsActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener {
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
        textView.setText("HAY " + filmsRvAdapter.getItemCount() + " PELICULAS EN TOTAL \n MANTEN PULSADO PARA ELIMINAR" );
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
    public boolean onLongClick(View view) {
        int posi = recyclerView.getChildAdapterPosition(view);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("SEGURO QUE QUIERES ELIMINAR ESTA PELICULA");
        builder.setPositiveButton("CONFIRMAR", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                c.deleteFilm(connect, filmList.get(posi));
                Toast.makeText(AllFilmsActivity.this, "Película eliminada correctamente", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("CANCELAR", null);
        AlertDialog dialog = builder.create();
        dialog.show();
        return false;
    }
}