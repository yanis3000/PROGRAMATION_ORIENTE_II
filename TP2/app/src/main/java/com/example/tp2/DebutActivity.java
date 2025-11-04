package com.example.tp2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DebutActivity extends AppCompatActivity {

    private GestionDB instance;
    private TextView texteTitre, texteScore;
    private Button boutonJouer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_debut_jeu);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        texteTitre = findViewById(R.id.texteTitre);
        texteScore = findViewById(R.id.texteScore);
        boutonJouer = findViewById(R.id.boutonJouer);

        Ecouteur ec = new Ecouteur();

        boutonJouer.setOnClickListener(ec);

    }

    @Override
    protected void onStart() {
        super.onStart();
        instance = GestionDB.getInstance(getApplicationContext());
        instance.ouvrirConnectionDB();

        texteScore.setText("HIGH SCORE : " + instance.afficherScoreMax());
    }

    private class Ecouteur implements View.OnClickListener {

        Intent i1 = new Intent(DebutActivity.this, MainActivity.class );

        @Override
        public void onClick(View source) {
            if (source == boutonJouer) {
                startActivity(i1);
            }
        }
        }
    }