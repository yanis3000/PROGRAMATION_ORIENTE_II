package com.example.tp1_datherealone;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

public class SuperPaint { // La super classe de tous les elements s'appliquant sur la surface de dessin

    protected Paint crayon; // Chaque forme possede son propre crayon, chemin (path), épaisseur et couleur
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

    void dessiner(Canvas canvas) { // redifinit les sous-classes pour dessiner leur forme sur le canvas
    }
}