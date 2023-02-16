package es.alexbonet.tetsingrealm;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import es.alexbonet.tetsingrealm.controller.FilmController;
import es.alexbonet.tetsingrealm.controller.SesionController;
import es.alexbonet.tetsingrealm.db.DataBase;
import es.alexbonet.tetsingrealm.model.Film;
import io.realm.Realm;

public class DetFilmActivity extends AppCompatActivity {
    private final FilmController filmController = new FilmController();
    private final SesionController sesionController = new SesionController();
    private TextView titulo, genero, edad, duracion;
    private Button btnDescrip, btnSesions;
    private ImageView img;
    private Film film;
    private Realm connect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_det_film);

        connect = DataBase.getInstance().conectar(this);

        String tituloFrom = getIntent().getExtras().getString("titulo");

        film = filmController.getFilmByName(connect, tituloFrom);

        titulo = findViewById(R.id.dfTitulo);
        genero = findViewById(R.id.dfCategoria);
        edad = findViewById(R.id.dfEdad);
        duracion = findViewById(R.id.dfDuracion);
        btnDescrip = findViewById(R.id.dfbtnDescrip);
        btnSesions = findViewById(R.id.dfbtnVerSesiones);
        img = findViewById(R.id.dfImg);

        titulo.setText(film.getTitulo());
        genero.setText("Genero: " + film.getGenero());
        duracion.setText("Duración:" + film.getDuracion());
        edad.setText("Edad mínima recomendada: " + film.getEdad_min());
        Picasso.get().load(film.getUrlImage()).into(img);

        btnDescrip.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("DESCRIPCION:");
            builder.setMessage(film.getDescrip());
            builder.setNeutralButton("Cerrar", null);
            AlertDialog dialog = builder.create();
            dialog.show();
        });


        btnSesions.setOnClickListener(view -> {
            Intent intent = new Intent(this, SesionsDispoActivity.class);
            //TODO putExtra?? yo crec q pasant el ttulo de la peli bé
            intent.putExtra("film", tituloFrom);
            startActivity(intent);
        });

    }
}