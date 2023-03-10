package es.alexbonet.tetsingrealm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import es.alexbonet.tetsingrealm.db.Controller;
import es.alexbonet.tetsingrealm.db.DataBase;
import es.alexbonet.tetsingrealm.model.enums.UserType;
import es.alexbonet.tetsingrealm.model.Usuario;
import io.realm.Realm;

public class AddUserActivity extends AppCompatActivity {

    private EditText inputDni, inputNombre, inputApell, inputUserName, inputPswd, inputConfPswd;
    private Button btnRegist, btnAECancelar;
    private Realm connect;
    private final Controller c = new Controller();
    private String userName;
    private Usuario u;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        connect = DataBase.getInstance().conectar(this);
        userName = getIntent().getExtras().getString("user");
        u = c.getUser(connect, userName);

        inputDni = findViewById(R.id.inputAEDni);
        inputNombre = findViewById(R.id.inputAENombre);
        inputApell = findViewById(R.id.inputAEApellido);
        inputUserName = findViewById(R.id.inputAEUserName);
        inputPswd = findViewById(R.id.inputAEPswd);
        inputConfPswd = findViewById(R.id.inputAEConfirmPswd);
        btnRegist = findViewById(R.id.btnAERegistrar);
        btnAECancelar = findViewById(R.id.btnAECancelar);

        btnAECancelar.setOnClickListener(view -> {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("user",userName);
            startActivity(intent);
        });

        btnRegist.setOnClickListener(view -> {
            registrar();
        });
    }

    private void registrar() {
        String dni = inputDni.getText().toString();
        String nom = inputNombre.getText().toString();
        String ape = inputApell.getText().toString();
        String user = inputUserName.getText().toString();
        String pswd = inputPswd.getText().toString();
        String cpswd = inputConfPswd.getText().toString();

        if (dni.isEmpty() || nom.isEmpty() || ape.isEmpty() || user.isEmpty() || pswd.isEmpty() || cpswd.isEmpty()) { // QUE NO HAYA NINGUN CAMPO VACIO
            Toast.makeText(this, "CAMPOS VACIOS", Toast.LENGTH_SHORT).show();
        } else {
            if (c.getUser(connect, user) != null) { // QUE NO HAYA OTRO USUARIO CON EL MISMO NOMBRE DE USUARIO
                Toast.makeText(this, "El nombre de usuario ya existe", Toast.LENGTH_SHORT).show();
            } else if (c.getFromDNI(connect,dni) != null) { // QUE NO HAYA OTRO USUARIO CON EL MISMO DNI
                Toast.makeText(this, "El DNI del usuario ya existe", Toast.LENGTH_SHORT).show();
            } else {
                if (pswd.equals(cpswd)) { // SI LAS CONTRASE??AS COINCIDEN
                    c.createUser(connect, new Usuario(dni,nom,ape,user,pswd, UserType.EMPLEADO.getString())); // CREA EL USUARIO

                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Usuario creado correctamente");
                    // Add the buttons
                    builder.setPositiveButton("Iniciar sesi??n como " + user, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                            intent.putExtra("user", user);
                            startActivity(intent);
                        }
                    });
                    builder.setNegativeButton("Continuar como ADMIN", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                            intent.putExtra("user", "admin");
                            startActivity(intent);
                        }
                    });
                    // Create the AlertDialog
                    AlertDialog dialog = builder.create();
                    dialog.show();

                } else {
                    Toast.makeText(this, "Las CONTRASE??AS no coinciden", Toast.LENGTH_SHORT).show();
                }
            }
        }
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