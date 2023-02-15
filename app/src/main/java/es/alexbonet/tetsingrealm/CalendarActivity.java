package es.alexbonet.tetsingrealm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

import es.alexbonet.tetsingrealm.db.DataBase;
import es.alexbonet.tetsingrealm.model.Film;

public class CalendarActivity extends AppCompatActivity {

    private CalendarView calendario;
    private Button btnComprarHoy, btnCancelar;
    private String titulo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        titulo = getIntent().getExtras().getString("film");

        calendario = findViewById(R.id.calendarView);
        btnCancelar = findViewById(R.id.calendarbtnCancelar);
        btnComprarHoy = findViewById(R.id.calenadarbtnComprarHoy);

        //Poner el calendario a hoy???? TODO
        Date today = Calendar.getInstance().getTime();
        calendario.setDate(today.getTime());

        calendario.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                Intent intent = new Intent(getApplicationContext(), SesionsDispoActivity.class);
                intent.putExtra("film", titulo); // PASEM EL NOM DE LA PELI
                intent.putExtra("tdia", 1); // TODO LLEVAR ESTO ES DE PROBA
                intent.putExtra("fecha", dayOfMonth + "/" + month + "/" + year); // PASA UN STRING DEL DIA
                intent.putExtra("dia", dayOfMonth);
                intent.putExtra("mes", month);
                intent.putExtra("any", year);
                Toast.makeText(getApplicationContext(), "DIA: " + dayOfMonth, Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });

        //TODO COM FAIG YO PER A QUE CADA
        btnComprarHoy.setOnClickListener(view -> {
            Intent intent = new Intent(this, SesionsDispoActivity.class);
            intent.putExtra("tdia", 2);
            intent.putExtra("dia", today.getTime());
            Toast.makeText(this, "DIA: " + new Date(today.getTime()), Toast.LENGTH_SHORT).show();
            startActivity(intent);
        });

        btnCancelar.setOnClickListener(view -> {
            Intent intent = new Intent(this, DetFilmActivity.class);
            intent.putExtra("titulo", titulo); //LI TORNE EL TITULO DE LA PELI
            //TODO PUT EXTRAS???? ns si añadir més, crec q no
            startActivity(intent);
        });



    }
}