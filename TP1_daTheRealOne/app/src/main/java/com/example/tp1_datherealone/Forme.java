package com.example.tp1_datherealone;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.MotionEvent;

public class Forme extends SuperPaint {

    float startX;
    float startY;
    float endX;
    float endY;

    public Forme(int couleur, int progress) {
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

    public void dessinerCercle(Canvas canvas) {
        canvas.drawOval(startX, startY, endX, endY , crayon);
    }



}