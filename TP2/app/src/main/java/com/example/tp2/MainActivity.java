package com.example.tp2;

import static android.view.View.VISIBLE;

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

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    TextView textView; // time
    TextView textView2; // random
    TextView textView4; // resultat du mot
    monTimer timer;
    LinearLayout tableau;
    String mot;


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


        for (int i = 0; i < tableau.getChildCount(); i++) {
            LinearLayout temp = (LinearLayout) tableau.getChildAt(i);
            for (int j = 0; j < temp.getChildCount(); j++) {
                Composant mot = (Composant) temp.getChildAt(j);
                mot.setOnDragListener(ec);
                mot.setOnTouchListener(ec);
            }
        }




        textView.setText(String.valueOf(Math.random())); // Pour faire un random
        // Pour parcourir une liste, on pourra utiliser shuffle : Atelier 1 - ameliore
        timer = new monTimer(10000L, 1000L);
        timer.start();

        HashMap<Character, Lettre> lettres = new HashMap<>();

        lettres.put('A', new Lettre('A', 9, 1));
        lettres.put('B', new Lettre('B',2, 3));
        lettres.put('C', new Lettre('C',2, 3));
        lettres.put('D', new Lettre('D',3, 2));
        lettres.put('E', new Lettre('E',15, 1));
        lettres.put('F', new Lettre('F',2, 4));
        lettres.put('G', new Lettre('G',2, 2));
        lettres.put('H', new Lettre('H',2, 4));
        lettres.put('I', new Lettre('I',8, 1));
        lettres.put('J', new Lettre('J',1, 8));
        lettres.put('K', new Lettre('K',1, 10));
        lettres.put('L', new Lettre('L',5, 1));
        lettres.put('M', new Lettre('M',3, 2));
        lettres.put('N', new Lettre('N',6, 1));
        lettres.put('O', new Lettre('O',6, 1));
        lettres.put('P', new Lettre('P',2, 3));
        lettres.put('Q', new Lettre('Q',1, 8));
        lettres.put('R', new Lettre('R',6, 1));
        lettres.put('S', new Lettre('S',6, 1));
        lettres.put('T', new Lettre('T',6, 1));
        lettres.put('U', new Lettre('U',6, 1));
        lettres.put('V', new Lettre('V',2, 4));
        lettres.put('W', new Lettre('W',1, 10));
        lettres.put('X', new Lettre('X',1, 10));
        lettres.put('Y', new Lettre('Y',1, 10));
        lettres.put('Z', new Lettre('Z',1, 10));

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
                    View source = (View) dragEvent.getLocalState();
                    if (source instanceof Composant) {
                        Composant comp = (Composant) source;
                        mot += comp.getTexteLettre().getText().toString();


                    }
                    break;



                case DragEvent.ACTION_DRAG_EXITED:
                    textView4.setText(mot);
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