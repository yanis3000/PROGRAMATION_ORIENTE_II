package com.example.tp1_datherealone;

import android.graphics.Canvas;
import android.graphics.Point;

public class Triangle extends SuperPaint {

    Point depart1, ligne1;
    Point depart2, ligne2;
    public Triangle(int couleur, int progress) {
        super(couleur, progress);
    }

    void dessiner(Canvas canvas) {
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