package com.example.tp2;

import android.graphics.Canvas;
import android.graphics.Point;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    TextView textView; // time
    TextView textView2; // random
    TextView textView4; // resultat du mot
    monTimer timer;
    LinearLayout tableau;
    GestionDB instanceV; // Pour la validation du mot
    String concat = "";

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

        textView = findViewById(R.id.textView);
        textView2 = findViewById(R.id.textView2);
        textView4 = findViewById(R.id.textView4);
        tableau = findViewById(R.id.tableau);

        Ecouteur ec = new Ecouteur();

        GrilleDeLettres grille = new GrilleDeLettres();


        for (int i = 0; i < tableau.getChildCount(); i++) {
            LinearLayout temp = (LinearLayout) tableau.getChildAt(i);
            for (int j = 0; j < temp.getChildCount(); j++) {
                Composant mot = (Composant) temp.getChildAt(j);
                grille.genererLettres();
                mot.setTexteLettre(grille.lettreRand.getLettre());
                mot.setTexteValeur(grille.lettreRand.getPoints());
                mot.setOnDragListener(ec);
                mot.setOnTouchListener(ec);
            }
        }



        textView.setText(String.valueOf(Math.random())); // Pour faire un random
        // Pour parcourir une liste, on pourra utiliser shuffle : Atelier 1 - ameliore
        timer = new monTimer(10000L, 1000L);
        timer.start();


    }
    private static class ShadowInvisible extends View.DragShadowBuilder {

        @Override
        public void onProvideShadowMetrics(Point outShadowSize, Point outShadowTouchPoint) {
            // tout petit
            outShadowSize.set(1, 1);
            outShadowTouchPoint.set(0, 0);
        }

        @Override
        public void onDrawShadow(Canvas canvas) {
            // rien faire, on ne dessine rien
        }

    }


    private class Ecouteur implements View.OnTouchListener, View.OnDragListener {
        private View derniereVueEntered = null; // Pour éviter les doublons
        @Override
        public boolean onDrag(View view, DragEvent dragEvent) {

            switch (dragEvent.getAction()) {
                case DragEvent.ACTION_DRAG_ENTERED:
                    View source = (View) dragEvent.getLocalState();

                    if (source instanceof Composant) {
                        Composant comp = (Composant) source;
                        concat += comp.getTexteLettre().getText().toString();
                        textView4.setText(concat);
                    }
                    break;

                case DragEvent.ACTION_DROP: // faudrait mettre cela dans drop selon le prof
                    textView4.setText(concat);
                    break;
            }

            return true;
        }

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            ShadowInvisible shadow = new ShadowInvisible();
            view.startDragAndDrop(null, shadow, view, 512);
            return true;
        }
    }

    private class monTimer extends CountDownTimer{

        public monTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            textView2.setText("seconds remaining: " + millisUntilFinished / 1000);
        }

        @Override
        public void onFinish() {
            textView2.setText("done!");
        }
    }







}