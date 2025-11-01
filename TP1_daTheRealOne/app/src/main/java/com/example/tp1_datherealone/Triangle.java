package com.example.tp1_datherealone;

import android.graphics.Canvas;
import android.graphics.Point;

public class Triangle extends SuperPaint {

    // On utilisera deux lignes collées qui seront reliees par une ligne tracée automatiquement pour faire un triangle
    // Similaire a ce qu'on a fait sur l'annexe7
    // On devra faire deux drawLine pour arriver a nos fins
    Point depart1, ligne1; // Pour les drawLines
    Point depart2, ligne2;
    public Triangle(int couleur, int progress) { // Constructeur
        super(couleur, progress);
    }

    void dessiner(Canvas canvas) {
        if (ligne1 != null) { // Validation : s'il n'existe pas, on ne l'affiche pas
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