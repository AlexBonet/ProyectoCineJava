package es.alexbonet.tetsingrealm;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.UUID;

import es.alexbonet.tetsingrealm.controller.UserController;
import es.alexbonet.tetsingrealm.db.DataBase;
import es.alexbonet.tetsingrealm.db.IniciarDB;
import es.alexbonet.tetsingrealm.model.Usuario;
import es.alexbonet.tetsingrealm.model.UserType;
import io.realm.Realm;

public class LogInActivity extends AppCompatActivity {

    private TextView textViewLogIn;
    private EditText inputUser, inputPswd;
    private Button btnLogIn, btnCrearCuenta;
    private Realm connect;
    private final UserController userController = new UserController();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        connect = DataBase.getInstance().conectar(this);

        inputUser = findViewById(R.id.inputLogUser);
        inputPswd = findViewById(R.id.inputLogPswd);
        btnLogIn = findViewById(R.id.btnLogIn);
        btnCrearCuenta = findViewById(R.id.btnCrearCuenta);
        textViewLogIn = findViewById(R.id.textViewLogIn);

        //SI no encuentra una base de datos te da la opcion de iniciar una
        long cuantos = connect.where(Usuario.class).count();
        if (cuantos == 0){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("COMO INICIAR LA APP");
            // Add the buttons
            builder.setPositiveButton("Utilizar con base de datos", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    IniciarDB ini = new IniciarDB();
                    ini.init(connect);
                    Toast.makeText(LogInActivity.this, "SE HA INICIADO LA BASE DE DATOS", Toast.LENGTH_SHORT).show();
                }
            });
            builder.setNegativeButton("Empezar sin base de datos", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    userController.createUser(connect, new Usuario(
                            UUID.randomUUID().toString(),
                            "DNIADMIN",
                            "admin",
                            "admin",
                            "admin",
                            "admin",
                            UserType.ADMINISTRADOR.toString()));
                    Toast.makeText(LogInActivity.this, "el unico usuaio es admin, contraseña admin", Toast.LENGTH_SHORT).show();
                    inputUser.setText("admin");
                    inputPswd.setText("admin");
                }
            });
            // Create the AlertDialog
            AlertDialog dialog = builder.create();
            dialog.show();
        }

        btnLogIn.setOnClickListener(view -> {
            login();
        });

        btnCrearCuenta.setOnClickListener(view -> {
            Intent intent = new Intent(this, SingInActivity.class);
            startActivity(intent);
        });

        textViewLogIn.setOnClickListener(view -> {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("user", "admin");
            startActivity(intent);
        });

    }

    private void login() {
        Usuario u = userController.getUser(connect,inputUser.getText().toString());

        if (u == null){
            Toast.makeText(this, "Usuario no existe", Toast.LENGTH_SHORT).show();
        } else {
            if (u.getPswd().equals(inputPswd.getText().toString())) {
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("user", u.getUserName());
                startActivity(intent);
            } else {
                Toast.makeText(this, "CONTRASEÑA INCORRECTA", Toast.LENGTH_SHORT).show();
            }
        }
    }
}