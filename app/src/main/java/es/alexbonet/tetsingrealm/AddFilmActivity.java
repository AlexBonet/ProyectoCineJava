package es.alexbonet.tetsingrealm;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.util.UUID;

import es.alexbonet.tetsingrealm.R;
import es.alexbonet.tetsingrealm.controller.FilmController;
import es.alexbonet.tetsingrealm.db.DataBase;
import es.alexbonet.tetsingrealm.db.IniciarDB;
import es.alexbonet.tetsingrealm.model.Film;
import es.alexbonet.tetsingrealm.model.UserType;
import es.alexbonet.tetsingrealm.model.Usuario;
import io.realm.Realm;

public class AddFilmActivity extends AppCompatActivity {
    private final FilmController fc = new FilmController();
    private Realm connect;

    private EditText inTitulo, inDescr, inGenero, inEdMin, inDuracion;
    private CheckBox cbEnCartelera;
    private Button btnAdd, btnCancelar;

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
        btnAdd = findViewById(R.id.afbtnInsertar);
        btnCancelar = findViewById(R.id.afbtnCancelar);
        btnCancelar.setOnClickListener(view -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });

        btnAdd.setOnClickListener(view -> {
            String titulo = inTitulo.getText().toString();
            String descrp = inDescr.getText().toString();
            String duraci = inDuracion.getText().toString();
            String edamin = inEdMin.getText().toString();
            String genero = inGenero.getText().toString();
            boolean enCat = cbEnCartelera.isChecked();
            //si son empty
            if (titulo.isEmpty() || descrp.isEmpty() || edamin.isEmpty() || genero.isEmpty() || duraci.isEmpty()) {
                Toast.makeText(this, "CAMPOS VACIOS", Toast.LENGTH_SHORT).show();
            } else {
                if (fc.getFilmByName(connect, titulo) != null){
                    Toast.makeText(this, "YA EXISTE UNA PELICULA CON EL MISMO NOMBRE", Toast.LENGTH_SHORT).show();
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Confirma los campos");
                    builder.setMessage("Titulo: " + titulo +"\n" +
                            "Duración: " + duraci + "\n" +
                            "Edad mñinima: " + edamin + "\n");
                    // Add the buttons
                    builder.setPositiveButton("CONFIRMAR", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            fc.createFilm(connect,new Film(titulo, descrp, Integer.parseInt(duraci), Integer.parseInt(edamin), genero, enCat, "https://cdn.kinepolis.es/images/ES/65459BAD-CA99-4711-A97B-E049A5FA94D2/HO00004169/0000006071/Miki_Dkai:_Cassalla_Festes_i_Humor..._.jpg"));
                            /*
                            inTitulo.setText(null);
                            inDescr.setText(null);
                            inDuracion.setText(null);
                            inEdMin.setText(null);
                            inGenero.setText(null);
                            cbEnCartelera.setChecked(false);
                            */
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                        }
                    });
                    builder.setNegativeButton("CANCELAR", null);
                    // Create the AlertDialog
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }
        });

    }
}