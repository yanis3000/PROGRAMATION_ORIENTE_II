package com.example.tp2;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextView texteTitre; // time
    private TextView texteMots; // random
    private TextView textePoints; // resultat du mot
    private TextView texteTemps;
    private monTimer timer;
    private LinearLayout tableau;
    private GestionDB instance; // Pour la validation du mot
//    private ScoreDB instanceS;
    private LinearLayout main;
    private int tempScore = 0;
    private int score = 0;
    private int valeur = 0;
    private int multi = 0;
    private String concat = "";
    private boolean motdouble = false;
    private ArrayList<View> lettreUtilise = new ArrayList<>();
    private ArrayList<String> motUtilise = new ArrayList<>();
    private SeekBar seekBar;
    private long temps;
    private long dureeTotale;

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

        texteTitre = findViewById(R.id.texteTitre);
        texteMots = findViewById(R.id.texteMots);
        textePoints = findViewById(R.id.textePoints);
        texteTemps =  findViewById(R.id.texteTemps);
        tableau = findViewById(R.id.tableau);
        seekBar = findViewById(R.id.seekBar);
        main = findViewById(R.id.main);

        Ecouteur ec = new Ecouteur();

        dureeTotale = 90000L; // tu peux mettre 30000L = 30s ou 60000L = 1min
        seekBar.setMax((int) (dureeTotale / 1000)); // nombre de secondes totales
        seekBar.setProgress((int) (dureeTotale / 1000)); // commence pleine

        timer = new monTimer(dureeTotale, 1000L);
        timer.start();

        seekBar.setOnSeekBarChangeListener(ec);

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
        instance = GestionDB.getInstance(getApplicationContext());
        instance.ouvrirConnectionDB();

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


    private class Ecouteur implements View.OnTouchListener, View.OnDragListener, SeekBar.OnSeekBarChangeListener {
        @Override
        public boolean onDrag(View view, DragEvent dragEvent) {

            switch (dragEvent.getAction()) {
                case DragEvent.ACTION_DRAG_ENTERED:
                    texteMots.setBackgroundColor(main.getSolidColor());
                    if (view instanceof Composant && !lettreUtilise.contains(view)) {
                        Composant comp = (Composant) view;
                        comp.setBackgroundColor(Color.BLACK);

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
                        texteMots.setText(concat);
                    }
                    break;

                case DragEvent.ACTION_DROP: // faudrait mettre cela dans drop selon le prof
                    texteMots.setText(concat);
                    if (motUtilise.contains(concat)){
                        texteMots.setBackgroundColor(Color.rgb(158, 7, 171));
                    }
                    else if (instance.verifMot(concat.toLowerCase(Locale.ROOT))) {
                        texteMots.setBackgroundColor(Color.rgb(7, 171, 36));
                        motUtilise.add(concat);
                        if (motdouble) {
                            score += tempScore * 2;
                        }
                        else {
                            score += tempScore;
                        }
                        textePoints.setText("Nombres de points : " +  score);
                    }
                    else {
                        texteMots.setBackgroundColor(Color.rgb(171, 7, 34));
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

        @Override
        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    }

    private class monTimer extends CountDownTimer{

        public monTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            temps = (millisUntilFinished / 1000);
            texteTemps.setText(temps + " s");
            seekBar.setProgress((int) temps);
        }

        @Override
        public void onFinish() {
            seekBar.setProgress(0);
            Score scoreInstance = new Score(score);
            instance.ajouterScore(scoreInstance);
            Intent i2 = new Intent(MainActivity.this, FinActivity.class );
            startActivity(i2);
        }
    }

//    @Override
//    protected void onStop() {
//        super.onStop();
//        instance.fermerConnectionDB();
//    }


}