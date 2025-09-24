package com.example.tp1_datherealone;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.MotionEvent;

public class TraceLibre extends SuperPaint {

    public TraceLibre(int couleur) {
        super(couleur);
    }

    protected void dessiner(Canvas canvas) {
        canvas.drawPath(path, crayon);
    }



}