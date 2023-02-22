package es.alexbonet.tetsingrealm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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
    private String username;
    private Button btnVolver, btnAdd;
    private Usuario u;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_sesions);

        connect = DataBase.getInstance().conectar(this);

        btnVolver = findViewById(R.id.allSSalir);
        btnAdd = findViewById(R.id.allSAdd);

        username = getIntent().getExtras().getString("user");
        u = c.getUser(connect, username);


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
            intent.putExtra("user",username);
            startActivity(intent);
        });

        btnAdd.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(),AddSesionActivity.class);
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
                geSionRVAdapter.notifyItemRemoved(posi);
            }
        });
        builder.setNegativeButton("CANCELAR",null);
        AlertDialog dialog = builder.create();
        dialog.show();
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