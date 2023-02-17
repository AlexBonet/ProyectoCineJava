package es.alexbonet.tetsingrealm;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
import es.alexbonet.tetsingrealm.model.UserType;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_det_film);

        connect = DataBase.getInstance().conectar(this);

        String tituloFrom = getIntent().getExtras().getString("film");
        userName = getIntent().getExtras().getString("user");


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
        duracion.setText("Duración:" + film.getDuracion());
        edad.setText("Edad mínima recomendada: " + film.getEdad_min());
        Picasso.get().load(film.getUrlImage()).into(img);


        //TODO puc que fer o que mostre algo de sesiones no disponible o desactivar el boton o ferlo amagat, ademés de que els admins puguen añadir
        //TODO BOTON APAGAT SI NO TE SESION


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

        cbCartelera.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                c.setCartelera(connect,tituloFrom, b);
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                intent.putExtra("user",userName);
                Toast.makeText(DetFilmActivity.this, "VOLVIENDO A LA PÁGINA DE INICIO", Toast.LENGTH_SHORT).show();
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
}