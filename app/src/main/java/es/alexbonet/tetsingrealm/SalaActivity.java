package es.alexbonet.tetsingrealm;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;

import es.alexbonet.tetsingrealm.db.Controller;
import es.alexbonet.tetsingrealm.db.DataBase;
import es.alexbonet.tetsingrealm.model.Butaca;
import es.alexbonet.tetsingrealm.model.Film;
import es.alexbonet.tetsingrealm.model.Sala;
import es.alexbonet.tetsingrealm.model.Sesion;
import es.alexbonet.tetsingrealm.model.Usuario;
import io.realm.Realm;

public class SalaActivity extends AppCompatActivity {

    private final Controller c = new Controller();
    private Realm connect;
    private Button btnComprar, btnCancelar;
    private TextView informacion;
    private TableLayout mainTable;
    private List<Integer> listaDeButacas; //TODO CAMBIAR NOM DE LA LIST
    private List<Butaca> asientosOcupados, listaDeButacasOcupadas;
    private Sala sala;
    private Sesion sesion;
    private Usuario user;
    private String username, id_sesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sala);

        connect = DataBase.getInstance().conectar(this);

        //Obtener usuario
        username = getIntent().getExtras().getString("user");
        user = c.getUser(connect, username);
        //Obtener sesion
        id_sesion = getIntent().getExtras().getString("sesion");
        sesion = c.getAllSesionFromID(connect, id_sesion);
        //Obtener sala
        sala = c.getSala(connect, sesion.getNum_sala());

        //TEXTVIEW CON INFORMACIÓN
        informacion = findViewById(R.id.salaInfo);
        informacion.setText(sesion.getTitulo_peli() + "\n" +
                sesion.getHora_empieza() + "\n" +
                "SALA: " + sala.getNum_sala() + " " + sala.getTipo_sala()
                );

        //TABLA DE BUTACAS
        mainTable = findViewById(R.id.salaTable);
        listaDeButacas = new LinkedList<>();
        asientosOcupados = new LinkedList<>();
        listaDeButacasOcupadas = new LinkedList<>();


        TableLayout contTable = new TableLayout(this);
        contTable.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        int contador = 0;
        for (int i = 0; i < sala.getFilas(); i++) {
            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            tableRow.setGravity(Gravity.CENTER);
            for (int j = 0; j < sala.getColumnas(); j++) {
                CheckBox cb = new CheckBox(this);
                cb.setButtonDrawable(R.drawable.selector_butaca);
                cb.setGravity(Gravity.CENTER);
                cb.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT));
                cb.setId(++contador);
                cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        int posibleY = 1;
                        int posibleX = buttonView.getId();

                        for (int k = 2, g = sala.getColumnas(); k <= sala.getFilas(); k++, g+=5) {
                            if (posibleX >= 1+g && posibleX <= 5+g){
                                posibleX -= g;
                                posibleY = k;
                            }
                        }

                        Butaca butaca = new Butaca(posibleX, posibleY, sala.getNum_sala());
                        if(isChecked){
                            asientosOcupados.add(butaca);
                        }else{
                            asientosOcupados.remove(butaca);
                        }
                    }
                });
                tableRow.addView(cb);
            }
            contTable.addView(tableRow);
        }
        mainTable.addView(contTable);

        //ONCLICK EN BOTONES
        btnCancelar = findViewById(R.id.salabtnCancelar);
        btnComprar = findViewById(R.id.salabtnAceptar);

        btnCancelar.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("DONDE QUIERES IR:");

            builder.setPositiveButton("VER SESIONES", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    Intent intent = new Intent(getApplicationContext(),SesionsDispoActivity.class);
                    intent.putExtra("film", sesion.getTitulo_peli());
                    intent.putExtra("user", username);
                    startActivity(intent);
                }
            });
            builder.setNegativeButton("IR AL PELICULA", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    Intent intent = new Intent(getApplicationContext(),DetFilmActivity.class);
                    intent.putExtra("film",sesion.getTitulo_peli());
                    intent.putExtra("user",username);
                    startActivity(intent);
                }
            });
            builder.setNeutralButton("", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    intent.putExtra("user", username);
                    startActivity(intent);
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        });

        btnComprar.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("SEGURO QUE QUIERES CONTAR ESTAS ENTRADAS");
            builder.setPositiveButton("CONFIRMAR", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // TODO COMPRA LA ENTRDA
                }
            });
            builder.setNegativeButton("CANCELAR", null);
            AlertDialog dialog = builder.create();
            dialog.show();
        });
    }
}