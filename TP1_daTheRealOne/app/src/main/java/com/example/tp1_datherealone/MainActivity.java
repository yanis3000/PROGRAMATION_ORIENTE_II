package com.example.tp1_datherealone;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.view.MotionEvent;
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

    SurfaceDessin sd;
    ConstraintLayout parent;

    boolean dessineIsTrue;
    int dessineOuForme = 1;
    int cercleOuRect = 0;
    int option = 1;
    int couleurChoisi;
    int progressChoisi = 15;
    int couleurCourante = Color.BLACK;
    int couleurBackground = Color.WHITE;
    LargeurTraitDialogue dialogue;
    TraceLibre traceLibre;
    Efface efface;
    Oval rectangle;
    MotionEvent event;
//    Efface efface;
    ImageView imgTraceLibre;
    ImageView imgEfface;
    ImageView imgEpaisseur;
    ImageView imgRemplir;
    ImageView imgPipette;
    ImageView imgRectangle;
    ImageView imgCercle;
    ImageView imgTriangle;
    ChipGroup chipGroup;
    Bitmap bitmapImage;

    ArrayList<SuperPaint> superPaintListe = new ArrayList<SuperPaint>();
    ArrayList<Efface> effaceListe = new ArrayList<Efface>();
    ArrayList<TraceLibre> dessinTrace = new ArrayList<TraceLibre>();

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
        sd.setBackgroundColor(couleurBackground);
        parent.addView(sd);

        chipGroup = findViewById(R.id.ChipGroup);
        parent.setBackgroundColor(couleurBackground);

        for (int i = 0; i < chipGroup.getChildCount(); i++) {
            ((Chip) chipGroup.getChildAt(i)).setOnClickListener(new Ecouteur());
        }

        imgTraceLibre = findViewById(R.id.traceLibre);
        imgEfface = findViewById(R.id.efface);
        imgEpaisseur = findViewById(R.id.epaisseur);
        imgRemplir = findViewById(R.id.remplir);
        imgPipette = findViewById(R.id.pipette);
        imgRectangle = findViewById(R.id.rectangle);
        imgCercle = findViewById(R.id.cercle);
        imgTriangle = findViewById(R.id.triangle);

        EcouteurSurfaceTrace es = new EcouteurSurfaceTrace();
        Ecouteur ec = new Ecouteur();

        imgTraceLibre.setOnClickListener(ec);
        imgEfface.setOnClickListener(ec);
        imgEpaisseur.setOnClickListener(ec);
        imgRemplir.setOnClickListener(ec);
        imgPipette.setOnClickListener(ec);
        imgRectangle.setOnClickListener(ec);
        imgCercle.setOnClickListener(ec);
        imgTriangle.setOnClickListener(ec);

        sd.setOnTouchListener(es);
        sd.setOnClickListener(ec);
    }

    private class SurfaceDessin extends View {
        Paint crayon;
        Path path;

        public SurfaceDessin(Context context) {
            super(context);
            crayon = new Paint(Paint.ANTI_ALIAS_FLAG);
            crayon.setStyle(Paint.Style.STROKE);
            crayon.setStrokeWidth(progressChoisi);
            crayon.setColor(couleurCourante);
            path = new Path();
        }

        public Bitmap getBitmapImage() {
            this.buildDrawingCache();
            bitmapImage = Bitmap.createBitmap(this.getDrawingCache());
            this.destroyDrawingCache();
            return bitmapImage;
        }

        @Override
        protected void onDraw(@NonNull Canvas canvas) {
            super.onDraw(canvas);

            // On met tous sur une liste pour qu'on puisse avoir tous les elems

            for (SuperPaint superPaint : superPaintListe) {
                superPaint.dessiner(canvas);
            }

//            for (TraceLibre traceLibre : dessinTrace) {
//                traceLibre.dessiner(canvas);
//            }

//            for (Efface efface : effaceListe) {
//                if (efface.couleur != couleurBackground) efface.couleur = couleurBackground;
//                efface.dessiner(canvas);
//            }

            // faut faire le draw circle

//            if (rectangle != null) {
//                rectangle.dessiner(canvas);
//            }

            if (traceLibre != null) {
                traceLibre.dessiner(canvas);
            }

            if (efface != null) {
                efface.dessiner(canvas);
            }

        }
    }

    private class EcouteurSurfaceTrace implements View.OnTouchListener {

        @Override
        public boolean onTouch(View v, MotionEvent event) {

            float x = event.getX();
            float y = event.getY();

            switch (option) {
                case 1 : {
                    if (event.getAction() == MotionEvent.ACTION_DOWN ) {
                        traceLibre = new TraceLibre(couleurCourante, progressChoisi);
                        traceLibre.path.moveTo(x, y);
                    }
                    else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                        traceLibre.path.lineTo(x, y);
                    }
                    else if (event.getAction() == MotionEvent.ACTION_UP) {
                        superPaintListe.add(traceLibre);
                        traceLibre = null;
                    }
                    break;
                }
                case 2 : {
                    if (event.getAction() == MotionEvent.ACTION_DOWN ) {
                        efface = new Efface(couleurBackground, progressChoisi);
                        efface.path.moveTo(x, y);
                    }
                    else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                        efface.path.lineTo(x, y);
                    }
                    else if (event.getAction() == MotionEvent.ACTION_UP) {
                        superPaintListe.add(efface);
                        efface = null;
                    }
                    break;
                }
                case 3 : {







                    break;
                }







            }



//                 if (event.getAction() == MotionEvent.ACTION_DOWN ) {
//                     rectangle = new Oval(couleurCourante, progressChoisi);
//                     rectangle.startP(x, y);
//                 }
//                 if (event.getAction() == MotionEvent.ACTION_MOVE) {
//                     rectangle.endP(x, y);
//                     sd.invalidate();
//                 }
//                 if (event.getAction() == MotionEvent.ACTION_UP) {
//                     rectangle.endP(x, y);
////                     dessinRectangle.add(rectangle);
//                     sd.invalidate();
//                 }

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

//                if (traceLibre != null) {
//                    traceLibre.setCouleur(couleurCourante);
//                    traceLibre.setEpaisseur(progressChoisi);
//                }
            }


            if (source == imgTraceLibre) {
                option = 1;
            }

            else if (source == imgEfface) {
                option = 2;
            }

            else if (source == imgEpaisseur) {
//                dialog.seekBar.ge t -- A quoi ca sert deja ?
                LargeurTraitDialogue dialog = new LargeurTraitDialogue(MainActivity.this);
                dialog.show();
            }

            else if (source == imgRemplir) {
                couleurBackground = couleurCourante;
                sd.setBackgroundColor(couleurBackground);
            }

            else if (source == imgPipette) {
            }

            else if (source == imgRectangle) {
            }
            else if (source == imgCercle) {
            }
            else if (source == imgTriangle) {

            }


        }
    }
}