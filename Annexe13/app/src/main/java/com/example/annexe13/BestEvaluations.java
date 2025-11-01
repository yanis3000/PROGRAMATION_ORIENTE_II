package com.example.annexe13;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class BestEvaluations extends AppCompatActivity {

    GestionDB instance;
    ListView listView;
    Button bouton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_best_evaluations);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        listView = findViewById(R.id.listView);
        bouton = findViewById(R.id.bouton);

        Ecouteur ec = new Ecouteur();

        bouton.setOnClickListener(ec);
    }

    @Override
    protected void onStart() {
        super.onStart();
        instance = GestionDB.getInstance(getApplicationContext()); // dans l'idee que c'est un singleton
        instance.ouvrirConnectionDB();  // this foncitonne aussi
//        ArrayAdapter adapter = null;
//        try {
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, instance.retournerMeilleurs());
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
        listView.setAdapter(adapter);
    }

    private class Ecouteur implements View.OnClickListener
    {

        @Override
        public void onClick(View v) {

            if (v == bouton) {
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