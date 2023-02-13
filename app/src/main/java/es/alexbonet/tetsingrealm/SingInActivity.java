package es.alexbonet.tetsingrealm;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import es.alexbonet.tetsingrealm.controller.UserController;
import es.alexbonet.tetsingrealm.db.DataBase;
import es.alexbonet.tetsingrealm.model.UserType;
import es.alexbonet.tetsingrealm.model.Usuario;
import io.realm.Realm;

public class SingInActivity extends AppCompatActivity {

    private EditText inputDni, inputNombre, inputApell, inputUserName, inputPswd, inputConfPswd;
    private Button btnRegist, btnIrLogIn;
    private Realm connect;
    private UserController userController = new UserController();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_in);

        connect = DataBase.getInstance().conectar(this);

        inputDni = findViewById(R.id.inputReDni);
        inputNombre = findViewById(R.id.inputReNombre);
        inputApell = findViewById(R.id.inputReApellido);
        inputUserName = findViewById(R.id.inputReUserName);
        inputPswd = findViewById(R.id.inputRePswd);
        inputConfPswd = findViewById(R.id.inputReConfirmPswd);
        btnRegist = findViewById(R.id.btnRegistrarse);
        btnIrLogIn = findViewById(R.id.btnIrALogIn);

        btnIrLogIn.setOnClickListener(view -> {
            Intent intent = new Intent(this, LogInActivity.class);
            startActivity(intent);
        });

        btnRegist.setOnClickListener(view -> {
            registrar();
        });
    }

    private void registrar() {
        String dni = inputDni.getText().toString();
        String nom = inputDni.getText().toString();
        String ape = inputDni.getText().toString();
        String user = inputDni.getText().toString();
        String pswd = inputDni.getText().toString();
        String cpswd = inputDni.getText().toString();

        if (dni.isEmpty() || nom.isEmpty() || ape.isEmpty() || user.isEmpty() || pswd.isEmpty() || cpswd.isEmpty()) { // QUE NO HAYA NINGUN CAMPO VACIO
            Toast.makeText(this, "CAMPOS VACIOS", Toast.LENGTH_SHORT).show();
        } else {
            if (userController.getUser(connect, user) != null) { // QUE NO HAYA OTRO USUARIO CON EL MISMO NOMBRE DE USUARIO
                Toast.makeText(this, "El nombre de usuario ya existe", Toast.LENGTH_SHORT).show();
            } else if (userController.getFromDNI(connect,dni) != null) { // QUE NO HAYA OTRO USUARIO CON EL MISMO DNI
                Toast.makeText(this, "El DNI del usuario ya existe", Toast.LENGTH_SHORT).show();
            } else {
                if (pswd.equals(cpswd)) { // SI LAS CONTRASEÑAS COINCIDEN
                    //TODO PETA
                    userController.createUser(connect, new Usuario(dni,nom,ape,user,pswd, UserType.CLIENTE.getString())); // CREA EL USUARIO
                    Intent intent = new Intent(this,MainActivity.class);
                    intent.putExtra("user", user);
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "Las CONTRASEÑAS no coinciden", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}