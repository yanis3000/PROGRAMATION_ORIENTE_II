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


    // Pour commencer avec le dessin libre, de couleur noir et une taille de 15 sur un background blanc
    private int option = 1;
    public int progressChoisi = 15;
    private int couleurCourante = Color.BLACK;
    public static int couleurBackground = Color.WHITE;
    // Pour le triangle, on doit faire deux lignes composés chacun de deux points
    private Point depart1, ligne1;
    private Point depart2, ligne2;
    //  Pour savoir quelle est la ligne qui est dessiné présentement dans le triangle
    private int firstLine = 0;
    // On definit les widgets
    SurfaceDessin sd;
    ConstraintLayout parent;
    TraceLibre traceLibre;
    Efface efface;
    Rectangle rectangle;
    Oval cercle;
    Triangle triangle;
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

    // Liste contenant tous les elements de Paint (Les sous classes de la classe SuperPaint)
    private ArrayList<SuperPaint> superPaintListe = new ArrayList<SuperPaint>();


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

        // Definir la surface de dessin
        parent = findViewById(R.id.parent);
        sd = new SurfaceDessin(this);
        sd.setLayoutParams(new LinearLayout.LayoutParams(-1, -1));
        sd.setBackgroundColor(couleurBackground);
        parent.addView(sd);


        chipGroup = findViewById(R.id.ChipGroup); // On lie l'id du ChipGroup a son widget
        parent.setBackgroundColor(couleurBackground); // La couleur changera dès qu'un chip différent sera selectionne

        // On crée nos deux écouteurs, un pour la surface de dessin et l'autre pour les boutons et les chips
        EcouteurSurfaceTrace es = new EcouteurSurfaceTrace();
        Ecouteur ec = new Ecouteur();

        // Pour chaque chip du chipGroup, on attribue un écouteur
        for (int i = 0; i < chipGroup.getChildCount(); i++) {
            (chipGroup.getChildAt(i)).setOnClickListener(ec);
        }

        // On assigne les ids aux widgets
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

        // on attribue des ecouteurs a chaque bouton
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

        // Parametre de base pour le crayon et creation d'un nouveau Path
        public SurfaceDessin(Context context) {
            super(context);
            crayon = new Paint(Paint.ANTI_ALIAS_FLAG);
            crayon.setStyle(Paint.Style.STROKE);
            crayon.setStrokeWidth(progressChoisi);
            crayon.setColor(couleurCourante);
            path = new Path();
        }

        // fonction bitmap du prof
        public Bitmap getBitmapImage() {
            this.buildDrawingCache();
            bitmapImage = Bitmap.createBitmap(this.getDrawingCache());
            this.destroyDrawingCache();
            return bitmapImage;
        }

        @Override
        protected void onDraw(@NonNull Canvas canvas) {
            super.onDraw(canvas);

            // On met tous sur une liste pour qu'on puisse avoir tous les traces et parametres de l'utilisateur
            // vu que toutes les fonctions sont des sous-classes de la fonction superpaint, ils font tous partis de la meme liste

            for (SuperPaint superPaint : superPaintListe) {
                superPaint.dessiner(canvas);
            }

            // Verification pour eviter l'erreur

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
            // Pour avoir le mouvement de l'utilisateur sur l'écran
            float x = event.getX();
            float y = event.getY();

            // TraceLibre
            switch (option) {
                case 1 : {
                    if (event.getAction() == MotionEvent.ACTION_DOWN ) {
                        // instancie un objet traceLibre qui aura une couleur et une epaisseur en fonction des parametres actuels
                        traceLibre = new TraceLibre(couleurCourante, progressChoisi);
                        traceLibre.path.moveTo(x, y);
                    }
                    else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                        traceLibre.path.lineTo(x, y);
                    }
                    else if (event.getAction() == MotionEvent.ACTION_UP) {
                        // on ajoute a la superclasse SuperPaint pour qu'elle reste sur la surface de dessin
                        superPaintListe.add(traceLibre);
                        traceLibre = null; // Evite de faire changer le couleur trait passé
                    }
                    sd.invalidate();
                    break;
                }
                // Efface
                case 2 : {
                    if (event.getAction() == MotionEvent.ACTION_DOWN ) {
                        efface = new Efface(progressChoisi); // meme fonctionnement que pour le trace mais son la couleur
                        efface.path.moveTo(x, y);
                    }
                    else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                        efface.path.lineTo(x, y);
                    }
                    else if (event.getAction() == MotionEvent.ACTION_UP) {
                        superPaintListe.add(efface); // on ajoute l'efface a la liste
                        efface = null; //
                    }
                    sd.invalidate();

                    break;
                }
                // Bitmap
                case 3 : {
                    Bitmap bmp = sd.getBitmapImage(); // fonction du prof
                    couleurCourante = bmp.getPixel((int)event.getX() , (int)event.getY());
                    break;
                }

                //Rectangle
                case 4 : {
                    if (event.getAction() == MotionEvent.ACTION_DOWN ) {
                        // lorsqu'on clique sur la surface on instancie un nouveau rectangle
                        rectangle = new Rectangle(couleurCourante, progressChoisi);
                        rectangle.startP(x, y);
                    }
                    if (event.getAction() == MotionEvent.ACTION_MOVE) {
                        // pour redimensionner le rectangle, on reste appuye sur l'ecran
                        rectangle.endP(x, y);
                        sd.invalidate(); // on montre en temps reel a quoi ressemble le rectangle
                    }
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        rectangle.endP(x, y); // on confirme la taille du
                        superPaintListe.add(rectangle);
                        sd.invalidate();
                    }
                    break;
                }
                // Cercle : meme fonctionnement que pour le rectangle
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
                // Triangle
                case 6 : {
                    // Si le dernier triangle, le firstLine est à 2
                    // On souhaite le rénitialiser a 0, pour en refaire un autre
                    if (firstLine == 2) {
                        depart1 = null; // ON ENLEVE TOUT
                        depart2 = null;
                        ligne1 = null;
                        ligne2 = null;
                        triangle = null;
                        firstLine = 0; // on le rénitialise à 0
                    }

                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        if (firstLine == 0) { // si aucun trait n'a ete fait  préalablement
                            triangle = new Triangle(couleurCourante, progressChoisi);
                            // Les points ne prennent pas les float donc on transtype
                            depart1 = new Point((int) x, (int)y);
                            // on assigne le point de depart au triangle
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
                            // on assigne le point de depart au triangle
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

            // Je n'etais pas en mesure de faire des switch cases sur la source donc j'ai fait un enchainement de if else if

            // Pour faire en sorte, que le chip fasse changer de couleur
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

        }
    }
}