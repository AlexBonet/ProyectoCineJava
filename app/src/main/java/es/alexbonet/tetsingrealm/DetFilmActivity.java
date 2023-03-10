package es.alexbonet.tetsingrealm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import es.alexbonet.tetsingrealm.db.Controller;
import es.alexbonet.tetsingrealm.db.DataBase;
import es.alexbonet.tetsingrealm.model.Film;
import es.alexbonet.tetsingrealm.model.Usuario;
import es.alexbonet.tetsingrealm.model.enums.UserType;
import io.realm.Realm;

public class DetFilmActivity extends AppCompatActivity {
    private final Controller c = new Controller();
    private TextView titulo, genero, edad, duracion;
    private Button btnDescrip, btnSesions, btnVolver;
    private ImageView img;
    private CheckBox cbCartelera;
    private Film film;
    private Realm connect;
    private String userName;
    private Usuario u;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_det_film);

        connect = DataBase.getInstance().conectar(this);

        String tituloFrom = getIntent().getExtras().getString("film");
        userName = getIntent().getExtras().getString("user");
        u = c.getUser(connect, userName);

        film = c.getFilmByName(connect, tituloFrom);

        titulo = findViewById(R.id.dfTitulo);
        genero = findViewById(R.id.dfCategoria);
        edad = findViewById(R.id.dfEdad);
        duracion = findViewById(R.id.dfDuracion);
        btnDescrip = findViewById(R.id.dfbtnDescrip);
        btnVolver = findViewById(R.id.dfbtnVolver);
        btnSesions = findViewById(R.id.dfbtnVerSesiones);
        cbCartelera = findViewById(R.id.cbEnCartelera);
        img = findViewById(R.id.dfImg);

        titulo.setText(film.getTitulo());
        genero.setText("Genero: " + film.getGenero());
        duracion.setText("Duraci??n:" + film.getDuracion());
        edad.setText("Edad m??nima recomendada: " + film.getEdad_min());
        Picasso.get().load(film.getUrlImage()).into(img);

        //Apagar el bot??n de ver sesiones si no hay
        if (c.getAllSesionFromAFilm(connect,tituloFrom).size() < 1){
            btnSesions.setEnabled(false);
        }

        //Si la peli esta en cartelera se marca con el check
        if (film.isEn_cartelera()){
            cbCartelera.setChecked(true);
        }else {
            cbCartelera.setChecked(false);
        }

        //El usuario no puede ver checkbox
        if (c.getUser(connect,userName).getTipo().equals(UserType.CLIENTE.getString())){
            cbCartelera.setVisibility(View.INVISIBLE);
        }

        //CheckBox para poner o quitar una pelicula de la cartelera
        cbCartelera.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                c.setCartelera(connect,tituloFrom, b);
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                intent.putExtra("user",userName);
                Toast.makeText(DetFilmActivity.this, "VOLVIENDO A LA P??GINA DE INICIO", Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });

        //BOTONES
        btnSesions.setOnClickListener(view -> {
            Intent intent = new Intent(this, SesionsDispoActivity.class);
            intent.putExtra("film", tituloFrom);
            intent.putExtra("user",userName);
            startActivity(intent);
        });

        btnDescrip.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("DESCRIPCION:");
            builder.setMessage(film.getDescrip());
            builder.setNeutralButton("Cerrar", null);
            AlertDialog dialog = builder.create();
            dialog.show();
        });

        btnVolver.setOnClickListener(view -> {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("user",userName);
            Toast.makeText(this, "Volviendo a la cartelera", Toast.LENGTH_SHORT).show();
            startActivity(intent);
        });
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