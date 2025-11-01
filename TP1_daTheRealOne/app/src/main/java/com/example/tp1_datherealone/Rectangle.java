package com.example.tp1_datherealone;

import android.graphics.Canvas;

public class Rectangle extends SuperPaint {

    // les valeurs ci-dessous servent à definir les endroits du debut et de la fin
    float startX;
    float startY;
    float endX;
    float endY;

    public Rectangle(int couleur, int progress) {
        super(couleur, progress);
    }

    public void startP(float x, float y) { // definit le point de départ du rectangle
        startX = x;
        startY = y;
    }

    public void endP(float x, float y) { // definit le point d'arrivée du rectangle
        endX = x;
        endY = y;
    }

    public void dessiner(Canvas canvas) {
        canvas.drawRect(startX, startY, endX, endY , crayon);
    } //Dessine le rectangle sur le canvas



}