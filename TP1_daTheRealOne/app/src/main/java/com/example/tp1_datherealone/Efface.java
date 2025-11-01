package com.example.tp1_datherealone;

import android.graphics.Canvas;
import android.graphics.Color;

public class Efface extends SuperPaint {

    MainActivity parent; // référence vers l'activité principale pour accéder à la couleur du fond

    public Efface(MainActivity parent, int progress) {
        super(Color.WHITE, progress); // couleur blanche par defaut
        this.parent = parent;
    }

    @Override
     void dessiner(Canvas canvas) {
        crayon.setColor(MainActivity.couleurBackground); // la couleur est mise à jour dynamiquement puisque le crayon récupera la couleur de fond
        canvas.drawPath(path, crayon);
    }
}