package com.example.tp1_datherealone;

import android.graphics.Canvas;

public class Rectangle extends SuperPaint {

    float startX;
    float startY;
    float endX;
    float endY;

    public Rectangle(int couleur, int progress) {
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
        canvas.drawRect(startX, startY, endX, endY , crayon);
    }



}