package com.example.annexe13;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class EvaluationUsager extends AppCompatActivity {

    TextView biere;
    TextView microbrasserie;
    EditText textBiere;
    EditText textMicro;
    RatingBar ratingBar;
    Button enregistrer;
    GestionDB instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_evaluation_usager);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        biere = findViewById(R.id.biere);
        microbrasserie = findViewById(R.id.microbrasserie);
        textBiere = findViewById(R.id.textBiere);
        textMicro = findViewById(R.id.textMicro);
        ratingBar = findViewById(R.id.ratingBar);
        enregistrer = findViewById(R.id.enregistrer);


        Ecouteur ec = new Ecouteur();

        ratingBar.setOnClickListener(ec);
        enregistrer.setOnClickListener(ec);
    }

    //Strinf message - getIntent.getStringExtra("name")
    //Toast toat

    @Override
    protected void onStart() {
        super.onStart();
        instance = GestionDB.getInstance(getApplicationContext()); // dans l'idee que c'est un singleton
        instance.ouvrirConnectionDB();
    }

    private class Ecouteur implements View.OnClickListener
    {

        @Override
        public void onClick(View source) {

            if (enregistrer == source) {
                if (!textBiere.toString().isBlank() && !textMicro.toString().isBlank()) {
                    Evaluation ev = new Evaluation(textBiere.getText().toString(), textMicro.getText().toString(), ratingBar.getNumStars());
                    instance.ajouterBiere(ev);
                }

                finish();

            }




        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        instance.fermerConnectionDB();
    }
}