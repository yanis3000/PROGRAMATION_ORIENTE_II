package com.example.annexe13;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {


    Button resultats;
    Button evaluation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        resultats = findViewById(R.id.resultats);
        evaluation = findViewById(R.id.evaluation);


        Ecouteur ec = new Ecouteur();

        resultats.setOnClickListener(ec);
        evaluation.setOnClickListener(ec);

    }

    private class Ecouteur implements View.OnClickListener
    {

        @Override
        public void onClick(View source) {

            Intent i1 = new Intent(MainActivity.this, EvaluationUsager.class );
            Intent i2 = new Intent(MainActivity.this, BestEvaluations.class );

            if (source == resultats) {
                startActivity(i2);
            }

            if (source == evaluation) {
                startActivity(i1);
            }

        }

    }



}