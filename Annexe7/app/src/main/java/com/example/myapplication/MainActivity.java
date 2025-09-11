package com.example.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;
import android.text.method.Touch;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {


    ConstraintLayout parent;
    SurfaceDessin sd;
    Point depart;
    Point fin;

    Point milieu;



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
            crayon.setColor(Color.RED);
            crayon.setStrokeWidth(15);

        }

        @Override
        protected void onDraw(@NonNull Canvas canvas) {
            super.onDraw(canvas);
                if (depart != null) {
                    canvas.drawRect(depart.x - 30, depart.y -30, depart.x + 30, depart.y + 30, crayon);
                    if (milieu != null) {
                        canvas.drawLine((depart.x) - 30, (depart.y) - 30, (milieu.x) - 30, (milieu.y) - 30, crayon);
                    }
                }

                if (fin != null) {
                    canvas.drawRect(fin.x - 30, fin.y -30, fin.x + 30, fin.y + 30, crayon);
                }

        }
    }
    private class Ecouteur implements View.OnTouchListener {
        public Ecouteur() {

        }

        @Override
        public boolean onTouch(View v, MotionEvent event) { // l'objet evenementiel
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                depart = new Point((int)event.getX(), (int)event.getY());
                fin = null;
                sd.invalidate(); // redessiner effaec et appele la foonc  on draw
            }

            if (event.getAction() == MotionEvent.ACTION_MOVE) {
                milieu = new Point((int)event.getX(), (int)event.getY());
                sd.invalidate(); // redessiner effaec et appele la foonc  on draw
            }

            if (event.getAction() == MotionEvent.ACTION_UP) {
                fin = new Point((int)event.getX(), (int)event.getY());
                sd.invalidate(); // redessiner effaec et appele la foonc  on draw
            }



            return true;
        }
    }



}