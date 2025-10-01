package com.example.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {


    ConstraintLayout parent;
    SurfaceDessin sd;
    Point depart1, ligne1;
    Point depart2, ligne2;
    int firstLine = 0;



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

        parent = findViewById(R.id.main);

        sd = new SurfaceDessin(this);
        sd.setLayoutParams(new ConstraintLayout.LayoutParams(-1, -1));
        sd.setBackgroundResource(R.drawable.carte);
        parent.addView(sd);

        Ecouteur ec = new Ecouteur();

        sd.setOnTouchListener(ec);

    }

    private class SurfaceDessin extends View {
        Paint crayon;

        public SurfaceDessin(Context context) {
            super(context);
            crayon = new Paint(Paint.ANTI_ALIAS_FLAG);
            crayon.setColor(Color.BLACK);
            crayon.setStrokeWidth(15);


        }

        @Override
        protected void onDraw(@NonNull Canvas canvas) {
            super.onDraw(canvas);

            if (ligne1 != null) {
                canvas.drawLine((depart1.x), (depart1.y), (ligne1.x), (ligne1.y), crayon);
            }

            if (ligne2 != null) {
                canvas.drawLine((depart2.x), (depart2.y), (ligne2.x), (ligne2.y), crayon);
            }

            if (ligne1 != null && ligne2 != null) {
                canvas.drawLine(ligne2.x, ligne2.y, depart1.x, depart1.y, crayon);
            }

        }
    }
    private class Ecouteur implements View.OnTouchListener {
        public Ecouteur() {
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) { // l'objet evenementiel

            int x = (int) event.getX();
            int y = (int) event.getY();

            if (firstLine == 2) {
                depart1 = null;
                depart2 = null;
                ligne1 = null;
                ligne2 = null;
                firstLine = 0;
            }

            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                if (firstLine == 0 || firstLine == 2) {
                    depart1 = new Point(x, y);
                    firstLine = 0;
                }
                else if (firstLine == 1) {
                    depart2 = ligne1;
                }
            }
            if (event.getAction() == MotionEvent.ACTION_MOVE) {
                if (firstLine == 0) {
                    ligne1 = new Point(x, y);
                } else if (firstLine == 1) {
                    ligne2 = new Point(x, y);
                }
                sd.invalidate();
            }

            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (firstLine == 0 || firstLine == 1) {
                    firstLine++;
                }
                sd.invalidate();
            }

            return true;
        }
    }




}