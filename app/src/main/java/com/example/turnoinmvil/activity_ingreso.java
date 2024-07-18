package com.example.turnoinmvil;

import android.content.Intent;
import android.os.Build;
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
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.Collectors;


public class activity_ingreso extends AppCompatActivity {

    EditText cedula;
    LinkedList<String> lista;
    int turno;
    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ingreso);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        intent = getIntent();
        lista = new LinkedList<String>( (ArrayList<String>) intent.getSerializableExtra("Lista") );
        turno = intent.getIntExtra("Turno", 0);

    }

    @Override
    protected void onStart() {
        super.onStart();
        cedula = (EditText) findViewById(R.id.cedula);


    }

    private Boolean cedula_comprobation(char [] ced){
        int [] coeficientes = {2,1,2,1,2,1,2,1,2};
        int suma = 0;
        for(int i = 0; i < ced.length-1; i++){

            int num = Integer.parseInt(Character.toString(ced[i]));

            int operacion = num * coeficientes[i];

            System.out.println(operacion);

            suma = suma + (( operacion >=10 )  ? operacion - 9 : operacion);

            System.out.println(suma + " " + operacion + " " + num + " " + coeficientes[i]);
        }
        int digVerificador = (suma - (suma%10) + 10) == 10 ? 0 : (suma - (suma%10) + 10) - suma;

        return digVerificador == Integer.parseInt(Character.toString(ced[9]));

    }
    private void Error(){
        Toast.makeText(this, "No se ha Ingresado una Cedula o No es Válida", Toast.LENGTH_LONG).show();
    }
    public void Agregar(View view){
        System.out.println(cedula.getText());
        System.out.println( cedula.getText().toString().length());

        if (cedula.getText() == null || cedula.getText().toString().length() != 10) {
            Error();
            return;
        }

        char [] ced = cedula.getText().toString().toCharArray();
        Integer.parseInt(Character.toString(ced[0]) + Character.toString(ced[1]));
        if (!(Integer.parseInt(Character.toString(ced[0]) + Character.toString(ced[1])) < 24)){
            Error();
            return;
        }



        if (!cedula_comprobation(ced)){
            Error();
            return;
        }


        if (
                lista.stream()
                .filter(
                        c -> c.startsWith(cedula.getText().toString())
                ).count() > 0
        ){
            Toast.makeText(this, "Esta persona ya tiene turno!", Toast.LENGTH_LONG).show();
            return;
        }


        turno += 1;
        lista.add(cedula.getText().toString() + "-" + turno);
        intent.putExtra("Turno", turno);
        intent.putExtra("Lista", (Serializable) lista);
        Toast.makeText(this, "Turno n°" + turno + " generado de manera exitosa", Toast.LENGTH_SHORT).show();

    }


    public void verTurnos (View view){
        intent.setClass(this, Turnos.class);

        startActivity(intent);
    }

    public void verListaTurnos(View view){
        intent.setClass(this, activity_lista_turnos.class);
        startActivity(intent);
    }
}