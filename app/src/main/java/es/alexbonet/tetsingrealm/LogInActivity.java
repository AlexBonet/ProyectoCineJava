package es.alexbonet.tetsingrealm;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.UUID;

import es.alexbonet.tetsingrealm.controller.UserController;
import es.alexbonet.tetsingrealm.db.DataBase;
import es.alexbonet.tetsingrealm.model.Usuario;
import es.alexbonet.tetsingrealm.model.UserType;
import io.realm.Realm;

public class LogInActivity extends AppCompatActivity {

    private EditText inputUser, inputPswd;
    private Button btnLogIn, btnCrearCuenta;
    private Realm connect;
    private UserController userController = new UserController();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        connect = DataBase.getInstance().conectar(this);

        inputUser = findViewById(R.id.inputLogUser);
        inputPswd = findViewById(R.id.inputLogPswd);
        btnLogIn = findViewById(R.id.btnLogIn);
        btnCrearCuenta = findViewById(R.id.btnCrearCuenta);

        long cuantos = connect.where(Usuario.class).count();
        if (cuantos == 0){
            try {
                Usuario u = new Usuario(
                        UUID.randomUUID().toString(),
                        "DNIADMIN",
                        "admin",
                        "admin",
                        "admin",
                        "admin",
                        UserType.ADMINISTRADOR.toString()
                );
                connect.beginTransaction();
                connect.copyToRealm(u);
                connect.commitTransaction();
            }catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "ERROR", Toast.LENGTH_SHORT).show();
            }
        }

        btnLogIn.setOnClickListener(view -> {
            login();
        });

        btnCrearCuenta.setOnClickListener(view -> {
            Intent intent = new Intent(this, SingInActivity.class);
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