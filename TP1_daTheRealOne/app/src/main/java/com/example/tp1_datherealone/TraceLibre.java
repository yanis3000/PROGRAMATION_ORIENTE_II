package com.example.tp1_datherealone;

import android.graphics.Canvas;

import androidx.annotation.NonNull;

public class TraceLibre extends SuperPaint {

    public TraceLibre(int couleur, int progress) {
        super(couleur, progress);
    }

    public void dessiner(Canvas canvas) {
        canvas.drawPath(path, crayon);
    }

    // setStrokeJoin(Join.ROUND)
    // setStrokeCap(Cap.ROUND)



}