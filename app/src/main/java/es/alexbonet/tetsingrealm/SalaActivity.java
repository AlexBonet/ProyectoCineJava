package es.alexbonet.tetsingrealm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.List;

import es.alexbonet.tetsingrealm.db.Controller;
import es.alexbonet.tetsingrealm.db.DataBase;
import es.alexbonet.tetsingrealm.model.Butaca;
import es.alexbonet.tetsingrealm.model.RecuentoButacas;
import es.alexbonet.tetsingrealm.model.Sala;
import es.alexbonet.tetsingrealm.model.Sesion;
import es.alexbonet.tetsingrealm.model.Usuario;
import es.alexbonet.tetsingrealm.model.enums.UserType;
import io.realm.Realm;

public class SalaActivity extends AppCompatActivity {

    private final Controller c = new Controller();
    private Realm connect;
    private Button btnComprar, btnCancelar;
    private TextView informacion;
    private TableLayout mainTable;
    private List<Butaca> butacas, butacasOcupasdas;
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
        butacas = new LinkedList<>();
        butacasOcupasdas = c.getAllButacasOcupadasDeSala(connect,id_sesion);

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

                for(Butaca b : butacasOcupasdas){
                    if(b.getColunna() == j+1 && b.getFila() == i+1){
                        cb.setChecked(false);
                        cb.setEnabled(false);
                        cb.setButtonDrawable(R.drawable.baseline_chair_24_gris);
                    }
                }
                cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        int num_fila = 1;
                        int num_columna = buttonView.getId();

                        for (int k = 2, g = sala.getColumnas(); k <= sala.getFilas(); k++, g+= sala.getColumnas()) {
                            if (num_columna >= 1+g && num_columna <= sala.getColumnas()+g){
                                num_columna -= g;
                                num_fila = k;
                            }
                        }

                        Butaca butaca = new Butaca(num_fila, num_columna, sala.getNum_sala());
                        if(isChecked){
                            butacas.add(butaca);
                        }else{
                            butacas.remove(butaca);
                            butacas.remove(butacas.size()-1);
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
            builder.setItems(new CharSequence[]{"VER SESIONES", "VOLVER A LA PELÍCULA", "IR A LA CARTELERA"}, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent;
                    switch (i){
                        case 0:
                            intent = new Intent(getApplicationContext(),SesionsDispoActivity.class);
                            intent.putExtra("film", sesion.getTitulo_peli());
                            intent.putExtra("user", username);
                            startActivity(intent);
                            break;
                        case 1:
                            intent = new Intent(getApplicationContext(),DetFilmActivity.class);
                            intent.putExtra("film",sesion.getTitulo_peli());
                            intent.putExtra("user",username);
                            startActivity(intent);
                            break;
                        case 2:
                            intent = new Intent(getApplicationContext(),MainActivity.class);
                            intent.putExtra("user", username);
                            Toast.makeText(getApplicationContext(), "Volviendo a la cartelera", Toast.LENGTH_SHORT).show();
                            startActivity(intent);
                            break;
                    }
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        });

        btnComprar.setOnClickListener(view -> {
            if (butacas.size() > 0){
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("SEGURO QUE QUIERES COMPRAR ESTAS ENTRADAS");
                builder.setPositiveButton("CONFIRMAR", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(SalaActivity.this, ComprarActivity.class);
                        RecuentoButacas butacas = new RecuentoButacas(SalaActivity.this.butacas);
                        intent.putExtra("butacas", butacas);
                        intent.putExtra("user", username);
                        intent.putExtra("sesion",sesion.getId_sesion()); //EN ESTO PASE LA SALA Y LA PELI
                        startActivity(intent);
                    }
                });
                builder.setNegativeButton("CANCELAR", null);
                AlertDialog dialog = builder.create();
                dialog.show();
            } else {
                Toast.makeText(this, "ELIGE SU ASIENTO", Toast.LENGTH_SHORT).show();
            }

        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (user.getTipo().equals(UserType.ADMINISTRADOR.getString())){
            getMenuInflater().inflate(R.menu.menu_admin, menu);
            return super.onCreateOptionsMenu(menu);
        } else if (user.getTipo().equals(UserType.EMPLEADO.getString())){
            getMenuInflater().inflate(R.menu.menu_emple, menu);
            return super.onCreateOptionsMenu(menu);
        } else if (user.getTipo().equals(UserType.CLIENTE.getString())){
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
                Toast.makeText(this, "EL USUARIO " + user.getUserName() + " ES " + user.getTipo(), Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}