package com.example.tp1_datherealone;

import android.graphics.Canvas;

public class Oval extends SuperPaint {

    float startX;
    float startY;
    float endX;
    float endY;

    public Oval(int couleur, int progress) {
        super(couleur, progress);
    }

    public void startP(float x, float y) {
        startX = x;
        startY = y;
    }

    public void endP(float x, float y) {
        endX = x;
        endY = y;
    }

    public void dessiner(Canvas canvas) {
        canvas.drawOval(startX, startY, endX, endY , crayon);
    }


}