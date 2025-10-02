package com.example.tp1_datherealone;

import android.graphics.Canvas;

public class Efface extends SuperPaint {

    public Efface(int couleur, int progress) {
        super(couleur, progress);
    }

    @Override
     void dessiner(Canvas canvas) {
        canvas.drawPath(path, crayon);
    }
}