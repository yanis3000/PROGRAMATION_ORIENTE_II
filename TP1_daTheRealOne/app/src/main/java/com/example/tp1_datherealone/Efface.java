package com.example.tp1_datherealone;

import android.graphics.Canvas;

public class Efface extends SuperPaint {

    MainActivity parent;

    public Efface(int couleur, int progress) {
        super(couleur, progress);
    }

    @Override
     void dessiner(Canvas canvas) {
        crayon.setColor(parent.couleurBackground);
        canvas.drawPath(path, crayon);
    }
}