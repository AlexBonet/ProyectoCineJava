package es.alexbonet.tetsingrealm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

import es.alexbonet.tetsingrealm.model.Film;

public class CalendarActivity extends AppCompatActivity {

    private CalendarView calendario;
    private Button btnComprarHoy, btnCancelar;
    private Film film;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        //film = (Film) getIntent().getExtras().getSerializable("film");

        calendario = findViewById(R.id.calendarView);
        btnCancelar = findViewById(R.id.calendarbtnCancelar);
        btnComprarHoy = findViewById(R.id.calenadarbtnComprarHoy);

        //Poner el calendario a hoy???? TODO
        Date today = Calendar.getInstance().getTime();
        calendario.setDate(today.getTime());

        btnComprarHoy.setOnClickListener(view -> {
            Intent intent = new Intent(this, SecondActivity.class);
            intent.putExtra("dia",today);
            Toast.makeText(this, "DIA: " + today, Toast.LENGTH_SHORT).show();
            startActivity(intent);
        });

        btnCancelar.setOnClickListener(view -> {
            Intent intent = new Intent(this, SecondActivity.class);
            intent.putExtra("dia",calendario.getDate());
            Toast.makeText(this, "DIA: " + calendario.getDate(), Toast.LENGTH_SHORT).show();
            startActivity(intent);
        });



    }
}