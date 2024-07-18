package com.example.turnoinmvil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Turnos extends AppCompatActivity {

    Intent intent;
    LinkedList<String> lista;
    int turno;
    String turnoGuard;
    String turnoAnt;

    String [] turnoA;
    String [] turnoS;
    String [] turnoAn;

    TextView turnoActual;
    TextView turnoSiguiente;
    TextView turnoAnterior;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_turnos);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        intent = getIntent();
        lista = new LinkedList<String>( (ArrayList<String>) intent.getSerializableExtra("Lista") );
        turno = intent.getIntExtra("Turno", 0);
        if (intent.getStringExtra("TurnoAnt") != null){
            turnoAnt = intent.getStringExtra("TurnoAnt");
            turnoAn = turnoAnt.split("-");
        }else{
            turnoAn = new String[] {"No Existe", "No exite"};
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        try {


            turnoActual = (TextView) findViewById(R.id.nTurno);
            turnoSiguiente = (TextView) findViewById(R.id.nTurnoSig);
            turnoAnterior = (TextView) findViewById(R.id.nTurnoAnterior);

            turnoGuard = lista.poll();
            turnoA = turnoGuard.split("-");
            if(lista.size() > 0) {
                turnoS = lista.peek().split("-");
            }else{
                turnoS = new String[]{"No hay", "No hay"};
            }
            turnoActual.setText("Persona: " + turnoA[0] + "\n N turno " + turnoA[1]);
            turnoSiguiente.setText("Persona: " + turnoS[0] + "\n N turno " + turnoS[1]);
            turnoAnterior.setText("Persona: " + turnoAn[0] + "\n N turno " + turnoAn[1]);
        }catch (Exception e){
            Toast.makeText(this, "No existen turnos generados", Toast.LENGTH_LONG).show();

        }
    }

    /* Turno guard mantiene el poll de la lista, el primer elemento, para trabajar con el
    * En caso de que el usuario no quiera pasar a siguiente solamente se pone a turnoGuard como primer elemento de la lista*/
    public void siguienteTurno(View view){
        try {

            if(lista.size() > 0) {
                //Actualizacion de variables
                turnoS = lista.peek().split("-");
                turnoAn = turnoA; //El turno antiguo será el turno actual
                turnoAnt = turnoGuard; //El string sin estar en array

                turnoA = turnoS; //Mismo que lo de arriba solo que con el siguiente


                turnoGuard = lista.poll(); //turnoGuard tendrá el siguiente turno (el 2)
                turnoActual.setText("Persona: " + turnoA[0] + "\n N turno " + turnoA[1]);
                turnoAnterior.setText("Persona: " + turnoAn[0] + "\n N turno " + turnoAn[1]);
            }else {
                throw new NullPointerException();
            }

            turnoSiguiente.setText("Persona: " + turnoS[0] + "\n N turno " + turnoS[1]);


        } catch (NullPointerException e){
            Toast.makeText(this, "No existe un turno siguiente", Toast.LENGTH_SHORT).show();
            turnoSiguiente.setText("Persona: " + "---------" + "\n N turno " + "---------");
        }

    }

    private void ActualizarIntent(){
        intent.putExtra("Lista", (Serializable) lista);
        intent.putExtra("TurnoAnt", turnoAnt);
        if(turnoGuard != null) {
            lista.addFirst(turnoGuard);
        }
    }

    public void aTurnos(View view)
    {
        intent.setClass(this, activity_lista_turnos.class);
        ActualizarIntent();

        startActivity(intent);
    }

    public void AggTurno(View view){
        intent.setClass(this, activity_ingreso.class);
        ActualizarIntent();

        startActivity(intent);
    }
}