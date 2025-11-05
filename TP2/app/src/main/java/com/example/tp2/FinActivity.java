package com.example.tp2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class FinActivity extends AppCompatActivity {

    private ListView listView;
    private GestionDB instance;
    private Button boutonRejouer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_fin);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        instance = GestionDB.getInstance(getApplicationContext()); // NE FONCTIONNAIT PAS DANS LE ONSTART, LE ONCREATE s'affiche avant le ONSTART
        instance.ouvrirConnectionDB();


        boutonRejouer = findViewById(R.id.boutonRejouer);
        listView = findViewById(R.id.listView);


        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, instance.afficherScores()); // on fait la liste

        listView.setAdapter(adapter);

        Ecouteur ec = new Ecouteur();

        boutonRejouer.setOnClickListener(ec);


    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onStop() {
        super.onStop();
        instance.fermerConnectionDB();
    }


    private class Ecouteur implements View.OnClickListener {

        Intent i1 = new Intent(FinActivity.this, MainActivity.class); // Pour rejouer

        @Override
        public void onClick(View source) {
            if (source == boutonRejouer) {
                startActivity(i1);
            }
        }
    }

}



