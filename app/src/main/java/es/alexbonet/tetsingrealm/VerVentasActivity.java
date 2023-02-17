package es.alexbonet.tetsingrealm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import java.util.List;

import es.alexbonet.tetsingrealm.RecyclerView.FilmsRVAdapter;
import es.alexbonet.tetsingrealm.RecyclerView.VentaRVAdapter;
import es.alexbonet.tetsingrealm.db.Controller;
import es.alexbonet.tetsingrealm.db.DataBase;
import es.alexbonet.tetsingrealm.model.Venta;
import io.realm.Realm;

public class VerVentasActivity extends AppCompatActivity {

    private final Controller c = new Controller();
    private Realm connect;
    private RecyclerView recyclerView;
    private VentaRVAdapter ventaRVAdapter;
    private Button btnSalir;
    private String username;
    private List<Venta> ventas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_ventas);

        connect = DataBase.getInstance().conectar(this);
        username = getIntent().getExtras().getString("user");
        ventas = c.getAllVentas(connect);

        btnSalir = findViewById(R.id.ventasbtnSalir);
        recyclerView = findViewById(R.id.RVVENTAS);

        ventaRVAdapter = new VentaRVAdapter(this, ventas);
        recyclerView.setAdapter(ventaRVAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        btnSalir.setOnClickListener(view -> {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("user",username);
            startActivity(intent);
        });

    }
}