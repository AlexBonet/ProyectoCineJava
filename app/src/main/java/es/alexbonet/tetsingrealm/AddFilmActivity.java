package es.alexbonet.tetsingrealm;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import es.alexbonet.tetsingrealm.R;
import es.alexbonet.tetsingrealm.controller.FilmController;
import es.alexbonet.tetsingrealm.db.DataBase;
import io.realm.Realm;

public class AddFilmActivity extends AppCompatActivity {
    private final FilmController fc = new FilmController();
    private Realm connect;

    private EditText inTitulo, inDescr, inGenero, inEdMin, inDuracion;
    private CheckBox cbEnCartelera;
    private Button btnAdd, btnCancelar;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_film);

        connect = DataBase.getInstance().conectar(this);

        inTitulo = findViewById(R.id.afInputTitulo);
        inDescr = findViewById(R.id.afInputDescip);
        inGenero = findViewById(R.id.afInputGenero);
        inEdMin = findViewById(R.id.afInputEdadMin);
        inDuracion = findViewById(R.id.afInputDuracion);
        cbEnCartelera = findViewById(R.id.afCheck);
        btnAdd = findViewById(R.id.afbtnCancelar);
        btnCancelar = findViewById(R.id.afbtnCancelar);
    }
}