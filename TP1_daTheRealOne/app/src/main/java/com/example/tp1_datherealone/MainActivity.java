package com.example.tp1_datherealone;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ImageView epaisseur;

    ConstraintLayout parent;

    int couleurCourante = Color.BLACK;
    int couleurBackground = Color.WHITE;

    TraceLibre traceLibre;
    Efface efface;
    ImageView imgTraceLibre;
    ImageView imgEfface;
    ChipGroup chipGroup;
    ArrayList<TraceLibre> dessin = new ArrayList<TraceLibre>();
    private TraceLibre traceLibreActuel;

    public Integer getColorBackground() {
        return couleurBackground;
    }

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

        parent = findViewById(R.id.parent);
        chipGroup = findViewById(R.id.ChipGroup);

        parent.setBackgroundColor(couleurBackground);

        epaisseur = findViewById(R.id.epaisseur);
        epaisseur.setOnClickListener(new Ecouteur());

        for (int i = 0; i < chipGroup.getChildCount(); i++) {
            ((Chip) chipGroup.getChildAt(i)).setOnClickListener(new Ecouteur());
        }

        imgTraceLibre = findViewById(R.id.traceLibre);
        imgEfface = findViewById(R.id.efface);

        imgTraceLibre.setOnClickListener(new Ecouteur());
        imgEfface.setOnClickListener(new Ecouteur());

    }

    private class Ecouteur implements View.OnClickListener{

        @Override
        public void onClick(View source) {
            if (source instanceof Chip) {
                Chip chip = (Chip) source;
                String couleur = (String) chip.getTag();
                couleurCourante = Color.parseColor(couleur);

                if (traceLibreActuel != null) {
                    traceLibreActuel.setCouleur(couleurCourante);
                }
            }

            if (source == imgTraceLibre) {
                traceLibre = new TraceLibre(MainActivity.this);
                traceLibre.setCouleur(couleurCourante);
                traceLibre.setLayoutParams(new LinearLayout.LayoutParams(-1, -1));
                dessin.add(traceLibre);
                parent.addView(traceLibre);

                traceLibreActuel = traceLibre;


            }

            if (source == imgEfface) {
                efface = new Efface(MainActivity.this);
                traceLibre.setLayoutParams(new LinearLayout.LayoutParams(-1, -1));
                parent.addView(efface);
            }

        }
    }
}