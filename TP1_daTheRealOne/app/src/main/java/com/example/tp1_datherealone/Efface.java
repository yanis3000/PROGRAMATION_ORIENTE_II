package com.example.tp1_datherealone;

import android.content.Context;
import android.graphics.Color;
import android.view.MotionEvent;

public class Efface extends SuperPaint {
    private MainActivity mainActivity;

    public Efface(Context context) {
        super(context);
        MainActivity mainActivity;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        setCouleur(mainActivity.couleurBackground);

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