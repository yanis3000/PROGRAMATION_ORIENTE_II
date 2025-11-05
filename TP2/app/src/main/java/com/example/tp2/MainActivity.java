package com.example.tp2;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
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

    private TextView texteMots; // Permet d'afficher la concaténation de lettre produit par l'utilisateur
    private TextView textePoints; // Permet d'afficher le score
    private TextView texteTemps; // Permet d'afficher le temps
    private monTimer timer;
    private LinearLayout tableau; // LinearLayout qui englobe la grille de lettre
    private GestionDB instance;
    private LinearLayout main; // LinearLayout qui comporte tout, à remettre la couleur de base du background de texteMots
    private int tempScore = 0; // score temporaire qui permet d'accumuler les points avant la validation
    private int score = 0; // une fois valide, le score s'initialise
    private int points = 0; // point de la lettre
    private int multi = 0; // point du multiplicateur
    private String concat = ""; // Permet de concaténer les lettres
    private boolean motdouble = false;
    private ArrayList<View> lettreUtilise = new ArrayList<>(); // permet de ne pas réutiliser la même lettre pour la concaténatino
    private ArrayList<String> motUtilise = new ArrayList<>(); // permet de ne pas réutiliser le même mots pour avoir des points infini
    private SeekBar seekBar; // pour faire défiller le temps
    private long temps; // pour faire montrer le temps
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

        texteMots = findViewById(R.id.texteMots);
        textePoints = findViewById(R.id.textePoints);
        texteTemps =  findViewById(R.id.texteTemps);
        tableau = findViewById(R.id.tableau);
        seekBar = findViewById(R.id.seekBar);
        main = findViewById(R.id.main);

        Ecouteur ec = new Ecouteur();

        dureeTotale = 90000L; // Pour faire 1min30
        seekBar.setMax((int) (dureeTotale / 1000)); // nombre de secondes totales
        seekBar.setProgress((int) (dureeTotale / 1000)); // commence pleine

        timer = new monTimer(dureeTotale, 1000L); // On instancie le timer
        timer.start(); // on demarre le timer

        seekBar.setOnSeekBarChangeListener(ec);

        GrilleDeLettres grille = new GrilleDeLettres(); // on instancie une instance grille

        ArrayList<Integer> multiListe = grille.genererMulti(); // retourne la liste des multiscores générés
        int indexMulti = 0; // pour parcourir la liste

        for (int i = 0; i < tableau.getChildCount(); i++) {
            LinearLayout temp = (LinearLayout) tableau.getChildAt(i); // Selectionne les LinearLayouts horizontaux
            for (int j = 0; j < temp.getChildCount(); j++) {
                Composant mot = (Composant) temp.getChildAt(j); // Selectionne les 4 composants par LinearLayout
                grille.genererLettres(); // générer la méthode pour pour sélectionner une lettre au hasard
                mot.setTexteLettre(grille.lettreRand.getLettre());
                mot.setTexteValeur(grille.lettreRand.getPoints());
                if (indexMulti < multiListe.size()) {
                    mot.setTexteMulti(multiListe.get(indexMulti)); // pour chaque lettre, on met un multiplicateur random
                    indexMulti++;
                }
                mot.setOnDragListener(ec);
                mot.setOnTouchListener(ec);
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        instance = GestionDB.getInstance(getApplicationContext());
        instance.ouvrirConnectionDB();
        }     // Pas de onStop -- causait des problèmes, des crashs dans le onCreate du prochain activity


    private static class ShadowInvisible extends View.DragShadowBuilder { // Vient du prof

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
                    texteMots.setBackgroundColor(main.getSolidColor()); // remettre la couleur de base du background de texteMots
                    if (view instanceof Composant && !lettreUtilise.contains(view)) {
                        Composant comp = (Composant) view;
//                        comp.setBackgroundColor(Color.BLACK);

                        lettreUtilise.add(view); // pour s'assurer qu'on ne remet pas la meme lettre
                        concat += comp.getTexteLettre().getText().toString(); // mot qu'on forme

                        points = Integer.parseInt(comp.getTexteValeur().getText().toString());
                        try {
                            multi = Integer.parseInt(comp.getTexteMulti().getText().toString());
                        }
                        catch (Exception e) {
                            multi = 4;  // Si multi = "D", on la converti à 4
                        }


                        if (multi == 4) { // Si multi c'est un mot double
                            motdouble = true;
                            tempScore += points;;
                        }
                        else {
                            tempScore += points * multi;
                        }
                        texteMots.setText(concat); // pour écrire la concaténation
                    }
                    break;

                case DragEvent.ACTION_DROP: // faudrait mettre DROP au lieu de EXITED dans drop selon le prof
                    texteMots.setText(concat);
                    if (motUtilise.contains(concat)){ // Si le mot à déjà été utilisé, on l'affiche en bleu
                        texteMots.setBackgroundColor(Color.rgb(158, 7, 171));
                    }
                    else if (instance.verifMot(concat.toLowerCase(Locale.ROOT))) { // verification avec la base de données, en minuscule pour que ca fonctionne
                        texteMots.setBackgroundColor(Color.rgb(7, 171, 36)); // en vert
                        motUtilise.add(concat); // on ajoute le mot à la liste
                        if (motdouble) {
                            score += tempScore * 2;
                        }
                        else {
                            score += tempScore;
                        }
                        textePoints.setText("Nombres de points : " +  score);
                    }
                    else {
                        texteMots.setBackgroundColor(Color.rgb(171, 7, 34)); // Si ça ne fonctionne pas
                    }

                    // pour tout réinitialiser avant de vérifier la prochaine concaténation
                    concat = "";
                    tempScore = 0;
                    multi = 0;
                    points = 0;
                    lettreUtilise.clear();
                    break;
            }

            return true;
        }

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            ShadowInvisible shadow = new ShadowInvisible();  // vient du prof
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
        public void onTick(long millisUntilFinished) { // A chaque seconde qui s'ecoule
            temps = (millisUntilFinished / 1000);
            texteTemps.setText(temps + " s"); // Pour montrer le temps sur le textView
            seekBar.setProgress((int) temps); // sur le seekbar
        }

        @Override
        public void onFinish() {
            seekBar.setProgress(0);
            Score scoreInstance = new Score(score); // on ajoute un nouvel objet Score
            instance.ajouterScore(scoreInstance); // on la passe dans la base de données
            Intent i2 = new Intent(MainActivity.this, FinActivity.class );
            startActivity(i2); // on part a la fin de l'activite
        }
    }


}