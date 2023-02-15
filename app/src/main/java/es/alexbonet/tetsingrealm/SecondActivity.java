package es.alexbonet.tetsingrealm;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Date;

public class SecondActivity extends AppCompatActivity {

    private TextView textView;
    private String diaS;
    private long diaL;
    private Date dia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        textView = findViewById(R.id.textviewSecondA);

        if (getIntent().getExtras().getInt("tdia") == 1){
            diaS = getIntent().getExtras().getString("dia");
            textView.setText("DIA:\n " + diaS);
        } else { //DIA DE HOY
            diaL = getIntent().getExtras().getLong("dia");
            dia = new Date(diaL);
            textView.setText("DIA:\n " + dia + "\n" + diaL);
        }




    }
}