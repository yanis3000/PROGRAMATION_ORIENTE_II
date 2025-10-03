package com.example.tp1_datherealone;

import android.graphics.Canvas;
import android.graphics.Color;

public class Efface extends SuperPaint {

    MainActivity parent;

    public Efface(int progress) {
        super(Color.WHITE, progress);
    }

    @Override
     void dessiner(Canvas canvas) {
        crayon.setColor(parent.couleurBackground);
        canvas.drawPath(path, crayon);
    }
}