package com.example.tp1_datherealone;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

public class SuperPaint {

    protected Paint crayon;
    protected Path path;
    protected float epaisseur = 15;
    protected int couleur = Color.BLACK;

    public SuperPaint(int couleur, int epaisseur) {
        this.couleur = couleur;
        this.epaisseur = epaisseur;

        crayon = new Paint(Paint.ANTI_ALIAS_FLAG);
        crayon.setStyle(Paint.Style.STROKE);

        crayon.setColor(couleur);
        crayon.setStrokeWidth(epaisseur);

        path = new Path();
    }

    public void setCouleur(int newColor) {
        this.couleur = newColor;
        crayon.setColor(newColor);
    }

    public void setEpaisseur(float newWidth) {
        epaisseur = newWidth;
        crayon.setStrokeWidth(epaisseur);
    }

    void dessiner(Canvas canvas) {
    }
}