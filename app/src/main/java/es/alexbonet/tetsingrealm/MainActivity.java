package es.alexbonet.tetsingrealm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import es.alexbonet.tetsingrealm.controller.UserController;
import es.alexbonet.tetsingrealm.db.DataBase;
import es.alexbonet.tetsingrealm.model.Empleado;
import es.alexbonet.tetsingrealm.model.Usuario;
import io.realm.Realm;

public class MainActivity extends AppCompatActivity {
    //TODO: tinc la idea vaig aanar fent pelis y probar el crud

    private UserController uc = new UserController();
    private TextView textView;
    private String userName;
    private Usuario u;
    private Realm connect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        connect = DataBase.getInstance().conectar(this);

        textView = findViewById(R.id.textView);

        userName = getIntent().getExtras().getString("user");

        if (userName != null){
            textView.setText("HOLA " + userName);
            u = uc.getUser(connect, userName);
        }


    }
}