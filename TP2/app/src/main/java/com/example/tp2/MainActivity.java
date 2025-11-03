package com.example.tp2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextView textView; // time
    private TextView textView2; // random
    private TextView textView4; // resultat du mot
    private monTimer timer;
    private LinearLayout tableau;
    private GestionDB instanceV; // Pour la validation du mot
    private int tempScore = 0;
    private int score = 0;
    private int valeur = 0;
    private int multi = 0;
    private String concat = "";
    private boolean motdouble = false;
    private ArrayList<View> lettreUtilise = new ArrayList<>();
    private ArrayList<String> motUtilise = new ArrayList<>();

    private Context context;

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

        ArrayList<Integer> multiListe = grille.genererMulti();
        int indexMulti = 0;

        for (int i = 0; i < tableau.getChildCount(); i++) {
            LinearLayout temp = (LinearLayout) tableau.getChildAt(i);
            for (int j = 0; j < temp.getChildCount(); j++) {
                Composant mot = (Composant) temp.getChildAt(j);
                grille.genererLettres();
                mot.setTexteLettre(grille.lettreRand.getLettre());
                mot.setTexteValeur(grille.lettreRand.getPoints());
                if (indexMulti < multiListe.size()) {
                    mot.setTexteMulti(multiListe.get(indexMulti));
                    indexMulti++;
                }
                mot.setOnDragListener(ec);
                mot.setOnTouchListener(ec);
            }
        }

//        textView.setText(score); // Pour faire un random
        // Pour parcourir une liste, on pourra utiliser shuffle : Atelier 1 - ameliore
//        timer = new monTimer(10000L, 1000L);
//        timer.start();

    }

    @Override
    protected void onStart() {
        super.onStart();
        instanceV = GestionDB.getInstance(getApplicationContext());
        instanceV.ouvrirConnectionDB();
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
        @Override
        public boolean onDrag(View view, DragEvent dragEvent) {

            switch (dragEvent.getAction()) {
                case DragEvent.ACTION_DRAG_ENTERED:
                    textView4.setBackgroundColor(Color.WHITE);
                    if (view instanceof Composant && !lettreUtilise.contains(view)) {
                        Composant comp = (Composant) view;
                        lettreUtilise.add(view);
                        concat += comp.getTexteLettre().getText().toString();
                        valeur = Integer.parseInt(comp.getTexteValeur().getText().toString());
                        multi = Integer.parseInt(comp.getTexteMulti().getText().toString());

                        if (multi == 4) {
                            motdouble = true;
                            tempScore += valeur;;
                        }
                        else {
                            tempScore += valeur * multi;
                        }
                        textView4.setText(concat);
                    }
                    break;

                case DragEvent.ACTION_DROP: // faudrait mettre cela dans drop selon le prof
                    textView4.setText(concat);
                    if (motUtilise.contains(concat)){
                        textView4.setBackgroundColor(Color.MAGENTA);
                    }
                    else if (instanceV.verifMot(concat.toLowerCase(Locale.ROOT))) {
                        textView4.setBackgroundColor(Color.GREEN);
                        motUtilise.add(concat);
                        if (motdouble) {
                            score += tempScore * 2;
                        }
                        else {
                            score += tempScore;
                        }
                        textView.setText(String.valueOf(score));
                    }
                    else {
                        textView4.setBackgroundColor(Color.RED);
                    }

                    concat = "";
                    tempScore = 0;
                    multi = 0;
                    valeur = 0;
                    lettreUtilise.clear(); // pour reinitialiser la liste
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

    @Override
    protected void onStop() {
        super.onStop();
        instanceV.fermerConnectionDB();
    }







}