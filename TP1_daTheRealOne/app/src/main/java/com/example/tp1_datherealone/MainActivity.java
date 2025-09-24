package com.example.tp1_datherealone;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
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

    SurfaceDessin sd;
    ConstraintLayout parent;

    int couleurCourante = Color.BLACK;
    int couleurBackground = Color.WHITE;

    TraceLibre traceLibre;
    Efface efface;
    ImageView imgTraceLibre;
    ImageView imgEfface;
    ChipGroup chipGroup;
    ArrayList<TraceLibre> dessin = new ArrayList<TraceLibre>();
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

        sd = new SurfaceDessin(this);
        sd.setLayoutParams(new LinearLayout.LayoutParams(-1, -1));
        sd.setBackgroundColor(Color.WHITE);
        parent.addView(sd);


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

        EcouteurSurface es = new EcouteurSurface();

        sd.setOnTouchListener(es);
    }

    private class SurfaceDessin extends View {
        Paint crayon;
        Path path;

        public SurfaceDessin(Context context) {
            super(context);
            crayon = new Paint(Paint.ANTI_ALIAS_FLAG);
            crayon.setStyle(Paint.Style.STROKE);
            path = new Path();
        }


        @Override
        protected void onDraw(@NonNull Canvas canvas) {
            super.onDraw(canvas);

            for (TraceLibre traceLibre : dessin) {
                traceLibre.dessiner(canvas);
            }

            if (traceLibre != null) {
                traceLibre.dessiner(canvas);
            }
        }

//        @Override
//        public boolean onTouchEvent(MotionEvent event) {
//
//            float x = event.getX();
//            float y = event.getY();
//
//            if (event.getAction() == MotionEvent.ACTION_DOWN ) {
//                path.moveTo(x, y);
//            }
//            if (event.getAction() == MotionEvent.ACTION_MOVE) {
//                path.lineTo(x, y);
//            }
////            if (event.getAction() == MotionEvent.ACTION_UP) {
////            }
//
//            invalidate();
//
//            return true;
//        }
    }

    private class EcouteurSurface implements View.OnTouchListener {


        @Override
        public boolean onTouch(View v, MotionEvent event) {

            float x = event.getX();
            float y = event.getY();

            if (event.getAction() == MotionEvent.ACTION_DOWN ) {
                traceLibre = new TraceLibre(couleurCourante);
                traceLibre.path.moveTo(x, y);
            }
            if (event.getAction() == MotionEvent.ACTION_MOVE) {
                traceLibre.path.lineTo(x, y);
            }
            if (event.getAction() == MotionEvent.ACTION_UP) {
                dessin.add(traceLibre);
            }

            sd.invalidate();

            return true;
        }
    }

    private class Ecouteur implements View.OnClickListener{

        @Override
        public void onClick(View source) {
            if (source instanceof Chip) {
                Chip chip = (Chip) source;
                String couleur = (String) chip.getTag();
                couleurCourante = Color.parseColor(couleur);

                if (traceLibre != null) {
                    traceLibre.setCouleur(couleurCourante);
                }
            }

//            if (source == imgTraceLibre) {
//                traceLibre = new TraceLibre(couleurCourante);
//            }

//            if (source == imgEfface) {
//                efface = new Efface(MainActivity.this, couleurBackground);
//                traceLibre.setLayoutParams(new LinearLayout.LayoutParams(-1, -1));
//                parent.addView(efface);
//            }

        }
    }
}