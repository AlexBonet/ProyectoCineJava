package es.alexbonet.tetsingrealm;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import es.alexbonet.tetsingrealm.RecyclerView.GeSionRVAdapter;
import es.alexbonet.tetsingrealm.RecyclerView.SesionRVAdapter;
import es.alexbonet.tetsingrealm.db.Controller;
import es.alexbonet.tetsingrealm.db.DataBase;
import es.alexbonet.tetsingrealm.db.IniciarDB;
import es.alexbonet.tetsingrealm.model.Sesion;
import es.alexbonet.tetsingrealm.model.Usuario;
import es.alexbonet.tetsingrealm.model.enums.UserType;
import io.realm.Realm;

public class AllSesionsActivity extends AppCompatActivity implements View.OnClickListener{

    private final Controller c = new Controller();
    private GeSionRVAdapter geSionRVAdapter;
    private List<Sesion> sesionList;
    private Realm connect;
    private RecyclerView recyclerView;
    private String peli, username;
    private Button btnVolver, btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_sesions);

        connect = DataBase.getInstance().conectar(this);

        btnVolver = findViewById(R.id.allSSalir);
        btnAdd = findViewById(R.id.allSAdd);

        username = getIntent().getExtras().getString("user");
        peli = getIntent().getExtras().getString("film");


        //RECYCLERVIEW
        sesionList = c.getAllSesions(connect);
        recyclerView = findViewById(R.id.allSRV);

        geSionRVAdapter = new GeSionRVAdapter(this, sesionList);
        geSionRVAdapter.setOnClickListener(this);
        recyclerView.setAdapter(geSionRVAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        btnVolver.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            intent.putExtra("film",peli);
            intent.putExtra("user",username);
            startActivity(intent);
        });
    }

    @Override
    public void onClick(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("¿QUIERES ELIMINAR LA SESION?");
        // Add the buttons
        builder.setPositiveButton("ELIMINAR", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                int posi = recyclerView.getChildAdapterPosition(view);
                c.deleteSesion(connect, sesionList.get(posi).getId_sesion());
                Toast.makeText(AllSesionsActivity.this, "ELIMINANDO", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(),AllSesionsActivity.class);
                intent.putExtra("film",peli);
                intent.putExtra("user",username);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("CANCELAR",null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}