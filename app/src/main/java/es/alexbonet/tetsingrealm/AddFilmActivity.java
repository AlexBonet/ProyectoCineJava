package es.alexbonet.tetsingrealm;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import es.alexbonet.tetsingrealm.db.Controller;
import es.alexbonet.tetsingrealm.db.DataBase;
import es.alexbonet.tetsingrealm.model.Film;
import io.realm.Realm;

public class AddFilmActivity extends AppCompatActivity {
    private final Controller c = new Controller();
    private Realm connect;

    private EditText inTitulo, inDescr, inGenero, inEdMin, inDuracion, inImgUrl;
    private CheckBox cbEnCartelera;
    private Button btnAdd, btnCancelar;
    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_film);

        connect = DataBase.getInstance().conectar(this);
        userName = getIntent().getExtras().getString("user");

        inTitulo = findViewById(R.id.afInputTitulo);
        inDescr = findViewById(R.id.afInputDescip);
        inGenero = findViewById(R.id.afInputGenero);
        inEdMin = findViewById(R.id.afInputEdadMin);
        inDuracion = findViewById(R.id.afInputDuracion);
        inImgUrl = findViewById(R.id.afInputImgUrl);
        cbEnCartelera = findViewById(R.id.afCheck);
        btnAdd = findViewById(R.id.afbtnInsertar);
        btnCancelar = findViewById(R.id.afbtnCancelar);
        btnCancelar.setOnClickListener(view -> {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("user",userName);
            startActivity(intent);
        });

        btnAdd.setOnClickListener(view -> {
            String titulo = inTitulo.getText().toString();
            String descrp = inDescr.getText().toString();
            String duraci = inDuracion.getText().toString();
            String edamin = inEdMin.getText().toString();
            String genero = inGenero.getText().toString();
            final String[] url = {inImgUrl.getText().toString()};
            boolean enCat = cbEnCartelera.isChecked();
            //si son empty
            if (titulo.isEmpty() || descrp.isEmpty() || edamin.isEmpty() || genero.isEmpty() || duraci.isEmpty()) {
                Toast.makeText(this, "CAMPOS VACIOS", Toast.LENGTH_SHORT).show();
            } else {
                if (c.getFilmByName(connect, titulo) != null){
                    Toast.makeText(this, "YA EXISTE UNA PELICULA CON EL MISMO NOMBRE", Toast.LENGTH_SHORT).show();
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Confirma los campos");
                    builder.setMessage("Titulo: " + titulo +"\n" +
                            "Duración: " + duraci + "\n" +
                            "Edad mñinima: " + edamin + "\n"
                    );
                    // Add the buttons
                    builder.setPositiveButton("CONFIRMAR", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            if (url[0].length() < 10){
                                url[0] = "https://cdn.kinepolis.es/images/ES/65459BAD-CA99-4711-A97B-E049A5FA94D2/HO00004169/0000006071/Miki_Dkai:_Cassalla_Festes_i_Humor..._.jpg";
                            }
                            c.createFilm(connect,new Film(titulo, descrp, Integer.parseInt(duraci), Integer.parseInt(edamin), genero, enCat, url[0]));
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            intent.putExtra("user",userName);
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