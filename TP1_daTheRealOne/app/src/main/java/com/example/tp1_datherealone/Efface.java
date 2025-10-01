package com.example.tp1_datherealone;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.MotionEvent;

public class Efface extends SuperPaint {

    public Efface(int couleur, int progress) {
        super(couleur, progress);
    }

    protected void dessiner(Canvas canvas) {
        canvas.drawPath(path, crayon);
    }

}