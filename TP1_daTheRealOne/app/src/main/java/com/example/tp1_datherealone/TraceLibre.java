package com.example.tp1_datherealone;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;

public class TraceLibre extends SuperPaint {

    public TraceLibre(MainActivity context) {
        super(context);
    }

//    public TraceLibre(Context context, int couleur, float epaisseur) {
//        super(context);
//        setCouleur(couleur);
//        setEpaisseur(epaisseur);
//    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        if (event.getAction() == MotionEvent.ACTION_DOWN ) {
            path.moveTo(x, y);
        }
        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            path.lineTo(x, y);
        }

        invalidate();

        return true;
    }
}