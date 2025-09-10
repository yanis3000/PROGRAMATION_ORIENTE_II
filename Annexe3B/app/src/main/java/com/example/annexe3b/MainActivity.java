package com.example.annexe3b;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    ImageView imgHotel;
    ImageView imgAvion;
    ImageView imgPlage;
    TextView textAvion;
    TextView textHotel;
    Button total;
    TextView textTotal;

    int quantiteAvion = 0;
    int quantiteHotel = 0;



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

        imgAvion = findViewById(R.id.imgAvion);
        imgHotel = findViewById(R.id.imgHotel);
        imgPlage = findViewById(R.id.imgPlage);
        total = findViewById(R.id.total);
        textTotal = findViewById(R.id.totalText);
        textAvion = findViewById(R.id.textAvion);
        textHotel = findViewById(R.id.textHotel);


        Ecouteur ec = new Ecouteur();

        imgAvion.setOnClickListener(ec);
        imgHotel.setOnClickListener(ec);
        imgPlage.setOnClickListener(ec);
        total.setOnClickListener(ec);

    }

    private class Ecouteur implements View.OnClickListener {
        @Override
        public void onClick(View source) {

            if (source == imgAvion) {
                quantiteAvion += 1;
                textAvion.setText(String.valueOf(quantiteAvion));
            }

            if (source == imgHotel) {
                quantiteHotel += 1;
                textHotel.setText(String.valueOf(quantiteHotel));
            }

            if (source == total) {
                HebergementHotel h = new HebergementHotel(quantiteHotel);
                BilletAvion a = new BilletAvion(quantiteAvion);
                Commande c = new Commande();

                c.ajouterProduit(h);
                c.ajouterProduit(a);

                DecimalFormat df = new DecimalFormat("#.##$");

                textTotal.setText(String.valueOf(df.format(c.grandTotal())));

            }



        }

    }



}