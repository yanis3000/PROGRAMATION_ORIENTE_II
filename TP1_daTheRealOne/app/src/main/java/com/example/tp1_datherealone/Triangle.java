package com.example.tp1_datherealone;

import android.graphics.Canvas;

public class Triangle extends SuperPaint {

    Canvas premierSeg, deuxiemeSeg;

    public Triangle(int couleur, int progress) {
        super(couleur, progress);
    }

    public void dessiner(Canvas canvas) {
        canvas.drawPath(path, crayon);
        canvas.drawPath(path, crayon);
    }


}