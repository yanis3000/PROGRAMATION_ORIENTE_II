package com.example.annexe2;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    Button boutonValider;
    Button boutonEnvoyer;
    TextView textDe;
    TextView textA;
    TextView textTransfert;
    TextView textSolde;



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

        boutonEnvoyer.findViewById(R.id.boutonEnvoyer);
        boutonValider.findViewById(R.id.boutonValider);
        textDe.findViewById(R.id.textDe);
        textA.findViewById(R.id.textA);
        textTransfert.findViewById(R.id.textTransfert);
        textSolde.findViewById(R.id.textSolde);

        Ecouteur ec = new Ecouteur();

        boutonEnvoyer.setOnClickListener(ec);
        boutonValider.setOnClickListener(ec);

    }

    private class Ecouteur implements View.OnClickListener {
        @Override
        public void onClick(View source) {

            if (source == boutonValider) {
                if (textDe.getText().toString().equals("Epargne"));

            }

        }

    }



}