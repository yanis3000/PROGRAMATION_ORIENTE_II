package com.example.annexe3;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    ImageView imgBidon;
    ImageView imgBouteille;
    ImageView imgVerre;
    TextView textmL;
    ProgressBar progressBar;

    int quantity;

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

        // Initialisation des composants
        imgBidon = findViewById(R.id.imgBidon);
        imgBouteille = findViewById(R.id.imgBouteille);
        imgVerre = findViewById(R.id.imgVerre);
        textmL = findViewById(R.id.textemL);
        progressBar = findViewById(R.id.progressBar);

        //Créer un écouteur
        Ecouteur ec = new Ecouteur();

        imgBidon.setOnClickListener(ec);
        imgBouteille.setOnClickListener(ec);
        imgVerre.setOnClickListener(ec);

    }

    private class Ecouteur implements View.OnClickListener {
        @Override
        public void onClick(View source) {
            if (source == imgBidon) quantity += 1500;
            if (source == imgBouteille) quantity += 330;
            if (source == imgVerre) quantity += 150;

            progressBar.setProgress(quantity);

            textmL.setText(String.valueOf(quantity) + " ml");

        }

    }


}