package com.example.turnoinmvil;

import android.content.Intent;
import android.graphics.Color;
import android.icu.text.MeasureFormat;
import android.os.Bundle;
import android.util.Size;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;

public class activity_lista_turnos extends AppCompatActivity {

    TextView texto;
    ScrollView scroll;
    Intent intent;
    LinearLayout scrollLayout;
    LinkedList<String> lista;
    String turnoAnt;

    int turno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_lista_turnos);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        intent = getIntent();
        lista = new LinkedList<String>( (ArrayList<String>) intent.getSerializableExtra("Lista") );
        turno = intent.getIntExtra("Turno", 0);
        turnoAnt = intent.getStringExtra("TurnoAnt");


    }

    @Override
    protected void onStart() {
        super.onStart();
        scroll = (ScrollView) findViewById(R.id.scrollView2);

        scrollLayout = (LinearLayout) scroll.getChildAt(0);

        LinkedList<String> provicional = new LinkedList<>(lista);

        while(!provicional.isEmpty()){
            String [] t = provicional.poll().split("-");

            texto = new TextView(this);

            texto.setTextColor(getResources().getColor(R.color.white));
            texto.setGravity(Gravity.CENTER);
            texto.setTextSize(TypedValue.COMPLEX_UNIT_PX, 50);

            texto.setText("Persona: " + t[0] + "\n N turno " + t[1] +"\n"+"\n");

            scrollLayout.addView(texto);
        }

    }



    private void ActualizarIntent(){
        intent.putExtra("Lista", (Serializable) lista);
        intent.putExtra("TurnoAnt", turnoAnt);

    }

    public void Limpiar(View view){
        lista.clear();
        turno = 0;
        turnoAnt = "";
        ActualizarIntent();
        intent.putExtra("Turno", turno);
        intent.setClass(this, activity_lista_turnos.class);
        startActivity(intent);


    }


    public void aTurno(View view){
        intent.setClass(this, Turnos.class);
        ActualizarIntent();

        startActivity(intent);
    }

    public void aAgregar(View view){
        intent.setClass(this, activity_ingreso.class);
        ActualizarIntent();

        startActivity(intent);
    }


}