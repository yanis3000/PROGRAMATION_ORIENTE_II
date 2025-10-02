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
    ChipGroup chipGroup;
    Bitmap bitmapImage;

    ArrayList<SuperPaint> superPaintListe = new ArrayList<SuperPaint>();
//    ArrayList<TraceLibre> dessinTrace = new ArrayList<TraceLibre>();
//    ArrayList<Oval> dessinRectangle = new ArrayList<Oval>();

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

        EcouteurSurfaceTrace es = new EcouteurSurfaceTrace();
        Ecouteur ec = new Ecouteur();

        imgTraceLibre.setOnClickListener(ec);
        imgEfface.setOnClickListener(ec);
        imgEpaisseur.setOnClickListener(ec);
        imgRemplir.setOnClickListener(ec);
        imgPipette.setOnClickListener(ec);
        imgRectangle.setOnClickListener(ec);
        imgCercle.setOnClickListener(ec);

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

            for (SuperPaint superPaint : superPaintListe) {
                superPaint.dessiner(canvas);
            }

//            for (Oval rectangle : dessinRectangle) {
//                rectangle.dessiner(canvas);
//            }
//
//            for (TraceLibre traceLibre : dessinTrace) {
//                traceLibre.dessiner(canvas);
//            }

            //                     for (TraceLibre traceLibre : dessinTrace) {
//                         if (traceLibre.couleur != couleurChoisi) {
//                             traceLibre.couleur = couleurChoisi;
//                         }
//                     }

            // Faire de faire qu'une seule liste pour tous les elements
            // faut faire le draw circle

            if (rectangle != null) {
                rectangle.dessiner(canvas);
            }

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

             couleurChoisi = dessineIsTrue ? couleurCourante : couleurBackground;

             if (dessineOuForme == 1) {
                 if (event.getAction() == MotionEvent.ACTION_DOWN ) {
                     traceLibre = new TraceLibre(couleurChoisi, progressChoisi);
                     traceLibre.path.moveTo(x, y);
                 }
                 else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                     traceLibre.path.lineTo(x, y);
                 }
                 else if (event.getAction() == MotionEvent.ACTION_UP) {
                     superPaintListe.add(traceLibre);

                     traceLibre = null;
                 }
                 sd.invalidate();
             }

             if (dessineOuForme == 2) {
                 if (event.getAction() == MotionEvent.ACTION_DOWN ) {
                     rectangle = new Oval(couleurCourante, progressChoisi);
                     rectangle.startP(x, y);
                 }
                 if (event.getAction() == MotionEvent.ACTION_MOVE) {
                     rectangle.endP(x, y);
                     sd.invalidate();
                 }
                 if (event.getAction() == MotionEvent.ACTION_UP) {
                     rectangle.endP(x, y);
//                     dessinRectangle.add(rectangle);
                     sd.invalidate();
                 }
             }

             if (dessineOuForme == 3) {
//                 if (event.getAction() == MotionEvent.ACTION_DOWN) {
//                     depart = new Point((int)event.getX(), (int)event.getY());
//                     fin = null;
//                     sd.invalidate(); // redessiner effaec et appele la foonc  on draw
//                 }
//
//                 if (event.getAction() == MotionEvent.ACTION_MOVE) {
//                     milieu = new Point((int)event.getX(), (int)event.getY());
//                     sd.invalidate(); // redessiner effaec et appele la foonc  on draw
//                 }
//
//                 if (event.getAction() == MotionEvent.ACTION_UP) {
//                     fin = new Point((int)event.getX(), (int)event.getY());
//                     sd.invalidate(); // redessiner effaec et appele la foonc  on draw
//                 }
             }

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
                    traceLibre.setEpaisseur(progressChoisi);
                }
            }

            if (source == imgTraceLibre) {
                dessineOuForme = 1;
                dessineIsTrue = true;
            }

            if (source == imgEfface) {
                dessineOuForme = 1;
                dessineIsTrue = false;
            }

            if (source == imgEpaisseur) {
                LargeurTraitDialogue dialog = new LargeurTraitDialogue(MainActivity.this);
//                dialog.seekBar.ge t
                dialog.show();
            }

            if (source == imgRemplir) {
                couleurBackground = couleurCourante;
                sd.setBackgroundColor(couleurBackground);
            }

            if (source == imgPipette) {
                Bitmap bmp = sd.getBitmapImage();
                couleurCourante = bmp.getPixel((int)event.getX() , (int)event.getY());
                // A voir pourquoi ca ne fonctionne pas ?
            }

            if (source == imgRectangle) {
                cercleOuRect = 1;
                dessineOuForme = 2;
            }

            if (source == imgCercle) {
                cercleOuRect = 2;
                dessineOuForme = 2;
            }


        }
    }
}