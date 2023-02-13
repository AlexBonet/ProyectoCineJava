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

public class AddUserActivity extends AppCompatActivity {

    private EditText inputDni, inputNombre, inputApell, inputUserName, inputPswd, inputConfPswd;
    private Button btnRegist, btnAECancelar;
    private Realm connect;
    private UserController userController = new UserController();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        connect = DataBase.getInstance().conectar(this);

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
            if (userController.getUser(connect, user) != null) { // QUE NO HAYA OTRO USUARIO CON EL MISMO NOMBRE DE USUARIO
                Toast.makeText(this, "El nombre de usuario ya existe", Toast.LENGTH_SHORT).show();
            } else if (userController.getFromDNI(connect,dni) != null) { // QUE NO HAYA OTRO USUARIO CON EL MISMO DNI
                Toast.makeText(this, "El DNI del usuario ya existe", Toast.LENGTH_SHORT).show();
            } else {
                if (pswd.equals(cpswd)) { // SI LAS CONTRASEÑAS COINCIDEN
                    userController.createUser(connect, new Usuario(dni,nom,ape,user,pswd, UserType.EMPLEADO.getString())); // CREA EL USUARIO
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