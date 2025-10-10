package com.example.tp1_datherealone;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Environment;
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

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    SurfaceDessin sd;
    ConstraintLayout parent;
    private int option = 1;
    public int progressChoisi = 15;
    private int couleurCourante = Color.BLACK;
    public static int couleurBackground = Color.WHITE;
    private Point depart1, ligne1;
    private Point depart2, ligne2;
    private int firstLine = 0;
    LargeurTraitDialogue dialogue;
    TraceLibre traceLibre;
    Efface efface;
    Rectangle rectangle;
    Oval cercle;
    Triangle triangle;
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
    ImageView imgEnregistrer;
    ImageView imgRedo;
    ImageView imgUndo;
    ChipGroup chipGroup;
    Bitmap bitmapImage;

    private ArrayList<SuperPaint> superPaintListe = new ArrayList<SuperPaint>();
    private ArrayList<SuperPaint> superListe = new ArrayList<SuperPaint>();
    private int indexListe = 0; // on pourra essayer d'utiliser un splice
    private int placeListe = 0;

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
            (chipGroup.getChildAt(i)).setOnClickListener(new Ecouteur());
        }

        imgTraceLibre = findViewById(R.id.traceLibre);
        imgEfface = findViewById(R.id.efface);
        imgEpaisseur = findViewById(R.id.epaisseur);
        imgRemplir = findViewById(R.id.remplir);
        imgPipette = findViewById(R.id.pipette);
        imgRectangle = findViewById(R.id.rectangle);
        imgCercle = findViewById(R.id.cercle);
        imgTriangle = findViewById(R.id.triangle);
        imgEnregistrer = findViewById(R.id.enregistre);
        imgRedo = findViewById(R.id.redo);
        imgUndo = findViewById(R.id.undo);

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
        imgEnregistrer.setOnClickListener(ec);
        imgRedo.setOnClickListener(ec);
        imgUndo.setOnClickListener(ec);

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
//            superListe.addAll(superPaintListe);

            for (int i = 0; i < superPaintListe.size(); i++) {

                placeListe = i + indexListe;
                if ((indexListe + superPaintListe.size()) < superPaintListe.size()) placeListe = superPaintListe.size();
                else if (placeListe < 0) placeListe = 0;

                superListe.add(superPaintListe.get(placeListe));
            }

            // On met tous sur une liste pour qu'on puisse avoir tous les elems

            for (SuperPaint superPaint : superListe) {
                superPaint.dessiner(canvas);
            }


            if (traceLibre != null) {
                traceLibre.dessiner(canvas);
            }

            if (efface != null) {
                efface.dessiner(canvas);
            }

            if (rectangle != null) {
                rectangle.dessiner(canvas);
            }

            if (cercle != null) {
                cercle.dessiner(canvas);
            }

            if (triangle != null) {
                triangle.dessiner(canvas);
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
                    sd.invalidate();
                    break;
                }
                case 2 : {
                    if (event.getAction() == MotionEvent.ACTION_DOWN ) {
                        efface = new Efface(progressChoisi);
                        efface.path.moveTo(x, y);
                    }
                    else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                        efface.path.lineTo(x, y);
                    }
                    else if (event.getAction() == MotionEvent.ACTION_UP) {
                        superPaintListe.add(efface);
                        efface = null;
                    }
                    sd.invalidate();

                    break;
                }
                case 3 : {
                    Bitmap bmp = sd.getBitmapImage();
                    couleurCourante = bmp.getPixel((int)event.getX() , (int)event.getY());
                    break;
                }

                case 4 : {
                    if (event.getAction() == MotionEvent.ACTION_DOWN ) {
                        rectangle = new Rectangle(couleurCourante, progressChoisi);
                        rectangle.startP(x, y);
                    }
                    if (event.getAction() == MotionEvent.ACTION_MOVE) {
                        rectangle.endP(x, y);
                        sd.invalidate();
                    }
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        rectangle.endP(x, y);
                        superPaintListe.add(rectangle);
                        sd.invalidate();
                    }
                    break;
                }

                case 5 : {
                    if (event.getAction() == MotionEvent.ACTION_DOWN ) {
                        cercle = new Oval(couleurCourante, progressChoisi);
                        cercle.startP(x, y);
                    }
                    if (event.getAction() == MotionEvent.ACTION_MOVE) {
                        cercle.endP(x, y);
                        sd.invalidate();
                    }
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        cercle.endP(x, y);
                        superPaintListe.add(cercle);
                        sd.invalidate();
                    }
                    break;
                }

                case 6 : {
                    if (firstLine == 2) {
                        depart1 = null;
                        depart2 = null;
                        ligne1 = null;
                        ligne2 = null;
                        triangle = null;
                        firstLine = 0;
                    }

                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        if (firstLine == 0 || firstLine == 2) {
                            triangle = new Triangle(couleurCourante, progressChoisi);
                            depart1 = new Point((int) x, (int)y);
                            triangle.depart1 = depart1;
                            firstLine = 0;
                        }
                        else if (firstLine == 1) {
                            depart2 = ligne1;
                            triangle.depart2 = depart2;
                        }
                    }
                    if (event.getAction() == MotionEvent.ACTION_MOVE) {
                        if (firstLine == 0) {
                            ligne1 = new Point((int) x, (int) y);
                            triangle.ligne1 = ligne1;
                        } else if (firstLine == 1) {
                            ligne2 = new Point((int) x, (int) y);
                            triangle.ligne2 = ligne2;
                        }
                        sd.invalidate();
                    }

                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        if (firstLine == 0 || firstLine == 1) {
                            firstLine++;
                        }
                        superPaintListe.add(triangle);
                        sd.invalidate();
                    }

                    break;
                }
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
            }


            if (source == imgTraceLibre) {
                option = 1;
            }

            else if (source == imgEfface) {
                option = 2;
            }

            else if (source == imgEpaisseur) {
                LargeurTraitDialogue dialog = new LargeurTraitDialogue(MainActivity.this);
                dialog.show();
            }

            else if (source == imgRemplir) {
                couleurBackground = couleurCourante;
                sd.setBackgroundColor(couleurBackground);
            }

            else if (source == imgPipette) {
                option = 3;
            }

            else if (source == imgRectangle) {
                option = 4;
            }
            else if (source == imgCercle) {
                option = 5;
            }
            else if (source == imgTriangle) {
                option = 6;
            }
            else if (source == imgRedo) {
                indexListe++;
            }

            else if (source == imgUndo) {
                indexListe--;
            }

            else if (source == imgEnregistrer) {

                // Source : https://www.geeksforgeeks.org/android/how-to-take-screenshot-programmatically-in-android/

                Date now = new Date();
                android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);

                try {
                    // image naming and path  to include sd card  appending name you choose for file
                    String mPath = Environment.getExternalStorageDirectory().toString() + "/" + now + ".jpg";

                    // create bitmap screen capture
                    View v1 = getWindow().getDecorView().getRootView();
                    v1.setDrawingCacheEnabled(true);
                    Bitmap bitmap = Bitmap.createBitmap(v1.getDrawingCache());
                    v1.setDrawingCacheEnabled(false);

                    File imageFile = new File(mPath);

                    FileOutputStream outputStream = new FileOutputStream(imageFile);
                    int quality = 100;
                    bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
                    outputStream.flush();
                    outputStream.close();

                } catch (Throwable e) {
                    // Several error may come out with file handling or DOM
                    e.printStackTrace();
                }
            }
        }
    }
}